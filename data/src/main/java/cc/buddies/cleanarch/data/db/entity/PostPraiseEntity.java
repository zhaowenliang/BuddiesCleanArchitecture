package cc.buddies.cleanarch.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 帖子点赞记录表
 */
@Entity(tableName = "post_praise")
public class PostPraiseEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "post_id")
    public long postId;

    @ColumnInfo(name = "user_id")
    public long userId;

}
