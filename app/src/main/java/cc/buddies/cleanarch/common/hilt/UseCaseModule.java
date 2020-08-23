package cc.buddies.cleanarch.common.hilt;

import cc.buddies.cleanarch.domain.interactor.GetNewsUseCase;
import cc.buddies.cleanarch.domain.interactor.LoginUseCase;
import cc.buddies.cleanarch.domain.interactor.ModifyUserAvatarUseCase;
import cc.buddies.cleanarch.domain.interactor.ModifyUserNicknameUseCase;
import cc.buddies.cleanarch.domain.interactor.RegisterUseCase;
import cc.buddies.cleanarch.domain.repository.NewsRepository;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class UseCaseModule {

    @Provides
    GetNewsUseCase provideGetNewsUseCase(NewsRepository repository) {
        return new GetNewsUseCase(repository);
    }

    @Provides
    LoginUseCase provideLoginUseCase(UserRepository repository) {
        return new LoginUseCase(repository);
    }

    @Provides
    RegisterUseCase provideRegisterUserCase(UserRepository repository) {
        return new RegisterUseCase(repository);
    }

    @Provides
    ModifyUserAvatarUseCase provideModifyUserAvatarUseCase(UserRepository repository) {
        return new ModifyUserAvatarUseCase(repository);
    }

    @Provides
    ModifyUserNicknameUseCase provideModifyUserNicknameUseCase(UserRepository repository) {
        return new ModifyUserNicknameUseCase(repository);
    }

}
