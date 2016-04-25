package database;

import android.content.ContentValues;
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
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, entrySchedule.getTitle());
        cv.put(TableFiled.ANNOTATION, entrySchedule.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, entrySchedule.getDate_create());
        cv.put(TableFiled.STATUS, entrySchedule.getStatus());

        cv.put(TableFiled.DATE_begin, entrySchedule.getDate_begin());
        cv.put(TableFiled.DATE_end, entrySchedule.getDate_end());
        cv.put(TableFiled.ALERT, entrySchedule.getAlert());
        cv.put(TableFiled.DATE_alert, entrySchedule.getDate_alert());
        cv.put(TableFiled.LOCATION, entrySchedule.getLocation());

        sqLiteDatabase.insert("Schedule", null, cv);
    }

    //1.2 日程-查
    public ArrayList<Entry> getSchedule() {
        ArrayList<Entry> ScheduleList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from Schedule order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntrySchedule entrySchedule = new EntrySchedule(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entrySchedule.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entrySchedule.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
                ScheduleList.add(entrySchedule);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return ScheduleList;
    }

    //2.1 这两天-增 4.19
    public void insertTheseDays(EntryTheseDays entryTheseDays) {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, entryTheseDays.getTitle());
        cv.put(TableFiled.ANNOTATION, entryTheseDays.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, entryTheseDays.getDate_create());
        cv.put(TableFiled.STATUS, entryTheseDays.getStatus());
        sqLiteDatabase.insert("TheseDays", null, cv);
    }

    //2.2 这两天-查
    public ArrayList<Entry> getTheseDays() {
        ArrayList<Entry> TheseDaysList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from TheseDays order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntryTheseDays entryTheseDays = new EntryTheseDays(c.getString(c.getColumnIndex(TITLE)));
                entryTheseDays.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entryTheseDays.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
//                entryTheseDays.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                TheseDaysList.add(entryTheseDays);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return TheseDaysList;
    }

    //3.1 DDL-增 4.19
    public void insertDDL(EntryDeadLine entryDeadLine) {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, entryDeadLine.getTitle());
        cv.put(TableFiled.ANNOTATION, entryDeadLine.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, entryDeadLine.getDate_create());
        cv.put(TableFiled.STATUS, entryDeadLine.getStatus());

        cv.put(TableFiled.ALERT, entryDeadLine.getAlert());
        cv.put(TableFiled.DATE_alert, entryDeadLine.getDate_alert());
        cv.put(TableFiled.LOCATION, entryDeadLine.getLocation());

        cv.put(TableFiled.TODO_DURATION, entryDeadLine.getTodo_duration());
        cv.put(TableFiled.TODO_NUMBERS, entryDeadLine.getTodo_numbers());
        cv.put(TableFiled.DATE_ddl, entryDeadLine.getDate_ddl());

        sqLiteDatabase.insert("DDL", null, cv);
    }

    //3.2 DDL-查
    public ArrayList<Entry> getDDL() {
        ArrayList<Entry> DDLList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from DDL order by date_create desc", null);
        if (c.moveToFirst()) {
            do {
                EntryDeadLine entryDeadLine = new EntryDeadLine(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryDeadLine.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entryDeadLine.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
//                entryDeadLine.setTitle(c.getString(c.getColumnIndex(TableFiled.TITLE)));
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
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, shortHand.getTitle());
        cv.put(TableFiled.ANNOTATION, shortHand.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, shortHand.getDate_create());
        cv.put(TableFiled.STATUS, shortHand.getStatus());

        cv.put(TableFiled.IS_UPPER, shortHand.getIsUpper());
        cv.put(TableFiled.DATE_upper, shortHand.getDate_upper());
        sqLiteDatabase.insert("ShortHand", null, cv);
    }

    //5.2 速记-查询
    public ArrayList<Entry> getShortHand() {
        ArrayList<Entry> shortHandList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from ShortHand order by date_create desc", null);
        int i = 0;
        if (c.moveToFirst()) {
            do {
                EntryShortHand entryShortHand = new EntryShortHand(c.getString(c.getColumnIndex(TableFiled.TITLE)));
                entryShortHand.setId(Integer.valueOf(c.getString(c.getColumnIndex(TableFiled.ID))));
                entryShortHand.setDate_create(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE)));
                shortHandList.add(entryShortHand);
                //shortHandList.add(c.getString(c.getColumnIndex(TableFiled.TITLE))); //返回String时，用这个
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
