package com.doubao.shop.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.tools.SwitchActivityManager;

/**
* @author zhuzhen
* create at 2019/1/15
* description:
*/

public class AgreementClickableSpan extends ClickableSpan {
    String string;
    Context context;

    public AgreementClickableSpan(String str, Context context) {
        super();
        this.string = str;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#FF7020"));
    }

    @Override
    public void onClick(View widget) {
        if (string.contains("斗宝俱乐部“克拉”使用规则")) {
            SwitchActivityManager.loadUrl(context,UrlHelper.WEB_URL+"/pages/xieyi/klrule","");
        }
        else if (string.contains("斗宝俱乐部用户注册协议")) {
            SwitchActivityManager.loadUrl(context,UrlHelper.WEB_URL+"/pages/xieyi/yhzcxy","");
        }
        else if (string.contains("隐私权协议")) {
            SwitchActivityManager.loadUrl(context,UrlHelper.WEB_URL+"/pages/xieyi/ysqxy","");
        }
    }
}
