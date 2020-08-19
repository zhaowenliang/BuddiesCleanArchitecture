package cc.buddies.cleanarch.home.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.home.adapter.NewsQuickAdapter;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.common.helper.StateViewHelper;
import cc.buddies.cleanarch.home.viewmodel.NewsViewModel;
import cc.buddies.component.common.utils.DensityUtils;

public class NewsFragment extends BaseFragment {

    private SmartRefreshLayout mSmartRefreshLayout;

    private BaseQuickAdapter<NewsModel, BaseViewHolder> mRecyclerAdapter;

    private NewsViewModel mNewsViewModel;

    private String mType;

    public NewsFragment() {
        this(R.layout.layout_news);
    }

    public NewsFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    public static NewsFragment newInstance(String title, String key) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("key", key);

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mType = getArguments().getString("key");
        }
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

    private void initViewModel() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        this.mNewsViewModel = viewModelProvider.get(NewsViewModel.class);
    }

    private void observeLiveData() {
        // 获取数据结果
        this.mNewsViewModel.newsLiveData.observe(getViewLifecycleOwner(), newsModels -> {
            updateEmptyStateView();
            mRecyclerAdapter.setList(newsModels);
            // 结束刷新
            finishOnRefresh(true);
        });

        // 获取数据出错
        this.mNewsViewModel.stateErrorLiveData.observe(getViewLifecycleOwner(), s -> {
            updateErrorStateView(s);
            // 结束刷新
            finishOnRefresh(false);
        });
    }

    private void initView(@NonNull View view) {
        mSmartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(requireContext()));
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> mNewsViewModel.fetchNews(mType));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mRecyclerAdapter = new NewsQuickAdapter();
        recyclerView.setAdapter(mRecyclerAdapter);

        // 设置空数据布局
        mRecyclerAdapter.setEmptyView(R.layout.layout_state_view);
        mRecyclerAdapter.setUseEmpty(false);

        if (recyclerView.getItemDecorationCount() == 0) {
            int itemOffset = DensityUtils.dp2px(requireContext(), 12);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL);
            Drawable drawable = dividerItemDecoration.getDrawable();
            if (drawable != null) {
                InsetDrawable insetDrawable = new InsetDrawable(drawable, itemOffset, 0, itemOffset, 0);
                dividerItemDecoration.setDrawable(insetDrawable);
            }

            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }

    private void initData() {
        mNewsViewModel.fetchNews(mType);
    }

    private void finishOnRefresh(boolean success) {
        mSmartRefreshLayout.finishRefresh(success);
    }

    private void updateEmptyStateView() {
        FrameLayout stateLayout = mRecyclerAdapter.getEmptyLayout();
        mRecyclerAdapter.setUseEmpty(true);
        StateViewHelper.updateEmptyStateView(stateLayout);
    }

    private void updateErrorStateView(String message) {
        FrameLayout stateLayout = mRecyclerAdapter.getEmptyLayout();
        mRecyclerAdapter.setUseEmpty(true);
        StateViewHelper.updateErrorStateView(stateLayout, message);
    }

}
