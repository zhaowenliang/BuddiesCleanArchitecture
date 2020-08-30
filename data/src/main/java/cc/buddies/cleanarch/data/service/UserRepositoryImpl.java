package cc.buddies.cleanarch.data.service;

import android.text.TextUtils;

import androidx.room.rxjava3.EmptyResultSetException;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

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

    private UserDao mUserDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.mUserDao = userDao;
    }

    // 用户token生成器
    private static class UserTokenGenerator {
        static String generate() {
            String key = String.valueOf(System.currentTimeMillis());
            return DigestUtils.md5Hex(key);
        }
    }

    // 密码加密
    private String encryptPassword(String password) {
        if (TextUtils.isEmpty(password)) return password;
        return DigestUtils.md5Hex(password.getBytes());
    }

    // 生成默认用户昵称
    private String generateNickname(String account) {
        return account;
    }

    // 转化数据实体为响应数据模型
    private UserModel transEntityToModel(UserEntity entity) {
        UserModel userModel = new UserModel();
        userModel.setAccount(entity.account);
        userModel.setToken(entity.token);
        userModel.setNickname(entity.nickname);
        userModel.setAvatar(entity.avatar);
        userModel.setUid(entity.id);
        return userModel;
    }

    /**
     * 登录
     *
     * @param params 请求参数
     * @return UserModel
     */
    @Override
    public Single<UserModel> login(LoginParams params) {
        // 密码加密
        String password = params.getPassword();
        String encryptPassword = encryptPassword(password);
        params.setPassword(encryptPassword);

        return mUserDao.getUser(params.getAccount(), params.getPassword())
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
                .map(this::transEntityToModel);
    }

    /**
     * 注册
     *
     * @param params 请求参数
     * @return UserModel
     */
    @Override
    public Single<UserModel> register(RegisterParams params) {
        // 密码加密
        String password = params.getPassword();
        String encryptPassword = encryptPassword(password);
        params.setPassword(encryptPassword);

        return mUserDao.getUser(params.getAccount())
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

                    // 生成默认昵称
                    if (entity.nickname == null || entity.nickname.length() == 0) {
                        entity.nickname = generateNickname(entity.account);
                    }

                    return mUserDao.insertUser(entity);
                })
                // 查询新用户数据
                .flatMap((Function<List<Long>, SingleSource<UserEntity>>) longs -> {
                    if (longs.isEmpty()) {
                        // 注册失败
                        ErrorEnum errorEnum = ErrorEnum.REGISTER_FAILED;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    } else {
                        return mUserDao.getUser(longs.get(0));
                    }
                })
                // 返回注册结果
                .map(this::transEntityToModel);
    }

    /**
     * 检查账号是否不存在
     *
     * @param account 用户账号
     * @return 账户是否不存在
     */
    @Override
    public Completable checkAccountNoExists(String account) {
        return mUserDao
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

    /**
     * 修改用户头像
     *
     * @param uid 用户id
     * @param url 头像地址
     * @return 头像地址
     */
    @Override
    public Single<String> modifyUserAvatar(long uid, String url) {
        return mUserDao
                .getUser(uid)
                .subscribeOn(Schedulers.io())
                // 查询数据为空处理
                .onErrorResumeNext((Function<Throwable, SingleSource<UserEntity>>) throwable -> {
                    if (throwable instanceof EmptyResultSetException) {
                        ErrorEnum errorEnum = ErrorEnum.ACCOUNT_NOT_EXISTS;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    } else {
                        return Single.error(throwable);
                    }
                })
                // 更新头像
                .flatMap((Function<UserEntity, SingleSource<Integer>>) entity -> {
                    entity.avatar = url;
                    return mUserDao.updateUser(entity);
                })
                // 返回结果
                .flatMap((Function<Integer, SingleSource<String>>) integer -> {
                    if (integer > 0) {
                        return Single.just(url);
                    } else {
                        ErrorEnum errorEnum = ErrorEnum.USER_MODIFY_FAILED;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    }
                });
    }

    /**
     * 修改用户昵称
     *
     * @param uid      用户id
     * @param nickname 昵称
     * @return 昵称
     */
    @Override
    public Single<String> modifyUserNickname(long uid, String nickname) {
        return mUserDao
                .getUser(uid)
                .subscribeOn(Schedulers.io())
                // 查询数据为空处理
                .onErrorResumeNext((Function<Throwable, SingleSource<UserEntity>>) throwable -> {
                    if (throwable instanceof EmptyResultSetException) {
                        ErrorEnum errorEnum = ErrorEnum.ACCOUNT_NOT_EXISTS;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    } else {
                        return Single.error(throwable);
                    }
                })
                // 更新昵称
                .flatMap((Function<UserEntity, SingleSource<Integer>>) entity -> {
                    entity.nickname = nickname;
                    return mUserDao.updateUser(entity);
                })
                // 返回结果
                .flatMap((Function<Integer, SingleSource<String>>) integer -> {
                    if (integer > 0) {
                        return Single.just(nickname);
                    } else {
                        ErrorEnum errorEnum = ErrorEnum.USER_MODIFY_FAILED;
                        return Single.error(new ResponseException(errorEnum.getCode(), errorEnum.getMessage()));
                    }
                });
    }

}
