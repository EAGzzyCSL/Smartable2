package algorithm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntrySomeDay;
import entry.EntryTheseDays;
import entry.EntryTrigger;
import my.MyMoment;

/*
author:宦
 */

public class Recently {
    private static Comparator<Entry> myComparator = new Comparator<Entry>() {
        @Override
        public int compare(Entry lhs, Entry rhs) {
            return lhs.getPriority() < rhs.getPriority() ? 1 : -1;
        }
    };
    static double sea, seb, tria, ddla;
    static double theseday, shor, dream, upper, ispass;

    private static void init() {
        sea = 4000;
        seb = 4000;
        tria = 4000;
        ddla = 6000;
        theseday = 1000;
        shor = 800;
        ispass = 3;
        upper = 10;
    }

    private static ArrayList<Entry> createTestDate() {
        ArrayList<Entry> in = new ArrayList<>();
        EntryDeadLine a = new EntryDeadLine("汇编");
        a.setDate_ddl(new MyMoment(2016, 5, 7, 0, 0));
        in.add(a);
        EntrySchedule c = new EntrySchedule("理发");
        c.setDate_begin(new MyMoment(2016, 5, 5, 0, 0));
        c.setDate_end(new MyMoment(2016, 5, 7, 0, 0));
        in.add(c);
        EntryShortHand d = new EntryShortHand("吃饭");
        d.setDate_create(new MyMoment(2016, 5, 1, 0, 0));
        d.setIsUpper("1");
        in.add(d);
        EntryShortHand f;
        f = new EntryShortHand("哈哈");
        f.setDate_create(new MyMoment(2016, 5, 1, 0, 0));
        in.add(f);

        return in;
    }

    private static double getDeadline(EntryDeadLine a) {
        Calendar c = Calendar.getInstance();
        if (a.getDate_ddl().compareToNow(c) <= 0) {
            return 0;
        }
        int w = a.getDate_ddl().computeDiff(c);
        return ddla / w;
    }

    private static double getShedule(EntrySchedule a) {
        Calendar c = Calendar.getInstance();
        if (a.getDate_end().compareToNow(c) <= 0) {
            return 0;
        }
        int w;
        if (a.getDate_begin().compareToNow(c) <= 0) {
            w = a.getDate_end().computeDiff(c);
            return seb * ispass / w;

        }
        w = a.getDate_end().computeDiff(c);
        int w1 = a.getDate_begin().computeDiff(c);
        return sea / w1 + seb / w;
    }

    private static double gettrigger(EntryTrigger a) {
        Calendar c = Calendar.getInstance();
        if (a.getDate_begin().compareToNow(c) <= 0) {
            return 0;
        }

        int w = a.getDate_begin().computeDiff(c);
        return tria / w;
    }

    private static double gettheseday(EntryTheseDays a) {
        int w = -a.getDate_create().computeDiffWithNow();
        return theseday / w;
    }

    private static double getShorthand(EntryShortHand a) {
        int w = -a.getDate_create().computeDiffWithNow();
        if (a.getIsUpper() == "1") {
            return shor * upper / w;
        }
        return shor / w;
    }

    private static double getdream(EntrySomeDay a) {
        int w = -(a.getDate_create().computeDiffWithNow());
        return dream / w;
    }


