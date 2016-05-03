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
}
