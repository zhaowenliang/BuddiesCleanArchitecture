package cc.buddies.cleanarch.common.hilt;

import javax.inject.Singleton;

import cc.buddies.cleanarch.data.http.HttpManager;
import cc.buddies.cleanarch.data.service.NewsService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class RetrofitServiceModule {

    @Singleton
    @Provides
    NewsService providerNewsService() {
        return HttpManager.getInstance().create(NewsService.class);
    }

}
