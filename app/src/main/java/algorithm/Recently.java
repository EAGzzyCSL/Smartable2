package algorithm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntrySomeDay;
import entry.EntryTheseDays;
import entry.EntryTrigger;

/*
author:宦
 */
class Sort implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Entry) o1).getPriority() < ((Entry) o2).getPriority() ? 1 : -1;
    }
}

public class Recently {
//    static ArrayList schedule, note, ans, in;//ans为最后结果，有序，内容与in一样，in是输入Entry
//    static double sea, seb, tria, ddla;
//    static double theseday, shor, dream, upper, ispass;
//
//    static void init() {
//        sea = 4000;
//        seb = 4000;
//        tria = 4000;
//        ddla = 6000;
//        theseday = 1000;
//        shor = 800;
//        ispass = 3;
//        upper = 10;
//        in = new ArrayList();
//        //一下in里面均为测试部分
////        EntryDeadLine a = new EntryDeadLine("汇编");
////        a.setDate_ddl("2016-05-07 00:00:00");
////        in.add(a);
////        EntryTrigger b = new EntryTrigger("编译");
////        b.setDate_begin("2016-05-07 00:00:00");
////        in.add(b);
////        EntrySchedule c = new EntrySchedule("理发");
////        c.setDate_begin("2016-05-05 00:00:00");
////        c.setDate_end("2016-05-07 00:00:00");
////        in.add(c);
////        EntryShortHand d = new EntryShortHand("吃饭");
////        d.setDate_create("2016-05-01 00:00:00");
////        d.setIsUpper("1");
////        in.add(d);
////        EntryShortHand f;
////        f = new EntryShortHand("哈哈");
////        f.setDate_create("2016-05-01 00:00:00");
////        in.add(f);
////        schedule = new ArrayList();
////        note = new ArrayList();
////        ans = new ArrayList();
//    }
//
//    static int gettime(String time) {
//        int s = 0;
//        for (int i = 0; i < 4; i++) {
//            s = s * 10 + time.charAt(i) - '0';
//        }
//        s = s * 24 * 3600 * 365;
//        int mounth = (time.charAt(5) - '0') * 10 + (time.charAt(6) - '0');
//        int day = (time.charAt(8) - '0') * 10 + (time.charAt(9) - '0');
//        int hour = (time.charAt(11) - '0') * 10 + (time.charAt(12) - '0');
//        int mint = (time.charAt(14) - '0') * 10 + (time.charAt(15) - '0');
//        int sec = (time.charAt(17) - '0') * 10 + (time.charAt(18) - '0');
//        s += mounth * 30 * 24 * 3600;
//        s += day * 24 * 3600;
//        s += hour * 3600;
//        s += mint * 60;
//        s += sec;
//        return s;
//    }
//
//    static String getnotime() {
//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = format.format(date);
//        return time;
//    }
//
//    static double getDeadline(EntryDeadLine a) {
//        String no = getnotime();
//        if (no.compareTo(a.getDate_ddl()) > 0) {
//            return 0;
//        }
//        int w = gettime(a.getDate_ddl()) - gettime(no);
//        return ddla / w;
//    }
//
//    static double getShedule(EntrySchedule a) {
//        String no = getnotime();
//        if (no.compareTo(a.getDate_end()) > 0) {
//            return 0;
//        }
//        int w;
//        if (no.compareTo(a.getDate_begin()) > 0) {
//            w = gettime(a.getDate_end()) - gettime(no);
//            return seb * ispass / w;
//        }
//        w = gettime(a.getDate_end()) - gettime(no);
//        int w1 = gettime(a.getDate_begin()) - gettime(no);
//        double s = sea / w1 + seb / w;
//        return s;
//    }
//
//    static double gettrigger(EntryTrigger a) {
//        String no = getnotime();
//        if (no.compareTo(a.getDate_begin()) > 0) {
//            return 0;
//        }
//        int w = gettime(a.getDate_begin()) - gettime(no);
//        return tria / w;
//    }
//
//    static double gettheseday(EntryTheseDays a) {
//        String no = getnotime();
//        int w = gettime(no) - gettime(a.getDate_create());
//        return theseday / w;
//    }
//
//    static double getShorthand(EntryShortHand a) {
//        String no = getnotime();
//        int w = gettime(no) - gettime(a.getDate_create());
//        if (a.getIsUpper() == "1") {
//            return shor * upper / w;
//        }
//        return shor / w;
//    }
//
//    static double getdream(EntrySomeDay a) {
//        String no = getnotime();
//        int w = gettime(no) - gettime(a.getDate_create());
//        return dream / w;
//    }
//
//
//    static void exec() {
//        init();
//        int p = in.size();
//        for (int i = 0; i < p; i++) {
//            Entry c = (Entry) in.get(i);
//            switch (c.getType()) {
//                case deadLine: {
//                    EntryDeadLine a = c.castEntryDeadLine();
//                    a.setPriority(getDeadline(a));
//                    schedule.add(a);
//                    break;
//                }
//                case schedule: {
//                    EntrySchedule a = c.castEntrySchedule();
//                    a.setPriority(getShedule(a));
//                    schedule.add(a);
//                    break;
//                }
//                case trigger: {
//                    EntryTrigger a = c.castEntryTrigger();
//                    a.setPriority(gettrigger(a));
//                    schedule.add(a);
//                }
//                case someDay: {
//                    EntrySomeDay a = (EntrySomeDay) c;
//                    a.setPriority(getdream(a));
//                    note.add(a);
//                }
//                case shortHand: {
//                    EntryShortHand a = (EntryShortHand) c;
//                    a.setPriority(getShorthand(a));
//                    note.add(a);
//                }
//                case theseDays: {
//                    EntryTheseDays a = (EntryTheseDays) c;
//                    a.setPriority(gettheseday(a));
//                    note.add(a);
//                }
//
//            }
//        }
//        Collections.sort(schedule, new Sort());
//        Collections.sort(note, new Sort());
//        int tot = 0;
//        p = schedule.size();
//        int x = 0, y = 0, z = 0;
//        if (p < 9) {
//            for (int i = 0; i < p; i++) {
//                ans.add(schedule.get(i));
//            }
//            tot = p;
//        } else {
//            tot = 9;
//            for (int i = 0; i < 9; i++) {
//                Object c = schedule.get(i);
//                if (c instanceof EntrySchedule) {
//                    x++;
//                } else if (c instanceof EntryTrigger) {
//                    y++;
//                } else z++;
//            }
//            if (x < 2) {
//                x = 0;
//                for (int i = 0; i < p; i++) {
//                    Object c = schedule.get(i);
//                    if (c instanceof EntrySchedule) {
//                        x++;
//                        if (x == 2) break;
//                    }
//                }
//
//            }
//            if (y < 2) {
//                y = 0;
//                for (int i = 0; i < p; i++) {
//                    Object c = schedule.get(i);
//                    if (c instanceof EntryTrigger) {
//                        y++;
//                        if (y == 2) break;
//                    }
//                }
//            }
//            if (z < 2) {
//                z = 0;
//                for (int i = 0; i < p; i++) {
//                    Object c = schedule.get(i);
//                    if (c instanceof EntryDeadLine) {
//                        z++;
//                        if (z == 2) break;
//                    }
//                }
//            }
//            if (x + y + z > 9) {
//                int q = x + y + z - 9;
//                while (q != 0) {
//                    if (z > 2) {
//                        q--;
//                        z--;
//                    }
//                    if (y > 2 && q != 0) {
//                        y--;
//                        q--;
//                    }
//                    if (x > 2 && q != 0) {
//                        x--;
//                        q--;
//                    }
//                }
//            }
//            for (int i = 0; i < p; i++) {
//                Object c = schedule.get(i);
//                if (c instanceof EntrySchedule) {
//                    x--;
//                    ans.add(c);
//                    if (x == 0) break;
//                }
//            }
//            for (int i = 0; i < p; i++) {
//                Object c = schedule.get(i);
//                if (c instanceof EntryTrigger) {
//                    y--;
//                    ans.add(c);
//                    if (y == 0) break;
//                }
//            }
//            for (int i = 0; i < p; i++) {
//                Object c = schedule.get(i);
//                if (c instanceof EntryDeadLine) {
//                    z--;
//                    ans.add(c);
//                    if (z == 0) break;
//                }
//            }
//        }
//        p = note.size();
//        x = 0;
//        y = 0;
//        z = 0;
//        int k = 0;
//        for (int i = 0; i < p; i++) {
//            Object c = note.get(i);
//            if (c instanceof EntryShortHand) {
//                EntryShortHand a = (EntryShortHand) c;
//                if (a.getIsUpper() == "1") {
//                    x++;
//                    ans.add(a);
//                    if (x + tot == 12) break;
//                }
//            }
//        }
//        int prex = 0, prey = 0, prez = 0;
//        while (x + y + k + z + tot < 12) {
//            int f = 0;
//            for (int i = prex; i < p; i++) {
//                Object c = note.get(i);
//                if (c instanceof EntryTheseDays) {
//                    EntryTheseDays a = (EntryTheseDays) c;
//                    ans.add(a);
//                    y++;
//                    f = 1;
//                    prex = i + 1;
//                    break;
//                }
//            }
//            if (x == 0) {
//                for (int i = prey; i < p; i++) {
//                    Object c = note.get(i);
//                    if (c instanceof EntryShortHand) {
//                        EntryShortHand a = (EntryShortHand) c;
//                        ans.add(a);
//                        k++;
//                        f = 1;
//                        prey = i + 1;
//                        break;
//                    }
//                }
//            }
//            for (int i = prez; i < p; i++) {
//                Object c = note.get(i);
//                if (c instanceof EntrySomeDay) {
//                    EntrySomeDay a = (EntrySomeDay) c;
//                    ans.add(a);
//                    z++;
//                    f = 1;
//                    prez = i + 1;
//                    break;
//                }
//            }
//            if (x != 0) {
//                for (int i = prey; i < p; i++) {
//                    Object c = note.get(i);
//                    if (c instanceof EntryShortHand) {
//                        EntryShortHand a = (EntryShortHand) c;
//                        if (a.getIsUpper() == "1") continue;
//                        ans.add(a);
//                        k++;
//                        f = 1;
//                        prey = i + 1;
//                        break;
//                    }
//                }
//            }
//            if (f == 0) break;
//        }
//        Collections.sort(ans, new Sort());
//        p = ans.size();
//        for (int i = 0; i < p; i++) {
//            System.out.println(((Entry) ans.get(i)).getName());
//        }
//    }
//
//    static public void main(String[] args) {
//        exec();
//    }
}
