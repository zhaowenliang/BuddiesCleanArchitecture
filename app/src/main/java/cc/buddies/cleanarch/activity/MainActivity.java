package cc.buddies.cleanarch.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.base.BaseActivity;

public class MainActivity extends BaseActivity implements NavController.OnDestinationChangedListener {

    private BottomNavigationView mNavView;

    @Override
    protected boolean useCommonLayout() {
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
        // 设置NavigationUI.setupWithNavController后，此处监听无效
        // mNavView.setOnNavigationItemSelectedListener(this);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(mNavView, navController);

            // 如果没有设置ActionBar标题栏，则不需要设置此项
//            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                    R.id.navigation_home, R.id.navigation_square, R.id.navigation_message, R.id.navigation_mine)
//                    .build();
//            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            navController.addOnDestinationChangedListener(this);
        }
    }

    private void setMessageBadge(int number) {
        BadgeDrawable badge = mNavView.getOrCreateBadge(R.id.navigation_message);
        badge.setMaxCharacterCount(3);
        badge.setNumber(number);
    }

    private void removeMessageBadge() {
        BadgeDrawable badge = mNavView.getBadge(R.id.navigation_message);
        if (badge != null) {
            badge.setVisible(false);
            badge.clearNumber();
        }
    }

    private void fillData() {
        Random r = new Random();
        setMessageBadge(r.nextInt(100));
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == R.id.navigation_message) {
            removeMessageBadge();
        }
    }
}