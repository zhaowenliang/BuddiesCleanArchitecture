package cc.buddies.cleanarch.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cc.buddies.cleanarch.data.db.entity.UserEntity;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {

    // 如果返回Single<T>，在返回空值的情况下会抛出EmptyResultSetException异常，因为RxJava不能发射空值。
    // 使用Single<List<T>>，在查询结构为空的情况下，会给出一个空列表，不算空值，所以不会抛出异常。

    @Query("SELECT * FROM users WHERE account=:account and password=:password")
    Single<UserEntity> getUser(String account, String password);

    @Query("SELECT * FROM users WHERE account=:account")
    Single<List<UserEntity>> getUser(String account);

    @Query("SELECT * FROM users WHERE id=:uid")
    Single<List<UserEntity>> getUser(long uid);

    // 数据不存在则插入，存在则更新 OnConflictStrategy.REPLACE
    // 默认 onConflict = OnConflictStrategy.ABORT
    // 返回的Long为插入行id
    @Insert
    Single<List<Long>> insertUser(UserEntity... userEntities);

    // UPDATE/DELETE 返回的Integer值，指的是该次操作影响到的总行数

}
