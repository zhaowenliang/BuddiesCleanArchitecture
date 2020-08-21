package cc.buddies.cleanarch.data.service;

import android.text.TextUtils;

import androidx.room.rxjava3.EmptyResultSetException;

import org.apache.commons.codec.digest.Md5Crypt;

import java.util.List;

import cc.buddies.cleanarch.data.db.AppDatabase;
import cc.buddies.cleanarch.data.db.dao.UserDao;
import cc.buddies.cleanarch.data.db.entity.UserEntity;
import cc.buddies.cleanarch.data.exception.ResponseException;
import cc.buddies.cleanarch.data.http.ErrorEnum;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.repository.UserRepository;
import cc.buddies.cleanarch.domain.request.LoginParams;
import cc.buddies.cleanarch.domain.request.RegisterParams;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepositoryImpl implements UserRepository {

    private static class UserTokenGenerator {
        static String generate() {
            String key = String.valueOf(System.currentTimeMillis());
            return Md5Crypt.md5Crypt(key.getBytes());
        }
    }

    @Override
    public Single<UserModel> login(LoginParams params) {
        // 密码加密
        String password = params.getPassword();
        String encryptPassword = encryptPassword(password);
        params.setPassword(encryptPassword);

        UserDao userDao = AppDatabase.getInstance().userDao();
        return userDao.getUser(params.getAccount(), params.getPassword())
                .subscribeOn(Schedulers.io())
                // 查询数据为空处理
                .onErrorResumeNext((Function<Throwable, SingleSource<UserEntity>>) throwable -> {
                    if (throwable instanceof EmptyResultSetException) {
                        ErrorEnum loginFailed = ErrorEnum.LOGIN_FAILED;
                        return Single.error(new ResponseException(loginFailed.getCode(), loginFailed.getMessage()));
                    } else {
                        return Single.error(throwable);
                    }
                })
                // 转化响应数据
                .map(userEntity -> {
                    UserModel userModel = new UserModel();
                    userModel.setAccount(userEntity.account);
                    userModel.setToken(userEntity.token);
                    userModel.setNickname(userEntity.nickname);
                    userModel.setAvatar(userEntity.avatar);
                    userModel.setUid(userEntity.id);
                    return userModel;
                });
    }

    @Override
    public Single<UserModel> register(RegisterParams params) {
        // 密码加密
        String password = params.getPassword();
        String encryptPassword = encryptPassword(password);
        params.setPassword(encryptPassword);

        UserDao userDao = AppDatabase.getInstance().userDao();

        return userDao.getUser(params.getAccount())
                .subscribeOn(Schedulers.io())
                // 判断用户是否存在
                .flatMap((Function<List<UserEntity>, SingleSource<RegisterParams>>) userEntities -> {
                    if (userEntities.isEmpty()) {
                        return Single.just(params);
                    } else {
                        // 账号已存在
                        ErrorEnum accountExists = ErrorEnum.ACCOUNT_EXISTS;
                        return Single.error(new ResponseException(accountExists.getCode(), accountExists.getMessage()));
                    }
                })
                // 创建新账户
                .flatMap((Function<RegisterParams, SingleSource<List<Long>>>) registerParams -> {

                    UserEntity entity = new UserEntity();
                    entity.account = registerParams.getAccount();
                    entity.password = registerParams.getPassword();
                    entity.nickname = registerParams.getNickname();
                    entity.avatar = registerParams.getAvatar();

                    entity.createDate = System.currentTimeMillis();
                    entity.modifyDate = entity.createDate;

                    entity.token = UserTokenGenerator.generate();

                    return userDao.insertUser(entity);
                })
                // 查询新用户数据
                .flatMap((Function<List<Long>, SingleSource<List<UserEntity>>>) longs -> {
                    if (longs.isEmpty()) {
                        // 注册失败
                        ErrorEnum errorEnum = ErrorEnum.REGISTER_FAILED;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    } else {
                        return userDao.getUser(longs.get(0));
                    }
                })
                // 返回注册结果
                .map(userEntities -> {
                    UserEntity userEntity = userEntities.get(0);

                    UserModel result = new UserModel();
                    result.setUid(userEntity.id);
                    result.setAccount(userEntity.account);
                    result.setNickname(userEntity.nickname);
                    result.setAvatar(userEntity.account);
                    result.setToken(userEntity.token);

                    return result;
                });
    }

    @Override
    public Completable checkUserExists(String account) {
        return AppDatabase.getInstance().userDao()
                .getUser(account)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(userEntities -> {
                    if (userEntities.isEmpty()) {
                        return Completable.complete();
                    } else {
                        ErrorEnum accountExists = ErrorEnum.ACCOUNT_EXISTS;
                        return Completable.error(new ResponseException(accountExists.getCode(), accountExists.getMessage()));
                    }
                });
    }

    // 密码加密
    private String encryptPassword(String password) {
        if (TextUtils.isEmpty(password)) return password;
        return Md5Crypt.md5Crypt(password.getBytes());
    }

}
