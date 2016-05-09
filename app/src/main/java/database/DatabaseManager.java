package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.internal.ParcelableSparseArray;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;

import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntryTheseDays;
import my.MyDate;
import my.MyMoment;
import my.TableFiled;

/**
 * Created by 宇 on 2016/3/12.
 */
public class DatabaseManager implements TableFiled {

    private SQLHelper dbHelper;
    private static DatabaseManager instance = null;
    private SQLiteDatabase sqLiteDatabase;

    private DatabaseManager(Context context) {
        sqLiteDatabase = (new SQLHelper(context)).getWritableDatabase();
    }

    /**
     * 获取本类对象实例
     */
    public static final DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        if (instance.sqLiteDatabase.isOpen() == false) {
            instance.sqLiteDatabase = instance.dbHelper.getWritableDatabase();
        }
        return instance;
    }

    //1.1 日程-增 4.19
    public void insertSchedule(EntrySchedule entrySchedule) {
        sqLiteDatabase.insert("Schedule", null, entrySchedule.toContentValues());
    }

    //1.2 日程-查
    public ArrayList<Entry> getSchedule() {
        ArrayList<Entry> ScheduleList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from Schedule order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntrySchedule entrySchedule = new EntrySchedule(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entrySchedule.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entrySchedule.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entrySchedule.setAnnotation(c.getString(c.getColumnIndex(TableFiled.ANNOTATION)));
                entrySchedule.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
                entrySchedule.setStatus(c.getString(c.getColumnIndex(TableFiled.STATUS)));

                entrySchedule.setDate_begin(c.getString(c.getColumnIndex(TableFiled.DATE_begin)));
                entrySchedule.setDate_end(c.getString(c.getColumnIndex(TableFiled.DATE_end)));
                entrySchedule.setAlert(c.getString(c.getColumnIndex(TableFiled.DATE_alert)));
                entrySchedule.setDate_alert(c.getString(c.getColumnIndex(TableFiled.DATE_alert)));
                entrySchedule.setLocation(c.getString(c.getColumnIndex(TableFiled.LOCATION)));
                ScheduleList.add(entrySchedule);

            } while (c.moveToNext());
        }
        c.close();
        close();
        return ScheduleList;
    }

    public ArrayList<EntrySchedule> readSchedule_byDate(MyDate myDate) {
        ArrayList<EntrySchedule> arr = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from Schedule", null);
        while (c.moveToNext()) {
            arr.add(new EntrySchedule(
                    c.getInt(c.getColumnIndex(TableFiled.ID)),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    c.getString(c.getColumnIndex(TableFiled.DATE_begin)),
                    c.getString(c.getColumnIndex(TableFiled.DATE_end))
            ));
        }
        c.close();
        return arr;
    }

    //1.3 日程-删
    public void deleteSchedule(int id) {
        sqLiteDatabase.delete("Schedule", "_id = ?", new String[]{String.valueOf(id)});
    }

    //1.4 日程-改
    public void updateSchedule(int id, EntrySchedule entrySchedule) {
        sqLiteDatabase.update("Schedule", entrySchedule.toContentValues(), "_id = ?", new String[]{String.valueOf(id)});
    }

    //2.1 这两天-增 4.19
    public void insertTheseDays(EntryTheseDays entryTheseDays) {
        sqLiteDatabase.insert("TheseDays", null, entryTheseDays.toContentValues());
    }

    //2.2 这两天-查
    public ArrayList<Entry> getTheseDays() {
        ArrayList<Entry> TheseDaysList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from TheseDays order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntryTheseDays entryTheseDays = new EntryTheseDays(c.getString(c.getColumnIndex(TITLE)));
                entryTheseDays.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entryTheseDays.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryTheseDays.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
                entryTheseDays.setStatus(c.getString(c.getColumnIndex(TableFiled.STATUS)));
                TheseDaysList.add(entryTheseDays);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return TheseDaysList;
    }

    //2.3 这两天-删
    public void deleteTheseDays(int id) {
        sqLiteDatabase.delete("TheseDays", "_id = ?", new String[]{String.valueOf(id)});
    }

    //2.4 这两天-改
    public void updateTheseDays(int id, EntryTheseDays entryTheseDays) {
        sqLiteDatabase.update("TheseDays", entryTheseDays.toContentValues(), "_id = ?", new String[]{String.valueOf(id)});
    }

    //3.1 DDL-增 4.19
    public void insertDDL(EntryDeadLine entryDeadLine) {
        sqLiteDatabase.insert("DDL", null, entryDeadLine.toContentValues());
    }

    //3.2 DDL-查
    public ArrayList<Entry> getDDL() {
        ArrayList<Entry> DDLList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from DDL order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntryDeadLine entryDeadLine = new EntryDeadLine(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryDeadLine.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entryDeadLine.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryDeadLine.setAnnotation(c.getString(c.getColumnIndex(TableFiled.ANNOTATION)));
                entryDeadLine.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
                entryDeadLine.setStatus(c.getString(c.getColumnIndex(TableFiled.STATUS)));

                entryDeadLine.setAlert(c.getString(c.getColumnIndex(TableFiled.DATE_alert)));
                entryDeadLine.setDate_alert(c.getString(c.getColumnIndex(TableFiled.DATE_alert)));
                entryDeadLine.setLocation(c.getString(c.getColumnIndex(TableFiled.LOCATION)));

                entryDeadLine.setTodo_duration(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.TODO_DURATION))));
                entryDeadLine.setTodo_numbers(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.TODO_NUMBERS))));
                entryDeadLine.setDate_ddl(c.getString(c.getColumnIndex(TableFiled.DATE_ddl)));

                DDLList.add(entryDeadLine);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return DDLList;
    }

    //3.3 DDL-删
    public void deleteDDL(int id) {
        sqLiteDatabase.delete("DDL", "_id = ?", new String[]{String.valueOf(id)});
    }

    //3.4 DDL-改
    public void updateDDL(int id, EntryDeadLine entryDeadLine) {
        sqLiteDatabase.update("DDL", entryDeadLine.toContentValues(), "_id = ?", new String[]{String.valueOf(id)});
    }

    //5.1 速记-增  4.19
    public void insertShortHand(EntryShortHand shortHand) {
        sqLiteDatabase.insert("ShortHand", null, shortHand.toContentValues());
    }
    public void insert(Entry entry){
        //以这个为近似的例子，采用一句话来完成insert和update操作。
        sqLiteDatabase.insert(entry.getClass().getSimpleName(),null,entry.toContentValues());
    }

    //5.2 速记-查询
    public ArrayList<Entry> getShortHand() {
        ArrayList<Entry> shortHandList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from ShortHand order by date_create desc", null);
        while (c.moveToNext()) {
            shortHandList.add(new EntryShortHand(
                    c.getInt(c.getColumnIndex(TableFiled.ID)),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)))
            ));
        }
