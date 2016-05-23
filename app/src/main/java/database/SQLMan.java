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
import entry.EntrySomeDay;
import entry.EntryTheseDays;
import my.MyDate;
import my.MyLog;
import my.MyMoment;
import my.TableFiled;

/**
 * Created by 宇 on 2016/3/12.
 */
public class SQLMan implements TableFiled {

    private static SQLMan instance;
    private SQLiteDatabase myDb;

    private SQLMan(Context context) {
        //TODO 数据库版本，数据库名字
        myDb = new SQLHelper(context).getWritableDatabase();
    }

    /**
     * 获取本类对象实例
     */
    public static SQLMan getInstance(Context context) {
        if (instance == null) {
            instance = new SQLMan(context);
        }
        return instance;
    }

    //EAGzzyCSL数据库代码修改，增删查改都可以用一句话来实现
    public void create(Entry entry) {
        //以这个为近似的例子，采用一句话来完成insert和update操作。
        myDb.insert(entry.getType().getTableName(), null, entry.toContentValues());
    }

    public void delete(Entry entry) {
        myDb.delete(entry.getType().getTableName(), "_id = ?",
                new String[]{
                        String.valueOf(entry.getId())
                }
        );
    }

    public void update(Entry entry) {
        myDb.update(entry.getType().getTableName(), entry.toContentValues(), "_id = ?",
                new String[]{String.valueOf(entry.getId())});

    }

    public ArrayList<Entry> readAll() {
        ArrayList<Entry> entries = new ArrayList<>();
        /*note中的内容不应该被读入，不过不需要担心，read会把它跳过*/
        for (EnumEntry e : EnumEntry.values()) {
            if (e == EnumEntry.note) {
                continue;
            }
            //TODO addAll方法可能有内存和效率上的问题
            entries.addAll(read(e));
        }
        return entries;
    }

    public ArrayList<? extends Entry> read(EnumEntry enumEntry, MyDate myDate) {
        //有日期情况下的读取，这么写还真是有点难看明白
        Cursor c = myDb.rawQuery(
                "select * from " + enumEntry.getTableName() +
                        (myDate == null ? "" : " where date(date_begin)= date( ? )"),
                myDate == null ? null : new String[]{
                        myDate.convertToString()
                });

        switch (enumEntry) {
            case schedule: {
                ArrayList<EntrySchedule> arr = new ArrayList<>();
                while (c.moveToNext()) {
                    arr.add(new EntrySchedule(
                            c.getInt(c.getColumnIndex(TableFiled.ID)),
                            c.getString(c.getColumnIndex(TableFiled.TITLE)),
                            c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                            c.getString(c.getColumnIndex(TableFiled.STATUS)),

                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_begin))),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_end))),
                            c.getString(c.getColumnIndex(TableFiled.ALERT)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_alert))),
                            c.getString(c.getColumnIndex(TableFiled.LOCATION))
                    ));
                }
                c.close();
                return arr;
            }
            case theseDays: {
                ArrayList<EntryTheseDays> arr = new ArrayList<>();
                while (c.moveToNext()) {
                    arr.add(new EntryTheseDays(
                            c.getInt(c.getColumnIndex(TableFiled.ID)),
                            c.getString(c.getColumnIndex(TableFiled.TITLE)),
                            c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                            c.getString(c.getColumnIndex(TableFiled.STATUS))
                    ));
                }
                c.close();
                return arr;
            }
            case deadLine: {
                ArrayList<EntryDeadLine> arr = new ArrayList<>();
                while (c.moveToNext()) {
                    arr.add(new EntryDeadLine(
                            c.getInt(c.getColumnIndex(TableFiled.ID)),
                            c.getString(c.getColumnIndex(TableFiled.TITLE)),
                            c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                            c.getString(c.getColumnIndex(TableFiled.STATUS)),

                            c.getString(c.getColumnIndex(TableFiled.ALERT)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_alert))),
                            c.getString(c.getColumnIndex(TableFiled.LOCATION)),
                            c.getInt(c.getColumnIndex(TableFiled.TODO_DURATION)),
                            c.getInt(c.getColumnIndex(TableFiled.TODO_NUMBERS)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_ddl)))
                    ));
                }
                c.close();
                return arr;
            }
            case shortHand: {
                ArrayList<EntryShortHand> arr = new ArrayList<>();
                while (c.moveToNext()) {
                    arr.add(new EntryShortHand(
                            c.getInt(c.getColumnIndex(TableFiled.ID)),
                            c.getString(c.getColumnIndex(TableFiled.TITLE)),
                            c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                            c.getString(c.getColumnIndex(TableFiled.STATUS)),
                            c.getString(c.getColumnIndex(TableFiled.IS_UPPER)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_upper)))
                    ));
                }
                c.close();
                return arr;
            }
            case someDay: {
                ArrayList<EntrySomeDay> arr = new ArrayList<>();
                while (c.moveToNext()){
                    arr.add(new EntrySomeDay(
                            c.getInt(c.getColumnIndex(TableFiled.ID)),
                            c.getString(c.getColumnIndex(TableFiled.TITLE)),
                            c.getString(c.getColumnIndex(TableFiled.ANNOTATION)),
                            new MyMoment(c.getString(c.getColumnIndex(TableFiled.DATE_CREATE))),
                            c.getString(c.getColumnIndex(TableFiled.STATUS))
                    ));
                }
                return arr;
            }
            case trigger: {
                ArrayList<EntrySomeDay> arr = new ArrayList<>();
                return arr;
            }
        }
        return null;
    }

    //尝试使用范型
    public ArrayList<? extends Entry> read(EnumEntry enumEntry) {
        return read(enumEntry, null);
//        Cursor c = myDb.rawQuery("select * from " + enumEntry.getTableName(), null);

    }
    //TODO 提供一个读取数据库中全部内容的方法供宦算法用
}
