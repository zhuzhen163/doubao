package com.qzxq.shop.tools;

/**
 * Created by zhuzhen on 2017/4/17.
 */

import android.view.Gravity;
import android.widget.Toast;

import com.qzxq.shop.application.ZApplication;

/**
 * Toast统一管理类
 */
public class ToastUtil {

    public static Toast toast;
    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(String message) {
        if (StringUtils.isNotBlank(message)){
            if (toast == null){
                toast = Toast.makeText(ZApplication.getAppContext(), message, Toast.LENGTH_SHORT);
            }else {
                toast.setText(message);
            }
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     *Mai中间
     * @param message
     */
    public static void showShortCenter(String message) {
        if (StringUtils.isNotBlank(message)){
            if (toast == null){
               toast = Toast.makeText(ZApplication.getAppContext(), message, Toast.LENGTH_SHORT);
            }else {
                toast.setText(message);
            }
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(String message) {
        if (StringUtils.isNotBlank(message)){
            if (toast == null){
                toast = Toast.makeText(ZApplication.getAppContext(), message, Toast.LENGTH_LONG);
            }else {
                toast.setText(message);
            }
            toast.show();
        }
    }


    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void showCustoms(String message, int duration) {
        if (StringUtils.isNotBlank(message)){
            if (toast == null){
                toast = Toast.makeText(ZApplication.getAppContext(), message, duration);
            }else {
                toast.setText(message);
            }
            toast.show();
        }
    }

}