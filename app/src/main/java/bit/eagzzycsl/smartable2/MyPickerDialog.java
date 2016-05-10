package bit.eagzzycsl.smartable2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import my.MyMoment;

public class MyPickerDialog {

    Context context;// 字面意思是上下文，就是表示这个dialog算是哪个activity上的
    AppCompatTextView textView;// 当选定时间后修改哪个文本的内容
    MyMoment myMoment;//对应的time

    public MyPickerDialog(Context context, AppCompatTextView textView, MyMoment myMoment) {
        this.context = context;
        this.textView = textView;
        this.myMoment = myMoment;
    }

}

class MyDatePickerDialog extends MyPickerDialog {


    public MyDatePickerDialog(Context context, AppCompatTextView textView, MyMoment myMoment) {
        super(context, textView, myMoment);
    }

    public void show() {
        new DatePickerDialog(context, R.style.MyDatePickerDialogYellowTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myMoment.setDate(year, monthOfYear + 1, dayOfMonth);
                textView.setText(myMoment.getDate().convertToLocalString());
            }
        }, myMoment.getYear(), myMoment.getMonth() - 1,
                myMoment.getDay()).show();
    }
}

class MyTimePickerDialog extends MyPickerDialog {
    public MyTimePickerDialog(Context context, AppCompatTextView textView, MyMoment myMoment) {
        super(context, textView, myMoment);
    }

    public void show(int themeResId) {
        new TimePickerDialog(context, themeResId, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myMoment.setTime(hourOfDay, minute);
                textView.setText(myMoment.getTime().convertToLocalString());
            }
        }, myMoment.getHour(), myMoment.getMinute(), true).show();
    }
}