package cc.buddies.cleanarch.data.service;

import java.util.List;

import cc.buddies.cleanarch.data.http.function.SingleResponseModelFunction;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.domain.repository.NewsRepository;
import io.reactivex.rxjava3.core.Single;

public class NewsRepositoryImpl implements NewsRepository {

    private NewsService newsService;

    private String apiKey;

    public NewsRepositoryImpl(NewsService newsService, String apiKey) {
        this.newsService = newsService;
        this.apiKey = apiKey;
    }

    @Override
    public Single<List<NewsModel>> news(String type) {
        return Single.defer(() -> newsService.news(apiKey, type))
                .flatMap(new SingleResponseModelFunction<>());
    }

}
