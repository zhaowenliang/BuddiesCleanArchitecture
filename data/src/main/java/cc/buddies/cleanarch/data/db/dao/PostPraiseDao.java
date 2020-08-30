package cc.buddies.cleanarch.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cc.buddies.cleanarch.data.db.entity.PostPraiseEntity;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PostPraiseDao {

    /**
     * 查询帖子点赞记录
     *
     * @param postId 帖子id
     * @param userId 用户id
     * @return PostPraiseEntity
     */
    @Query("SELECT * FROM post_praise WHERE post_id=:postId and user_id=:userId")
    Single<List<PostPraiseEntity>> getPostPraise(long postId, long userId);

    /**
     * 帖子点赞
     *
     * @param postPraiseEntity 帖子点赞记录
     * @return 插入行id
     */
    @Insert
    Single<Long> insertPostPraise(PostPraiseEntity postPraiseEntity);

    /**
     * 删帖子点赞记录
     *
     * @param postPraiseEntity 帖子点赞记录
     * @return 操作影响行数
     */
    @Delete
    Single<Integer> deletePostPraise(PostPraiseEntity postPraiseEntity);

}
