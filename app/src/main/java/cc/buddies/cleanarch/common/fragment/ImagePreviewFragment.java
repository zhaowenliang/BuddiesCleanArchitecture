package cc.buddies.cleanarch.common.fragment;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.component.common.adapter.CommonPagerAdapter;
import cc.buddies.component.common.helper.StatusBarHelper;
import me.relex.circleindicator.CircleIndicator;

/**
 * 图片列表预览Fragment
 */
public class ImagePreviewFragment extends Fragment {

    private List<String> data;
    private int position;
    private String transitionName;

    private List<Fragment> fragments;

    public ImagePreviewFragment() {
        this(R.layout.fragment_image_preview);
    }

    public ImagePreviewFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @NotNull
    public static ImagePreviewFragment newInstance(ArrayList<String> data, int position, String transitionName) {
        Bundle args = new Bundle();
        args.putStringArrayList("data", data);
        args.putInt("position", position);
        args.putString("transitionName", transitionName);

        ImagePreviewFragment fragment = new ImagePreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

        if (getArguments() != null) {
            this.data = getArguments().getStringArrayList("data");
            this.position = getArguments().getInt("position", 0);
            this.transitionName = getArguments().getString("transitionName", "");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarHelper.translucentStatusBar(requireContext(), true);

        initFragments();
        initView(view);
    }

    private void initFragments() {
        if (fragments == null) {
            fragments = new ArrayList<>();

            if (data != null) {
                for (String url : data) {
                    fragments.add(SingleImageFragment.newInstance(url));
                }
            }
        }
    }

    private void initView(View view) {
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        ViewCompat.setTransitionName(viewPager, transitionName);

        viewPager.setAdapter(new CommonPagerAdapter(getChildFragmentManager(), fragments));
        viewPager.setCurrentItem(position);

        CircleIndicator circleIndicator = view.findViewById(R.id.circle_indicator);
        circleIndicator.setViewPager(viewPager);
        if (fragments == null || fragments.size() < 2) {
            circleIndicator.setVisibility(View.GONE);
        }
    }
}
