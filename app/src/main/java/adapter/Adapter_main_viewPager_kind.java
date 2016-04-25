package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class Adapter_main_viewPager_kind extends PagerAdapter {
    private View[] views;
    private String[] pageTitle;
    private int[] titleSort;

    //yu---
    private OnItemClickListener mListener;//yu---
    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        return views.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Adapter_main_viewPager_kind(View[] views, String[] pageTitle, int titleSort[]) {
        this.views = views;
        this.pageTitle = pageTitle;

        this.titleSort = titleSort;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = views[position];
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = views[position];
        container.removeView(v);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return pageTitle[titleSort[position]];
    }



    public interface OnItemClickListener<String>{//yu---
        void onItemClick(int position, String data);
    }

}
