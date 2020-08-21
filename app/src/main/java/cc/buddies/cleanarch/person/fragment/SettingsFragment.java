package cc.buddies.cleanarch.person.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.DeviceUtils;
import cc.buddies.component.common.utils.ToastUtils;

public class SettingsFragment extends Fragment {

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
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);

        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;

        StatusBarHelper.tintStatusBar(requireContext(), requireActivity().getWindow(), color, true);
    }

    private void initView(@NonNull View view) {
        TextView textVersion = view.findViewById(R.id.text_version);

        String appVerName = DeviceUtils.getAppVerName(requireContext());
        textVersion.setText(getString(R.string.settings_version_info, appVerName));

        view.findViewById(R.id.panel_person_info).setOnClickListener(v ->
                ToastUtils.shortToast(requireContext(), R.string.settings_person_info_label));
        view.findViewById(R.id.panel_cache_mange).setOnClickListener(v ->
                ToastUtils.shortToast(requireContext(), R.string.settings_manage_space_label));
        view.findViewById(R.id.panel_about_us).setOnClickListener(v ->
                ToastUtils.shortToast(requireContext(), R.string.settings_about_us_label));
        view.findViewById(R.id.text_logout_label).setOnClickListener(v -> {
            UserManager.getInstance().saveUserInfo(null);
            Navigation.findNavController(v).navigateUp();
        });
    }
}