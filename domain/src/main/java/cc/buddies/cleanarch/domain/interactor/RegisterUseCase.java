package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import cc.buddies.cleanarch.domain.request.RegisterParams;
import io.reactivex.rxjava3.core.Single;

public class RegisterUseCase implements SingleUseCaseWithParameter<RegisterParams, UserModel> {

    private UserRepository userRepository;

    public RegisterUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<UserModel> execute(RegisterParams parameter) {
        return this.userRepository.register(parameter);
    }

}
