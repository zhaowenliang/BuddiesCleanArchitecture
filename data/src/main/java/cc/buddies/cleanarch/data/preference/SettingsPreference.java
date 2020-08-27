package cc.buddies.cleanarch.data.preference;

import android.content.Context;

import cc.buddies.component.storage.preference.BasePreferences;
import cc.buddies.component.storage.provider.StorageContextProvider;

public class SettingsPreference extends BasePreferences {

    public interface Config {
        // Preference Name
        String SP_NAME = "setting_preference";

        // 夜间模式
        String NIGHT_NODE = "night_mode";
    }

    @Override
    protected Context initContext() {
        return StorageContextProvider.getApplication().getApplicationContext();
    }

    @Override
    protected String initName() {
        return Config.SP_NAME;
    }

    public void saveNightMode(boolean isNightMode) {
        getSharedPreferences().edit().putBoolean(Config.NIGHT_NODE, isNightMode).apply();
    }

    public boolean isNightMode() {
        return getSharedPreferences().getBoolean(Config.NIGHT_NODE, false);
    }
}
