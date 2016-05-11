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
import my.MyMoment;
import my.TableFiled;

/**
 * Created by 宇 on 2016/3/12.
 */
public class SQLMan implements TableFiled {

    private static SQLMan instance ;
    private SQLiteDatabase myDb;

    private SQLMan(Context context) {
        //TODO 数据库版本，数据库名字
        myDb = new SQLHelper(context).getWritableDatabase();
    }

    /**
     * 获取本类对象实例
     */
    public static SQLMan getInstance(Context context) {
        if(instance==null){
            instance=new SQLMan(context);
        }
        return instance;
    }

    //3.2 DDL-查
    public ArrayList<Entry> getDDL() {
        ArrayList<Entry> DDLList = new ArrayList<>();
        Cursor c = myDb.rawQuery("select * from DDL order by date_create desc", null);
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







    //5.2 速记-查询
    public ArrayList<Entry> getShortHand() {
        ArrayList<Entry> shortHandList = new ArrayList<>();

        Cursor c = myDb.rawQuery("select * from ShortHand order by date_create desc", null);
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
    //尝试使用范型
    public ArrayList<? extends Entry> read(EnumEntry enumEntry){
        Cursor c = myDb.rawQuery("select * from "+enumEntry.getTableName(), null);
        switch (enumEntry){
            case schedule:{
                ArrayList<EntrySchedule> arr = new ArrayList<>();
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
        return null;
    }
    //TODO 提供一个读取数据库中全部内容的方法供宦算法用
}
