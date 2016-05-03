package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntryTheseDays;
import my.TableFiled;

/**
 * Created by 宇 on 2016/3/12.
 */
public class DatabaseManager extends Entry implements TableFiled {

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
        sqLiteDatabase.insert("Schedule", null, entrySchedule.toContentValues(entrySchedule));
    }

    //1.2 日程-查
    public ArrayList<Entry> getSchedule() {
        ArrayList<Entry> ScheduleList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from Schedule order by date_create desc", null);
        if (c.moveToFirst()) {
            do{
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

    //2.1 这两天-增 4.19
    public void insertTheseDays(EntryTheseDays entryTheseDays) {
        sqLiteDatabase.insert("TheseDays", null, entryTheseDays.toContentValues(entryTheseDays));
    }

    //2.2 这两天-查
    public ArrayList<Entry> getTheseDays() {
        ArrayList<Entry> TheseDaysList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from TheseDays order by date_create desc", null);
        if (c.moveToFirst()) {
            do{
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

    //3.1 DDL-增 4.19
    public void insertDDL(EntryDeadLine entryDeadLine) {
        sqLiteDatabase.insert("DDL", null, entryDeadLine.toContentValues(entryDeadLine));
    }

    //3.2 DDL-查
    public ArrayList<Entry> getDDL() {
        ArrayList<Entry> DDLList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from DDL order by date_create desc", null);
        if (c.moveToFirst()) {
            do{
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

    //3.3 DDL-查byId
    public EntryDeadLine getDDLById(int id) {
        EntryDeadLine DDL = new EntryDeadLine("testDDL1");

        Cursor c = sqLiteDatabase.rawQuery("select * from ShortHand where _id=?", new String[]{
                String.valueOf(id)
        });
//        Cursor c = sqLiteDatabase.query("DDL", null, null, null, null, null, null);

        int i = 0;
        if (c.moveToFirst()) {
            do {
                //EntryDeadLine entryDeadLine = new EntryDeadLine(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                DDL.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                //DDLList.add(entryDeadLine);
                //shortHandList.add(c.getString(c.getColumnIndex(TableFiled.TITLE))); //返回String时，用这个
            } while (c.moveToNext());
        }
        c.close();
        close();
        return DDL;
    }


    //5.1 速记-增  4.19
    public void insertShortHand(EntryShortHand shortHand) {
        sqLiteDatabase.insert("ShortHand", null, shortHand.toContentValues(shortHand));
    }

    //5.2 速记-查询
    public ArrayList<Entry> getShortHand() {
        ArrayList<Entry> shortHandList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from ShortHand order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntryShortHand entryShortHand = new EntryShortHand(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryShortHand.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entryShortHand.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryShortHand.setAnnotation(c.getString(c.getColumnIndex(TableFiled.ANNOTATION)));
                entryShortHand.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
                entryShortHand.setStatus(c.getString(c.getColumnIndex(TableFiled.STATUS)));

                entryShortHand.setIsUpper(c.getString(c.getColumnIndex(TableFiled.DATE_upper)));
                shortHandList.add(entryShortHand);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return shortHandList;
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