    private static ArrayList<Entry> exec(ArrayList<Entry> in) {
        init();
        ArrayList<Entry> schedule = new ArrayList<>();
        ArrayList<Entry> note = new ArrayList<>();
        ArrayList<Entry> ans = new ArrayList<>();
        int p = in.size();
        for (int i = 0; i < p; i++) {
            Entry c = in.get(i);
            switch (c.getType()) {
                case deadLine: {
                    EntryDeadLine a = c.castEntryDeadLine();
                    a.setPriority(getDeadline(a));
                    schedule.add(a);
                    break;
                }
                case schedule: {
                    EntrySchedule a = c.castEntrySchedule();
                    a.setPriority(getShedule(a));
                    schedule.add(a);
                    break;
                }
                case trigger: {
                    EntryTrigger a = c.castEntryTrigger();
                    a.setPriority(gettrigger(a));
                    schedule.add(a);
                    break;
                }
                case someDay: {
                    EntrySomeDay a = (EntrySomeDay) c;
                    a.setPriority(getdream(a));
                    note.add(a);
                    break;
                }
                case shortHand: {
                    EntryShortHand a = (EntryShortHand) c;
                    a.setPriority(getShorthand(a));
                    note.add(a);
                    break;
                }
                case theseDays: {
                    EntryTheseDays a = (EntryTheseDays) c;
                    a.setPriority(gettheseday(a));
                    note.add(a);
                    break;
                }

            }
        }
        Collections.sort(schedule, myComparator);
        Collections.sort(note, myComparator);
        Collections.sort(note, myComparator);
        int tot;
        p = schedule.size();
        int x = 0, y = 0, z = 0;
        if (p < 9) {
            for (int i = 0; i < p; i++) {
                ans.add(schedule.get(i));
            }
            tot = p;
        } else {
            tot = 9;
            for (int i = 0; i < 9; i++) {
                Entry c = schedule.get(i);
                if (c instanceof EntrySchedule) {
                    x++;
                } else if (c instanceof EntryTrigger) {
                    y++;
                } else z++;
            }
            if (x < 2) {
                x = 0;
                for (int i = 0; i < p; i++) {
                    Entry c = schedule.get(i);
                    if (c instanceof EntrySchedule) {
                        x++;
                        if (x == 2) break;
                    }
                }

            }
            if (y < 2) {
                y = 0;
                for (int i = 0; i < p; i++) {
                    Entry c = schedule.get(i);
                    if (c instanceof EntryTrigger) {
                        y++;
                        if (y == 2) break;
                    }
                }
            }
            if (z < 2) {
                z = 0;
                for (int i = 0; i < p; i++) {
                    Entry c = schedule.get(i);
                    if (c instanceof EntryDeadLine) {
                        z++;
                        if (z == 2) break;
                    }
                }
            }
            if (x + y + z > 9) {
                int q = x + y + z - 9;
                while (q != 0) {
                    if (z > 2) {
                        q--;
                        z--;
                    }
                    if (y > 2 && q != 0) {
                        y--;
                        q--;
                    }
                    if (x > 2 && q != 0) {
                        x--;
                        q--;
                    }
                }
            }
            for (int i = 0; i < p; i++) {
                Entry c = schedule.get(i);
                if (c instanceof EntrySchedule) {
                    x--;
                    ans.add(c);
                    if (x == 0) break;
                }
            }
            for (int i = 0; i < p; i++) {
                Entry c = schedule.get(i);
                if (c instanceof EntryTrigger) {
                    y--;
                    ans.add(c);
                    if (y == 0) break;
                }
            }
            for (int i = 0; i < p; i++) {
                Entry c = schedule.get(i);
                if (c instanceof EntryDeadLine) {
                    z--;
                    ans.add(c);
                    if (z == 0) break;
                }
            }
        }
        p = note.size();
        x = 0;
        y = 0;
        z = 0;
        int k = 0;
        for (int i = 0; i < p; i++) {
            Entry c = note.get(i);
            if (c instanceof EntryShortHand) {
                EntryShortHand a = (EntryShortHand) c;
                if (a.getIsUpper() == "1") {
                    x++;
                    ans.add(a);
                    if (x + tot == 12) break;
                }
            }
        }
        int prex = 0, prey = 0, prez = 0;
        while (x + y + k + z + tot < 12) {
            int f = 0;
            for (int i = prex; i < p; i++) {
                Entry c = note.get(i);
                if (c instanceof EntryTheseDays) {
                    EntryTheseDays a = (EntryTheseDays) c;
                    ans.add(a);
                    y++;
                    f = 1;
                    prex = i + 1;
                    break;
                }
            }
            if (x == 0) {
                for (int i = prey; i < p; i++) {
                    Entry c = note.get(i);
                    if (c instanceof EntryShortHand) {
                        EntryShortHand a = (EntryShortHand) c;
                        ans.add(a);
                        k++;
                        f = 1;
                        prey = i + 1;
                        break;
                    }
                }
            }
            for (int i = prez; i < p; i++) {
                Entry c = note.get(i);
                if (c instanceof EntrySomeDay) {
                    EntrySomeDay a = (EntrySomeDay) c;
                    ans.add(a);
                    z++;
                    f = 1;
                    prez = i + 1;
                    break;
                }
            }
            if (x != 0) {
                for (int i = prey; i < p; i++) {
                    Entry c = note.get(i);
                    if (c instanceof EntryShortHand) {
                        EntryShortHand a = (EntryShortHand) c;
                        if (a.getIsUpper() == "1") continue;
                        ans.add(a);
                        k++;
                        f = 1;
                        prey = i + 1;
                        break;
                    }
                }
            }
            if (f == 0) break;
        }
        Collections.sort(ans, myComparator);

        return ans;

    }

    public static ArrayList<Entry> sort(ArrayList<Entry> in) {
        ArrayList<Entry> ans = exec(in);
        printResult(ans);
        return ans;
    }

    public static void main(String[] args) {
        sort(createTestDate());
    }

    private static void printResult(ArrayList<Entry> ans) {
        for (Entry entry : ans) {
            System.out.println(entry.getTitle());
        }
    }
}
