package com.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.util.permission.PermissionUtils;

import java.util.LinkedList;

public final class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private static LinkedList<Activity> sActivityList = new LinkedList<>();

    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(@NonNull final Context context) {
        Utils.sApplication = (Application) context.getApplicationContext();
        Utils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param application application
     */
    public static void init(@NonNull final Application application) {
        Utils.sApplication = application;
        Utils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    public static void setTopActivity(final Activity activity) {
        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
        if (sActivityList.contains(activity)) {
            if (!sActivityList.getLast().equals(activity)) {
                sActivityList.remove(activity);
                sActivityList.addLast(activity);
            }
        } else {
            sActivityList.addLast(activity);
        }
    }

    public static LinkedList<Activity> getActivityList() {
        return sActivityList;
    }
}
