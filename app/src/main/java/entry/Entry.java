package entry;

import android.content.ContentValues;

import java.io.Serializable;

import bit.eagzzycsl.smartable2.EnumEntry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
//lily,略作修改:implements Serializable,为了能在页面间传递entry
public abstract class Entry implements Serializable {
    protected String title;
    protected int id;
    protected double priority;

    public Entry(String title) {
        this.title = title;
    }

    public Entry() {

    }

    public double getPriority() {
        return this.priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public EnumEntry getType() {
        if (this instanceof EntryDeadLine) {
            return EnumEntry.deadLine;
        }
        if (this instanceof EntryNotebook) {
            return EnumEntry.notebook;
        }
        if (this instanceof EntryNotebookDetail) {
            return EnumEntry.notebookDetail;
        }
        if (this instanceof EntrySchedule) {
            return EnumEntry.schedule;
        }
        if (this instanceof EntryShortHand) {

            return EnumEntry.shortHand;
        }
        if (this instanceof EntrySomeDay) {
            return EnumEntry.someDay;
        }
        if (this instanceof EntryTheseDays) {
            return EnumEntry.theseDays;
        }
        if (this instanceof EntryTrigger) {
            return EnumEntry.trigger;
        }
        return null;
    }


    public EntryDeadLine castEntryDeadLine() {
        return this instanceof EntryDeadLine ? (EntryDeadLine) this : null;
    }

    public EntryNotebook castEntryNotebook() {

        return this instanceof EntryNotebook ? (EntryNotebook) this : null;
    }

    public EntryNotebookDetail castEntryNoteChild() {
        return this instanceof EntryNotebookDetail ? (EntryNotebookDetail) this : null;
    }

    public EntrySchedule castEntrySchedule() {
        return this instanceof EntrySchedule ? (EntrySchedule) this : null;
    }

    public EntrySomeDay castEntrySomeDay() {
        return this instanceof EntrySomeDay ? (EntrySomeDay) this : null;
    }

    public EntryShortHand castEntryShortHand() {
        return this instanceof EntryShortHand ? (EntryShortHand) this : null;
    }

    public EntryTheseDays castEntryTheseDays() {
        return this instanceof EntryTheseDays ? (EntryTheseDays) this : null;
    }

    public EntryTrigger castEntryTrigger() {
        return this instanceof EntryTrigger ? (EntryTrigger) this : null;
    }

    public abstract ContentValues toContentValues();

}
