package cc.buddies.cleanarch.main.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.main.adapter.PostsPagedListAdapter;
import cc.buddies.cleanarch.main.viewmodel.SquareViewModel;
import cc.buddies.component.common.utils.DensityUtils;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SquareFragment extends BaseFragment {

    private SquareViewModel mSquareViewModel;

    private PostsPagedListAdapter mPostsPagedListAdapter;

    public SquareFragment() {
        this(R.layout.fragment_square);
    }

    public SquareFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        translucentStatusBar(false);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mSquareViewModel = new ViewModelProvider(this).get(SquareViewModel.class);

        this.mSquareViewModel.postsPagedListLiveData.observe(getViewLifecycleOwner(), postWithDetails ->
                mPostsPagedListAdapter.submitList(postWithDetails));

        this.mSquareViewModel.praisePostLiveData.observe(getViewLifecycleOwner(), booleanDataResult -> {

        });
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (recyclerView.getItemDecorationCount() == 0) {
            int offset = DensityUtils.dp2px(requireContext(), 12);

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildLayoutPosition(view);

                    int offsetTop = position == 0 ? offset : 0;
                    outRect.set(offset, offsetTop, offset, offset);
                }
            });
        }

        mPostsPagedListAdapter = new PostsPagedListAdapter();
        recyclerView.setAdapter(mPostsPagedListAdapter);

        // 解决点赞刷新的时候item闪烁。
        if (recyclerView.getItemAnimator() instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }

        // 点赞/评论/分享
        mPostsPagedListAdapter.setOnPostClickGoodListener(new PostsPagedListAdapter.OnPostClickViewListener() {
            @Override
            public void onPostClickGood(long postId, boolean addOrCancel) {
                UserModel userInfo = UserManager.getInstance().getUserInfo();
                long uid = userInfo == null ? 0 : userInfo.getUid();

                mSquareViewModel.praisePost(postId, uid, addOrCancel);
            }

            @Override
            public void onPostClickComment(long postId) {

            }

            @Override
            public void onPostClickShare(long postId) {

            }
        });
    }

}
