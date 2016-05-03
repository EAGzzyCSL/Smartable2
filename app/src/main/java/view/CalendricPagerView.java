package view;

import java.util.ArrayList;

import entry.EntrySchedule;
import my.MyDate;

public interface CalendricPagerView {
    void setEntrySchedule(MyDate date, ArrayList<EntrySchedule> schedules);

    int myGetScroll();

    void mySetScroll(int y);

}
