package cc.buddies.cleanarch;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import cc.buddies.cleanarch.data.manager.SettingsManager;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        applyNightMode();
    }

    private void applyNightMode() {
        boolean isNightMode = SettingsManager.getInstance().isNightMode();
        int newMode = isNightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(newMode);
    }

}
