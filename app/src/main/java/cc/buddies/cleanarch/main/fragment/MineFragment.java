package cc.buddies.cleanarch.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.data.manager.SettingsManager;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.component.common.utils.ToastUtils;

public class MineFragment extends BaseFragment {

    private ImageButton mImageNightMode;
    private TextView mTextNickname;
    private ImageView mImageAvatar;

    public MineFragment() {
        this(R.layout.fragment_mine);
    }

    public MineFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        translucentStatusBar(false);
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
        String avatar = userInfo != null ? userInfo.getAvatar() : "";

        mTextNickname.setText(nickname);

        Glide.with(mImageAvatar)
                .load(avatar)
                .error(R.drawable.setting_avatar_icon)
                .into(mImageAvatar);
    }

    private void initView(View view) {
        mTextNickname = view.findViewById(R.id.text_nickname);
        mImageAvatar = view.findViewById(R.id.image_avatar);
        mImageNightMode = view.findViewById(R.id.image_button_mode);

        view.findViewById(R.id.panel_trends).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_trends_label));
        view.findViewById(R.id.panel_follow).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_follow_label));
        view.findViewById(R.id.panel_fans).setOnClickListener(v -> ToastUtils.shortToast(requireContext(), R.string.person_fans_label));

        view.findViewById(R.id.image_button_setting).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_to_settings));

        // 切换 夜间/日间 模式
        mImageNightMode.setOnClickListener(v -> changeNightMode());

        initNightMode(SettingsManager.getInstance().isNightMode());
    }

    private void initNightMode(boolean isNightMode) {
        int resId = isNightMode ? R.drawable.setting_sun_icon : R.drawable.setting_moon_icon;
        mImageNightMode.setImageResource(resId);
    }

    private void changeNightMode() {
        boolean isNightMode = SettingsManager.getInstance().isNightMode();
        SettingsManager.getInstance().saveNightMode(!isNightMode);

        int newMode = isNightMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(newMode);

        initNightMode(!isNightMode);
    }
}
