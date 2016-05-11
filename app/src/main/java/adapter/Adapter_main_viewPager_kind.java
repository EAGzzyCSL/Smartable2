package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import decorator.DividerItemDecoration;
import entry.Entry;

public class Adapter_main_viewPager_kind extends PagerAdapter {
    private View[] views;
    private String[] pageTitle;
    private int[] titleSort;


    @Override
    public int getCount() {
        return views.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Adapter_main_viewPager_kind(ArrayList<ArrayList< ? extends Entry>> entries, Context context, String[]pageTitle, int titleSort[]) {
        //在构造方法里完成recycler的创建
        RecyclerView[] pages=new RecyclerView[]{
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
        };
        int i=0;
        for(RecyclerView r:pages){

            r.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            r.setItemAnimator(new DefaultItemAnimator());
            r.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
            r.setAdapter(new Adapter_main_kind_recyclerView(entries.get(titleSort[i++]),context));
        }
        this.views=pages;
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
