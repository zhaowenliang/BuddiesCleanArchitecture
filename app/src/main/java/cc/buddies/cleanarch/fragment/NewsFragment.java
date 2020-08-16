package cc.buddies.cleanarch.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.adapter.NewsRecyclerAdapter;
import cc.buddies.cleanarch.base.BaseFragment;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.viewmodel.NewsViewModel;
import cc.buddies.component.common.utils.DensityUtils;

public class NewsFragment extends BaseFragment {

    private NewsRecyclerAdapter mRecyclerAdapter;

    private NewsViewModel mNewsViewModel;

    private List<NewsModel> mData;

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

    private void initViewModel() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        this.mNewsViewModel = viewModelProvider.get(NewsViewModel.class);
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
        initData();
    }

    private void initView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (mData == null)
            mData = new ArrayList<>();

        mRecyclerAdapter = new NewsRecyclerAdapter(mData);
        recyclerView.setAdapter(mRecyclerAdapter);

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
        mNewsViewModel.fetchNews(mType).observe(getViewLifecycleOwner(), newsModels -> {
            mData.clear();
            if (newsModels != null) {
                mData.addAll(newsModels);
            }

            mRecyclerAdapter.notifyDataSetChanged();
        });
    }

}
