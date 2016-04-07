package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import my.MyTime;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private android.support.v7.widget.AppCompatEditText edit_activity_title;
    private RadioGroup edit_radioGroup;
    private android.support.v7.widget.AppCompatRadioButton edit_rbtn1;
    private android.support.v7.widget.AppCompatRadioButton edit_rbtn2;
    private android.support.v7.widget.AppCompatRadioButton edit_rbtn3;
    private android.support.v7.widget.AppCompatRadioButton edit_rbtn4;
    private android.support.v7.widget.AppCompatTextView edit_ddl_datetime_picker;
    private android.support.v7.widget.AppCompatTextView edit_ddl_datetime_picker2;
    private Button edit_remind_picker;
    private android.support.v7.widget.AppCompatEditText edit_location_et;
    private android.support.v7.widget.AppCompatEditText edit_ddl_expand_et;
    private android.support.v7.widget.AppCompatEditText edit_ddl_expand_et2;
    private LinearLayout linearlayout_schedule;
    private LinearLayout linearlayout_deadling;
    private LinearLayout linearlayout_remind;
    private LinearLayout linearlayout_location;
    private LinearLayout linearlayout_ddl_expand;

    private android.support.design.widget.AppBarLayout edit_barlayout;
    private android.support.v7.widget.AppCompatTextView textView_startDate;
    private android.support.v7.widget.AppCompatTextView textView_endDate;
    private android.support.v7.widget.AppCompatTextView textView_startTime;
    private android.support.v7.widget.AppCompatTextView textView_endTime;
    private MyTime timeStart = new MyTime();
    private MyTime timeEnd = new MyTime();
    private MyTime timeDDL = new MyTime();

    private AlertDialog remindDialog;
    private Button btn_remind_time1;
    private Button btn_remind_time2;
    private Button btn_remind_time3;
    private Button btn_remind_time4;
    private Button btn_remind_time5;
    private Button btn_remind_time6;
    private Button btn_remind_time7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(getResources().getColor(R.color.colorMyRed));
        }

        myFindView();
        myInit();
        mySetView();


    }

    private void myFindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edit_activity_title = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_activity_title);
        edit_radioGroup = (RadioGroup) findViewById(R.id.edit_radioGroup);
        edit_rbtn1 = (android.support.v7.widget.AppCompatRadioButton) findViewById(R.id.edit_rbtn1);
        edit_rbtn2 = (android.support.v7.widget.AppCompatRadioButton) findViewById(R.id.edit_rbtn2);
        edit_rbtn3 = (android.support.v7.widget.AppCompatRadioButton) findViewById(R.id.edit_rbtn3);
        edit_rbtn4 = (android.support.v7.widget.AppCompatRadioButton) findViewById(R.id.edit_rbtn4);
        edit_ddl_datetime_picker = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.edit_ddl_datetime_picker);
        edit_ddl_datetime_picker2 = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.edit_ddl_datetime_picker2);
        edit_remind_picker = (Button) findViewById(R.id.edit_remind_picker);
        edit_location_et = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_location_et);
        edit_ddl_expand_et = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_ddl_expand_et);
        edit_ddl_expand_et2 = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_ddl_expand_et2);
        linearlayout_schedule = (LinearLayout) findViewById(R.id.linearlayout_schedule);
        linearlayout_deadling = (LinearLayout) findViewById(R.id.linearlayout_deadling);
        linearlayout_remind = (LinearLayout) findViewById(R.id.linearlayout_remind);
        linearlayout_location = (LinearLayout) findViewById(R.id.linearlayout_location);
        linearlayout_ddl_expand = (LinearLayout) findViewById(R.id.linearlayout_ddl_expand);

        edit_barlayout = (android.support.design.widget.AppBarLayout) findViewById(R.id.edit_barlayout);
        textView_startDate = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_startDate);
        textView_endDate = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_endDate);
        textView_startTime = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_startTime);
        textView_endTime = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_endTime);

    }

    private void myInit() {
        edit_rbtn1.setSelected(true);
        set_layout_visible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
        edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyOrange));

        Calendar c = Calendar.getInstance();
        timeStart = new MyTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), 0);
        timeEnd = new MyTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), 0);
        timeDDL = new MyTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), 0);


        textView_startDate.setText(MyPickerDialog.getShortDate(timeStart.getYear(),
                timeStart.getMonth(), timeStart.getDay()));
        textView_endDate.setText(MyPickerDialog.getShortDate(timeEnd.getYear(),
                timeEnd.getMonth(), timeEnd.getDay()));
        textView_startTime.setText(MyPickerDialog.getMoment(timeStart.getHour(), timeStart.getMinute()));
        textView_endTime.setText(MyPickerDialog.getMoment(timeEnd.getHour(), timeEnd.getMinute()));
        edit_ddl_datetime_picker.setText(MyPickerDialog.getShortDate(timeDDL.getYear(),
                timeDDL.getMonth(), timeDDL.getDay()));
        edit_ddl_datetime_picker2.setText(MyPickerDialog.getMoment(timeDDL.getHour(), timeDDL.getMinute()));

    }

    private void mySetView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//顶部返回按钮


        edit_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == edit_rbtn1.getId()) {  //速记
                    set_layout_visible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
                    edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyOrange));
                } else if (checkedId == edit_rbtn2.getId()) { //日程
                    set_layout_visible(View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE);
                    edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyYellow));
                } else if (checkedId == edit_rbtn3.getId()) { //这两天
                    set_layout_visible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
                    edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyPurple));
                } else if (checkedId == edit_rbtn4.getId()) { //DDL
                    set_layout_visible(View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
                    edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyBlue));

                }
            }
        });


        textView_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(EditActivity.this, textView_startDate, timeStart)
                        .show();
            }
        });
        textView_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(EditActivity.this, textView_endDate, timeEnd)
                        .show();
            }
        });
        textView_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(EditActivity.this, textView_startTime, timeStart)
                        .show(R.style.MyTimePickerDialogYellowTheme);
            }
        });
        textView_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(EditActivity.this, textView_endTime, timeEnd)
                        .show(R.style.MyTimePickerDialogYellowTheme);
            }
        });

        edit_ddl_datetime_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(EditActivity.this, edit_ddl_datetime_picker, timeDDL)
                        .show();
            }
        });

        edit_ddl_datetime_picker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(EditActivity.this, edit_ddl_datetime_picker2, timeDDL)
                        .show(R.style.MyTimePickerDialogYellowTheme);
            }
        });

        edit_remind_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog();
            }
        });


    }

    private void set_layout_visible(int visible1, int visible2, int visible3, int visible4, int visible5) {
        linearlayout_schedule.setVisibility(visible1);
        linearlayout_deadling.setVisibility(visible2);
        linearlayout_remind.setVisibility(visible3);
        linearlayout_location.setVisibility(visible4);
        linearlayout_ddl_expand.setVisibility(visible5);
    }

    //toolbar返回按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(EditActivity.this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //选择提醒时间dialog
    private void myDialog() {
//        alertDialog = new AlertDialog.Builder(EditActivity.this).create();
//        alertDialog.setTitle("lily");
//        alertDialog.show();
//        Window window = alertDialog.getWindow();
//        window.setContentView(R.layout.dialog_remind);
//        TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);
//        tv_title.setText("详细信息");

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_remind,
                (ViewGroup) findViewById(R.id.dialog_remind));
        remindDialog = new AlertDialog.Builder(this).setView(view).create();
        btn_remind_time1 = (Button) view.findViewById(R.id.btn_remind_time1);
        btn_remind_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("30min");
                remindDialog.dismiss();
            }
        });

        btn_remind_time2 = (Button) view.findViewById(R.id.btn_remind_time2);
        btn_remind_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("1h");
                remindDialog.dismiss();
            }
        });
        btn_remind_time3 = (Button) view.findViewById(R.id.btn_remind_time3);
        btn_remind_time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("3h");
                remindDialog.dismiss();
            }
        });
        btn_remind_time4 = (Button) view.findViewById(R.id.btn_remind_time4);
        btn_remind_time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("5min");
                remindDialog.dismiss();
            }
        });
        btn_remind_time5 = (Button) view.findViewById(R.id.btn_remind_time5);
        btn_remind_time5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("9:00 in the morning");
                remindDialog.dismiss();
            }
        });

        btn_remind_time6 = (Button) view.findViewById(R.id.btn_remind_time6);
        btn_remind_time6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("22:00 last day");
                remindDialog.dismiss();
            }
        });
        btn_remind_time7 = (Button) view.findViewById(R.id.btn_remind_time7);
        btn_remind_time7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText("Exact");
                remindDialog.dismiss();
            }
        });
        remindDialog.show();

    }
}
