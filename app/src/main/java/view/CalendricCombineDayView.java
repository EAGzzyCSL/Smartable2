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
public class CalendricCombineDayView<CalendricSimpleDayView> extends CalendricPagerView {
    private TextView textView_showDay;

    public CalendricCombineDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textView_showDay = (TextView) findViewById(R.id.view_calendric_day_textView_showDay);
    }

    @Override
    protected void transData(MyMoment myMoment, ArrayList arrayList, CalendricViewItemClick calendricViewItemClick) {
        super.transData(myMoment, arrayList, calendricViewItemClick);
        textView_showDay.setText(String.valueOf(myMoment.getDay()));

    }

    @Override
    public int getLayoutId() {
        return R.layout.view_calendric_day;
    }


}
