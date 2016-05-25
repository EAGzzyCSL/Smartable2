package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import my.TableFiled;

/**
 * Created by 宇 on 2016/3/12.
 */
public class SQLHelper extends SQLiteOpenHelper implements TableFiled{

    public SQLHelper(Context context) {
        super(context, "db_smartable", null, 1);//// db_smartable 为数据库的“文件名”
    }

    //建表的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //1. 日程-Schedule
        db.execSQL("CREATE TABLE IF NOT EXISTS Schedule("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string,"
                        +TableFiled.DATE_CREATE + " string,"

                        +TableFiled.DATE_begin + " string,"
                        +TableFiled.DATE_end +" string,"
                        +TableFiled.ALERT + " string,"
                        +TableFiled.DATE_alert +" string,"
                        +TableFiled.LOCATION +" string"
                        + ");"
        );

        //2. 这两天-TheseDays
        db.execSQL("CREATE TABLE IF NOT EXISTS TheseDays("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string,"
                        +TableFiled.DATE_CREATE + " string"
                        + ");"
        );

        //3. DDL
        db.execSQL("CREATE TABLE IF NOT EXISTS DDL("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string,"
                        +TableFiled.DATE_CREATE + " string,"

                        +TableFiled.ALERT + " string,"
                        +TableFiled.DATE_alert +" string,"
                        +TableFiled.LOCATION +" string,"

                        +TableFiled.DATE_ddl +" string,"
                        +TableFiled.TODO_DURATION     +" integer,"
                        +TableFiled.TODO_NUMBERS     +" integer"
                        + ");"
        );
        //4. 触发-Trigger
        db.execSQL("CREATE TABLE IF NOT EXISTS Trigger("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string"
                        + ");"
        );
        //5. 速记-ShortHand
        db.execSQL("CREATE TABLE IF NOT EXISTS ShortHand("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string,"
                        +TableFiled.DATE_CREATE + " string,"

                        +TableFiled.IS_UPPER   +" string,"
                        +TableFiled.DATE_upper   +" string"
                        + ");"
        );
        //6. 有朝一日-Someday
        db.execSQL("CREATE TABLE IF NOT EXISTS Someday("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string,"
                        +TableFiled.DATE_CREATE + " string,"
                        +TableFiled.ALERT + " string,"
                        +TableFiled.DATE_alert +" string"
                        + ");"
        );
        //7.1 笔记本 - Notebook
        db.execSQL("CREATE TABLE IF NOT EXISTS Notebook("
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string"
                        + ");"
        );
        //7.2 笔记 - NoteChild
        db.execSQL("CREATE TABLE IF NOT EXISTS NoteChild("
                        //todo 链接外键到 7.1 笔记本 - Notebook
                        +TableFiled.ID +" Integer primary key autoincrement,"
                        +TableFiled.TITLE +" string,"
                        +TableFiled.ANNOTATION +" string,"
                        +TableFiled.STATUS +" string"
                        + ");"
        );
    }

    // 必须有的函数 - 升级数据库的函数
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
