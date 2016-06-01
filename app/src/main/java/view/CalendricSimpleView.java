package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;
import my.MyUtil;


public abstract class CalendricSimpleView extends ViewGroup {
    /*从dp转化为像素乘的值*/
    protected final float destiny = getContext().getResources().getDisplayMetrics().density;
    //TODO 是不是该使用float表示绘制的量
    /*绘制需要的基本量*/
    /*一个小时的高度*/
    protected final int heightOf1h = MyUtil.dpToPxInCode(destiny, 60);
    /*一分钟的高度*/
    protected final int heightOf1m = heightOf1h / 60;//表示每分钟表示的高度
    /*画笔的线条宽度*/
    protected final int lineSize = 1;
    /*画笔的文本大小*/
    protected final int textSize = MyUtil.dpToPxInCode(destiny, 13);
    /*处于对强迫症的照顾，文本大小和线宽的奇偶应该不同*/
    /*日历的网格部分的边界,top和left无需measure即可获得，right和bottom需要measure才可以*/
    protected int grid_left = letLeftText(), grid_top = letTopPad(), grid_right, grid_bottom;
    /*计算得到的量*/
    /*线的起始高度*/
    protected int lineFirst = grid_top + lineSize;
    /*文字的起始高度，注意文字的绘制是从左下角开始而不是左上角*/
    protected int textFirst = grid_top + lineSize + (textSize - lineSize) / 2;
    /*带线条的小时高度，方便计算使用*/
    protected int heightOf1hWithLine = heightOf1h + lineSize;
    /*最终经过计算后得到的view的大小*/
    protected int myWidth, myHeight;
    /*view在wrapContent的时候的大小，实际情况是view也只允许warpContent，matchParent的情况并为处理*/
    private final int defaultWidth = MyUtil.dpToPxInCode(destiny, 240);
    private final int defaultHeight = 24 * heightOf1h + 25 * lineSize + grid_top + letBottomPad();

    /*画线条和时间的画笔*/
    protected Paint paint = new Paint();
    /*点击事件的侦听*/
    protected CalendricViewItemClick calendricViewItemClick;
    /*日程*/
    protected ArrayList<EntrySchedule> schedules;
    /*存放日程的label的定位信息*/
    protected CalEntryLayoutPos calEntryLayoutPos = new CalEntryLayoutPos();
    protected int addButtonHour = 0;
    protected MyMoment myMoment;
    /*添加按钮*/
    protected AppCompatButton preAddButton;

    public CalendricSimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*完成画笔创建设置等初始化工作*/
        this.setBackgroundColor(Color.rgb(255, 255, 255));
        paint.setStrokeWidth(lineSize);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(ContextCompat.getColor(getContext(), R.color.divider));
        preAddButton = new AppCompatButton(getContext());
        preAddButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.view_calendric_day_item_add_bkg));
        preAddButton.setText("+");
        preAddButton.setTextColor(Color.BLACK);
        preAddButton.setGravity(Gravity.START);
        preAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //日期是view自己的日期，时间通过计算获取
                MyMoment m = new MyMoment(myMoment.getYear(), myMoment.getMonth(),
                        myMoment.getDay(), addButtonHour, 0);
                calendricViewItemClick.onAddClick(v, m);
            }
        });
        //TODO 是否用xml来创建view？
    }

    /*将px转化为dp*/
    protected int toPx(int dp) {
        return MyUtil.dpToPxInCode(destiny, dp);
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

    /*创建一个新的条目标签*/
    protected View createNewEntryLabel(final EntrySchedule schedule) {
        //TODO 设置其大小的measure
//        AppCompatButton b = new AppCompatButton(getContext());
        TextView b = new TextView(getContext());//可以使用textView
        b.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.cal_yellow));
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
                View vEntry = createNewEntryLabel(entrySchedule);
                addView(vEntry);
                calcEntryLayoutPos(entrySchedule);
                vEntry.layout(calEntryLayoutPos.getLeft(),
                        calEntryLayoutPos.getTop(),
                        calEntryLayoutPos.getRight(),
                        calEntryLayoutPos.getBottom());
            }
        }
        /*添加当点击空白处预备添加时的按钮*/
        addView(preAddButton);
    }


    /*覆写onTouch事件来实现触摸添加一个添加按钮*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                /*根据触摸的时间差来模拟点击*/
                if (event.getEventTime() - event.getDownTime() < 200) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    /*判断是否在日历的网格部分*/
                    if (MyUtil.inRange(grid_left, x, grid_right)
                            && MyUtil.inRange(grid_top, y, grid_bottom)
                            ) {
                            /*根据触摸的位置来决定是否显示一个添加按钮*/
                        calcPreAddLayoutPos(x - grid_left, y - grid_top);
                        preAddButton.layout(calEntryLayoutPos.getLeft(),
                                calEntryLayoutPos.getTop(),
                                calEntryLayoutPos.getRight(),
                                calEntryLayoutPos.getBottom()
                        );

                    }
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
        /*只有在myWidth和myHeight的值确定后才可以计算网格部分边界*/
        grid_right = myWidth - letRightPad();
        grid_bottom = myHeight - letBottomPad();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int horLineY = lineFirst, horLineRight = myWidth - letRightPad();
        int leftTimeY = textFirst;
        int textLeftPad = (grid_left - (int) (2.5 * textSize)) / 2;
        //TODO 文字大小与文字宽度的问题
        for (int i = 0; i <= 24; i++) {
            canvas.drawLine(grid_left, horLineY, horLineRight, horLineY, paint);
            canvas.drawText(String.format("%02d:00", i), textLeftPad,
                    leftTimeY,
                    paint);
            horLineY += heightOf1hWithLine;
            leftTimeY += heightOf1hWithLine;
        }
    }

    //TODO 事件的显示大小要有一个最小值，否则持续时间很短的事件会变成细条
    /*计算事件该被摆放的位置*/
    protected abstract void calcEntryLayoutPos(EntrySchedule entrySchedule);

    /*计算添加按钮该被摆放的位置*/
    protected abstract void calcPreAddLayoutPos(int x, int y);

    /*计算若要将当前时间显示在屏幕中心的滚动量*/
    protected abstract int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime);

    /*为了美观等因素，顶部和下部会有一些留空*/
    protected abstract int letTopPad();

    protected abstract int letBottomPad();

    protected abstract int letLeftText();

    protected abstract int letRightPad();
}

