package cc.buddies.cleanarch.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.adapter.NewsRecyclerAdapter;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private NewsRecyclerAdapter mRecyclerAdapter;

    private HomeViewModel mHomeViewModel;

    private List<NewsModel> mData;

    private String mType;

    public HomeFragment() {
        this(R.layout.fragment_home);
    }

    public HomeFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mType = getArguments().getString("type");
        }
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
        initData();
    }

    private void initView(@NonNull View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (mData == null)
            mData = new ArrayList<>();

        mRecyclerAdapter = new NewsRecyclerAdapter(mData);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void initData() {
        mHomeViewModel.fetchNews(mType).observe(getViewLifecycleOwner(), newsModels -> {
            mData.clear();
            if (newsModels != null) {
                mData.addAll(newsModels);
            }

            mRecyclerAdapter.notifyDataSetChanged();
        });
    }

}
