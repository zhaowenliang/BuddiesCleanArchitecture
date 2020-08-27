package cc.buddies.cleanarch.data.manager;

import cc.buddies.cleanarch.data.preference.SettingsPreference;

public class SettingsManager {

    private SettingsManager() {
    }

    public static SettingsManager getInstance() {
        return SettingsManager.SingleTonHolder.PREFERENCES;
    }

    private static class SingleTonHolder {
        private static final SettingsManager PREFERENCES = new SettingsManager();
    }

    public void saveNightMode(boolean isNightMode) {
        SettingsPreference settingsPreference = new SettingsPreference();
        settingsPreference.saveNightMode(isNightMode);
    }

    public boolean isNightMode() {
        SettingsPreference settingsPreference = new SettingsPreference();
        return settingsPreference.isNightMode();
    }
}
