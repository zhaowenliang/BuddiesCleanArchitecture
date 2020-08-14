package cc.buddies.cleanarch.data.service;

import cc.buddies.cleanarch.data.http.model.PageModel;
import cc.buddies.cleanarch.data.http.model.ResponseModel;
import cc.buddies.cleanarch.domain.model.ArticleModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleService {

    @GET("https://www.wanandroid.com/article/list/{page}/json")
    Single<ResponseModel<PageModel<ArticleModel>>> articles(@Path("page") int page);

}