//        if (c.moveToFirst()) {
//            do {
//                EntryShortHand entryShortHand = new EntryShortHand(c.getString(c.getColumnIndex(TableFiled.TITLE)));
//                entryShortHand.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
//                entryShortHand.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
//                entryShortHand.setAnnotation(c.getString(c.getColumnIndex(TableFiled.ANNOTATION)));
//                entryShortHand.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
//                entryShortHand.setStatus(c.getString(c.getColumnIndex(TableFiled.STATUS)));
//
//                entryShortHand.setIsUpper(c.getString(c.getColumnIndex(TableFiled.DATE_upper)));
//                shortHandList.add(entryShortHand);
//            } while (c.moveToNext());
//        }
        c.close();
//        close();
        return shortHandList;
    }

    //5.3 速记-删
    public void deleteShortHand(int id) {
        sqLiteDatabase.delete("ShortHand", "_id = ?", new String[]{String.valueOf(id)});
    }

    //5.4 速记-改
    public void updateShortHand(int id, EntryShortHand entryShortHand) {
        sqLiteDatabase.update("ShortHand", entryShortHand.toContentValues(), "_id = ?", new String[]{String.valueOf(id)});
    }


    //释放内存..
    public void close() {
        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
        if (dbHelper != null)
            dbHelper.close();
        if (instance != null)
            instance = null;
    }

}
