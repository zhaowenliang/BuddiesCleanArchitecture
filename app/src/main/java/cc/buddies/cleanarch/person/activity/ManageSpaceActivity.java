package cc.buddies.cleanarch.person.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseActivity;

/**
 * 管理存储空间页面，在AndroidManifest清单文件的application中配置manageSpaceActivity字段，设置当前页面，
 * 可在系统设置的对应应用"清除存储空间"功能跳转到当前页面，由当前页面来处理清理事项。
 */
public class ManageSpaceActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_space);
    }
}
