package cc.buddies.cleanarch.common.hilt;

import android.content.Context;

import cc.buddies.cleanarch.data.db.DBDaoFactory;
import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.dao.UserDao;
import cc.buddies.cleanarch.data.db.dao.PostPraiseDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class DatabaseDaoModule {

    @Provides
    UserDao provideUserDao(@ApplicationContext Context context) {
        return DBDaoFactory.getUserDao(context);
    }

    @Provides
    PostDao providePostDao(@ApplicationContext Context context) {
        return DBDaoFactory.getPostDao(context);
    }

    @Provides
    PostPraiseDao provideUserGoodPostDao(@ApplicationContext Context context) {
        return DBDaoFactory.getPostPraiseDao(context);
    }

}
