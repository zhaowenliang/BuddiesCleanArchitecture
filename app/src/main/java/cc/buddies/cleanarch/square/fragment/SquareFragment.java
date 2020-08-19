package cc.buddies.cleanarch.square.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import cc.buddies.cleanarch.R;

public class SquareFragment extends Fragment {

    public SquareFragment() {
        this(R.layout.fragment_square);
    }

    public SquareFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.test_button).setOnClickListener(v -> {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(v.getContext());
            materialAlertDialogBuilder
                    .setTitle("title")
                    .setMessage("你好啊")
                    .setPositiveButton("确定", (dialog, which) ->
                            Toast.makeText(v.getContext(), "123", Toast.LENGTH_SHORT).show())
                    .show();
        });
    }
}
