package cc.buddies.cleanarch.data.db.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.entity.UserEntity;

/**
 * 使用Relation查询外键关联表数据
 */
public class PostWithUser {

    // 使用Embedded引用的为待查询表主体
    @Embedded
    public PostEntity post;

    // 使用Relation引用的是外键关联表
    // parentColumn为 主体表中的外键字段
    // entityColumn为 外键关联表字段
    @Relation(parentColumn = "user_id", entityColumn = "id")
    public UserEntity user;

}
