package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;

/*在最简单的日视图的基础上的嵌套，以提供滚动和左上角角标，同时也是为了降低单页视图的复杂度*/
public class CalendricCombineDayView extends FrameLayout implements CalendricPagerView {
    private TextView textView_showDay;
    private CalendricSimpleDayView calendricSimpleDayView;
    private ScrollView scrollView;

    public CalendricCombineDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_calendric_day, this);
        textView_showDay = (TextView) findViewById(R.id.view_calendric_day_textView_showDay);
        calendricSimpleDayView = (CalendricSimpleDayView) findViewById(R.id.view_calendric_day_calendricSimpleView);
        scrollView = (ScrollView) findViewById(R.id.view_calendric_day_scrollView);
    }


    @Override
    public void transData(MyMoment myMoment, ArrayList<EntrySchedule> schedules, CalendricViewItemClick calendricViewItemClick) {

        calendricSimpleDayView.setEntrySchedule(schedules);
        calendricSimpleDayView.setCalendricViewItemClick(calendricViewItemClick);
        calendricSimpleDayView.setViewDate(myMoment);
        //临时创建一个MyMoment对象来传给日历那边
        //TODO 重构view这块的日历使用。

        textView_showDay.setText(String.valueOf(myMoment.getDay()));
    }


    @Override
    public int myGetScroll() {
        return scrollView.getScrollY();
    }

    @Override
    public void mySetScroll(int y) {
        scrollView.setScrollY(y);
    }

    @Override
    public void scrollToCurrentTime(MyTime myTime) {
        scrollView.setScrollY(calendricSimpleDayView.getScrollYCurrentToCenter(this.getHeight(),myTime));
    }
}
