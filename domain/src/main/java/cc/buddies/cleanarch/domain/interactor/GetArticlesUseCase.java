package cc.buddies.cleanarch.domain.interactor;

import java.util.List;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.ArticleModel;
import cc.buddies.cleanarch.domain.repository.ArticleRepository;
import io.reactivex.rxjava3.core.Single;

public class GetArticlesUseCase implements SingleUseCaseWithParameter<Integer, List<ArticleModel>> {

    private ArticleRepository articleRepository;

    public GetArticlesUseCase(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Single<List<ArticleModel>> execute(Integer parameter) {
        return articleRepository.articles(parameter);
    }
}
