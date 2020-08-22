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
     * @return 用户数据
     */
    Single<UserModel> login(LoginParams params);

    /**
     * 注册
     *
     * @param params 请求参数
     * @return 用户数据
     */
    Single<UserModel> register(RegisterParams params);

    /**
     * 检查用户是否不存在
     *
     * @param account 用户账号
     * @return 是否不存在
     */
    Completable checkAccountNoExists(String account);

    /**
     * 修改用户头像
     *
     * @param uid 用户id
     * @param url 头像地址
     * @return 头像地址
     */
    Single<String> modifyUserAvatar(long uid, String url);

    /**
     * 修改用户昵称
     *
     * @param uid      用户id
     * @param nickname 昵称
     * @return 昵称
     */
    Single<String> modifyUserNickname(long uid, String nickname);

}
