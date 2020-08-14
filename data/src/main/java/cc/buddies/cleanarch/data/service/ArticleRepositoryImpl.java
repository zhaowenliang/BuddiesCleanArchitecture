package cc.buddies.cleanarch.data.service;

import java.util.List;

import cc.buddies.cleanarch.data.http.HttpManager;
import cc.buddies.cleanarch.data.http.function.SingleResponseModelFunction;
import cc.buddies.cleanarch.data.http.model.PageModel;
import cc.buddies.cleanarch.domain.model.ArticleModel;
import cc.buddies.cleanarch.domain.repository.ArticleRepository;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;

public class ArticleRepositoryImpl implements ArticleRepository {

    private ArticleService articleService;

    public ArticleRepositoryImpl() {
        this.articleService = HttpManager.getInstance().build().create(ArticleService.class);
    }

    @Override
    public Single<List<ArticleModel>> articles(int page) {
        return Single.defer(() -> articleService.articles(page)
                .flatMap(new SingleResponseModelFunction<>())
                .flatMap((Function<PageModel<ArticleModel>, SingleSource<List<ArticleModel>>>) pageModel ->
                        Single.just(pageModel.getDatas())));
    }

}
