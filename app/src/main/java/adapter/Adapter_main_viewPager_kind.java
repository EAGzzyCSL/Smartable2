package adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class Adapter_main_viewPager_kind extends PagerAdapter {
    @Override
    public int getCount() {
        return views.size();
    }

    private ArrayList<View> views;

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public Adapter_main_viewPager_kind(ArrayList<View> views) {
        this.views = views;
        Log.i("hello", "##" + views.size());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = views.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = views.get(position);
        container.removeView(v);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }

    private static String[] pageTitle = new String[]{
            "速记",
            "ddl",
            "这两天",
            "有朝一日"
    };
}
