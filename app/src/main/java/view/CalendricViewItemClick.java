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

    void onAddClick(View v, EntrySchedule entrySchedule);
}
