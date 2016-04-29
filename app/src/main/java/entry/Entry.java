package entry;

import java.io.Serializable;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 *
 */
//lily,略作修改:implements Serializable,为了能在页面间传递entry
public abstract class Entry implements Serializable {
    protected String name;
    protected int id;

    public Entry(String name) {
        this.name = name;
    }

    public Entry() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public EntryDeadLine castEntryDeadLine() {
        return this instanceof EntryDeadLine ? (EntryDeadLine) this : null;
    }

    public EntryNotebook castEntryNotebook() {

        return this instanceof EntryNotebook ? (EntryNotebook) this : null;
    }

    public EntryNoteChild castEntryNoteChild() {
        return this instanceof EntryNoteChild ? (EntryNoteChild) this : null;
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

}
