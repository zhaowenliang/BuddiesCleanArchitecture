package cc.buddies.cleanarch.base;

import android.os.Build;
import android.view.View;
import android.view.ViewStub;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import cc.buddies.cleanarch.R;
import cc.buddies.component.common.activity.BuddiesCompatActivity;

public class BaseActivity extends BuddiesCompatActivity {

    @Override
    protected int getTitleBarLayout() {
        return R.layout.layout_appbar_dark;
    }

    @Override
    protected void initTitleBarStub(ViewStub stub) {
        final View inflate = stub.inflate();
        if (inflate instanceof Toolbar) {
            initTitleBar((Toolbar) inflate);
            return;
        }

        // ViewStub是AppBarLayout嵌套ToolBar
        if (inflate instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) inflate;

            // 控制标题阴影
            if (!hasTitleElevation()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // 废弃方法 appBarLayout.setTargetElevation(0); 改为如下
                    appBarLayout.setStateListAnimator(null);
                }
            }

            for (int i = 0, size = appBarLayout.getChildCount(); i < size; i++) {
                final View childAt = appBarLayout.getChildAt(i);
                if (childAt instanceof Toolbar) {
                    initTitleBar((Toolbar) childAt);
                    break;
                }
            }
        }
    }
}
