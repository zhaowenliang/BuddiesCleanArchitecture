package cc.buddies.cleanarch.domain.interactor.type;

import io.reactivex.rxjava3.core.Observable;

public interface ObservableUseCase<T> {
    Observable<T> execute();
}
