package view;

/**
 * Created by eagzzycsl on 6/1/16.
 */
public class CalEntryLayoutPos {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public void setValue(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }
}