package cc.buddies.cleanarch.message.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.message.adapter.SessionsQuickAdapter;
import cc.buddies.cleanarch.common.helper.BadgeDrawableHelper;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.DensityUtils;

public class MessageFragment extends Fragment {

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
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
            int itemOffset = DensityUtils.dp2px(requireContext(), 10);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL) {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildLayoutPosition(view);
                    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                    if (layoutManager != null && layoutManager.getItemCount() - 1 == position) {
                        outRect.bottom = 0;
                    }
                }
            };
            Drawable drawable = dividerItemDecoration.getDrawable();
            if (drawable != null) {
                InsetDrawable insetDrawable = new InsetDrawable(drawable, itemOffset, 0, itemOffset, 0);
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
