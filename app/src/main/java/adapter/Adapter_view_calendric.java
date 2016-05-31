package adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;

import entry.EntrySchedule;
import my.MyDate;
import view.CalendricCombineDayView;
import view.CalendricCombineWeekView;
import view.CalendricPageView;
import view.CalendricViewItemClick;
import view.CalendricViewItemProvider;
import view.EnumCalendricViewType;


public class Adapter_view_calendric extends PagerAdapter {
    /*枚举类型，决定了产生什么样子的日历视图*/
    private EnumCalendricViewType enumViewType;
    /*viewPager的视图集合*/
    private LinkedList<View> pagers;
    private Context context;
    /*三个页面分别命名为abc*/
    private CalendricPageView page_a;
    private CalendricPageView page_b;
    private CalendricPageView page_c;
    /*数据的提供器，用来提供在日历上显示的日程*/
    private CalendricViewItemProvider itemProvider;
    /*当日历上内容被点击的时候执行的事件*/
    private CalendricViewItemClick calendricViewItemClick;

    /*构造方法中创建视图*/
    public Adapter_view_calendric(
            Context context,
            EnumCalendricViewType enumViewType,
            CalendricViewItemProvider itemProvider,
            CalendricViewItemClick calendricViewItemClick) {
        this.context = context;
        this.pagers = new LinkedList<>();
        this.itemProvider = itemProvider;
        this.enumViewType = enumViewType;
        this.calendricViewItemClick = calendricViewItemClick;
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
                CalendricCombineWeekView a = new CalendricCombineWeekView(context, null);
                pagers.add(a);
                CalendricCombineWeekView b = new CalendricCombineWeekView(context, null);
                pagers.add(b);
                CalendricCombineWeekView c = new CalendricCombineWeekView(context, null);
                pagers.add(c);
                this.page_a = a;
                this.page_b = b;
                this.page_c = c;
                break;
            }
            case Month: {
                break;
            }
        }
        //TODO CombineView的布局参数问题

    }

    /*获取枚举类供视图来判断自己是什么视图*/
    public EnumCalendricViewType getEnumViewType() {
        return this.enumViewType;
    }

    /*获取三个页面*/
    public CalendricPageView getPage_a() {
        return this.page_a;
    }

    public CalendricPageView getPage_b() {
        return this.page_b;
    }

    public CalendricPageView getPage_c() {
        return this.page_c;
    }

    /*从内容提供器提供内容供视图调用*/
    public ArrayList<EntrySchedule> getScheduleFromItemProvider(MyDate date) {
        return itemProvider.getItems(date);
    }

    /*供视图调用传递点击事件操作到视图*/
    public CalendricViewItemClick getCalendricViewItemClick() {
        return this.calendricViewItemClick;
    }


    /*覆写viewpager中的方法*/
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
