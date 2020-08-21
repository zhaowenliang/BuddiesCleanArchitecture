package cc.buddies.cleanarch.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String account;

    public String password;

    public String token;

    public String nickname;

    public String avatar;

    @ColumnInfo(name = "create_date")
    public long createDate;

    @ColumnInfo(name = "modify_date")
    public long modifyDate;

}
