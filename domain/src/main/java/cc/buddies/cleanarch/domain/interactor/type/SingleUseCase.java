package cc.buddies.cleanarch.domain.interactor.type;

import io.reactivex.rxjava3.core.Single;

public interface SingleUseCase<T> {
    Single<T> execute();
}
