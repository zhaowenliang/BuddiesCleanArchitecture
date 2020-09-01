package cc.buddies.cleanarch.main.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.common.helper.BadgeDrawableHelper;
import cc.buddies.cleanarch.common.recyclerview.DividerItemDecoration;
import cc.buddies.cleanarch.main.adapter.SessionsQuickAdapter;
import cc.buddies.component.common.utils.DensityUtils;

public class MessageFragment extends BaseFragment {

    private SessionsQuickAdapter mSessionsAdapter;

    private BadgeDrawable mSystemNotifyBadgeDrawable;
    private BadgeDrawable mUserNotifyBadgeDrawable;

    public MessageFragment() {
        this(R.layout.fragment_message);
    }

    public MessageFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fitTranslucentStatusBar(view.findViewById(R.id.panel_message_title));
        translucentStatusBar(false);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initView(@NonNull View view) {
        ImageView imageSystemNotifyIcon = view.findViewById(R.id.iv_system_notify_icon);
        ImageView imageUserNotifyIcon = view.findViewById(R.id.iv_user_notify_icon);

        // 手动添加系统通知角标
        imageSystemNotifyIcon.post(() -> {
            mSystemNotifyBadgeDrawable = createBadge(imageSystemNotifyIcon);
            mSystemNotifyBadgeDrawable.clearNumber();
        });

        // 手动添加用户通知角标
        imageUserNotifyIcon.post(() -> {
            mUserNotifyBadgeDrawable = createBadge(imageUserNotifyIcon);
            mUserNotifyBadgeDrawable.setMaxCharacterCount(3);
            mUserNotifyBadgeDrawable.setNumber(1);
        });

        // 点击角标隐藏
        imageSystemNotifyIcon.setOnClickListener(v -> mSystemNotifyBadgeDrawable.setVisible(false));
        imageUserNotifyIcon.setOnClickListener(v -> mUserNotifyBadgeDrawable.setVisible(false));

        RecyclerView recyclerView = view.findViewById(R.id.list_view_sessions);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (recyclerView.getItemDecorationCount() == 0) {
            // 自定义DividerItemDecoration，不显示最后一个分割线。
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL);

            Drawable drawable = dividerItemDecoration.getDrawable();
            if (drawable != null) {
                int inset = DensityUtils.dp2px(requireContext(), 10);
                InsetDrawable insetDrawable = new InsetDrawable(drawable, inset, 0, inset, 0);
                dividerItemDecoration.setDrawable(insetDrawable);
            }

            recyclerView.addItemDecoration(dividerItemDecoration);
        }

        mSessionsAdapter = new SessionsQuickAdapter();
        recyclerView.setAdapter(mSessionsAdapter);

        mSessionsAdapter.setOnItemClickListener((adapter, view1, position) ->
                Toast.makeText(requireContext(), "Toast", Toast.LENGTH_SHORT).show());
    }

    private BadgeDrawable createBadge(View author) {
        BadgeDrawable badgeDrawable = BadgeDrawable.create(requireContext());
        BadgeDrawableHelper.attachBadgeDrawable(badgeDrawable, author, null);

        ViewGroup parentLayout = (ViewGroup) author.getParent();
        parentLayout.setClipChildren(false);
        parentLayout.setClipToPadding(false);
        return badgeDrawable;
    }

    private void initData() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new Object());
        }

        mSessionsAdapter.setList(objects);
    }

}
