package cc.buddies.cleanarch.common.helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cc.buddies.cleanarch.R;

public class StateViewHelper {

    public static void updateLoadingStateView(View stateView) {
        if (stateView == null) return;

        ImageView imageStateView = stateView.findViewById(R.id.image_state_view);
        TextView textStateDes = stateView.findViewById(R.id.text_state_des);

        imageStateView.setImageResource(R.drawable.state_loading_drawable);
        textStateDes.setText("");
    }

    public static void updateEmptyStateView(View stateView) {
        if (stateView == null) return;

        ImageView imageStateView = stateView.findViewById(R.id.image_state_view);
        TextView textStateDes = stateView.findViewById(R.id.text_state_des);

        imageStateView.setImageResource(R.drawable.state_empty_drawable);
        textStateDes.setText(R.string.state_empty_no_data);
    }

    public static void updateErrorStateView(View stateView, String message) {
        if (stateView == null) return;

        ImageView imageStateView = stateView.findViewById(R.id.image_state_view);
        TextView textStateDes = stateView.findViewById(R.id.text_state_des);

        imageStateView.setImageResource(R.drawable.state_error_drawable);
        textStateDes.setText(message);
    }

}
