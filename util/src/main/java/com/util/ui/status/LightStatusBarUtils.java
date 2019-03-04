package com.util.ui.status;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.util.constants.StatusBarConstants;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 状态栏颜色
 */
public class LightStatusBarUtils {

    //true 修改成功
    public static boolean setLightStatusBar(Activity activity, boolean dark) {
        if(null == activity){
            return false;
        }
        boolean flag = false;
        switch (RomUtils.getLightStatausBarAvailableRomType()) {
            case StatusBarConstants.ANDROID6:
                setAndroidNativeLightStatusBar(activity, dark);
                flag = true;
                break;
            case StatusBarConstants.MIUI:
                flag = setMIUILightStatusBar(activity, dark);
                break;
            case StatusBarConstants.FLYME:
                flag = setFlymeLightStatusBar(activity, dark);
                break;
            case StatusBarConstants.DEFAULT:
                // N/A do nothing
                break;
        }
        // 无语系列：ZTK C2016只能时间和电池图标变色。。。。
        if (RomHelper.isZTKC2016()) {
            flag = false;
        }
        return flag;
    }

    private static boolean setMIUILightStatusBar(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean setFlymeLightStatusBar(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    //注意：魅族6.0以上手机 StatusBar只有背景色为白色时，才能设置为深色字
    @TargetApi(23)
    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decorView = activity.getWindow().getDecorView();
        int systemUi = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if(dark){
            systemUi = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        systemUi = changeStatusBarModeRetainFlag(activity.getWindow(), systemUi);
        decorView.setSystemUiVisibility(systemUi);
        if (RomHelper.isMIUIV9()) {
            // MIUI 9 低于 6.0 版本依旧只能回退到以前的方案
            // https://github.com/QMUI/QMUI_Android/issues/160
            setMIUILightStatusBar(activity, dark);
        }
    }

    @TargetApi(23)
    private static int changeStatusBarModeRetainFlag(Window window,int out){
        int flag = out;
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_FULLSCREEN);
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        return flag;
    }

    private static int retainSystemUiFlag(Window window,int out,int type){
        int flag = out;
        int now = window.getDecorView().getSystemUiVisibility();
        if ((now &  type) == type) {
            flag = flag | type;
        }
        return flag;
    }


}
