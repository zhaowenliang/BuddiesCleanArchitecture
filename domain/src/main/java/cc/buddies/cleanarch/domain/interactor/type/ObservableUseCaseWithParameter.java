package cc.buddies.cleanarch.domain.interactor.type;

import io.reactivex.rxjava3.core.Observable;

public interface ObservableUseCaseWithParameter<P, R> {
    Observable<R> execute(P parameter);
}
