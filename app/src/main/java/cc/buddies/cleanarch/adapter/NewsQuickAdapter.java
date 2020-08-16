package cc.buddies.cleanarch.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.domain.model.NewsModel;

public class NewsQuickAdapter extends BaseDelegateMultiAdapter<NewsModel, BaseViewHolder> {

    private static final int MULTI_TYPE_1 = 1;
    private static final int MULTI_TYPE_2 = 2;

    public NewsQuickAdapter() {
        setMultiTypeDelegate(new NewsMultiTypeDelegate());
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, NewsModel model) {
        switch (holder.getItemViewType()) {
            case MULTI_TYPE_1:
                holder.setText(R.id.text_title, model.getTitle());
                holder.setText(R.id.text_author, model.getAuthor_name());
                holder.setText(R.id.text_time, model.getDate());

                ImageView imageDesStart = holder.getView(R.id.image_des_start);
                ImageView imageDesMiddle = holder.getView(R.id.image_des_middle);
                ImageView imageDesEnd = holder.getView(R.id.image_des_end);

                Glide.with(imageDesStart).load(model.getThumbnail_pic_s()).centerCrop().into(imageDesStart);
                Glide.with(imageDesMiddle).load(model.getThumbnail_pic_s02()).centerCrop().into(imageDesMiddle);
                Glide.with(imageDesEnd).load(model.getThumbnail_pic_s03()).centerCrop().into(imageDesEnd);
                break;

            case MULTI_TYPE_2:
                holder.setText(R.id.text_title, model.getTitle());
                holder.setText(R.id.text_author, model.getAuthor_name());
                holder.setText(R.id.text_time, model.getDate());

                ImageView imageDes = holder.getView(R.id.image_des);

                String singleImage = getSingleImage(model);
                if (singleImage == null) {
                    imageDes.setVisibility(View.GONE);
                } else {
                    imageDes.setVisibility(View.VISIBLE);
                    Glide.with(imageDes).load(singleImage).centerCrop().into(imageDes);
                }
                break;
        }
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

    final static class NewsMultiTypeDelegate extends BaseMultiTypeDelegate<NewsModel> {

        public NewsMultiTypeDelegate() {
            addItemType(MULTI_TYPE_1, R.layout.layout_news_list_item);
            addItemType(MULTI_TYPE_2, R.layout.layout_news_list_item2);
        }

        @Override
        public int getItemType(@NotNull List<? extends NewsModel> data, int position) {
            NewsModel model = data.get(position);
            if (TextUtils.isEmpty(model.getThumbnail_pic_s()) || TextUtils.isEmpty(model.getThumbnail_pic_s02()) || TextUtils.isEmpty(model.getThumbnail_pic_s03())) {
                return MULTI_TYPE_2;
            } else {
                return MULTI_TYPE_1;
            }
        }
    }

}
