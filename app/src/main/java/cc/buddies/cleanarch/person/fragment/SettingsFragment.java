package cc.buddies.cleanarch.person.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.component.common.utils.DeviceUtils;
import cc.buddies.component.common.utils.ToastUtils;

public class SettingsFragment extends BaseFragment {

    public SettingsFragment() {
        this(R.layout.fragment_settings);
    }

    public SettingsFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.title_bar);
        initTitleBar(toolbar);
        fitTranslucentStatusBar(toolbar);
        translucentStatusBar(true);

        initView(view);
    }

    private void initView(@NonNull View view) {
        TextView textVersion = view.findViewById(R.id.text_version);

        String appVerName = DeviceUtils.getAppVerName(requireContext());
        textVersion.setText(getString(R.string.settings_version_info, appVerName));

        view.findViewById(R.id.panel_person_info).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_to_person_info));
        view.findViewById(R.id.panel_about_us).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_to_about_us));

        view.findViewById(R.id.panel_cache_mange).setOnClickListener(v ->
                ToastUtils.shortToast(requireContext(), R.string.settings_manage_space_label));

        view.findViewById(R.id.text_logout_label).setOnClickListener(v -> {
            UserManager.getInstance().saveUserInfo(null);
            Navigation.findNavController(v).navigate(R.id.action_popup_main);
        });
    }
}
