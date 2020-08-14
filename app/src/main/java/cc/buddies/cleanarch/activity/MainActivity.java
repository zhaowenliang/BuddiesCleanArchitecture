package cc.buddies.cleanarch.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.base.BaseActivity;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private BottomNavigationView mNavView;

    @Override
    protected boolean hasBackIcon() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavigation();
        fillData();
    }

    private void initNavigation() {
        mNavView = findViewById(R.id.bottom_navigation_view);
        mNavView.setOnNavigationItemSelectedListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_square, R.id.navigation_message, R.id.navigation_mine)
                .build();

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(mNavView, navController);
        }

        initMessageBadge();
    }

    private void initMessageBadge() {
        MenuItem menuItemMessage = mNavView.getMenu().findItem(R.id.navigation_message);
        View actionView = menuItemMessage.getActionView();

        BadgeDrawable orCreateBadge = mNavView.getOrCreateBadge(R.id.navigation_message);
        orCreateBadge.setMaxCharacterCount(3);
        orCreateBadge.setVisible(false);
        orCreateBadge.updateBadgeCoordinates(actionView, mNavView);
    }

    private void clearMessageBadgeCount() {
        BadgeDrawable badge = mNavView.getBadge(R.id.navigation_message);
        if (badge != null) {
            badge.setVisible(false);
            badge.clearNumber();
        }
    }

    private void fillData() {
        BadgeDrawable badge = mNavView.getBadge(R.id.navigation_message);
        if (badge != null) {
            badge.setVisible(true);
            badge.setNumber(100);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected item: " + item.getTitle());

        switch (item.getItemId()) {
            case R.id.navigation_home:
                Log.d(TAG, getString(R.string.main_navigation_home));
                return true;
            case R.id.navigation_square:
                Log.d(TAG, getString(R.string.main_navigation_square));
                return true;
            case R.id.navigation_message:
                Log.d(TAG, getString(R.string.main_navigation_message));
                clearMessageBadgeCount();
                return true;
            case R.id.navigation_mine:
                Log.d(TAG, getString(R.string.main_navigation_mine));
                return true;
        }
        return false;
    }
}