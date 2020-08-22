package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.CompletableUseCaseWithParameter;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Completable;

/**
 * 检测用户名是否存在用例
 */
public class CheckAccountNoExistsUseCase implements CompletableUseCaseWithParameter<String> {

    private UserRepository userRepository;

    public CheckAccountNoExistsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Completable execute(String parameter) {
        return this.userRepository.checkAccountNoExists(parameter);
    }
}
