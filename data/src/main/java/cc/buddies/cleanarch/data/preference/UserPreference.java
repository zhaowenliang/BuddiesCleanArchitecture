package cc.buddies.cleanarch.data.preference;

import android.content.Context;

import androidx.annotation.Nullable;

import cc.buddies.cleanarch.data.encrypt.EncryptConstants;
import cc.buddies.component.storage.crypto.AESUtils;
import cc.buddies.component.storage.preference.BasePreferences;
import cc.buddies.component.storage.provider.StorageContextProvider;

public class UserPreference extends BasePreferences {

    public interface Config {
        // Preference Name
        String SP_NAME = "user_preference";

        // 用户信息
        String KEY_USER_INFO = "user_info";
    }

    @Override
    protected Context initContext() {
        return StorageContextProvider.getApplication().getApplicationContext();
    }

    @Override
    protected String initName() {
        return Config.SP_NAME;
    }

    public void saveUserInfo(String user) throws Exception {
        String encryptData = null;
        if (user != null && !"".equals(user)) {
            encryptData = AESUtils.encryptBase64CBC(user.getBytes(), EncryptConstants.AES_KEY.getBytes(), EncryptConstants.AES_IV.getBytes());
        }
        getSharedPreferences().edit().putString(Config.KEY_USER_INFO, encryptData).apply();
    }

    @Nullable
    public String getUserInfo() {
        String user = getSharedPreferences().getString(Config.KEY_USER_INFO, "");
        if (!"".equals(user)) {
            try {
                return AESUtils.decryptBase64CBC(user, EncryptConstants.AES_KEY.getBytes(), EncryptConstants.AES_IV.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
