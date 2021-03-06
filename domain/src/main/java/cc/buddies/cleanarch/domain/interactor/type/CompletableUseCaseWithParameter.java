package cc.buddies.cleanarch.domain.interactor.type;

import io.reactivex.rxjava3.core.Completable;

public interface CompletableUseCaseWithParameter<P> {
    Completable execute(P parameter);
}
