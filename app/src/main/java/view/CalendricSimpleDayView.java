package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.EntrySchedule;
import my.MyUtil;

//此处的代码基本保留原来的，等后面再改

public class CalendricSimpleDayView extends ViewGroup {

    public CalendricSimpleDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.rgb(255, 255, 255));
    }

    public void setBussiness(ArrayList<EntrySchedule> bs) {
        this.bs = bs;
    }

    private final float destiny = getContext().getResources().getDisplayMetrics().density;//dp到像素
    //最终经过计算后得到的view的大小
    private int myWidth;
    private int myHeight;
    //线宽和字的大小，如果线宽是奇数的话字大小宜为奇数，反之偶数
    private int lineWidth = 1;//线宽
    private int textSize = MyUtil.dpToPxInCode(destiny, 15);//文本大小，文本就是指左边的显示时间的那一个，也叫文本
    private final int topBlank = MyUtil.dpToPxInCode(destiny, 60);//顶部会空一小部分,为了美观
    private int height1h = MyUtil.dpToPxInCode(destiny, 60);//一个条的高度，不包括上下的线
    private int lineStart = topBlank + 1;//线的起始高度，即顶部空开始的下一行
    private int textStart = topBlank + lineWidth + (textSize - lineWidth) / 2;//文字的起始高度，注意文字的绘制是从左下角开始而不是左上角
    private int textPadLeft = MyUtil.dpToPxInCode(destiny, 10);//文本和文本左边的空隙
    private int linePadLeft = MyUtil.dpToPxInCode(destiny, 5);//文本和文本右边的空隙
    private int lineLeft = textPadLeft + 3 * textSize + linePadLeft;//线的左端（加入了文字占去的地）
    private int lineRight;//值为myWidth - linePadLeft,但在此处定义无效，因为myWidth还没有赋值。
    private int hpm = height1h / 60;//表示每分钟表示的高度
    //view自由发挥的大小，即没有限定view大小的时候view的大小，比如wrapContent的时候
    private final int defaultWidth = MyUtil.dpToPxInCode(destiny, 240);
    private final int defaultHeight = 27 * height1h;//一天24小时，上下各自空白一些按27算
    //如果调用draw方法的时候结束坐标比起始坐标小了它依然会绘制，因为它并不区分左右先后。
    private ArrayList<EntrySchedule> bs; //存储事项
    private AppCompatButton button;
    private int indicateLineY;//表示当前时间的指示线的纵坐标

    public CalendricSimpleDayView(Context context, ArrayList<EntrySchedule> bs) {
        super(context);
        this.bs = bs;
        Log.i("构造", this.bs.toString());
        //为这个calendarView设置一个背景色否则它无法正常显示
        setBackgroundColor(Color.rgb(255, 255, 255));
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //如果给定了大小的话就用给定的大小，否则用自己默认的大小
        myWidth = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : defaultWidth;
        myHeight = (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : defaultHeight;
        setMeasuredDimension(myWidth, myHeight);
        lineRight = myWidth - linePadLeft;//在measure后计算lineRight
    }

    //负责排布那些事件的按钮和那个添加的按钮
    private void arrangeLayout() {
        this.removeAllViews();
        Log.i("arangeLayout", "before if");
        if (bs != null) {
            Log.i("arrangeLayout", "not null" + bs.size());
            for (int i = 0; i < bs.size(); i++) {

                //定义事件的view并为他们添加相关属性
                AppCompatButton businessView = new AppCompatButton(getContext());
                businessView.setBackgroundResource(R.drawable.bkg_view_calendric_day_item_preview);
                businessView.setGravity(Gravity.LEFT);
                businessView.setTextColor(Color.BLACK);
                businessView.setText(bs.get(i).getName());
                businessView.setTag(bs.get(i).getId());
                businessView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString("opt", "edit_withId");
//                                bundle.putString("id", v.getTag().toString());
//                                Intent intent = new Intent(getContext(), SingleAddActivity.class);
//                                intent.putExtras(bundle);
//                                getContext().startActivity(intent);
                        Toast.makeText(getContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                addView(businessView);
                //计算事件被摆放的位置
                //位置为起始加上时间折算出来的像素，每个小时的时间要多加一个横线的高度
                //不要使用时间的差值，因为差值计算出来的小时无法判断中间有没有跨越一条横线所以不能准确绘制
                int bt = topBlank + lineWidth + bs.get(i).getStart().getHour() * (height1h + lineWidth) + bs.get(i).getStart().getMinute() * hpm;
                int bm = topBlank + lineWidth + bs.get(i).getEnd().getHour() * (height1h + lineWidth) + bs.get(i).getEnd().getMinute() * hpm;
                //如果这个事件结束时间是整点的话绘制的时候减去两个像素为了美观
                bm -= bs.get(i).getEnd().getMinute() == 0 ? 2 * lineWidth : 0;

                getChildAt(i).layout(lineLeft, bt, lineRight, bm);
            }
        }
        //先添加事件最后再添加添加事件的那个view,后添加的缘故是这样可以让它居于上面
        button = new AppCompatButton(getContext());

        button.setBackgroundResource(R.drawable.bkg_view_calendric_day_item_add);
        button.setText("+");
        button.setTextColor(Color.BLACK);
        button.setGravity(Gravity.LEFT);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


//                        Intent intent = new Intent(getContext(), SingleAddActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("year", calendar.get(Calendar.YEAR));
//                        bundle.putInt("month", calendar.get(Calendar.MONTH));
//                        bundle.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
//                        bundle.putInt("hour", (int) button.getTag());
//                        bundle.putInt("minute", calendar.get(Calendar.MINUTE));
//                        bundle.putString("opt", "add");
//                        intent.putExtras(bundle);
//                        getContext().startActivity(intent);
                Toast.makeText(getContext(), "click to add new business", Toast.LENGTH_SHORT).show();
            }
        });
        addView(button);
        //TODO 对于时间很短的事件块需要有一个处理，不能显示成一个细条，同样这时不能用块的宽窄来断定事件时间长度
    }

    //注意onLayout的函数执行在onDraw之前
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        arrangeLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //定义一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.rgb(169, 169, 169));
        paint.setStrokeWidth(lineWidth);//线宽
        paint.setTextSize(textSize); //据说线宽和字号会冲突，但是目前没有发现
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        int lineY;//线的纵坐标
        for (int i = 0; i <= 24; i++) {
            //画日期
            canvas.drawText(String.format("%02d", i) + ":00", textPadLeft, textStart + (height1h + lineWidth) * i, paint);
            lineY = lineStart + (height1h + lineWidth) * i;
            lineRight = myWidth - linePadLeft;//maybe not need;
            //画线
            canvas.drawLine(lineLeft, lineY, lineRight, lineY, paint);
        }
        //临时先把单天显示的线注释掉
