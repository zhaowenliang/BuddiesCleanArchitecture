package cc.buddies.cleanarch.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import cc.buddies.cleanarch.data.db.converter.ListStringConverter;
import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.dao.UserDao;
import cc.buddies.cleanarch.data.db.dao.PostPraiseDao;
import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.entity.PostPraiseEntity;
import cc.buddies.cleanarch.data.db.entity.UserEntity;

@Database(entities = {UserEntity.class, PostEntity.class, PostPraiseEntity.class}, version = 2)
@TypeConverters({ListStringConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "BuddiesDatabase.db";

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, DB_NAME)
                // .addMigrations(new DBMigration(1, 2))
                .build();
    }

    public abstract UserDao userDao();

    public abstract PostDao postDao();

    public abstract PostPraiseDao postPraiseDao();

}
