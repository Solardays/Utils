package com.util.constants;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StatusBarConstants {

    public static final int DEFAULT = 0;
    public static final int MIUI = 1;
    public static final int FLYME = 2;
    public static final int ANDROID6 = 3; // Android 6.0

    @IntDef({DEFAULT, MIUI, FLYME, ANDROID6})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

}
