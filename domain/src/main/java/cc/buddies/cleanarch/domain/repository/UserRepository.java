package cc.buddies.cleanarch.domain.repository;

import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.request.LoginParams;
import cc.buddies.cleanarch.domain.request.RegisterParams;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserRepository {

    /**
     * 登录
     *
     * @param params 请求参数
     * @return Single
     */
    Single<UserModel> login(LoginParams params);

    /**
     * 注册
     *
     * @param params 请求参数
     * @return Single
     */
    Single<UserModel> register(RegisterParams params);

    /**
     * 检查用户是否已存在
     *
     * @param account 用户账号
     * @return Completable
     */
    Completable checkUserExists(String account);

}
