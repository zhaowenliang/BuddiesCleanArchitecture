package cc.buddies.cleanarch.common.fragment;

import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;

import cc.buddies.cleanarch.R;

/**
 * 单张图片预览Fragment
 */
public class SingleImageFragment extends Fragment {

    private SubsamplingScaleImageView mScaleImageView;

    private String mUrl;

    public SingleImageFragment() {
        this(R.layout.fragment_single_image);
    }

    public SingleImageFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    public static SingleImageFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);

        SingleImageFragment fragment = new SingleImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mUrl = getArguments().getString("url", "");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mScaleImageView = view.findViewById(R.id.scale_image_view);

        // 使用该方式才可以缩放图片，与setMinScale/setMaxScale配合使用。
        mScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillData();
    }

    private void fillData() {
        Glide.with(requireContext())
                .download(mUrl)
                .into(new CustomTarget<File>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        mScaleImageView.setImage(ImageSource.resource(R.drawable.state_404_drawable));
                    }

                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        mScaleImageView.post(() -> {
                            int measuredWidth = mScaleImageView.getMeasuredWidth();
                            int measuredHeight = mScaleImageView.getMeasuredHeight();

                            Pair<Integer, Integer> imageWidthAndHeight = computeImageWidthAndHeight(resource.getAbsolutePath());
                            int imageWidth = imageWidthAndHeight.first;
                            int imageHeight = imageWidthAndHeight.second;

                            // 初始缩放倍数
                            float initScale = computeInitImageScale(imageWidth, imageHeight, measuredWidth, measuredHeight);
                            // 双击缩放倍数
                            float doubleTapZoomScale = computeDoubleTapZoomScale(imageWidth, imageHeight, measuredWidth, measuredHeight);
                            // 最大缩放倍数
                            float maxScale = computeMaxImageScale(imageWidth, imageHeight, measuredWidth, measuredHeight);

                            mScaleImageView.setMaxScale(maxScale);
                            mScaleImageView.setDoubleTapZoomScale(doubleTapZoomScale);

                            ImageSource imageSource = ImageSource.uri(Uri.fromFile(resource));
                            ImageViewState imageViewState = new ImageViewState(initScale, new PointF(0, 0), 0);
                            mScaleImageView.setImage(imageSource, imageViewState);
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private Pair<Integer, Integer> computeImageWidthAndHeight(String filepath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);

        return new Pair<>(options.outWidth, options.outHeight);
    }

    // 计算图片最大缩放倍数
    private float computeMaxImageScale(int imageWidth, int imageHeight, int viewWidth, int viewHeight) {
        float imageRatio = (float) imageHeight / (float) imageWidth;
        float viewRatio = (float) viewHeight / (float) viewWidth;

        // true: 缩放图片宽度；false: 缩放图片高度
        boolean isScaleWidthOrHeight;

        if (viewHeight >= viewWidth) {        // 竖屏
            if (imageWidth >= imageHeight) {  // 竖屏 宽图
                isScaleWidthOrHeight = false;
            } else {                          // 竖屏 高图
                isScaleWidthOrHeight = imageRatio >= viewRatio;
            }
        } else {                              // 横屏
            if (imageWidth <= imageHeight) {  // 横屏 高图
                isScaleWidthOrHeight = true;
            } else {                          // 横屏 宽图
                isScaleWidthOrHeight = imageRatio > viewRatio;
            }
        }

        // 默认宽度缩放倍数为 两倍视图宽/图片宽，高度同理。实际缩放倍数为 Math.max(默认倍数, 实际像素大小 / 视图大小) 。
        float defaultWidthMaxScale = viewWidth * 2F / imageWidth;
        float defaultHeightMaxScale = viewHeight * 2F / imageHeight;

        float scale;
        if (isScaleWidthOrHeight) {
            scale = Math.max(defaultWidthMaxScale, viewWidth * 1.0f / imageWidth);
        } else {
            scale = Math.max(defaultHeightMaxScale, viewHeight * 1.0f / imageHeight);
        }

        return scale;
    }

    // 计算图片初始放大倍数（CenterInside）
    private float computeInitImageScale(int imageWidth, int imageHeight, int viewWidth, int viewHeight) {
//        // 图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
//        if (imageWidth > viewWidth && imageHeight <= viewHeight)
//        // 图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
//        if (imageWidth <= viewWidth && imageHeight > viewHeight)
//        // 图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
//        if (imageWidth < viewWidth && imageHeight < viewHeight)
//        // 图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
//        if (imageWidth > viewWidth && imageHeight > viewHeight)
//        return viewWidth * 1.0f / imageWidth;

        float imageRatio = (float) imageHeight / (float) imageWidth;
        float viewRatio = (float) viewHeight / (float) viewWidth;

        // true: 缩放图片宽度；false: 缩放图片高度
        boolean isScaleWidthOrHeight;

        if (viewRatio >= 1) {        // 竖屏
            if (imageRatio <= 1) {   // 竖屏 宽图
                isScaleWidthOrHeight = true;
            } else {                 // 竖屏 高图
                isScaleWidthOrHeight = imageRatio < viewRatio;
            }
        } else {                     // 横屏
            if (imageRatio >= 1) {   // 横屏 高图
                isScaleWidthOrHeight = false;
            } else {                 // 横屏 宽图
                isScaleWidthOrHeight = imageRatio <= viewRatio;
            }
        }

        float scale;
        if (isScaleWidthOrHeight) {
            scale = viewWidth * 1.0f / imageWidth;
        } else {
            scale = viewHeight * 1.0f / imageHeight;
        }

        return scale;
    }

    // 计算双击缩放倍数（CenterCrop）
    private float computeDoubleTapZoomScale(int imageWidth, int imageHeight, int viewWidth, int viewHeight) {
//        // 图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
//        if (imageWidth > viewWidth && imageHeight <= viewHeight) {
//        // 图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
//        if (imageWidth <= viewWidth && imageHeight > viewHeight) {
//        // 图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
//        if (imageWidth < viewWidth && imageHeight < viewHeight) {
//        // 图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
//        if (imageWidth > viewWidth && imageHeight > viewHeight) {
//        return viewHeight * 1.0f / imageHeight;

        float imageRatio = (float) imageHeight / (float) imageWidth;
        float viewRatio = (float) viewHeight / (float) viewWidth;

        // true: 缩放图片宽度；false: 缩放图片高度
        boolean isScaleWidthOrHeight;

        if (viewHeight >= viewWidth) {        // 竖屏
            if (imageWidth >= imageHeight) {  // 竖屏 宽图
                isScaleWidthOrHeight = false;
            } else {                          // 竖屏 高图
                isScaleWidthOrHeight = imageRatio >= viewRatio;
            }
        } else {                              // 横屏
            if (imageWidth <= imageHeight) {  // 横屏 高图
                isScaleWidthOrHeight = true;
            } else {                          // 横屏 宽图
                isScaleWidthOrHeight = imageRatio > viewRatio;
            }
        }

        float scale;
        if (isScaleWidthOrHeight) {
            scale = viewWidth * 1.0f / imageWidth;
        } else {
            scale = viewHeight * 1.0f / imageHeight;
        }

        return scale;
    }

}
