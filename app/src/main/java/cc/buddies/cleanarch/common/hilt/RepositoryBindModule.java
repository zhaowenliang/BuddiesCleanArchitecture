package cc.buddies.cleanarch.common.hilt;

import cc.buddies.cleanarch.data.service.NewsRepositoryImpl;
import cc.buddies.cleanarch.data.service.UserRepositoryImpl;
import cc.buddies.cleanarch.domain.repository.NewsRepository;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;

@Module
@InstallIn(ActivityRetainedComponent.class)
public abstract class RepositoryBindModule {

    @Binds
    public abstract NewsRepository provideNewsRepository(NewsRepositoryImpl repository);

    @Binds
    public abstract UserRepository provideUserRepository(UserRepositoryImpl repository);

}
