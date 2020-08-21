package cc.buddies.cleanarch.main.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseNavigateFragment;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.ToastUtils;

public class MineFragment extends BaseNavigateFragment {

    public MineFragment() {
        this(R.layout.fragment_mine);
    }

    public MineFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(R.attr.colorSurface, typedValue, true);
        int color = typedValue.data;

        StatusBarHelper.tintStatusBar(requireContext(), requireActivity().getWindow(), color, false);
    }

    private void initView(View view) {
        view.findViewById(R.id.panel_trends).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_trends_label));
        view.findViewById(R.id.panel_follow).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_follow_label));
        view.findViewById(R.id.panel_fans).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_fans_label));

        view.findViewById(R.id.image_button_setting).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_to_settings_navigation));

//        view.findViewById(R.id.image_button_setting).setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.action_to_settings);
//        });
    }
}
