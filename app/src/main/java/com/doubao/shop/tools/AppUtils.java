package com.doubao.shop.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.doubao.shop.application.ZApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuzhen on 2017/11/2.
 * 工具类
 */

public class AppUtils {

    /**
     * 隐藏键盘
     * @param ctx
     */
    public static void hideInputMethod(Activity ctx) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            View view = ctx.getCurrentFocus();
            if (view != null && imm.isActive()) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }
    /**
     * 获取渠道号
     * @param ctx
     * @return
     */
    public static String getChannelId(Context ctx) {
        String resultData = "doubao";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString("UMENG_CHANNEL");
                        if (TextUtils.isEmpty(resultData)) {
                            resultData = applicationInfo.metaData.getInt("UMENG_CHANNEL") + "";
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(resultData)) {
            resultData = "doubao";
        }
        return resultData;
    }
    /**
     * h获取正确的user-Agent
     * @return
     */
    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(ZApplication.getAppContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //加载网络图片
    public static void setImage(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * 电话号码验证
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^1([3-9][0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson (String jsonData, Class<T> type){
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    /**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String regx0 = "[0-9]{17}X";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(regx0) || text.matches(reg1) || text.matches(regex);
    }

}
