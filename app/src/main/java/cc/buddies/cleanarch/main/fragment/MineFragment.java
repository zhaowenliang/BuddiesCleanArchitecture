package cc.buddies.cleanarch.main.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseNavigateFragment;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.ToastUtils;

public class MineFragment extends BaseNavigateFragment {

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

        // 使用Navigation.createNavigateOnClickListener创建响应时间，会在当前View所在最近的Fragment容器内寻找Action，
        // 但是当前意图为在主页主容器内导航，所以只能指定View寻找NavController。
        // view.findViewById(R.id.image_button_setting).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_to_settings_navigation));

        view.findViewById(R.id.image_button_setting).setOnClickListener(v -> {
            navigate().navigate(R.id.action_to_settings_navigation);
        });
    }
}
