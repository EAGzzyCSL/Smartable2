package view;

import java.util.ArrayList;

import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;

/*所有的单页日历视图均实现该接口*/
public interface CalendricPagerView {

    void transData(MyMoment myMoment, ArrayList<EntrySchedule> schedules, CalendricViewItemClick calendricViewItemClick);

    int myGetScroll();

    void mySetScroll(int y);

    void scrollToCurrentTime(MyTime myTime);
}
