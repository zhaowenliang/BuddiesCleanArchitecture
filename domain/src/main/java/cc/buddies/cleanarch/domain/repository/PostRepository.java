package cc.buddies.cleanarch.domain.repository;

import java.util.List;

import cc.buddies.cleanarch.domain.model.PostModel;
import cc.buddies.cleanarch.domain.request.ReleasePostParams;
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

}
