package cc.buddies.cleanarch.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.main.viewmodel.MainViewModel;
import cc.buddies.component.common.helper.StatusBarHelper;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private MainViewModel mMainViewModel;

    private BottomNavigationView mNavView;

    @Override
    public boolean onSupportNavigateUp() {
        if (!super.onSupportNavigateUp()) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initNavigation();
        fillData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarHelper.translucentStatusBar(this, false);
    }

    private void initViewModel() {
        this.mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        this.mMainViewModel.isMainSceneLiveData.observe(this,
                isMainScene -> mNavView.setVisibility(isMainScene ? View.VISIBLE : View.GONE));
    }

    private void initNavigation() {
        mNavView = findViewById(R.id.bottom_navigation_view);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(mNavView, navController);

            navController.addOnDestinationChangedListener(this);

            mNavView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_square:
                    case R.id.navigation_message:
                    case R.id.navigation_mine:
                        if (!UserManager.getInstance().isLogin()) {
                            navController.navigate(R.id.action_to_login);
                            return false;
                        }
                        break;
                }

                return NavigationUI.onNavDestinationSelected(item, navController);
            });
        }

        mNavView.setOnNavigationItemReselectedListener(item -> {
            // 拦截重复点击处理，如果不拦截，则会交由系统默认处理，刷新页面。
        });
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
        boolean isMainScenePage = isMainScenePage(destination.getId());
        this.mMainViewModel.notifyMainScene(isMainScenePage);

        if (destination.getId() == R.id.navigation_message) {
            removeMessageBadge();
        }
    }

    private boolean isMainScenePage(@IdRes int navDestinationId) {
        switch (navDestinationId) {
            case R.id.navigation_home:
            case R.id.navigation_square:
            case R.id.navigation_message:
            case R.id.navigation_mine:
                return true;
            default:
                return false;
        }
    }

}