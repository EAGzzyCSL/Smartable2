package bit.eagzzycsl.smartable2;


import android.view.View;

import my.TableFiled;

public enum EnumEntry {
    deadLine(1,
            TableFiled.TableName.DDL,
            new int[]{
                    View.GONE,
                    View.VISIBLE,
                    View.VISIBLE,
                    View.VISIBLE,
                    View.VISIBLE
            },
            R.color.colorMyBlue
    ),
    note(2,
            null
    ),
    schedule(3,
            TableFiled.TableName.Schedule,
            new int[]{
                    View.VISIBLE,
                    View.GONE,
                    View.VISIBLE,
                    View.VISIBLE,
                    View.GONE
            },
            R.color.colorMyYellow
    ),
    shortHand(4,
            TableFiled.TableName.ShortHand,
            new int[]{
                    View.GONE,
                    View.GONE,
                    View.GONE,
                    View.GONE,
                    View.GONE
            },
            R.color.colorMyOrange
    ),
    someDay(5,
            TableFiled.TableName.SomeDay,//lily-对有朝一日的可见性进行了修改
            new int[]{
                    View.GONE,
                    View.GONE,
                    View.GONE,
                    View.GONE,
                    View.GONE
            },
            R.color.colorMyRed),
    theseDays(6,
            TableFiled.TableName.TheseDays,
            new int[]{
                    View.GONE,
                    View.GONE,
                    View.GONE,
                    View.GONE,
                    View.GONE
            },
            R.color.colorMyPurple
    ),
    trigger(7,
            TableFiled.TableName.Trigger
    );
    private int priority;
    private String tableName;

    EnumEntry(int priority, String tableName) {
        this.priority = priority;
        this.tableName = tableName;
    }

    private int[] viewVisible;
    //为每一种类型设定一种表示颜色
    private int colorId;

    EnumEntry(int priority, String tableName, int[] viewVisible, int colorId) {
        this(priority, tableName);
        this.viewVisible = viewVisible;
        this.colorId = colorId;
    }

    public int[] getViewVisible() {
        return this.viewVisible;
    }

    public int getColorId() {
        return this.colorId;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getTableName() {
        return this.tableName;
    }

    public static EnumEntry[] fourEnumEntries = new EnumEntry[]{
            EnumEntry.shortHand,
            EnumEntry.deadLine,
            EnumEntry.theseDays,
            EnumEntry.schedule
    };
}