//                Calendar calendar = Calendar.getInstance();
//                indicateLineY = topBlank + lineWidth + calendar.get(Calendar.HOUR_OF_DAY) * (height1h + lineWidth) + calendar.get(Calendar.MINUTE) * hpm;
//                paint.setColor(Color.rgb(30, 144, 255));
//                canvas.drawLine(lineLeft, indicateLineY, lineRight, indicateLineY, paint);
        paint.setColor(Color.rgb(169, 169, 169));
//                System.out.println("onDraw");
    }

    private void showAddBusiness(float eventY) {
        //显示一个类似谷歌日历的新建活动的view
        int y = (int) eventY;//点击的纵坐标
        int poor = y - lineStart;//表示点击的位置和线的起始的差值
        //当点击的范围在那24个条的范围时才显示view，最上端和最下端都不行。
        if (poor > 0 && poor < (24 * height1h + 25 * lineWidth)) {
            //判断点击的条属于哪个条，对于每个条，上面的线属于这个条，下面的线不属于这个条。
            //但是当那个添加的view显示的时候会把上下的线都覆盖，为了美观
            //同样当画事件块的时候为了美观下面的条也会不属于这个事件块
            int i = poor / (height1h + lineWidth);
            //计算view该显示的位置
            int addBusinessViewTop = lineStart + (height1h + lineWidth) * i;
            int addBusinessViewBottom = lineStart + (height1h + lineWidth) * (i + 1) + lineWidth;
            //系统在layout的时候坐标的计算是取坐标的左侧
            //假设屏幕有100*100像素，则当画（20，20，80，80）的时候用掉的像素点是20-79，正好60行个点，同样，如果画2到3则粗细为1，画2-2则无结果因为粗细为0
            button.setTag(i);
            button.layout(lineLeft, addBusinessViewTop, lineRight, addBusinessViewBottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (event.getEventTime() - event.getDownTime() < 200) {
                    //因为单击事件不能获取点击的位置所以用touch事件，根据手指按下和起来的时间的差值是否小于200ms来判断是不是一次点击
                    showAddBusiness(event.getY());
                }
                break;
        }
        return true;
        //警告：如果不return true的话会有问题，但是如果返回true了会不会对别的造成影响还不可知，默认的写法是return super.onTouchEvent(event)
    }
}