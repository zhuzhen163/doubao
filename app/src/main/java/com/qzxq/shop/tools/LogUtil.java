package com.qzxq.shop.tools;

import android.util.Log;

/**
 * Created by zhuzhen on 2017/9/1.
 */

public class LogUtil {

    private static final String TAG = "qizi--";
    private static final boolean isDebug = true;// 打包发布时修改为false

    public static void i(String tag, String content) {
        if (isDebug) {
            String log = getTraceInfo() + "  :  " + content;
            Log.i(TAG+tag, log);
        }
    }
    public static void w(String tag, String content) {
        if (isDebug) {
            String log = getTraceInfo() + "  :  " + content;
            Log.w(TAG+tag, log);
        }
    }
    public static void e(String tag, String content) {
        if (isDebug) {
            String log = getTraceInfo() + "  :  " + content;
            Log.e(TAG+tag, log);
        }
    }

    public static void d(String tag, String content) {
        if (isDebug) {
            String log = getTraceInfo() + "  :  " + content;
            Log.d(TAG+tag, log);
        }
    }

    public static void v(String tag, String content) {
        if (isDebug) {
            String log = getTraceInfo() + "  :  " + content;
            Log.v(TAG+tag, log);
        }
    }

    public static void syso(String content) {
        if (isDebug) {
            String log = getTraceInfo() + "  :  " + content;
            System.out.println(log);
        }
    }

    /**
     * 获取堆栈信息
     */
    private static String getTraceInfo() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[2].getClassName();
        int index = className.lastIndexOf('.');
        if (index >= 0) {
            className = className.substring(index + 1, className.length());
        }
        String methodName = stacks[2].getMethodName();
        int lineNumber = stacks[2].getLineNumber();
        sb.append(className).append("->").append(methodName).append("()->").append(lineNumber);
        return sb.toString();
    }

    /**
     * 超出部分截断输出
     * @param tag
     * @param content
     */
    public static void logE(String tag, String content) {
        if (isDebug){
            int p = 2048;
            long length = content.length();
            if (length < p || length == p){
                Log.e(tag, content);
            } else {
                while (content.length() > p) {
                    String logContent = content.substring(0, p);
                    content = content.replace(logContent, "");
                    Log.e(tag, logContent);
                }
                Log.e("OOOOO", content);
            }
        }
    }
}
