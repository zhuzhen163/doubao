package com.doubao.shop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class GuideViewPagerAdapter extends PagerAdapter {
    public int[] listImage;
    Context context;

    public GuideViewPagerAdapter(Context mContext, int[] list) {
        this.context = mContext;
        listImage = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(listImage[position]);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public int getCount() {
        return listImage.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
