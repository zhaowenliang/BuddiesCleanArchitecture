package cc.buddies.cleanarch.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import cc.buddies.cleanarch.R;

public class SessionsQuickAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public SessionsQuickAdapter() {
        super(R.layout.layout_message_sessions_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Object o) {
        Random r = new Random();
        int unReadCount = r.nextInt(10);

        String description = unReadCount < 5
                ? "今天天气好晴朗，处处好风光"
                : "今天天气好晴朗，处处好风光。今天天气好晴朗，处处好风光。今天天气好晴朗，处处好风光";

        holder.setImageResource(R.id.image_avatar, R.drawable.message_user_notification_icon);
        holder.setText(R.id.text_name, "张三");
        holder.setText(R.id.text_description, description);
        holder.setText(R.id.text_time, "刚刚");


        holder.setText(R.id.text_tips, String.valueOf(unReadCount));
        holder.setVisible(R.id.text_tips, unReadCount > 0);
    }
}
