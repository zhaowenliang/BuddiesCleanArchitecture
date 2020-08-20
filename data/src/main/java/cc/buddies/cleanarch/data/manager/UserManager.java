package cc.buddies.cleanarch.data.manager;

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
        if (userInfo != null) {
            return true;
        } else {
            UserPreference userPreference = new UserPreference();
            return userPreference.getUserInfo() != null;
        }
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
        if (userInfo != null) {
            return userInfo;
        } else {
            UserPreference userPreference = new UserPreference();
            return userPreference.getUserInfo();
        }
    }

}
