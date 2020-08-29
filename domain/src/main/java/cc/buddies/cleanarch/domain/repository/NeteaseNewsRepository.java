package cc.buddies.cleanarch.domain.repository;

import java.util.List;

import cc.buddies.cleanarch.domain.model.NeteaseNewsModel;
import io.reactivex.rxjava3.core.Single;

public interface NeteaseNewsRepository {

    int RESPONSE_SUCCESS_CODE = 200;

    /**
     * 网易新闻列表
     *
     * @param page  页面
     * @param count 数量
     * @return Single
     */
    Single<List<NeteaseNewsModel>> news(int page, int count);

}
