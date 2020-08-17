package cc.buddies.cleanarch.adapter;

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
        holder.setImageResource(R.id.image_avatar, R.drawable.message_user_notification_icon);
        holder.setText(R.id.text_name, "张三");
        holder.setText(R.id.text_description, "今天天气好晴朗，处处好风光");
        holder.setText(R.id.text_time, "刚刚");

        Random r = new Random();
        String tipCount = String.valueOf(r.nextInt());
        holder.setText(R.id.text_tips, tipCount);
    }
}
