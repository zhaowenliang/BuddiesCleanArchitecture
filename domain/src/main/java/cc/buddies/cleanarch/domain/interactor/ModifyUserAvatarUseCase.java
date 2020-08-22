package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import cc.buddies.cleanarch.domain.request.ModifyUserAvatarParams;
import io.reactivex.rxjava3.core.Single;

public class ModifyUserAvatarUseCase implements SingleUseCaseWithParameter<ModifyUserAvatarParams, String> {

    private UserRepository userRepository;

    public ModifyUserAvatarUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<String> execute(ModifyUserAvatarParams parameter) {
        return this.userRepository.modifyUserAvatar(parameter.getUid(), parameter.getAvatar());
    }

}
