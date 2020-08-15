package cc.buddies.cleanarch.domain.interactor;

import java.util.List;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.domain.repository.NewsRepository;
import io.reactivex.rxjava3.core.Single;

public class GetNewsUseCase implements SingleUseCaseWithParameter<String, List<NewsModel>> {

    private NewsRepository newsRepository;

    public GetNewsUseCase(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public Single<List<NewsModel>> execute(String parameter) {
        return newsRepository.news(parameter);
    }

}
