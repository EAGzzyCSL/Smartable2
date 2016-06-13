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

import bit.eagzzycsl.smartable2.EnumEntry;
import bit.eagzzycsl.smartable2.R;
import decorator.DividerItemDecoration;
import entry.Entry;
import my.MyUtil;

public class Adapter_main_viewPager_kind extends PagerAdapter {
    private View[] views;
    private String[] pageTitle;
    private int[] titleSort;
    private Adapter_recyclerView_entry[] adapters = new Adapter_recyclerView_entry[4];
    private ArrayList<ArrayList<? extends Entry>> entries;


    @Override
    public int getCount() {
        return views.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Adapter_main_viewPager_kind(ArrayList<ArrayList<? extends Entry>> entries, Context context, String[] pageTitle, int titleSort[]) {
        //在构造方法里完成recycler的创建
        this.entries = entries;
        RecyclerView[] pages = new RecyclerView[]{
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(context, R.layout.adapter_main_smart_pager, null),
        };
        int i = 0;
        for (RecyclerView r : pages) {
            r.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            r.setItemAnimator(new DefaultItemAnimator());
            r.addItemDecoration(new DividerItemDecoration(context));
            adapters[i] = new Adapter_recyclerView_entry(entries.get(titleSort[i]), context);
            r.setAdapter(adapters[i++]);
        }
        this.views = pages;
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

    private int getTypeId(Entry entry) {
        return MyUtil.indexOf(EnumEntry.fourEnumEntries, entry.getType());
    }

    @SuppressWarnings("unchecked")
    public void insertEntry(Entry entry) {
        //首先要找到对应的适配器和数据组
        int typeId = getTypeId(entry);
        ((ArrayList<Entry>) entries.get(typeId)).add(entry);
        adapters[typeId].notifyItemInserted(entries.get(typeId).size());
    }

    @SuppressWarnings("unchecked")
    public void updateEntry(Entry entry) {
        int typeId = getTypeId(entry);
        int i = getIndexInEntries(typeId, entry);
        ((ArrayList<Entry>) entries.get(typeId)).set(i, entry);
        adapters[typeId].notifyItemChanged(i);

    }

    public void deleteEntry(Entry entry) {
        int typeId = getTypeId(entry);
        int i = getIndexInEntries(typeId, entry);
        entries.get(typeId).remove(i);
        adapters[typeId].notifyItemRemoved(i);
    }

    private int getIndexInEntries(int typeId, Entry entry) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(typeId).get(i).getId() == entry.getId()) {
                return i;
            }
        }
        return -1;
    }
}
