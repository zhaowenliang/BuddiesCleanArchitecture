package cc.buddies.cleanarch.data.service;

import java.util.List;

import cc.buddies.cleanarch.data.http.model.ResponseModel;
import cc.buddies.cleanarch.domain.model.NeteaseNewsModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NeteaseNewsService {

    @POST("https://api.apiopen.top/getWangYiNews")
    @FormUrlEncoded
    Single<ResponseModel<List<NeteaseNewsModel>>> news(@Field("page") int page, @Field("count") int count);

}
