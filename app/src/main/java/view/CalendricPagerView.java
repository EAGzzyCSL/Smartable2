package view;

import java.util.ArrayList;

import entry.EntrySchedule;
import my.MyDate;

/*所有的单页日历视图均实现该接口*/
public interface CalendricPagerView {

    void transData(MyDate date, ArrayList<EntrySchedule> schedules, CalendricViewItemClick calendricViewItemClick);

    int myGetScroll();

    void mySetScroll(int y);
}
