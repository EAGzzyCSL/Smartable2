package view;


public enum EnumCalendricViewType {
    Day(1), ThreeDay(3), Week(7), Month(35);

    private int div;


    EnumCalendricViewType(int div) {
        this.div = div;
    }

    public int getDiv() {
        return this.div;
    }

    //与数组中的顺序对应
    static EnumCalendricViewType[] sort = {
            Month,
            Week,
            ThreeDay,
            Day,
    };

    public static EnumCalendricViewType getEnum(int positon) {
        return sort[positon];
    }
}
