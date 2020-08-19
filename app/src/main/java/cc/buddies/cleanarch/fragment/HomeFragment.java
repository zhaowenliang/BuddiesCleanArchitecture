package cc.buddies.cleanarch.fragment;

import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.viewmodel.HomeViewModel;
import cc.buddies.component.common.adapter.CommonPagerAdapter;
import cc.buddies.component.common.helper.StatusBarHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel mHomeViewModel;

    private ViewPager mViewPager;

    private List<String> mTitles;

    private List<Fragment> mFragments = new ArrayList<>();

    public HomeFragment() {
        this(R.layout.fragment_home);
    }

    public HomeFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    private void initViewModel() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        this.mHomeViewModel = viewModelProvider.get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        observeLiveData();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(R.attr.colorSurface, typedValue, true);
        int color = typedValue.data;

        StatusBarHelper.tintStatusBar(requireContext(), requireActivity().getWindow(), color, false);
    }

    private void initView(@NonNull View view) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);

        // TabLayout初始化
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void observeLiveData() {
        this.mHomeViewModel.newsTitlesLiveData.observe(getViewLifecycleOwner(), pairs -> {
            mTitles = new ArrayList<>(pairs.size());
            mFragments.clear();

            for (Pair<String, String> item : pairs) {
                String title = item.first;
                String key = item.second;

                mTitles.add(title);
                mFragments.add(NewsFragment.newInstance(title, key));
            }

            initViewPager();
        });
    }

    private void initData() {
        mHomeViewModel.getNewsTitles(requireContext());
    }

    private void initViewPager() {
        PagerAdapter pagerAdapter = new CommonPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(pagerAdapter);
    }

}
