package cc.buddies.cleanarch.domain.repository;

import java.util.List;

import cc.buddies.cleanarch.domain.model.PostModel;
import cc.buddies.cleanarch.domain.request.ReleasePostParams;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface PostRepository {

    /**
     * 帖子列表
     *
     * @param page 页码
     * @return Single
     */
    Single<List<PostModel>> posts(int page);

    /**
     * 发布帖子
     *
     * @param params 参数
     * @return Single
     */
    Single<PostModel> releasePost(ReleasePostParams params);

    /**
     * 帖子点赞
     *
     * @param postId      帖子id
     * @param addOrCancel 点赞或取消
     * @return Completable
     */
    Completable praisePost(long postId, long userId, boolean addOrCancel);

}
