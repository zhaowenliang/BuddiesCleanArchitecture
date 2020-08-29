package cc.buddies.cleanarch.main.adapter;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.entity.UserEntity;
import cc.buddies.cleanarch.data.db.relation.PostWithUser;

public class PostsPagedListAdapter extends PagedListAdapter<PostWithUser, PostsPagedListAdapter.PostsViewHolder> {

    public PostsPagedListAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_square_list_item, parent, false);
        return new PostsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        PostWithUser item = getItem(position);

        if (item == null || item.post == null) {
            // 空数据，placeholder
            holder.imageAvatar.setImageResource(R.drawable.setting_avatar_icon);

            holder.textNickname.setText("");
            holder.textPostTime.setText("");
            holder.textContent.setText("");

            holder.textGood.setText("");
            holder.textComment.setText("");
            holder.textShare.setText("");
        } else {
            UserEntity user = item.user;
            PostEntity post = item.post;

            Glide.with(holder.imageAvatar)
                    .load(user.account)
                    .into(holder.imageAvatar);

            holder.textNickname.setText(user.nickname);
            holder.textPostTime.setText(DateUtils.getRelativeTimeSpanString(post.createDate));
            holder.textContent.setText(post.description);

            holder.textGood.setText(String.valueOf(post.goodCount));
            holder.textComment.setText(String.valueOf(post.commentCount));
            holder.textShare.setText(String.valueOf(post.shareCount));
        }
    }

    static class PostsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAvatar;
        TextView textNickname;
        TextView textPostTime;
        TextView textContent;
        TextView textGood, textComment, textShare;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAvatar = itemView.findViewById(R.id.image_avatar);
            textNickname = itemView.findViewById(R.id.text_nickname);
            textPostTime = itemView.findViewById(R.id.text_time);
            textContent = itemView.findViewById(R.id.text_description);
            textGood = itemView.findViewById(R.id.text_good);
            textComment = itemView.findViewById(R.id.text_comment);
            textShare = itemView.findViewById(R.id.text_share);
        }
    }

    private static DiffUtil.ItemCallback<PostWithUser> DIFF_ITEM_CALLBACK = new DiffUtil.ItemCallback<PostWithUser>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostWithUser oldItem, @NonNull PostWithUser newItem) {
            return oldItem.post.id == newItem.post.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostWithUser oldItem, @NonNull PostWithUser newItem) {
            return oldItem.post.equals(newItem.post);
        }
    };

}
