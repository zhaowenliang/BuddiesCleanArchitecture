package cc.buddies.cleanarch.main.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.data.db.entity.PostEntity;
import cc.buddies.cleanarch.data.db.relation.PostWithDetail;
import cc.buddies.component.common.utils.DensityUtils;

public class PostsPagedListAdapter extends PagedListAdapter<PostWithDetail, PostsPagedListAdapter.PostsViewHolder> {

    // 图片列表列数，最大比重
    private static final int IMAGES_COLUMN_MAX_WEIGHT = 6;

    // 图片列表列数计算函数
    private Function<Integer, Integer> mImageSpanSizeLookupFunction = position -> {
        PostWithDetail item = getItem(position);
        if (item == null || item.post == null) return 0;
        if (item.post.images == null || item.post.images.isEmpty()) return 0;

        int size = item.post.images.size();
        if (size < 3) {
            return IMAGES_COLUMN_MAX_WEIGHT / size;
        } else {
            return IMAGES_COLUMN_MAX_WEIGHT / 3;
        }
    };

    // 根据索引获取数据
    private Function<Integer, PostWithDetail> mGetItemFunction = this::getItem;

    private OnPostClickViewListener mOnPostClickGoodListener;

    public interface OnPostClickViewListener {
        void onPostClickGood(long postId, boolean addOrCancel);

        void onPostClickComment(long postId);

        void onPostClickShare(long postId);

        void onPostClickImage(View view, List<String> list, int position);
    }

