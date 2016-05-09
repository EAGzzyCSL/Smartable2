package bit.eagzzycsl.smartable2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import my.MyMoment;
import my.MyUtil;

public class MyPickerDialog {

    // 因为日期时间的选择有相似性而且雷同多所以先为他们写一个父类
    Context context;// 字面意思是上下文，就是表示这个dialog算是哪个activity上的
    AppCompatTextView textView;// 当选定时间后修改哪个文本的内容
    MyMoment myMoment;//对应的time

    public MyPickerDialog(Context context, AppCompatTextView textView, MyMoment myMoment) {
        this.context = context;
        this.textView = textView;
        this.myMoment = myMoment;
    }

    public static String getDate(int year, int monthOfYear, int dayOfMonth) {
        // 有参数的getDate方法用于在通过对话框选定了日期后拼接一个日期的字符串出来，同时加上星期
        Calendar calendar = Calendar.getInstance();
        // 因为要获取星期，所以先给日历设定一个当前的日期以便于返回一个星期
        calendar.set(year, monthOfYear - 1, dayOfMonth);
        // 因为返回的星期是数字而且周日为第一天所以用数组来实现转化
        return year + "." + monthOfYear + "." + dayOfMonth + " "
                + MyUtil.weekEtoC(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static String getShortDate(int year, int monthOfYear, int dayOfMonth) {
        // 因为返回的星期是数字而且周日为第一天所以用数组来实现转化
        return year + "年" + monthOfYear + "月" + dayOfMonth + "日";
    }

    // getTime参考getDate
    public static String getMoment(int hourOfDay, int minute) {
        // 格式化字符串，和c语言相近
        // 这一段关于日期的操作应该java类库中有对应的方法说不定可以直接用，这儿就姑且先造个轮子
        return hourOfDay + ":" + String.format("%02d", minute);
    }

}

class MyDatePickerDialog extends MyPickerDialog {
    public MyDatePickerDialog(Context context, AppCompatTextView textView, MyMoment myMoment) {
        super(context, textView, myMoment);
    }

    // show方法其实就是显示一个日期的选框
    public void show() {
        new DatePickerDialog(context,R.style.MyDatePickerDialogYellowTheme,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myMoment.setDate(year, monthOfYear + 1, dayOfMonth);
                textView.setText(MyPickerDialog.getShortDate(year, monthOfYear + 1,
                        dayOfMonth));//getShortDate不显示星期
            }
        }, myMoment.getYear(), myMoment.getMonth() - 1,
                myMoment.getDay()).show();


//        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                myTime.setDate(year, monthOfYear + 1, dayOfMonth);
//                textView.setText(MyPickerDialog.getShortDate(year, monthOfYear + 1,
//                        dayOfMonth));//getShortDate不显示星期
//            }
//        }, myTime.getYear(), myTime.getMonth() - 1,
//                myTime.getDay()).show();



    }
}

class MyTimePickerDialog extends MyPickerDialog {
    public MyTimePickerDialog(Context context, AppCompatTextView textView,MyMoment myMoment) {
        super(context, textView, myMoment);
    }

    public void show() {
        new TimePickerDialog(context, R.style.MyTimePickerDialogYellowTheme,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myMoment.setTime(hourOfDay, minute);
                textView.setText(MyPickerDialog.getMoment(hourOfDay, minute));
            }
        }, myMoment.getHour(), myMoment.getMinute(), true).show();
    }

    public void show(int themeResId) {
        new TimePickerDialog(context, themeResId,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myMoment.setTime(hourOfDay, minute);
                textView.setText(MyPickerDialog.getMoment(hourOfDay, minute));
            }
        }, myMoment.getHour(), myMoment.getMinute(), true).show();
    }
}