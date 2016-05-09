package bit.eagzzycsl.smartable2;


import my.TableFiled;

public enum EnumEntry {
    deadLine(1, TableFiled.TableName.DDL),
    note(2, null),
    schedule(3, TableFiled.TableName.Schedule),
    shortHand(4, TableFiled.TableName.ShortHand),
    someDay(5, null),
    theseDays(6, TableFiled.TableName.TheseDays),
    trigger(7, null);
    private int priority;
    private String tableName;

    EnumEntry(int priority, String tableName) {
        this.priority = priority;
        this.tableName = tableName;
    }

    public int getPriority() {
        return this.priority;
    }
    public String getTableName(){
        return this.tableName;
    }
}
