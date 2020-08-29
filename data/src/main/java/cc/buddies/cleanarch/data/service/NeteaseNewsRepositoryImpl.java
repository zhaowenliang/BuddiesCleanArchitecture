package cc.buddies.cleanarch.data.service;

import java.util.List;

import cc.buddies.cleanarch.data.http.function.SingleResponseModelFunction;
import cc.buddies.cleanarch.domain.model.NeteaseNewsModel;
import cc.buddies.cleanarch.domain.repository.NeteaseNewsRepository;
import io.reactivex.rxjava3.core.Single;

public class NeteaseNewsRepositoryImpl implements NeteaseNewsRepository {

    private NeteaseNewsService neteaseNewsService;

    public NeteaseNewsRepositoryImpl(NeteaseNewsService neteaseNewsService) {
        this.neteaseNewsService = neteaseNewsService;
    }

    @Override
    public Single<List<NeteaseNewsModel>> news(int page, int count) {
        return Single.defer(() -> this.neteaseNewsService.news(page, count))
                .flatMap(new SingleResponseModelFunction<>(integer -> integer == RESPONSE_SUCCESS_CODE));
    }

}
