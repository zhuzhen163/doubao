package com.doubao.shop.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.adapter.PayBeforeCheckAdapter;
import com.doubao.shop.entity.PayBeforeCheckBean;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.widget.xrecyclerview.XRecyclerView;


/**
* @author zhuzhen
* create at 2019/1/21
* description: 支付前检查是否有下架商品
*/

public class PayBeforeCheckDialog extends Dialog {
    Context mContext;
    TextView tv_cancel,tv_ok;
    LinearLayout ll_ok;
    XRecyclerView xrv_pay_before;
    TextView tv_message;
    PayBeforeCheckAdapter adapter;
    PayBeforeCheckBean bean;
    PayBeforeInterFace payBeforeInterFace;

    public void setPayBeforeInterFace(PayBeforeInterFace payBeforeInterFace) {
        this.payBeforeInterFace = payBeforeInterFace;
    }

    public PayBeforeCheckDialog(Context context) {
        super(context, R.style.delete_address_dialog);
        this.mContext = context;
    }

    public interface PayBeforeInterFace{
        void payBefore();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater=LayoutInflater.from(mContext);//将xml布局转换为view
        View view=inflater.inflate(R.layout.pay_before_check_dialog, null);//将xml布局转换为view,里面有listview
        initView(view);

    }

    public void initView(View view) {
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        ll_ok = (LinearLayout) view.findViewById(R.id.ll_ok);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        xrv_pay_before = (XRecyclerView) view.findViewById(R.id.xrv_pay_before);
        xrv_pay_before.setLoadingMoreEnabled(false);
        xrv_pay_before.setPullRefreshEnabled(false);
        xrv_pay_before.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new PayBeforeCheckAdapter(mContext);
        xrv_pay_before.setAdapter(adapter);
        setContentView(view);

        String str = "这位客官~你看中的以下宝贝实在太抢手，已经脱销啦。您再看看别的呗？斗宝俱乐部应有尽有哦~";
        tv_message.setText(Html.fromHtml(str));
        if (bean.getUnsells().size()>0){
            adapter.setDataList(bean.getUnsells());
            adapter.notifyDataSetChanged();
        }

        if ("1".equals(bean.getIsSell())){
            ll_ok.setVisibility(View.GONE);
            tv_cancel.setText("知道了，去重新下单");
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startShopBuyDetailActivity(mContext,"0","","cart");
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(bean.getIsSell())){
                    payBeforeInterFace.payBefore();
                }
                dismiss();
            }
        });
    }

    public void show(PayBeforeCheckBean bean) {
        this.bean = bean;
        super.show();
        if (adapter != null){
            adapter.setDataList(bean.getUnsells());
            adapter.notifyDataSetChanged();

            if ("1".equals(bean.getIsSell())){
                ll_ok.setVisibility(View.GONE);
                tv_cancel.setText("知道了，去重新下单");
            }else {
                ll_ok.setVisibility(View.VISIBLE);
                tv_cancel.setText("再看看");
            }
        }
    }
}
