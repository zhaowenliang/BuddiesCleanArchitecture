package cc.buddies.cleanarch.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.adapter.SessionsQuickAdapter;
import cc.buddies.component.common.helper.StatusBarHelper;
import cc.buddies.component.common.utils.DensityUtils;

public class MessageFragment extends Fragment {

    private SessionsQuickAdapter mSessionsAdapter;

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
        view.findViewById(R.id.iv_system_notify_icon).setOnClickListener(v ->
                Toast.makeText(requireContext(), R.string.message_system_notify, Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.iv_user_notify_icon).setOnClickListener(v ->
                Toast.makeText(requireContext(), R.string.message_user_notify, Toast.LENGTH_SHORT).show());

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

    private void initData() {
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        objects.add(new Object());

        mSessionsAdapter.setList(objects);
    }

}
