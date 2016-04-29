package adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;

import entry.EntrySchedule;
import view.CalendricCombineDayView;
import view.CalendricPagerView;
import view.CalendricViewItemProvider;
import view.EnumCalendricViewType;


public class Adapter_view_calendric extends PagerAdapter {
    private EnumCalendricViewType enumViewType;

    public EnumCalendricViewType getEnumViewType() {
        return this.enumViewType;
    }

    private LinkedList<View> pagers;
    private Context context;
    private CalendricPagerView page_a;
    private CalendricPagerView page_b;
    private CalendricPagerView page_c;

    public CalendricPagerView getPage_a() {
        return this.page_a;
    }

    public CalendricPagerView getPage_b() {
        return this.page_b;
    }

    public CalendricPagerView getPage_c() {
        return this.page_c;
    }

    private CalendricViewItemProvider itemProvider;

    public ArrayList<EntrySchedule> getScheduleFromItemProvider() {
        return itemProvider.getItems(0);
    }


    public Adapter_view_calendric(Context context, EnumCalendricViewType enumViewType, CalendricViewItemProvider itemProvider) {
        this.context = context;
        this.pagers = new LinkedList<>();
        this.itemProvider = itemProvider;
        this.enumViewType = enumViewType;
        switch (enumViewType) {
            case Day: {
                CalendricCombineDayView a = new CalendricCombineDayView(context, null);
                pagers.add(a);
                CalendricCombineDayView b = new CalendricCombineDayView(context, null);
                pagers.add(b);
                CalendricCombineDayView c = new CalendricCombineDayView(context, null);
                pagers.add(c);
                this.page_a = a;
                this.page_b = b;
                this.page_c = c;
                break;
            }
            case ThreeDay: {
                break;
            }
            case Week: {
                break;
            }
            case Month: {
                break;
            }
        }
        //TODO 布局参数的问题

    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View s = pagers.get(position);
        container.addView(s, 0);
        return s;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View s = pagers.get(position);
        container.removeView(s);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
