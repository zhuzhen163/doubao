package com.qzxq.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseFragment;
import com.qzxq.shop.presenter.MineFragmentPresenter;
import com.qzxq.shop.tools.StringUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.view.MineFragmentView;

import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * Created by zhuzhen
 */

public class MineFragment extends BaseFragment<MineFragmentPresenter> implements MineFragmentView {

    @BindView(R.id.civ_headImage)
    CircleImageView civ_headImage;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_ptMoney)
    TextView tv_ptMoney;
    @BindView(R.id.tv_accountCenter)
    TextView tv_accountCenter;
    @BindView(R.id.tv_customService)
    TextView tv_customService;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragmentPresenter.getUserAccount();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected MineFragmentPresenter loadMPresenter() {
        return new MineFragmentPresenter();
    }

    @Override
    protected void initListener() {
        tv_accountCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startAccountCenterActivity(mContext);
            }
        });
        tv_customService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startCustomServiceActivity(mContext);
            }
        });
        civ_headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startLoginActivity(mContext);
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    @Override
    public void getAccountSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String data = jsonObject.getString("data");
            if (StringUtils.isNotBlank(data)){
                tv_ptMoney.setText(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAccountFail(String s) {

    }
}
