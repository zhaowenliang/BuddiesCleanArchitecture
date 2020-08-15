package cc.buddies.cleanarch.domain.repository;

import java.util.List;

import cc.buddies.cleanarch.domain.model.NewsModel;
import io.reactivex.rxjava3.core.Single;

public interface NewsRepository {

    /**
     * 新闻列表
     *
     * @param type 新闻类型
     * @return Single
     */
    Single<List<NewsModel>> news(String type);

}
