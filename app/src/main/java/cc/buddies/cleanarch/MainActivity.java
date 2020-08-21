package cc.buddies.cleanarch;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import cc.buddies.cleanarch.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected boolean useCommonLayout() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View containerView = findViewById(R.id.fragment_container_view);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = NavHostFragment.findNavController(navHostFragment);
            Navigation.setViewNavController(containerView, navController);
        }
    }
}