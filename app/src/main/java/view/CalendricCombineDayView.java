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


public class CalendricCombineDayView extends FrameLayout {
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


    public ArrayList<EntrySchedule> schedules;

    public void setEntrySchedule(ArrayList<EntrySchedule> schedules) {
        this.schedules = schedules;
        calendricSimpleDayView.setBussiness(schedules);

    }

    public ArrayList<EntrySchedule> getEnetrySchedule() {
        return this.schedules;
    }

    public void setTextView_showDay(String text) {
        textView_showDay.setText(text);
    }

    public int myGetScroll() {
        return scrollView.getScrollY();
    }

    public void mySetScroll(int y) {
        scrollView.setScrollY(y);
    }
}
