package view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import entry.EntrySchedule;
import my.MyTime;


public class CalendricSimpleWeekView extends CalendricSimpleView {
    private int widthOf1d;
    private int widthOf1dWithLine;


    @Override
    protected int letTopPad() {
        return toPx(60);
    }

    @Override
    protected int letBottomPad() {
        return toPx(0);
    }

    @Override
    protected int letLeftText() {
        return toPx(40);
    }

    @Override
    protected int letRightPad() {
        return 0;
    }

    public CalendricSimpleWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthOf1d = (grid_right - grid_left) / 7;
        widthOf1dWithLine = widthOf1d + lineSize;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 7; i++) {
            int verLineX = grid_left + (widthOf1d + 1) * i;
            canvas.drawLine(verLineX, 0, verLineX, myHeight, paint);
        }
    }

    @Override
    public int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime) {
        //TODO 计算正确的return值
        return 0;
    }

    @Override
    protected void calcEntryLayoutPos(EntrySchedule entrySchedule) {
        int t = grid_top + lineSize +
                entrySchedule.getDate_begin().getHour() * heightOf1hWithLine
                + entrySchedule.getDate_begin().getMinute() * heightOf1m;
        int b = grid_top + lineSize +
                entrySchedule.getDate_end().getHour() * heightOf1hWithLine
                + entrySchedule.getDate_end().getMinute() * heightOf1m;
//                int bl = tmp * schedules.get(i).getWeek() + 1;
//                int br = tmp * (schedules.get(i).getWeek() + 1);
        //为了方便假设都是周三
        int l = grid_left + widthOf1dWithLine * 3;
        int r = grid_left + widthOf1dWithLine * 4;
        calEntryLayoutPos.setValue(l, t, r, b);
    }

    @Override
    protected void calcPreAddLayoutPos(int x, int y) {
        calEntryLayoutPos.setValue(
                grid_left + x / widthOf1dWithLine * widthOf1dWithLine,
                grid_top + y / heightOf1hWithLine * heightOf1hWithLine,
                grid_left + (x / widthOf1dWithLine + 1) * widthOf1dWithLine,
                grid_top + (y / heightOf1hWithLine + 1) * heightOf1hWithLine
        );
    }

}