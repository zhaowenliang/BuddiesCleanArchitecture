package cc.buddies.cleanarch;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cc.buddies.cleanarch.common.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public NavController mainNavController(@NonNull Activity activity) {
        return Navigation.findNavController(activity, R.id.fragment_container_view);
    }

    public void navigationLogin(@NonNull Activity activity) {
        mainNavController(activity).navigate(R.id.action_to_login_navigation);
    }

}
