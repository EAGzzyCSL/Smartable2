package view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;

import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;
import my.MyUtil;


public abstract class CalendricSimpleView extends ViewGroup {

    /*从dp转化为像素乘的值*/
    protected final float destiny = getContext().getResources().getDisplayMetrics().density;
    protected int height1h = MyUtil.dpToPxInCode(destiny, 60);//一个条的高度，不包括上下的线

    /*最终经过计算后得到的view的大小*/
    protected int myWidth;
    protected int myHeight;
    /*view自由发挥的大小，即没有限定view大小的时候view的大小，比如wrapContent的时候*/
    private final int defaultWidth = MyUtil.dpToPxInCode(destiny, 240);

    private final int defaultHeight = 27 * height1h;//一天24小时，上下各自空白一些按27算
    /*画线条和时间的画笔*/
    protected  Paint paint = new Paint();
    public CalendricSimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        /*如果给定了大小的话就用给定的大小，否则用自己默认的大小*/
        myWidth = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : defaultWidth;
        myHeight = (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : defaultHeight;
        setMeasuredDimension(myWidth, myHeight);

    }

    abstract int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime);

    abstract void setEntrySchedule(ArrayList<EntrySchedule> schedules);

    abstract void setCalendricViewItemClick(CalendricViewItemClick calendricViewItemClick);

    abstract void setViewDate(MyMoment myMoment);
}
