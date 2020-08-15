package cc.buddies.cleanarch.adapter;

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

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsItemViewHolder> {

    private List<NewsModel> data;

    public NewsRecyclerAdapter(List<NewsModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_list_item, parent, false);
        return new NewsItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        NewsModel model = data.get(position);

        holder.textTitle.setText(model.getTitle());
        holder.textAuthor.setText(model.getAuthor_name());
        holder.textTime.setText(model.getDate());

        Glide.with(holder.imageDesStart).load(model.getThumbnail_pic_s()).centerCrop().into(holder.imageDesStart);
        Glide.with(holder.imageDesMiddle).load(model.getThumbnail_pic_s02()).centerCrop().into(holder.imageDesMiddle);
        Glide.with(holder.imageDesEnd).load(model.getThumbnail_pic_s03()).centerCrop().into(holder.imageDesEnd);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
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

}
