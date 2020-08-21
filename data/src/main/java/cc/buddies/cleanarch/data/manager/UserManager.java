package cc.buddies.cleanarch.data.manager;

import cc.buddies.cleanarch.data.preference.UserPreference;
import cc.buddies.cleanarch.data.serialize.JSONUtils;
import cc.buddies.cleanarch.domain.model.UserModel;

public class UserManager {

    private UserModel user;

    private UserManager() {
    }

    public static UserManager getInstance() {
        return UserManager.SingleTonHolder.PREFERENCES;
    }

    private static class SingleTonHolder {
        private static final UserManager PREFERENCES = new UserManager();
    }

    public boolean isLogin() {
        return getUserInfo() != null;
    }

    public void saveUserInfo(UserModel user) {
        try {
            this.user = user;
            String s = JSONUtils.toJSON(user);

            UserPreference userPreference = new UserPreference();
            userPreference.saveUserInfo(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserModel getUserInfo() {
        if (user == null) {
            UserPreference userPreference = new UserPreference();
            String userInfo = userPreference.getUserInfo();

            this.user = JSONUtils.toBean(userInfo, UserModel.class);
        }
        return user;
    }

}