    public PostsPagedListAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    public void setOnPostClickGoodListener(OnPostClickViewListener onPostClickGoodListener) {
        this.mOnPostClickGoodListener = onPostClickGoodListener;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_square_list_item, parent, false);
        return new PostsViewHolder(inflate, IMAGES_COLUMN_MAX_WEIGHT, mImageSpanSizeLookupFunction, mGetItemFunction, mOnPostClickGoodListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        PostWithDetail item = getItem(position);

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
            PostEntity post = item.post;
            String nickname = item.author.nickname;
            String avatar = item.author.avatar;

            Glide.with(holder.imageAvatar)
                    .load(avatar)
                    .into(holder.imageAvatar);

            holder.textNickname.setText(nickname);
            holder.textPostTime.setText(DateUtils.getRelativeTimeSpanString(post.createDate));
            holder.textContent.setText(post.description);

            holder.textGood.setText(String.valueOf(post.goodCount));
            holder.textComment.setText(String.valueOf(post.commentCount));
            holder.textShare.setText(String.valueOf(post.shareCount));

            // 图片列表
            holder.submitImages(item.post.images, getItemId(position));

            boolean hasPraise = item.praiseId > 0;
            ColorStateList colorStateList = ColorStateList.valueOf(hasPraise ? Color.RED : Color.GRAY);
            TextViewCompat.setCompoundDrawableTintList(holder.textGood, colorStateList);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class PostsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAvatar;
        TextView textNickname;
        TextView textPostTime;
        TextView textContent;
        TextView textGood, textComment, textShare;
        RecyclerView imagesRecyclerView;

        ImagesQuickAdapter imagesQuickAdapter;

        public PostsViewHolder(@NonNull View itemView, int imagesColumnMaxWeight,
                               Function<Integer, Integer> imageSpanSizeLookupFunction,
                               Function<Integer, PostWithDetail> getItemFunction,
                               OnPostClickViewListener clickViewListener) {
            super(itemView);
            imageAvatar = itemView.findViewById(R.id.image_avatar);
            textNickname = itemView.findViewById(R.id.text_nickname);
            textPostTime = itemView.findViewById(R.id.text_time);
            textContent = itemView.findViewById(R.id.text_description);
            textGood = itemView.findViewById(R.id.text_good);
            textComment = itemView.findViewById(R.id.text_comment);
            textShare = itemView.findViewById(R.id.text_share);
            imagesRecyclerView = itemView.findViewById(R.id.images_recycler_view);

            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return imageSpanSizeLookupFunction.apply(getAdapterPosition());
                }
            };

            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), imagesColumnMaxWeight);
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
            imagesRecyclerView.setLayoutManager(gridLayoutManager);

            imagesQuickAdapter = new ImagesQuickAdapter();
            imagesRecyclerView.setAdapter(imagesQuickAdapter);

            // 添加分割间距
            if (imagesRecyclerView.getItemDecorationCount() == 0) {
                int itemOffset = DensityUtils.dp2px(itemView.getContext(), 4);
                imagesRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        // 因为使用GridLayoutManager.SpanSizeLookup计算跨列，所以不能使用GridLayoutManager.getSpanCount获取列数。
                        int itemCount = imagesQuickAdapter.getItemCount();
                        int spanCount = Math.min(itemCount, 3);

                        int position = parent.getChildLayoutPosition(view);
                        int column = position % spanCount; // item column

                        outRect.left = column * itemOffset / spanCount; // column * (列间距 * (1f / 列数))
                        outRect.right = itemOffset - (column + 1) * itemOffset / spanCount; // 列间距 - (column + 1) * (列间距 * (1f /列数))
                    }
                });
            }

            // 点击图片
            imagesQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                if (view.getId() == R.id.image_view) {
                    if (getItemFunction != null) {
                        PostWithDetail postWithDetail = getItemFunction.apply(getAdapterPosition());
                        if (postWithDetail == null || postWithDetail.post == null || postWithDetail.post.images == null)
                            return;
                        if (position >= postWithDetail.post.images.size()) return;

                        if (clickViewListener != null) {
                            clickViewListener.onPostClickImage(view, postWithDetail.post.images, position);
                        }
                    }
                }
            });

            textGood.setOnClickListener(v -> {
                if (clickViewListener != null) {
                    if (getItemFunction != null) {
                        PostWithDetail postWithDetail = getItemFunction.apply(getAdapterPosition());
                        boolean hasPraise = postWithDetail.praiseId > 0;

                        if (!hasPraise) {
                            v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.click_goods_anim));
                        }

                        clickViewListener.onPostClickGood(postWithDetail.post.id, !hasPraise);
                    }
                }
            });

            textComment.setOnClickListener(v -> {
                if (clickViewListener != null) {
                    clickViewListener.onPostClickComment(getAdapterPosition());
                }
            });

            textShare.setOnClickListener(v -> {
                if (clickViewListener != null) {
                    clickViewListener.onPostClickShare(getAdapterPosition());
                }
            });
        }

        public void submitImages(List<String> data, long itemId) {
            imagesQuickAdapter.setInListItemId(itemId);
            imagesQuickAdapter.setNewInstance(data);
        }

        private static class ImagesQuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

            private long inListItemId;

            public ImagesQuickAdapter() {
                super(R.layout.layout_square_list_images_item);
                addChildClickViewIds(R.id.image_view);
            }

            public void setInListItemId(long inListItemId) {
                this.inListItemId = inListItemId;
            }

            @Override
            protected void convert(@NotNull BaseViewHolder holder, String s) {
                ImageView imageView = holder.getView(R.id.image_view);
                // 根据嵌套列表对应itemId，计算当前item唯一id。
                String transitionName = "preview_image_" + inListItemId + "_" + holder.getAdapterPosition();
                ViewCompat.setTransitionName(imageView, transitionName);

                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                    boolean isSingleItem = getItemCount() == 1;
                    ((ConstraintLayout.LayoutParams) layoutParams).dimensionRatio = isSingleItem ? "2:1" : "1:1";
                }

                Glide.with(imageView).load(s).into(imageView);
            }

            @Override
            public int getItemCount() {
                // 数量只能为0、1、2、3、6、9.
                int itemCount = super.getItemCount();
                if (itemCount < 3) {
                    return itemCount;
                } else if (itemCount < 6) {
                    return 3;
                } else if (itemCount < 9) {
                    return 6;
                } else {
                    return 9;
                }
            }
        }
    }

    private static DiffUtil.ItemCallback<PostWithDetail> DIFF_ITEM_CALLBACK = new DiffUtil.ItemCallback<PostWithDetail>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostWithDetail oldItem, @NonNull PostWithDetail newItem) {
            return oldItem.post.id == newItem.post.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostWithDetail oldItem, @NonNull PostWithDetail newItem) {
            return oldItem.post.equals(newItem.post);
        }
    };

}
