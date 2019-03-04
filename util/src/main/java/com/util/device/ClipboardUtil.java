package com.util.device;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.util.LogUtils;

public class ClipboardUtil {
    /**
     * 实现文本复制功能
     */
    public static void copy(String content, Context context) {
        if (context == null || content == null) {
            return;
        }
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb == null) {
            return;
        }
        try {
            cmb.setPrimaryClip(ClipData.newPlainText(null, content.trim()));
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }


    /**
     * 实现粘贴功能
     */
    public static String paste(Context context) {
        if (context == null) {
            return "";
        }
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb == null) {
            return "";
        }
        ClipData clip = null;
        try {
            clip = cmb.getPrimaryClip();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        if (clip != null && clip.getItemCount() > 0) {
            String trim = "";
            try {
                trim = clip.getItemAt(0).coerceToText(context).toString().trim();
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
            return trim;
        } else {
            return "";
        }
    }
}
