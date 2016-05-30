package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;

/*所有的单页日历视图均实现该接口*/
public abstract class CalendricPagerView<T extends CalendricSimpleView> extends FrameLayout {
    protected ScrollView scrollView;
    protected CalendricSimpleView simpleView;

    public CalendricPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        scrollView = (ScrollView) findViewById(R.id.view_calendric_scrollView);
        simpleView = (T) findViewById(R.id.view_calendricSimpleView);

    }


    protected void transData(MyMoment myMoment,
                             ArrayList<EntrySchedule> schedules,
                             CalendricViewItemClick calendricViewItemClick) {
        simpleView.setEntrySchedule(schedules);
        simpleView.setCalendricViewItemClick(calendricViewItemClick);
        simpleView.setViewDate(myMoment);
    }

    public int myGetScroll() {
        return scrollView.getScrollY();
    }

    public void mySetScroll(int y) {
        scrollView.setScrollY(y);
    }

    public void scrollToCurrentTime(MyTime myTime) {
        scrollView.setScrollY(simpleView.getScrollYCurrentToCenter(this.getHeight(), myTime));
    }

    public abstract int getLayoutId();
}
