package cc.buddies.cleanarch.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "posts")
public class PostEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "create_date")
    public long createDate;

    public String description;

    public List<String> images;

    @ColumnInfo(name = "good_count")
    public int goodCount;

    @ColumnInfo(name = "comment_count")
    public int commentCount;

    @ColumnInfo(name = "share_count")
    public int shareCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity that = (PostEntity) o;
        return id == that.id &&
                userId == that.userId &&
                createDate == that.createDate &&
                goodCount == that.goodCount &&
                commentCount == that.commentCount &&
                shareCount == that.shareCount &&
                Objects.equals(description, that.description) &&
                Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, createDate, description, images, goodCount, commentCount, shareCount);
    }
}
