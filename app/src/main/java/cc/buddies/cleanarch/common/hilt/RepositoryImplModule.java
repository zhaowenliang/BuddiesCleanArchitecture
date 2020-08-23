package cc.buddies.cleanarch.common.hilt;

import android.content.Context;

import cc.buddies.cleanarch.BuildConfig;
import cc.buddies.cleanarch.data.service.NewsRepositoryImpl;
import cc.buddies.cleanarch.data.service.NewsService;
import cc.buddies.cleanarch.data.service.UserRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class RepositoryImplModule {

    @Provides
    NewsRepositoryImpl provideNewsRepositoryImpl(NewsService newsService) {
        return new NewsRepositoryImpl(newsService, BuildConfig.JUHE_API_KEY);
    }

    @Provides
    UserRepositoryImpl provideUserRepositoryImpl(@ApplicationContext Context context) {
        return new UserRepositoryImpl(context);
    }

}
