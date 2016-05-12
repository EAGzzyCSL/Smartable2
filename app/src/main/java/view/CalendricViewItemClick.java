package view;

import android.view.View;
import android.view.ViewGroup;

import entry.Entry;
import entry.EntrySchedule;
import my.MyMoment;

/**
 * Created by eagzzycsl on 5/6/16.
 */
public interface CalendricViewItemClick {
    void onItemClick(View v, EntrySchedule entrySchedule);
    void onAddClick(View v, MyMoment m);
}
