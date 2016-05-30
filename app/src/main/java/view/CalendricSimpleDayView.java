package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;
import my.MyUtil;


public class CalendricSimpleDayView extends CalendricSimpleView {
    /*绘制线条等需要的参数*/
    private int lineWidth = 1;//线宽，如果线宽是奇数的话字大小宜为奇数，反之偶数
    private int textSize = MyUtil.dpToPxInCode(destiny, 15);//文本大小，文本就是指左边的显示时间的那一个，也叫文本
    private final int topBlank = MyUtil.dpToPxInCode(destiny, 60);//顶部会空一小部分,为了美观
    private int lineStart = topBlank + 1;//线的起始高度，即顶部空开始的下一行
    private int textStart = topBlank + lineWidth + (textSize - lineWidth) / 2;//文字的起始高度，注意文字的绘制是从左下角开始而不是左上角
    private int textPadLeft = MyUtil.dpToPxInCode(destiny, 10);//文本和文本左边的空隙
    private int linePadLeft = MyUtil.dpToPxInCode(destiny, 5);//文本和文本右边的空隙
    private int lineLeft = textPadLeft + 3 * textSize + linePadLeft;//线的左端（加入了文字占去的地）
    private int lineRight;//值为myWidth - linePadLeft,但在此处定义无效，因为myWidth还没有赋值。
    private int hpm = height1h / 60;//表示每分钟表示的高度
    private int indicateLineY;//表示当前时间的指示线的纵坐标

    /*日程*/
    private ArrayList<EntrySchedule> schedules;
    /*添加按钮*/
    private AppCompatButton preAddButton;
    /*点击事件*/
    private CalendricViewItemClick calendricViewItemClick;


    /*构造方法*/
    public CalendricSimpleDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*设置背景为白色，否则会显示不良*/
        setBackgroundColor(Color.rgb(255, 255, 255));
        /*为画笔设置颜色，线宽，字号，字体*/
        paint.setColor(Color.rgb(169, 169, 169));
        paint.setStrokeWidth(lineWidth);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
    }

    /*设置条目点击事件*/
    public void setCalendricViewItemClick(CalendricViewItemClick calendricViewItemClick) {
        this.calendricViewItemClick = calendricViewItemClick;
    }

    /*设置条目*/
    public void setEntrySchedule(ArrayList<EntrySchedule> schedules) {
        this.schedules = schedules;
    }

    /*创建一个新的条目按钮*/
    private AppCompatButton createNewEntryButton(final EntrySchedule schedule) {
        //TODO 如何measuer它的大小
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
        return b;
    }

    /*安排日历视图上事件的显示*/
    private void arrangeLayout() {
        this.removeAllViews();
        if (schedules != null) {
            for (int i = 0; i < schedules.size(); i++) {
                /*增加一个新的条目*/
                addView(createNewEntryButton(schedules.get(i)));
                /*
                计算事件被摆放的位置
                位置为起始加上时间折算出来的像素，每个小时的时间要多加一个横线的高度
                不要使用时间的差值，因为差值计算出来的小时无法判断中间有没有跨越一条横线所以不能准确绘制
                 */
                int bt = topBlank + lineWidth +
                        schedules.get(i).getDate_begin().getHour() * (height1h + lineWidth) +
                        schedules.get(i).getDate_begin().getMinute() * hpm;
                int bm = topBlank + lineWidth +
                        schedules.get(i).getDate_end().getHour() * (height1h + lineWidth) +
                        schedules.get(i).getDate_end().getMinute() * hpm;
                /*如果这个事件结束时间是整点的话绘制的时候减去两个像素为了美观*/
                bm -= schedules.get(i).getDate_end().getMinute() == 0 ? 2 * lineWidth : 0;
                getChildAt(i).layout(lineLeft, bt, lineRight, bm);
            }
        }
        /*添加当点击空白处预备添加时的按钮*/
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
        addView(preAddButton);
        //TODO 对于时间很短的事件块需要有一个处理，不能显示成一个细条，同样这时不能用块的宽窄来断定事件时间长度
    }

    private int addButtonHour = 0;

    /*点击空白处时显示添加按钮*/
    private void showPreAddButton(float eventY) {
        int y = (int) eventY;//点击的纵坐标
        int poor = y - lineStart;//表示点击的位置和线的起始的差值
        /*当点击的范围在那24个条的范围时才显示view，最上端和最下端都不行。*/
        if (poor > 0 && poor < (24 * height1h + 25 * lineWidth)) {
            /*
            判断点击的条属于哪个条，对于每个条，上面的线属于这个条，下面的线不属于这个条。
            但是当那个添加的view显示的时候会把上下的线都覆盖，为了美观
            同样当画事件块的时候为了美观下面的条也会不属于这个事件块
             */
            int i = poor / (height1h + lineWidth);
            addButtonHour = i;
            /*
            计算view该显示的位置
            系统在layout的时候坐标的计算是取坐标的左侧
            假设屏幕有100*100像素，则当画（20，20，80，80）的时候用掉的像素点是20-79，正好60行个点，
            同样，如果画2到3则粗细为1，画2-2则无结果因为粗细为0
             */
            int addBusinessViewTop = lineStart + (height1h + lineWidth) * i;
            int addBusinessViewBottom = lineStart + (height1h + lineWidth) * (i + 1) + lineWidth;
            preAddButton.layout(lineLeft, addBusinessViewTop, lineRight, addBusinessViewBottom);
        }
    }

    /*覆写一些父类方法来实现自身目的*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*在measure时计算lineRight*/
        lineRight = myWidth - linePadLeft;//
    }

    /*onLayout的函数执行在onDraw之前执行*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        arrangeLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0, lineY; i <= 24; i++) {
            /*画线条左侧的时间*/
            canvas.drawText(String.format("%02d", i) + ":00", textPadLeft, textStart + (height1h + lineWidth) * i, paint);
            lineY = lineStart + (height1h + lineWidth) * i;
            lineRight = myWidth - linePadLeft;//maybe not need;
            /*画线条*/
            canvas.drawLine(lineLeft, lineY, lineRight, lineY, paint);
        }
        /*画一条指示当前时间的线*/

        indicateLineY = topBlank + lineWidth + myMoment.getHour() * (height1h + lineWidth) + myMoment.getMinute() * hpm;
        paint.setColor(Color.rgb(30, 144, 255));
        canvas.drawLine(lineLeft, indicateLineY, lineRight, indicateLineY, paint);
        paint.setColor(Color.rgb(169, 169, 169));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (event.getEventTime() - event.getDownTime() < 200) {
                    //因为单击事件不能获取点击的位置所以用touch事件，根据手指按下和起来的时间的差值是否小于200ms来判断是不是一次点击
                    showPreAddButton(event.getY());
                }
                break;
        }
        return true;
        //TODO onTouch事件的returen值该如何设置？
        //警告：如果不return true的话会有问题，但是如果返回true了会不会对别的造成影响还不可知，默认的写法是return super.onTouchEvent(event)
    }

    //
    private MyMoment myMoment;

    public void setViewDate(MyMoment myMoment) {
        this.myMoment = myMoment;
    }


    @Override
    public int getScrollYCurrentToCenter(int fatherHeight, MyTime myTime) {
        int cTH = (myTime.getHour() + 1) * (height1h + lineWidth) + myTime.getMinute() * hpm;
        if (cTH < fatherHeight) {
            return 0;
        } else if (cTH < myHeight - fatherHeight) {
            return cTH - fatherHeight / 2;
        } else {
            return myHeight - fatherHeight;
        }
    }
}