package cc.buddies.cleanarch.person.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseActivity;
import cc.buddies.component.common.utils.DeviceUtils;
import cc.buddies.component.common.utils.ToastUtils;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.settings_label);

        initView();
    }

    private void initView() {
        TextView textVersion = findViewById(R.id.text_version);

        String appVerName = DeviceUtils.getAppVerName(this);
        textVersion.setText(getString(R.string.settings_version_info, appVerName));

        findViewById(R.id.panel_person_info).setOnClickListener(v ->
                ToastUtils.shortToast(this, R.string.settings_person_info_label));
        findViewById(R.id.panel_cache_mange).setOnClickListener(v ->
                ToastUtils.shortToast(this, R.string.settings_manage_space_label));
        findViewById(R.id.panel_about_us).setOnClickListener(v ->
                ToastUtils.shortToast(this, R.string.settings_about_us_label));
        findViewById(R.id.text_logout_label).setOnClickListener(v ->
                ToastUtils.shortToast(this, R.string.settings_logout_label));
    }
}
