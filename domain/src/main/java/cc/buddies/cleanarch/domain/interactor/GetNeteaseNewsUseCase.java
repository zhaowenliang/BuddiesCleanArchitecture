package cc.buddies.cleanarch.domain.interactor;

import java.util.List;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.NeteaseNewsModel;
import cc.buddies.cleanarch.domain.repository.NeteaseNewsRepository;
import cc.buddies.cleanarch.domain.request.NeteaseNewsParams;
import io.reactivex.rxjava3.core.Single;

public class GetNeteaseNewsUseCase implements SingleUseCaseWithParameter<NeteaseNewsParams, List<NeteaseNewsModel>> {

    private NeteaseNewsRepository neteaseNewsRepository;

    public GetNeteaseNewsUseCase(NeteaseNewsRepository neteaseNewsRepository) {
        this.neteaseNewsRepository = neteaseNewsRepository;
    }

    @Override
    public Single<List<NeteaseNewsModel>> execute(NeteaseNewsParams parameter) {
        return this.neteaseNewsRepository.news(parameter.getPage(), parameter.getCount());
    }

}
