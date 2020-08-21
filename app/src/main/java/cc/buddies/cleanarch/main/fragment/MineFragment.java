package cc.buddies.cleanarch.main.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.ToastUtils;

public class MineFragment extends BaseFragment {

    private TextView mTextNickname;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        UserModel userInfo = UserManager.getInstance().getUserInfo();
        String nickname = userInfo != null ? userInfo.getNickname() : "";

        mTextNickname.setText(nickname);
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
        mTextNickname = view.findViewById(R.id.text_nickname);

        view.findViewById(R.id.panel_trends).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_trends_label));
        view.findViewById(R.id.panel_follow).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_follow_label));
        view.findViewById(R.id.panel_fans).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_fans_label));

        view.findViewById(R.id.image_button_setting).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_to_settings));
    }
}
