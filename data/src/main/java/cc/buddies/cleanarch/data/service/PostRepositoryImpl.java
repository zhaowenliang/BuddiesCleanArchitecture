package cc.buddies.cleanarch.data.service;

import androidx.room.rxjava3.EmptyResultSetException;

import java.util.List;

import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.dao.PostPraiseDao;
import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.entity.PostPraiseEntity;
import cc.buddies.cleanarch.data.db.relation.PostWithAuthor;
import cc.buddies.cleanarch.data.exception.ResponseException;
import cc.buddies.cleanarch.data.http.ErrorEnum;
import cc.buddies.cleanarch.domain.model.PostModel;
import cc.buddies.cleanarch.domain.repository.PostRepository;
import cc.buddies.cleanarch.domain.request.ReleasePostParams;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostRepositoryImpl implements PostRepository {

    private PostDao mPostDao;
    private PostPraiseDao mPostPraiseDao;

    public PostRepositoryImpl(PostDao postDao, PostPraiseDao postPraiseDao) {
        this.mPostDao = postDao;
        this.mPostPraiseDao = postPraiseDao;
    }

    // 转化数据实体为响应数据模型
    private PostModel transEntityToModel(PostWithAuthor postWithUser) {
        PostEntity post = postWithUser.post;
        if (post == null) return null;

        String nickname = postWithUser.author.nickname;
        String avatar = postWithUser.author.avatar;

        PostModel model = new PostModel();

        model.setId(post.id);
        model.setUserId(post.userId);
        model.setDescription(post.description);
        model.setImages(post.images);
        model.setTime(post.createDate);
        model.setGoodCount(post.goodCount);
        model.setCommentCount(post.commentCount);
        model.setShareCount(post.shareCount);

        model.setAvatar(avatar);
        model.setNickname(nickname);

        return model;
    }

    @Override
    public Single<List<PostModel>> posts(int page) {
        return null;
    }

    @Override
    public Single<PostModel> releasePost(ReleasePostParams params) {
        PostEntity postEntity = new PostEntity();
        postEntity.userId = params.getUserId();
        postEntity.createDate = System.currentTimeMillis();
        postEntity.description = params.getDescription();
        postEntity.images = params.getImages();

        return mPostDao.insertPosts(postEntity)
                .observeOn(Schedulers.io())
                // 查询结果
                .flatMap((Function<List<Long>, SingleSource<PostWithAuthor>>) longs -> {
                    if (longs.isEmpty()) {
                        ErrorEnum errorEnum = ErrorEnum.POST_RELEASE_FAILED;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    } else {
                        return mPostDao.getPostWithAuthor(longs.get(0));
                    }
                })
                // 返回数据
                .map(this::transEntityToModel);
    }

    @Override
    public Completable praisePost(long postId, long userId, boolean addOrCancel) {
        // 点赞
        if (addOrCancel) {
            return mPostPraiseDao.getPostPraise(postId, userId)
                    .flatMap((Function<List<PostPraiseEntity>, SingleSource<Long>>) postPraiseEntities -> {
                        if (postPraiseEntities.isEmpty()) {
                            // 点赞
                            PostPraiseEntity userGoodPostEntity = new PostPraiseEntity();
                            userGoodPostEntity.postId = postId;
                            userGoodPostEntity.userId = userId;
                            return mPostPraiseDao.insertPostPraise(userGoodPostEntity);
                        } else {
                            // 赞已存在
                            ErrorEnum errorEnum = ErrorEnum.POST_PRAISE_ALREADY_EXISTS_FAILED;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        }
                    })
                    // 查询帖子信息，增加帖子点赞数
                    .flatMap((Function<Long, SingleSource<PostEntity>>) insertId -> {
                        if (insertId > 0) {
                            return mPostDao.getPost(postId);
                        } else {
                            ErrorEnum errorEnum = ErrorEnum.POST_ADD_PRAISE_FAILED;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        }
                    })
                    // 帖子不存在
                    .onErrorResumeNext(throwable -> {
                        if (throwable instanceof EmptyResultSetException) {
                            ErrorEnum errorEnum = ErrorEnum.POST_NOT_EXISTS;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        } else {
                            throw throwable;
                        }
                    })
                    // 增加帖子点赞数
                    .flatMap((Function<PostEntity, SingleSource<Integer>>) postEntity -> {
                        postEntity.goodCount++;
                        return mPostDao.updatePosts(postEntity);
                    })
                    // 完成
                    .flatMapCompletable(effectRowCount -> {
                        if (effectRowCount > 0) {
                            return Completable.complete();
                        } else {
                            ErrorEnum errorEnum = ErrorEnum.POST_ADD_PRAISE_FAILED;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        }
                    });
        }
        // 取消赞
        else {
            return mPostPraiseDao.getPostPraise(postId, userId)
                    .flatMap((Function<List<PostPraiseEntity>, SingleSource<Integer>>) postPraiseEntities -> {
                        if (postPraiseEntities.isEmpty()) {
                            // 赞不存在
                            ErrorEnum errorEnum = ErrorEnum.POST_PRAISE_NOT_EXISTS_FAILED;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        } else {
                            // 赞存在
                            PostPraiseEntity postPraiseEntity = postPraiseEntities.get(0);
                            return mPostPraiseDao.deletePostPraise(postPraiseEntity);
                        }
                    })
                    // 查询帖子信息，减少帖子点赞数
                    .flatMap((Function<Integer, SingleSource<PostEntity>>) effectRowCount -> {
                        if (effectRowCount > 0) {
                            return mPostDao.getPost(postId);
                        } else {
                            ErrorEnum errorEnum = ErrorEnum.POST_ADD_PRAISE_FAILED;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        }
                    })
                    // 帖子不存在
                    .onErrorResumeNext(throwable -> {
                        if (throwable instanceof EmptyResultSetException) {
                            ErrorEnum errorEnum = ErrorEnum.POST_NOT_EXISTS;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        } else {
                            throw throwable;
                        }
                    })
                    // 减少帖子点赞数
                    .flatMap((Function<PostEntity, SingleSource<Integer>>) postEntity -> {
                        postEntity.goodCount--;
                        return mPostDao.updatePosts(postEntity);
                    })
                    // 完成
                    .flatMapCompletable(effectRowCount -> {
                        if (effectRowCount > 0) {
                            return Completable.complete();
                        } else {
                            ErrorEnum errorEnum = ErrorEnum.POST_CANCEL_PRAISE_FAILED;
                            throw new ResponseException(errorEnum.getCode(), errorEnum.getMessage());
                        }
                    });
        }
    }
}
