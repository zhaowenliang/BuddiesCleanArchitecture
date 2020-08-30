package cc.buddies.cleanarch.common.hilt;

import cc.buddies.cleanarch.BuildConfig;
import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.dao.PostPraiseDao;
import cc.buddies.cleanarch.data.db.dao.UserDao;
import cc.buddies.cleanarch.data.service.NewsRepositoryImpl;
import cc.buddies.cleanarch.data.service.NewsService;
import cc.buddies.cleanarch.data.service.PostRepositoryImpl;
import cc.buddies.cleanarch.data.service.UserRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class RepositoryImplModule {

    @Provides
    NewsRepositoryImpl provideNewsRepositoryImpl(NewsService newsService) {
        return new NewsRepositoryImpl(newsService, BuildConfig.JUHE_API_KEY);
    }

    @Provides
    UserRepositoryImpl provideUserRepositoryImpl(UserDao userDao) {
        return new UserRepositoryImpl(userDao);
    }

    @Provides
    PostRepositoryImpl providePostRepositoryImpl(PostDao postDao, PostPraiseDao postPraiseDao) {
        return new PostRepositoryImpl(postDao, postPraiseDao);
    }

}
