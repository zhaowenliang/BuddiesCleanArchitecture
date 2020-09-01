package cc.buddies.cleanarch.main.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
            // 使用PagedListAdapter，改变数据直接作用到视图。
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

        // 点赞/评论/分享/点击图片
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

            @Override
            public void onPostClickImage(List<String> list, int position) {
//                String url1 = "https://desk-fd.zol-img.com.cn/t_s4096x2160c5/g6/M00/0B/03/ChMkKV9MqWyIQ5OnABptwzfj6goAABsjwAAQ9wAGm3b490.jpg";
//                String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598980627083&di=4359fd94a3daf3964298bbbd30932977&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201407%2F20%2F20140720141804_j2zi5.thumb.700_0.jpeg";
//                String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598980791264&di=5033992955be4610522d21be13c66fae&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F36c59ff6a9b5cc45d71181f930036e35b8a54d9d.jpg";
//                String url4 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=346813894,2725830243&fm=26&gp=0.jpg";
//
//                ArrayList<String> strings = new ArrayList<>();
//                strings.add(url1);
//                strings.add(url2);
//                strings.add(url3);
//                strings.add(url4);

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", new ArrayList<>(list));
                bundle.putInt("position", position);

                NavHostFragment.findNavController(SquareFragment.this)
                        .navigate(R.id.action_to_image_preview, bundle);
            }
        });
    }

}
