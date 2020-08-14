package cc.buddies.cleanarch.domain.repository;

import java.util.List;

import cc.buddies.cleanarch.domain.model.ArticleModel;
import io.reactivex.rxjava3.core.Single;

public interface ArticleRepository {

    /**
     * 文章列表
     *
     * @param page 列表页码
     * @return Single
     */
    Single<List<ArticleModel>> articles(int page);

}
