package cc.buddies.cleanarch.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cc.buddies.cleanarch.R;
import cc.buddies.component.common.helper.StatusBarHelper;

public class MineFragment extends Fragment {

    public MineFragment() {
        this(R.layout.fragment_mine);
    }

    public MineFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(R.attr.colorSurface, typedValue, true);
        int color = typedValue.data;

        StatusBarHelper.tintStatusBar(requireContext(), requireActivity().getWindow(), color, false);
    }
}
