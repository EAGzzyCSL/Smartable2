package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.EnumEntry;
import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntryTheseDays;
import my.MyDate;
import my.MyMoment;
import my.TableFiled;
import view.EnumCalendricViewType;

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

//    //1.1 日程-增 4.19
//    public void insertSchedule(EntrySchedule entrySchedule) {
//        sqLiteDatabase.insert("Schedule", null, entrySchedule.toContentValues());
//    }

    //1.2 日程-查
    public ArrayList<Entry> getSchedule() {
        ArrayList<Entry> ScheduleList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from Schedule order by date_create desc", null);
        while (c.moveToNext()) {
            ScheduleList.add(new EntrySchedule(
                    c.getInt(c.getColumnIndex(TableFiled.ID)),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                    c.getString(c.getColumnIndex(TableFiled.STATUS)),

                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_begin))),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_end))),
                    c.getString(c.getColumnIndex(TableFiled.ALERT)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_alert))),
                    c.getString(c.getColumnIndex(TableFiled.LOCATION))
            ));
        }
        c.close();
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

//    //2.1 这两天-增 4.19
//    public void insertTheseDays(EntryTheseDays entryTheseDays) {
//        sqLiteDatabase.insert("TheseDays", null, entryTheseDays.toContentValues());
//    }

    //2.2 这两天-查
    public ArrayList<Entry> getTheseDays() {
        ArrayList<Entry> TheseDaysList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from TheseDays order by date_create desc", null);
        while (c.moveToNext()) {
            TheseDaysList.add(new EntryTheseDays(
                    c.getInt(c.getColumnIndex(TableFiled.ID)),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                    c.getString(c.getColumnIndex(TableFiled.STATUS))
            ));
        }
        c.close();
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

//    //3.1 DDL-增 4.19
//    public void insertDDL(EntryDeadLine entryDeadLine) {
//        sqLiteDatabase.insert("DDL", null, entryDeadLine.toContentValues());
//    }

    //3.2 DDL-查
    public ArrayList<Entry> getDDL() {
        ArrayList<Entry> DDLList = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from DDL order by date_create desc", null);
        while (c.moveToNext()) {
            DDLList.add(new EntryDeadLine(
                    c.getInt(c.getColumnIndex(TableFiled.ID)),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                    c.getString(c.getColumnIndex(TableFiled.STATUS)),

                    c.getString(c.getColumnIndex(TableFiled.ALERT)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_alert))),
                    c.getString(c.getColumnIndex(TableFiled.LOCATION)),
                    c.getInt(c.getColumnIndex(TableFiled.TODO_DURATION)),
                    c.getInt(c.getColumnIndex(TableFiled.TODO_NUMBERS)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_ddl)))
            ));
        }
        c.close();
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

//    //5.1 速记-增  4.19
//    public void insertShortHand(EntryShortHand shortHand) {
//        sqLiteDatabase.insert("ShortHand", null, shortHand.toContentValues());
//    }


    //5.2 速记-查询
    public ArrayList<Entry> getShortHand() {
        ArrayList<Entry> shortHandList = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("select * from ShortHand order by date_create desc", null);
        while (c.moveToNext()) {
            shortHandList.add(new EntryShortHand(
                    c.getInt(c.getColumnIndex(TableFiled.ID)),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                    c.getString(c.getColumnIndex(TableFiled.STATUS)),
                    c.getString(c.getColumnIndex(TableFiled.IS_UPPER)),
                    MyMoment.createFromString(c.getString(c.getColumnIndex(TableFiled.DATE_upper)))
            ));
        }
        c.close();
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

    //EAGzzyCSL数据库代码修改，增删查改都可以用一句话来实现
    public void create(Entry entry) {
        //以这个为近似的例子，采用一句话来完成insert和update操作。
        sqLiteDatabase.insert(entry.getType().getTableName(), null, entry.toContentValues());
    }

    public void delete(Entry entry) {
        sqLiteDatabase.delete(entry.getType().getTableName(), "_id = ?",
                new String[]{
                        String.valueOf(entry.getId())
                }
        );
    }

    public void update(Entry entry) {
        sqLiteDatabase.update(entry.getType().getTableName(), entry.toContentValues(), "_id = ?",
                new String[]{String.valueOf(entry.getId())});

    }
    //尝试使用类集
    public ArrayList<? extends Entry> read(EnumEntry enumEntry){
        ArrayList<EntrySchedule> arr = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("select * from "+enumEntry.getTableName(), null);
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
}
