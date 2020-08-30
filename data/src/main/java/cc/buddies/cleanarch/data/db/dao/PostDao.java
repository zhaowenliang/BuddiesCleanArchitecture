package cc.buddies.cleanarch.data.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.relation.PostWithAuthor;
import cc.buddies.cleanarch.data.db.relation.PostWithDetail;
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
    DataSource.Factory<Integer, PostWithAuthor> getPostWithAuthorPaging();

    /**
     * 使用Paging加载数据
     * <br/>
     * 通过左连接，联查posts和post_praise两表，增加联查条件post_praise表的user_id为指定userId。
     *
     * @param userId 用户id
     * @return DataSource.Factory PostWithAuthor
     */
    @Transaction
    @Query("SELECT posts.*, post_praise.id as praiseId FROM posts LEFT JOIN post_praise ON posts.id=post_praise.post_id and post_praise.user_id=:userId ORDER BY create_date DESC")
    DataSource.Factory<Integer, PostWithDetail> getPostWithAuthorPaging(long userId);

    @Transaction
    @Query("SELECT * FROM posts WHERE id=:id")
    Single<PostWithAuthor> getPostWithAuthor(long id);

    @Query("SELECT * FROM posts WHERE id=:id")
    Single<PostEntity> getPost(long id);

    @Insert
    Single<List<Long>> insertPosts(PostEntity... postEntities);

    @Update
    Single<Integer> updatePosts(PostEntity... postEntities);

}
