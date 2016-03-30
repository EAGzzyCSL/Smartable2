package adapter;


import android.app.ActionBar;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;

import entry.EntrySchedule;
import my.MyDate;
import my.MyTime;
import test.ScheduleCaseProvider;
import view.CalendricCombineDayView;


public class Adapter_view_calendric extends PagerAdapter {
    private LinkedList<CalendricCombineDayView> pagers;
    private Context context;
    private MyDate date;

    public LinkedList<CalendricCombineDayView> getMySimpleByDayViews() {
        return this.pagers;
    }

    public Adapter_view_calendric(Context context, MyDate date) {
        this.context = context;
        this.date = date;
        this.pagers = new LinkedList<CalendricCombineDayView>();
        //TODO 布局参数的问题
        CalendricCombineDayView a = new CalendricCombineDayView(context, null);
        a.setEntrySchedule(ScheduleCaseProvider.getSchedule(14));
        a.setTextView_showDay("14");
        pagers.add(a);
        //
        CalendricCombineDayView b = new CalendricCombineDayView(context, null);
        b.setEntrySchedule(ScheduleCaseProvider.getSchedule(15));
        b.setTextView_showDay("15");
        pagers.add(b);
        //
        CalendricCombineDayView c = new CalendricCombineDayView(context, null);
        c.setEntrySchedule(ScheduleCaseProvider.getSchedule(16));
        c.setTextView_showDay("16");
        pagers.add(c);
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

        CalendricCombineDayView s = pagers.get(position);
        container.addView(s, 0);
        return s;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        CalendricCombineDayView s = pagers.get(position);
        container.removeView(s);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
