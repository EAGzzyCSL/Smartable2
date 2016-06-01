package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import entry.EntrySchedule;
import my.MyTime;
import my.MyUtil;


public class CalendricSimpleDayView extends CalendricSimpleView {
    /*绘制线条等需要的参数*/
    private int textPadLeft = MyUtil.dpToPxInCode(destiny, 10);//文本和文本左边的空隙
    private int linePadLeft = MyUtil.dpToPxInCode(destiny, 5);//文本和文本右边的空隙
    private int lineLeft = textPadLeft + 3 * textSize + linePadLeft;//线的左端（加入了文字占去的地）
    private int indicateLineY;//表示当前时间的指示线的纵坐标

    //TODO 减少draw的计算量
    @Override
    protected int letTopPad() {
        return MyUtil.dpToPxInCode(destiny, 60);
    }

    @Override
    protected int letBottomPad() {
        return MyUtil.dpToPxInCode(destiny, 60);
    }

    @Override
    protected int letLeftText() {
        return MyUtil.dpToPxInCode(destiny, 60);
    }

    @Override
    protected int letRightPad() {
        return MyUtil.dpToPxInCode(destiny, 10);
    }

    public CalendricSimpleDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void calcEntryLayoutPos(EntrySchedule entrySchedule) {
         /*根据时间计算位置
         * 不使用时间差值的原因是第一时间差值的计算可能会麻烦
         * 第二如果用时间差的话如果有跨过一条小时线的话小时线宽度不会计算在内
                 */
        int t = grid_top +
                entrySchedule.getDate_begin().getHour() * heightOf1hWithLine +
                entrySchedule.getDate_begin().getMinute() * heightOf1m;
        int b = grid_top +
                entrySchedule.getDate_end().getHour() * heightOf1hWithLine +
                entrySchedule.getDate_end().getMinute() * heightOf1m;
        /*如果这个事件结束时间是整点的话绘制的时候减去两个像素为了美观*/
        b -= entrySchedule.getDate_end().getMinute() == 0 ? 2 * lineSize : 0;
        calEntryLayoutPos.setValue(grid_left, t, grid_right, b);
    }

    @Override
    protected void calcPreAddLayoutPos(int x, int y) {
        calEntryLayoutPos.setValue(
                grid_left,
                grid_top + y / heightOf1hWithLine * heightOf1hWithLine,
                grid_right,
                grid_top + (y / heightOf1hWithLine + 1) * heightOf1hWithLine
        );
    }


    /*覆写一些父类方法来实现自身目的*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*画一条指示当前时间的线*/

        indicateLineY = letTopPad() + lineSize + myMoment.getHour() * (heightOf1h + lineSize) + myMoment.getMinute() * heightOf1m;
        paint.setColor(Color.rgb(30, 144, 255));
        canvas.drawLine(lineLeft, indicateLineY, grid_left, indicateLineY, paint);
        paint.setColor(Color.rgb(169, 169, 169));
    }

    @Override
    public int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime) {
        int cTH = (myTime.getHour() + 1) * (heightOf1h + lineSize) + myTime.getMinute() * heightOf1m;
        if (cTH < fatherHeight) {
            return 0;
        } else if (cTH < myHeight - fatherHeight) {
            return cTH - fatherHeight / 2;
        } else {
            return myHeight - fatherHeight;
        }
    }


}