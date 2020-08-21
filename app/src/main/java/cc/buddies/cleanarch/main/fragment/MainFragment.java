package cc.buddies.cleanarch.main.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.common.base.BaseNavigateFragment;
import cc.buddies.cleanarch.data.manager.UserManager;

public class MainFragment extends BaseNavigateFragment implements NavController.OnDestinationChangedListener {

    private BottomNavigationView mNavView;

    public MainFragment() {
        this(R.layout.fragment_main);
    }

    public MainFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNavigation(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillData();
    }

    private void initNavigation(@NonNull View view) {
        mNavView = view.findViewById(R.id.bottom_navigation_view);

        Fragment navHostFragment = getChildFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(mNavView, navController);

            // 如果没有设置ActionBar标题栏，则不需要设置此项
//            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                    R.id.navigation_home, R.id.navigation_square, R.id.navigation_message, R.id.navigation_mine)
//                    .build();
//            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            navController.addOnDestinationChangedListener(this);

            mNavView.setOnNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.navigation_message || item.getItemId() == R.id.navigation_mine) {
                    if (!UserManager.getInstance().isLogin()) {
                        mainViewModel.navigationLogin(requireActivity());
                        return false;
                    }
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
        if (destination.getId() == R.id.navigation_message) {
            removeMessageBadge();
        }
    }

}
