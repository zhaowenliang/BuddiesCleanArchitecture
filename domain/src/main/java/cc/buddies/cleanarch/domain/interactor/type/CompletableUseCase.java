package cc.buddies.cleanarch.domain.interactor.type;

import io.reactivex.rxjava3.core.Completable;

public interface CompletableUseCase {
    Completable execute();
}
