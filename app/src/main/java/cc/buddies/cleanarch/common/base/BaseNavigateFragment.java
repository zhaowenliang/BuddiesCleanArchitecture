package cc.buddies.cleanarch.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import cc.buddies.cleanarch.MainViewModel;

public class BaseNavigateFragment extends BaseFragment {

    protected MainViewModel mainViewModel;

    public BaseNavigateFragment() {
    }

    public BaseNavigateFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    protected NavController navigate() {
        return mainViewModel.mainNavController(requireActivity());
    }
}
