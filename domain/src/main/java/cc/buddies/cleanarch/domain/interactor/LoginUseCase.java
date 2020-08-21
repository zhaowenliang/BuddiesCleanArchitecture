package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import cc.buddies.cleanarch.domain.request.LoginParams;
import io.reactivex.rxjava3.core.Single;

public class LoginUseCase implements SingleUseCaseWithParameter<LoginParams, UserModel> {

    private UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<UserModel> execute(LoginParams parameter) {
        return this.userRepository.login(parameter);
    }

}
