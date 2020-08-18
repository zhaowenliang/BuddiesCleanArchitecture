package cc.buddies.cleanarch.helper;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.badge.BadgeDrawable;

/**
 * {@link com.google.android.material.badge.BadgeUtils}只能在material包下使用，复制出两个方法开放使用。
 */
public class BadgeDrawableHelper {

    public static void attachBadgeDrawable(
            @NonNull BadgeDrawable badgeDrawable,
            @NonNull View anchor,
            @Nullable FrameLayout compatBadgeParent) {
        setBadgeDrawableBounds(badgeDrawable, anchor, compatBadgeParent);
        anchor.getOverlay().add(badgeDrawable);
    }

    public static void setBadgeDrawableBounds(
            @NonNull BadgeDrawable badgeDrawable,
            @NonNull View anchor,
            @Nullable FrameLayout compatBadgeParent) {
        Rect badgeBounds = new Rect();
        anchor.getDrawingRect(badgeBounds);
        badgeDrawable.setBounds(badgeBounds);
        badgeDrawable.updateBadgeCoordinates(anchor, compatBadgeParent);
    }

}
