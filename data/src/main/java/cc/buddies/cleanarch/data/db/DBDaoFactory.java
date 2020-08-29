package cc.buddies.cleanarch.data.db;

import android.content.Context;

import cc.buddies.cleanarch.data.db.dao.PostDao;

public class DBDaoFactory {

    public static PostDao getPostDao(Context context) {
        return AppDatabase.getInstance(context).postDao();
    }

}
