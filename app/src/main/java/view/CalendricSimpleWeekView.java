package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import entry.Entry;
import entry.EntrySchedule;
import my.MyMoment;
import my.MyTime;
import my.MyUtil;


public class CalendricSimpleWeekView extends CalendricSimpleView {
    private int defaultWidth = 100;
    private int defaultHeight = 1500;
    private int myWidth;
    private int myHeight;
    private int lineWidth = 1;
    private final float destiny = getContext().getResources().getDisplayMetrics().density;//dp到像素
    private int textSize = MyUtil.dpToPxInCode(destiny, 15);//文本大小，文本就是指左边的显示时间的那一个，也叫文本
    private int heightOf2H;
    private int topHead = MyUtil.dpToPxInCode(destiny, 30);
    private int widthOf1Day;
    private int textLeftPad = MyUtil.dpToPxInCode(destiny, 5);
    private int textTopPad = MyUtil.dpToPxInCode(destiny, 10);
    private ArrayList<EntrySchedule> schedules;
    private int hpm;
    Paint paint = new Paint();

    public CalendricSimpleWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.rgb(255, 255, 255));
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
        widthOf1Day = (myWidth - 7 * lineWidth) / 8;
        heightOf2H = (myHeight - topHead - 13 * lineWidth) / 13;
        hpm = heightOf2H / 120;
    }

    private void arrangeLayout() {
        this.removeAllViews();


        PresetBkg presetBkg[] = new PresetBkg[]{
                new PresetBkg(1, 0, 12),
                new PresetBkg(1, 14, 16),
                new PresetBkg(1, 17, 18),
                new PresetBkg(1, 19, 21),

                new PresetBkg(2, 0, 9),
                new PresetBkg(2, 11, 12),
                new PresetBkg(2, 14, 16),
                new PresetBkg(2, 14, 18),
                new PresetBkg(2, 22, 24),
                new PresetBkg(3, 0, 8),
                new PresetBkg(3, 11, 12),
                new PresetBkg(3, 14, 18),
                new PresetBkg(3, 19, 21),
                new PresetBkg(4, 0, 8),
                new PresetBkg(4, 11, 12),
                new PresetBkg(4, 14, 18),
                new PresetBkg(5, 0, 7),
                new PresetBkg(5, 9, 14),
                new PresetBkg(5, 17, 18),
                new PresetBkg(5, 22, 24),
                new PresetBkg(6, 0, 12),
                new PresetBkg(6, 17, 18),
                new PresetBkg(6, 19, 21),
                new PresetBkg(7, 0, 12),
                new PresetBkg(7, 17, 18),


        };
        for (int i = 0; i < presetBkg.length; i++) {
            AppCompatTextView t = new AppCompatTextView(getContext());
            t.setBackgroundColor(Color.rgb(250, 241, 182));
            addView(t);
            t.setText("#");
            int bt = topHead + lineWidth + presetBkg[i].start * (heightOf2H / 2 + lineWidth);
            int bm = topHead + lineWidth + presetBkg[i].end * (heightOf2H / 2 + lineWidth);
            int tmp = widthOf1Day + lineWidth;
            int bl = tmp * presetBkg[i].week + 1;
            int br = tmp * (presetBkg[i].week + 1);
            t.layout(bl, bt, br, bm);
        }
        if (schedules != null) {

            for (int i = 0; i < schedules.size(); i++) {
                AppCompatTextView businessView = new AppCompatTextView(getContext());
                businessView.setBackgroundColor(Color.rgb(60, 174, 256));
                businessView.setText(schedules.get(i).getTitle());
                businessView.setTag(schedules.get(i).getId());
                businessView.setTextSize(MyUtil.dpToPxInCode(destiny, 3));
                businessView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                addView(businessView);
                int bt = topHead + lineWidth +
                        schedules.get(i).getDate_begin().getHour() * (heightOf2H / 2 + lineWidth)
                        + schedules.get(i).getDate_begin().getMinute() * hpm;
                int bm = topHead + lineWidth +
                        schedules.get(i).getDate_end().getHour() * (heightOf2H / 2 + lineWidth)
                        + schedules.get(i).getDate_end().getMinute() * hpm;
                int tmp = widthOf1Day + lineWidth;
//                int bl = tmp * schedules.get(i).getWeek() + 1;
//                int br = tmp * (schedules.get(i).getWeek() + 1);
                //为了方便假设都是周三
                int bl = tmp * 3 + 1;
                int br = tmp * 3 + 1;
                businessView.layout(bl, bt, br, bm);
            }
        }


    }

    public void updateBusiness(ArrayList<EntrySchedule> schedules) {
        this.schedules = schedules;
        arrangeLayout();
    }

    public void setBusiness(ArrayList<EntrySchedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        arrangeLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        paint.setStrokeWidth(lineWidth);//线宽
        paint.setTextSize(textSize); //据说线宽和字号会冲突，但是目前没有发现
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
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
        return 0;
    }

    @Override
    public void setEntrySchedule(ArrayList<EntrySchedule> schedules) {

    }

    @Override
    public void setCalendricViewItemClick(CalendricViewItemClick calendricViewItemClick) {

    }

    @Override
    public void setViewDate(MyMoment myMoment) {

    }
}

class PresetBkg {
    public int week;
    public int start;
    public int end;

    public PresetBkg(int week, int start, int end) {
        this.week = week;
        this.start = start;
        this.end = end;
    }
}