package view;

import android.view.View;
import android.view.ViewGroup;

import entry.Entry;
import entry.EntrySchedule;

/**
 * Created by eagzzycsl on 5/6/16.
 */
public interface CalendricViewItemClick {
    void onItemClick(View v, EntrySchedule entrySchedule);
    //TODO 这里应该传递时间
    void onAddClick(View v, EntrySchedule entrySchedule);
}
