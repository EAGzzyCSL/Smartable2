package test;

import java.util.ArrayList;

import entry.EntrySchedule;
import my.MyTime;


public class ScheduleCaseProvider {
    //just for test
    public static ArrayList<EntrySchedule>[] schedules = new ArrayList[]{
            null,
            null,null,null,null,null,
            null,null,null,null,null,
            /*index 11*/
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(1, "3:00-8:00", new MyTime(3, 0), new MyTime(8, 0)));


                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(1, "5:00-7:00", new MyTime(5, 0), new MyTime(7, 0)));


                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(1, "12:00-14:00", new MyTime(12, 0), new MyTime(14, 0)));


                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(1, "11:00-11:30", new MyTime(11, 0), new MyTime(11, 30)));


                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(1, "18:00-22:00", new MyTime(18, 0), new MyTime(22, 0)));


                }
            },
            /*index 16*/
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(1, "1:00-2:00", new MyTime(1, 0), new MyTime(2, 0)));


                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(2, "4:50:6:00", new MyTime(4, 50), new MyTime(6, 0)));

                }

            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(4, "18:00-20:00", new MyTime(18, 0), new MyTime(20, 0)));
                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(4, "14:00-18:00", new MyTime(14, 0), new MyTime(18, 0)));
                }
            },
            new ArrayList<EntrySchedule>() {
                {
                    this.add(new EntrySchedule(3, "21:53-23:38", new MyTime(21, 53), new MyTime(23, 38)));
                }
            },
            /*index 21*/
            null,null,null,null,null,null,
            null,null,null,null,null,null,
            null,null,null,null,null,null,


    };

    public static ArrayList<EntrySchedule> getSchedule(int day) {
        return schedules[day];
    }
}