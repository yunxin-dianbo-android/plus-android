package com.zhiyicx.thinksnsplus.utils;

import com.zhiyicx.baseproject.config.ApiConfig;

public class MyUtils {
    public static String getImagePath(String imgId) {
        return ApiConfig.APP_DOMAIN + ApiConfig.APP_PATH_STORAGE_UPLAOD_FILE + "/" + imgId;
    }

    public static String getImagePath(String imgId, int width) {
        return ApiConfig.APP_DOMAIN + ApiConfig.APP_PATH_STORAGE_UPLAOD_FILE + "/" + imgId + "?w=" + width;
    }


    /**
     * 转换时间格式
     *
     * @param time
     * @return
     */
    public static String timeFormatFor00(long time) {
        if (time < 10) {
            return "0" + time;
        }
        if (time < 60) {
            return "" + time;
        }
        return "00";
    }

    /**
     * 转换时间格式
     *
     * @param time
     * @return
     */
    public static String timeFormatFor0(long time) {
        if (time < 10) {
            return "0" + time;
        }
        if (time < 60) {
            return "" + time;
        }
        return "0";
    }

//    /**
//     * 把时间转换成00:00
//     *
//     * @param position
//     * @return
//     */
//    public static String timeFormat1(long position) {
//        //1524550200 1 524 550
//        position = timeIntToLong(position);
//        long time = position / 1000;
//        if (time < 60) {
//            return "00:" + timeFormatFor00(time);
//        }
//        long m = time / 60;
//        long s = time % 60;
//        if (m < 60) {
//            return timeFormatFor00(m) + ":" + timeFormatFor00(s);
//        }
//        return time + ":" + timeFormatFor00(s);
//    }


    /**
     * 把时间转换成00:00
     *
     * @param position
     * @return
     */
    public static String timeFormatOneZero(long position) {
        long time = position / 1000;
        if (time < 60) {
            return "0:" + timeFormatFor00(time);
        }
        long m = time / 60;
        long s = time % 60;
        if (m < 60) {
            return timeFormatFor0(m) + ":" + timeFormatFor00(s);
        }
        return time + ":" + timeFormatFor00(s);
    }

    /**
     * 把时间转换成00:00
     *
     * @param position
     * @return
     */
    public static String timeFormat(long position) {
        long time = position / 1000;
        if (time < 60) {
            return "00:" + timeFormatFor00(time);
        }
        long m = time / 60;
        long s = time % 60;
        if (m < 60) {
            return timeFormatFor00(m) + ":" + timeFormatFor00(s);
        }
        return m + ":" + timeFormatFor00(s);
    }

    /**
     * 把时间转换成00:00
     *
     * @param time
     * @return
     */
    public static String timeFormat2(long time) {
        if (time < 60) {
            return "00:" + timeFormatFor00(time);
        }
        long m = time / 60;
        long s = time % 60;
        if (m <= 60) {
            return timeFormatFor00(m) + ":" + timeFormatFor00(s);
        }
        return time + ":" + timeFormatFor00(s);
    }
}
