package view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import entry.EntrySchedule;
import my.MyTime;
import my.MyUtil;


public class CalendricSimpleWeekView extends CalendricSimpleView {
    private int heightOf2H;
    private int topHead = MyUtil.dpToPxInCode(destiny, 30);
    private int widthOf1Day;
    private int textLeftPad = MyUtil.dpToPxInCode(destiny, 5);
    private int textTopPad = MyUtil.dpToPxInCode(destiny, 10);
    private int hpm;

    public CalendricSimpleWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthOf1Day = (myWidth - 7 * lineWidth) / 8;
        heightOf2H = (myHeight - topHead - 13 * lineWidth) / 13;
        hpm = heightOf2H / 120;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 1; i <= 7; i++) {
            int vx = (widthOf1Day + 1) * i;
            canvas.drawLine(vx, 0, vx, myHeight, paint);
            canvas.drawText(MyUtil.weekEtoC(i), vx + (widthOf1Day - 2 * textSize) / 2, +textTopPad + textSize, paint);
        }
        for (int i = 0; i <= 12; i++) {
            int hy = (topHead + lineWidth) + (heightOf2H + lineWidth) * i;
            canvas.drawLine(widthOf1Day, hy, myWidth, hy, paint);
            canvas.drawText(String.format("%02d", i * 2) + ":00", textLeftPad, hy + (textSize - lineWidth) / 2, paint);
        }

    }

    @Override
    public int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime) {
        //TODO 计算正确的return值
        return 0;
    }

    @Override
    protected void calcEntryLayoutPos(EntrySchedule entrySchedule) {
        int bt = topHead + lineWidth +
                entrySchedule.getDate_begin().getHour() * (heightOf2H / 2 + lineWidth)
                + entrySchedule.getDate_begin().getMinute() * hpm;
        int bm = topHead + lineWidth +
                entrySchedule.getDate_end().getHour() * (heightOf2H / 2 + lineWidth)
                + entrySchedule.getDate_end().getMinute() * hpm;
        int tmp = widthOf1Day + lineWidth;
//                int bl = tmp * schedules.get(i).getWeek() + 1;
//                int br = tmp * (schedules.get(i).getWeek() + 1);
        //为了方便假设都是周三
        int bl = tmp * 3 + 1;
        int br = tmp * (3 + 1);
        Log.i("事件位置", bl + "," + bt + "," + br + "," + bm);
        calEntryLayoutPos.setValue(bl, bt, br, bm);
    }

    @Override
    protected boolean calcPreAddLayoutPos(float eventX, float eventY) {
        //TODO 计算被添加按钮的显示位置
        return false;
    }

}