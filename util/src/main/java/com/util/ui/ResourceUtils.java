package com.util.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.*;
import androidx.core.content.ContextCompat;
import com.util.Utils;

public final class ResourceUtils {
    private static Resources resources;

    static {
        resources = Utils.getApp().getResources();
    }

    public static String getString(@StringRes int id) {
        return resources.getString(id);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        return resources.getString(id,formatArgs);
    }

    public static int getColor( @ColorRes int id) {
        return ContextCompat.getColor(Utils.getApp(), id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(Utils.getApp(),id);
    }

    public static int getDimensionPixelSize(@DimenRes int id) {
        return resources.getDimensionPixelSize(id);
    }

    public static View getView(Context context, ViewGroup root, @LayoutRes int resource) {
        return LayoutInflater.from(context).inflate(resource, root, false);
    }

    public static View getViewByView(Context context, ViewGroup root,@LayoutRes int resource) {
        return View.inflate(context,resource,root);
    }

}
