package cc.buddies.cleanarch.fragment;

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

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.adapter.NewsQuickAdapter;
import cc.buddies.cleanarch.base.BaseFragment;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.helper.StateViewHelper;
import cc.buddies.cleanarch.viewmodel.NewsViewModel;
import cc.buddies.component.common.utils.DensityUtils;

public class NewsFragment extends BaseFragment {

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
        this.mNewsViewModel.getNewsLiveData().observe(getViewLifecycleOwner(), newsModels -> {
            updateEmptyStateView();
            mRecyclerAdapter.setList(newsModels);
        });

        // 获取数据出错
        this.mNewsViewModel.getStateErrorLiveData().observe(getViewLifecycleOwner(), this::updateErrorStateView);
    }

    private void initView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mRecyclerAdapter = new NewsQuickAdapter();
        recyclerView.setAdapter(mRecyclerAdapter);

        // 设置空数据布局
        mRecyclerAdapter.setEmptyView(R.layout.layout_state_view);
        updateLoadingStateView();

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

    private void updateLoadingStateView() {
        FrameLayout stateLayout = mRecyclerAdapter.getEmptyLayout();
        StateViewHelper.updateLoadingStateView(stateLayout);
    }

    private void updateEmptyStateView() {
        FrameLayout stateLayout = mRecyclerAdapter.getEmptyLayout();
        StateViewHelper.updateEmptyStateView(stateLayout);
    }

    private void updateErrorStateView(String message) {
        FrameLayout stateLayout = mRecyclerAdapter.getEmptyLayout();
        StateViewHelper.updateErrorStateView(stateLayout, message);
    }

}
