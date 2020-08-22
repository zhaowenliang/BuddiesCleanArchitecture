package cc.buddies.cleanarch.common.base;

import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cc.buddies.component.common.fragment.BuddiesCompatFragment;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.DeviceUtils;

public class BaseFragment extends BuddiesCompatFragment {

    public BaseFragment() {
    }

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    protected void initTitleBar(Toolbar toolbar) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
    }

    protected void fitTranslucentStatusBar(View view) {
        int statusBarHeight = DeviceUtils.getStatusBarHeight(view.getContext());

        int start = view.getPaddingStart();
        int end = view.getPaddingEnd();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        view.setPaddingRelative(start, top + statusBarHeight, end, bottom);
    }

    protected void translucentStatusBar(boolean isDark) {
        StatusBarHelper.translucentStatusBar(requireContext(), isDark);
    }

    protected void setTitle(@StringRes int resId) {
        CharSequence text = requireContext().getText(resId);
        setTitle(text);
    }

    protected void setTitle(CharSequence title) {
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setTitle(title);
    }

}
