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

public abstract class CalendricPageView<T extends CalendricSimpleView> extends FrameLayout {
    /*嵌套在simple外面使其能够滚动*/
    protected ScrollView scrollView;
    protected CalendricSimpleView simpleView;

    @SuppressWarnings("unchecked")

    public CalendricPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        scrollView = (ScrollView) findViewById(R.id.view_calendric_scrollView);

        simpleView = (T) findViewById(R.id.view_calendricSimpleView);

    }

    /*将日程的数据传动到page中*/
    protected void transData(MyMoment myMoment,
                             ArrayList<EntrySchedule> schedules,
                             CalendricViewItemClick calendricViewItemClick) {
        simpleView.setEntrySchedule(schedules);
        simpleView.setCalendricViewItemClick(calendricViewItemClick);
        simpleView.setViewDate(myMoment);
    }

    /*获取当前scrollView的垂直滚动*/
    public int myGetScroll() {
        return scrollView.getScrollY();
    }

    /*设置当前scrollView的垂直滚动*/
    public void mySetScroll(int y) {
        scrollView.setScrollY(y);
    }

    /*将scrollView滚动到当前时间*/
    public void scrollToCurrentTime(MyTime myTime) {
        scrollView.setScrollY(simpleView.getScrollYCurrentToCenter(this.getHeight(), myTime));
    }

    /*获取布局id*/
    public abstract int getLayoutId();
}
