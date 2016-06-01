package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.EntrySchedule;
import my.MyMoment;
import my.MyUtil;


public class CalendricCombineWeekView extends CalendricPageView<CalendricSimpleWeekView> {
    private TextView[] top_day = new TextView[7];
    private int[] top_day_id = new int[]{
            R.id.cal_week_top_day_1,
            R.id.cal_week_top_day_2,
            R.id.cal_week_top_day_3,
            R.id.cal_week_top_day_4,
            R.id.cal_week_top_day_5,
            R.id.cal_week_top_day_6,
            R.id.cal_week_top_day_7,
    };
    private TextView textView_month;

    public CalendricCombineWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < top_day.length; i++) {
            top_day[i] = (TextView) findViewById(top_day_id[i]);
        }
        textView_month = (TextView) findViewById(R.id.textView_month);

    }

    @Override
    public int getLayoutId() {
        return R.layout.view_calendric_week;
    }

    @Override
    protected void transData(MyMoment myMoment, ArrayList<EntrySchedule> schedules, CalendricViewItemClick calendricViewItemClick) {
        super.transData(myMoment, schedules, calendricViewItemClick);
        textView_month.setText(MyUtil.getMonthInChinese(myMoment.getMonth()));
        for (TextView textView : top_day) {
            textView.setText(String.format("%02d", myMoment.getDay()));
//            textView.setBackgroundResource(R.drawable.circle);
            myMoment.dayAdd(1);
        }
        myMoment.dayAdd(1 - EnumCalendricViewType.Week.getDiv());
    }
}
