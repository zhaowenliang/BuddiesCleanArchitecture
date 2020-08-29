package cc.buddies.cleanarch.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import cc.buddies.cleanarch.data.db.DBDaoFactory;
import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.relation.PostWithUser;

public class SquareViewModel extends AndroidViewModel {

    public final LiveData<PagedList<PostWithUser>> postsPagedListLiveData;

    private PostDao mPostDao;

    public SquareViewModel(@NonNull Application application) {
        super(application);
        this.mPostDao = DBDaoFactory.getPostDao(application);

        postsPagedListLiveData = new LivePagedListBuilder<>(mPostDao.getPostsPaging(), 20)
                .build();
    }
}
