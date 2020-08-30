package cc.buddies.cleanarch.data.db;

import android.content.Context;

import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.dao.UserDao;
import cc.buddies.cleanarch.data.db.dao.PostPraiseDao;

public class DBDaoFactory {

    public static UserDao getUserDao(Context context) {
        return AppDatabase.getInstance(context).userDao();
    }

    public static PostDao getPostDao(Context context) {
        return AppDatabase.getInstance(context).postDao();
    }

    public static PostPraiseDao getPostPraiseDao(Context context) {
        return AppDatabase.getInstance(context).postPraiseDao();
    }

}
