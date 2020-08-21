package cc.buddies.cleanarch.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cc.buddies.cleanarch.data.db.dao.UserDao;
import cc.buddies.cleanarch.data.db.entity.UserEntity;
import cc.buddies.component.storage.provider.StorageContextProvider;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "BuddiesDatabase.db";

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            instance = create(StorageContextProvider.getApplication().getApplicationContext());
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build();
    }

    public abstract UserDao userDao();

}
