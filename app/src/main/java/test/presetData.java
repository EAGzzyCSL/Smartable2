package test;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import entry.EntryDeadLine;

/**
 * Created by 宇 on 2016/4/21.
 */
public class presetData {

    public static void getTestDDL(){

       // return getTestDDL1();

//        //日程
//        EntrySchedule entrySchedule = new EntrySchedule("maybeCanRemove");
//
//        entrySchedule.setEntrySchedule(1, "chifan", new Date(2016, 4, 21, 16, 00), new Date(2016, 4, 21, 16, 00));



    }

    //ddl
    public static EntryDeadLine testDDL1(){

        EntryDeadLine ddl = new EntryDeadLine("ddl1");


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date(2016 - 1900,3,21,17,00));//年份要减去1900；月份要注意转换， 1月是0

        ddl.setTitle(simpleDateFormat.format(date));
        ddl.setDate_ddl(date);

        Log.i("TAG---------", date.toString());
        return ddl;
    }

}
