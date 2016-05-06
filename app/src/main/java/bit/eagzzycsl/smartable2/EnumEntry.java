package bit.eagzzycsl.smartable2;


public enum EnumEntry {
    deadLine(1), note(2), schedule(3), shortHand(4), someDay(5), theseDays(6), trigger(7);
    private int priority;

    EnumEntry(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
