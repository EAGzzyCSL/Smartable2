package view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
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
    protected Paint paint = new Paint();
    /*点击事件*/
    protected CalendricViewItemClick calendricViewItemClick;
    /*日程*/
    protected ArrayList<EntrySchedule> schedules;
    protected int lineWidth = 1;//线宽，如果线宽是奇数的话字大小宜为奇数，反之偶数
    protected int textSize = MyUtil.dpToPxInCode(destiny, 15);//文本大小，文本就是指左边的显示时间的那一个，也叫文本
    protected CalEntryLayoutPos calEntryLayoutPos = new CalEntryLayoutPos();
    protected int addButtonHour = 0;
    protected MyMoment myMoment;

    /*添加按钮*/
    protected AppCompatButton preAddButton;

    public CalendricSimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*完成画笔创建设置等初始化工作*/
        this.setBackgroundColor(Color.rgb(255, 255, 255));
        paint.setStrokeWidth(lineWidth);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        preAddButton = new AppCompatButton(getContext());
        preAddButton.setBackgroundResource(R.drawable.bkg_view_calendric_day_item_add);
        preAddButton.setText("+");
        preAddButton.setTextColor(Color.BLACK);
        preAddButton.setGravity(Gravity.START);
        preAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //日期是view自己的日期，时间通过计算获取
                MyMoment m = new MyMoment(myMoment.getYear(), myMoment.getMonth(), myMoment.getDay(), addButtonHour, 0);
                calendricViewItemClick.onAddClick(v, m);
            }
        });
        //TODO 是否用xml来创建view？

    }

    /*设置条目点击事件*/
    public void setCalendricViewItemClick(CalendricViewItemClick calendricViewItemClick) {
        this.calendricViewItemClick = calendricViewItemClick;
    }

    /*设置条目*/
    public void setEntrySchedule(ArrayList<EntrySchedule> schedules) {
        this.schedules = schedules;
    }


    /*设置时间*/
    public void setViewDate(MyMoment myMoment) {
        this.myMoment = myMoment;
    }

    /*创建一个新的条目按钮*/
    protected AppCompatButton createNewEntryButton(final EntrySchedule schedule) {
        //TODO 设置其大小的measure
        AppCompatButton b = new AppCompatButton(getContext());
        b.setBackgroundResource(R.drawable.bkg_view_calendric_day_item_preview);
        b.setGravity(Gravity.START);
        b.setTextColor(Color.BLACK);
        b.setText(schedule.getTitle());
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendricViewItemClick.onItemClick(v, schedule);
            }
        });
        //TODO 优化listener对象的创建
        return b;
    }

    /*安排日历视图上事件的显示*/
    protected void arrangeLayout() {
        this.removeAllViews();
        if (schedules != null) {
            for (EntrySchedule entrySchedule : schedules) {
                                /*增加一个新的条目*/
                AppCompatButton button = createNewEntryButton(entrySchedule);
                addView(button);
                calcEntryLayoutPos(entrySchedule);
                button.layout(calEntryLayoutPos.getLeft(),
                        calEntryLayoutPos.getTop(),
                        calEntryLayoutPos.getRight(),
                        calEntryLayoutPos.getBottom());
            }
        }
        /*添加当点击空白处预备添加时的按钮*/
        addView(preAddButton);
    }


    /*根据触摸的位置来决定是否显示一个添加按钮*/
    protected void showPreAddButton(float eventX, float eventY) {
        if (calcPreAddLayoutPos(eventX, eventY)) {
            preAddButton.layout(calEntryLayoutPos.getLeft(),
                    calEntryLayoutPos.getTop(),
                    calEntryLayoutPos.getRight(),
                    calEntryLayoutPos.getBottom());

        }

    }

    /*覆写onTouch事件来实现触摸添加一个添加按钮*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (event.getEventTime() - event.getDownTime() < 200) {
                    //因为单击事件不能获取点击的位置所以用touch事件，根据手指按下和起来的时间的差值是否小于200ms来判断是不是一次点击
                    showPreAddButton(event.getX(), event.getY());
                }
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        arrangeLayout();
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

    //TODO 事件的显示大小要有一个最小值，否则持续时间很短的事件会变成细条
    /*计算事件该被摆放的位置*/
    protected abstract void calcEntryLayoutPos(EntrySchedule entrySchedule);

    /*计算添加按钮该被摆放的位置*/
    protected abstract boolean calcPreAddLayoutPos(float eventX, float eventY);

    /*计算若要将当前时间显示在屏幕中心的滚动量*/
    protected abstract int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime);
}

class CalEntryLayoutPos {
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