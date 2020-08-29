package cc.buddies.cleanarch.data.service;

import android.content.Context;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.List;

import cc.buddies.cleanarch.data.db.AppDatabase;
import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.entity.UserEntity;
import cc.buddies.cleanarch.data.db.relation.PostWithUser;
import cc.buddies.cleanarch.data.exception.ResponseException;
import cc.buddies.cleanarch.data.http.ErrorEnum;
import cc.buddies.cleanarch.domain.model.PostModel;
import cc.buddies.cleanarch.domain.repository.PostRepository;
import cc.buddies.cleanarch.domain.request.ReleasePostParams;
import cc.buddies.component.storage.provider.StorageContextProvider;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostRepositoryImpl implements PostRepository {

    private WeakReference<Context> contextWeakReference;

    public PostRepositoryImpl(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    @NonNull
    private PostDao getPostDao() {
        Context context = this.contextWeakReference != null
                ? this.contextWeakReference.get()
                : StorageContextProvider.getApplication().getApplicationContext();

        return AppDatabase.getInstance(context).postDao();
    }

    // 转化数据实体为响应数据模型
    private PostModel transEntityToModel(PostWithUser postWithUser) {
        PostEntity post = postWithUser.post;
        UserEntity user = postWithUser.user;

        if (post == null)
            return null;

        PostModel model = new PostModel();

        model.setId(post.id);
        model.setUserId(post.userId);
        model.setDescription(post.description);
        model.setImages(post.images);
        model.setTime(post.createDate);
        model.setGoodCount(post.goodCount);
        model.setCommentCount(post.commentCount);
        model.setShareCount(post.shareCount);

        model.setAvatar(user.avatar);
        model.setNickname(user.nickname);

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

        return getPostDao().insertPosts(postEntity)
                .observeOn(Schedulers.io())
                // 查询结果
                .flatMap((Function<List<Long>, SingleSource<PostWithUser>>) longs -> {
                    if (longs.isEmpty()) {
                        ErrorEnum errorEnum = ErrorEnum.POST_RELEASE_FAILED;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    } else {
                        return getPostDao().getPost(longs.get(0));
                    }
                })
                // 返回数据
                .map(this::transEntityToModel);
    }
}
