package cc.buddies.cleanarch.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.data.manager.UserManager;

public class SquareFragment extends BaseFragment {

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

        view.findViewById(R.id.test_button).setOnClickListener(v -> {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(v.getContext());
            materialAlertDialogBuilder
                    .setTitle("title")
                    .setMessage("你好啊")
                    .setPositiveButton("确定", (dialog, which) ->
                            Toast.makeText(v.getContext(), "123", Toast.LENGTH_SHORT).show())
                    .show();
        });

        TextView textWelcome = view.findViewById(R.id.test_text_view);
        if (UserManager.getInstance().isLogin()) {
            textWelcome.setText(UserManager.getInstance().getUserInfo().getNickname());
        } else {
            textWelcome.setText("用户未登录");
        }
    }

}
