package cc.buddies.cleanarch.home.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.domain.model.NewsModel;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsModel> data;

    public NewsRecyclerAdapter(List<NewsModel> data) {
        this.data = data;
    }

    private static final int ITEM_TYPE_1 = 0;
    private static final int ITEM_TYPE_2 = 1;

    @Override
    public int getItemViewType(int position) {
        NewsModel model = data.get(position);
        if (TextUtils.isEmpty(model.getThumbnail_pic_s()) || TextUtils.isEmpty(model.getThumbnail_pic_s02()) || TextUtils.isEmpty(model.getThumbnail_pic_s03())) {
            return ITEM_TYPE_2;
        } else {
            return ITEM_TYPE_1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_1) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list_item, parent, false);
            return new NewsItemViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list_item2, parent, false);
            return new NewsItemViewHolder2(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsModel model = data.get(position);

        if (holder.getItemViewType() == ITEM_TYPE_1) {
            NewsItemViewHolder holder1 = (NewsItemViewHolder) holder;

            holder1.textTitle.setText(model.getTitle());
            holder1.textAuthor.setText(model.getAuthor_name());
            holder1.textTime.setText(model.getDate());

            Glide.with(holder1.imageDesStart).load(model.getThumbnail_pic_s()).centerCrop().into(holder1.imageDesStart);
            Glide.with(holder1.imageDesMiddle).load(model.getThumbnail_pic_s02()).centerCrop().into(holder1.imageDesMiddle);
            Glide.with(holder1.imageDesEnd).load(model.getThumbnail_pic_s03()).centerCrop().into(holder1.imageDesEnd);
        } else {
            NewsItemViewHolder2 holder2 = (NewsItemViewHolder2) holder;

            holder2.textTitle.setText(model.getTitle());
            holder2.textAuthor.setText(model.getAuthor_name());
            holder2.textTime.setText(model.getDate());

            String singleImage = getSingleImage(model);
            if (singleImage == null) {
                holder2.imageDes.setVisibility(View.GONE);
            } else {
                holder2.imageDes.setVisibility(View.VISIBLE);
                Glide.with(holder2.imageDes).load(singleImage).centerCrop().into(holder2.imageDes);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private String getSingleImage(NewsModel model) {
        if (!TextUtils.isEmpty(model.getThumbnail_pic_s())) {
            return model.getThumbnail_pic_s();
        }
        if (!TextUtils.isEmpty(model.getThumbnail_pic_s02())) {
            return model.getThumbnail_pic_s02();
        }
        if (!TextUtils.isEmpty(model.getThumbnail_pic_s03())) {
            return model.getThumbnail_pic_s03();
        }
        return null;
    }

    static class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textAuthor;
        TextView textTime;
        ImageView imageDesStart;
        ImageView imageDesMiddle;
        ImageView imageDesEnd;

        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textAuthor = itemView.findViewById(R.id.text_author);
            textTime = itemView.findViewById(R.id.text_time);
            imageDesStart = itemView.findViewById(R.id.image_des_start);
            imageDesMiddle = itemView.findViewById(R.id.image_des_middle);
            imageDesEnd = itemView.findViewById(R.id.image_des_end);
        }
    }

    static class NewsItemViewHolder2 extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textAuthor;
        TextView textTime;
        ImageView imageDes;

        public NewsItemViewHolder2(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textAuthor = itemView.findViewById(R.id.text_author);
            textTime = itemView.findViewById(R.id.text_time);
            imageDes = itemView.findViewById(R.id.image_des);
        }
    }

}
