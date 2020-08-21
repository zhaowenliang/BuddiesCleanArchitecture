package cc.buddies.cleanarch.data.manager;

import android.text.TextUtils;
import android.util.Log;

import cc.buddies.cleanarch.data.preference.UserPreference;

public class UserManager {

    private String userInfo;

    private UserManager() {
    }

    public static UserManager getInstance() {
        return UserManager.SingleTonHolder.PREFERENCES;
    }

    private static class SingleTonHolder {
        private static final UserManager PREFERENCES = new UserManager();
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getUserInfo());
    }

    public boolean saveUserInfo(String user) {
        try {
            UserPreference userPreference = new UserPreference();
            userPreference.saveUserInfo(user);

            this.userInfo = user;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserInfo() {
        if (userInfo == null) {
            UserPreference userPreference = new UserPreference();
            userInfo = userPreference.getUserInfo();
        }
        return userInfo;
    }

}
