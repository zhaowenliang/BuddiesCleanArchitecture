package cc.buddies.cleanarch.data.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.relation.PostWithUser;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PostDao {

    /**
     * 使用Paging加载数据
     *
     * @return DataSource.Factory
     */
    @Transaction
    @Query("SELECT * FROM posts ORDER BY create_date DESC")
    DataSource.Factory<Integer, PostWithUser> getPostsPaging();

//    @Query("SELECT * FROM posts ORDER BY create_date DESC")
//    DataSource.Factory<Integer, PostEntity> getPostsPaging();

    @Transaction
    @Query("SELECT * FROM posts WHERE id=:id")
    Single<PostWithUser> getPost(long id);

    @Insert
    Single<List<Long>> insertPosts(PostEntity... postEntities);

}
