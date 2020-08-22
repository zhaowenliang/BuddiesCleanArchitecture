package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import cc.buddies.cleanarch.domain.request.ModifyUserNicknameParams;
import io.reactivex.rxjava3.core.Single;

public class ModifyUserNicknameUseCase implements SingleUseCaseWithParameter<ModifyUserNicknameParams, String> {

    private UserRepository userRepository;

    public ModifyUserNicknameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<String> execute(ModifyUserNicknameParams parameter) {
        return this.userRepository.modifyUserNickname(parameter.getUid(), parameter.getNickname());
    }

}
