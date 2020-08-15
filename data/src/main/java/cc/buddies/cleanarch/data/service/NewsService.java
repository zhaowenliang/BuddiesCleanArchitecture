package cc.buddies.cleanarch.data.service;

import java.util.List;

import cc.buddies.cleanarch.data.http.model.ResponseModel;
import cc.buddies.cleanarch.domain.model.NewsModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("https://v.juhe.cn/toutiao/index")
    Single<ResponseModel<List<NewsModel>>> news(@Query("key") String key, @Query("type") String type);

}
