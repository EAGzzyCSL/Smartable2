package bit.eagzzycsl.smartable2;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import database.SQLMan;
import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntrySomeDay;
import entry.EntryTheseDays;
import my.EnumMyMoment;
import my.MyMoment;
import my.MyUtil;

public abstract class EntryEditActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected AppCompatEditText edit_activity_title;
    protected RadioGroup edit_radioGroup;
    protected AppCompatRadioButton edit_rbtn1;
    protected AppCompatRadioButton edit_rbtn2;
    protected AppCompatRadioButton edit_rbtn3;
    protected AppCompatRadioButton edit_rbtn4;
    protected AppCompatRadioButton edit_rbtn5;
    protected AppCompatTextView edit_ddl_datetime_picker;
    protected AppCompatTextView edit_ddl_datetime_picker2;
    protected Button edit_remind_picker;
    protected AppCompatEditText edit_location_et;
    protected AppCompatEditText edit_ddl_expand_et;
    protected AppCompatEditText edit_ddl_expand_et2;
    protected LinearLayout linearlayout_schedule;
    protected LinearLayout linearlayout_deadling;
    protected LinearLayout linearlayout_remind;
    protected LinearLayout linearlayout_location;
    protected LinearLayout linearlayout_ddl_expand;
    protected Button edit_done_btn;//yu---

    protected AppBarLayout edit_barlayout;
    protected AppCompatTextView textView_startDate;
    protected AppCompatTextView textView_endDate;
    protected AppCompatTextView textView_startTime;
    protected AppCompatTextView textView_endTime;
    protected MyMoment timeStart;
    protected MyMoment timeEnd;
    protected MyMoment timeDDL;

    protected AlertDialog remindDialog;

    protected EnumEntry enumEntry;//枚举类型，判断是哪种类型
    protected Entry entryToEdit;//这个界面上被操作的entry

    protected Button btn_edit_schedule_or_trigger;//日程/触发切换按钮
    protected Button btn_edit_note;//笔记
    protected Button btn_edit_someday;//有朝一日
    protected android.app.AlertDialog dialog_someday;
    protected EditText et_someday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        myFindView();
        /*myInit必须在find和set之间，否则会出现空指针等*/
        myInit();
        mySetView();

    }

    /*抽象方法，一定需要覆写来完成初始化的操作*/
    protected abstract void myInit();

    protected void myFindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edit_activity_title = (AppCompatEditText) findViewById(R.id.edit_activity_title);
        edit_radioGroup = (RadioGroup) findViewById(R.id.edit_radioGroup);
        edit_rbtn1 = (AppCompatRadioButton) findViewById(R.id.edit_rbtn1);
        edit_rbtn2 = (AppCompatRadioButton) findViewById(R.id.edit_rbtn2);
        edit_rbtn3 = (AppCompatRadioButton) findViewById(R.id.edit_rbtn3);
        edit_rbtn4 = (AppCompatRadioButton) findViewById(R.id.edit_rbtn4);
        edit_rbtn5 = (AppCompatRadioButton) findViewById(R.id.edit_rbtn5);
        edit_ddl_datetime_picker = (AppCompatTextView) findViewById(R.id.edit_ddl_datetime_picker);
        edit_ddl_datetime_picker2 = (AppCompatTextView) findViewById(R.id.edit_ddl_datetime_picker2);
        edit_remind_picker = (Button) findViewById(R.id.edit_remind_picker);
        edit_location_et = (AppCompatEditText) findViewById(R.id.edit_location_et);
        edit_ddl_expand_et = (AppCompatEditText) findViewById(R.id.edit_ddl_expand_et);
        edit_ddl_expand_et2 = (AppCompatEditText) findViewById(R.id.edit_ddl_expand_et2);
        linearlayout_schedule = (LinearLayout) findViewById(R.id.linearlayout_schedule);
        linearlayout_deadling = (LinearLayout) findViewById(R.id.linearlayout_deadling);
        linearlayout_remind = (LinearLayout) findViewById(R.id.linearlayout_remind);
        linearlayout_location = (LinearLayout) findViewById(R.id.linearlayout_location);
        linearlayout_ddl_expand = (LinearLayout) findViewById(R.id.linearlayout_ddl_expand);
        edit_done_btn = (Button) findViewById(R.id.edit_done_btn);//yu---
        edit_barlayout = (AppBarLayout) findViewById(R.id.edit_barlayout);
        textView_startDate = (AppCompatTextView) findViewById(R.id.textView_startDate);
        textView_endDate = (AppCompatTextView) findViewById(R.id.textView_endDate);
        textView_startTime = (AppCompatTextView) findViewById(R.id.textView_startTime);
        textView_endTime = (AppCompatTextView) findViewById(R.id.textView_endTime);
        btn_edit_note = (Button) findViewById(R.id.edit_note_btn);
        btn_edit_someday = (Button) findViewById(R.id.edit_someday_btn);
        btn_edit_schedule_or_trigger = (Button) findViewById(R.id.edit_schedule_or_trigger_btn);
    }

    protected void changeStatusBarColor(int color) {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    protected void mySetView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//顶部返回按钮
        }
        edit_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == edit_rbtn1.getId()) {  //速记
                    selectedEntryType(EnumEntry.shortHand);
                } else if (checkedId == edit_rbtn2.getId()) { //日程
                    selectedEntryType(EnumEntry.schedule);
                } else if (checkedId == edit_rbtn3.getId()) { //这两天
                    selectedEntryType(EnumEntry.theseDays);
                } else if (checkedId == edit_rbtn4.getId()) { //DDL
                    selectedEntryType(EnumEntry.deadLine);
                } else if (checkedId == edit_rbtn5.getId()) { //有朝一日
                    selectedEntryType(EnumEntry.someDay);
                }

                //是否显示"日程/触"发按钮
                if (checkedId != edit_rbtn2.getId()) {
                    btn_edit_schedule_or_trigger.setVisibility(View.INVISIBLE);
                } else {
                    btn_edit_schedule_or_trigger.setVisibility(View.VISIBLE);
                }
            }
        });
        textView_startDate.setOnClickListener(createOnclickListener(EnumMyMoment.myDate, timeStart));
        textView_endDate.setOnClickListener(createOnclickListener(EnumMyMoment.myDate, timeEnd));
        textView_startTime.setOnClickListener(createOnclickListener(EnumMyMoment.myTime, timeStart));
        textView_endTime.setOnClickListener(createOnclickListener(EnumMyMoment.myTime, timeEnd));
        edit_ddl_datetime_picker.setOnClickListener(createOnclickListener(EnumMyMoment.myDate, timeDDL));
        edit_ddl_datetime_picker2.setOnClickListener(createOnclickListener(EnumMyMoment.myTime, timeDDL));
        edit_remind_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog();
            }
        });
    }

    /*创建listener，手写太累*/
    protected View.OnClickListener createOnclickListener(EnumMyMoment enumMyMoment, final MyMoment myMoment) {
        switch (enumMyMoment) {
            case myDate: {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (new MyDatePickerDialog(EntryEditActivity.this, (AppCompatTextView) v, myMoment))
                                .show();
                    }
                };
            }
            case myTime: {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MyTimePickerDialog(EntryEditActivity.this, (AppCompatTextView) v, myMoment)
                                .show(R.style.MyTimePickerDialogYellowTheme);
                    }
                };
            }
        }
        return null;
    }

    /*选择了哪种类型的entry*/
    protected void selectedEntryType(EnumEntry enumEntry) {
        this.enumEntry = enumEntry;
        set_layout_visible(enumEntry.getViewVisible());
        edit_barlayout.setBackgroundColor(ContextCompat.getColor(EntryEditActivity.this, enumEntry.getColorId()));
        changeStatusBarColor(ContextCompat.getColor(EntryEditActivity.this, enumEntry.getColorId()));
    }

    protected void selectedRadio(EnumEntry enumEntry) {
        switch (enumEntry) {
            case shortHand: {
                edit_rbtn1.setChecked(true);
                break;
            }
            case schedule: {
                edit_rbtn2.setChecked(true);
                break;
            }
            case theseDays: {
                edit_rbtn3.setChecked(true);
                break;
            }
            case deadLine: {
                edit_rbtn4.setChecked(true);
                break;
            }
            case someDay: {
                edit_rbtn5.setChecked(true);
                break;
            }
        }
    }

    private void setTimeToTextView(MyMoment myMoment, AppCompatTextView vDate, AppCompatTextView vTime) {
        vDate.setText(myMoment.getDate().convertToLocalString());
        vTime.setText(myMoment.getTime().convertToLocalString());
    }

    protected void setTimeStart(MyMoment myMoment) {
        this.timeStart = myMoment;
        setTimeToTextView(myMoment, textView_startDate, textView_startTime);
    }

    protected void setTimeEnd(MyMoment myMoment) {
        this.timeEnd = myMoment;
        setTimeToTextView(myMoment, textView_endDate, textView_endTime);
    }

    protected void setTimeDDL(MyMoment myMoment) {
        this.timeDDL = myMoment;
        setTimeToTextView(myMoment, edit_ddl_datetime_picker, edit_ddl_datetime_picker2);
    }

    //TODO 是否提醒等参数该如何处置？
    /*保存到数据库*/
    protected void saveEntryToDB(boolean update) {
        //假定update的时候entryToEdit不为空

        int preId = update ? entryToEdit.getId() : 0;

        switch (enumEntry) {
            case shortHand: {//速记
                entryToEdit = new EntryShortHand(
                        edit_activity_title.getText().toString(),
                        "",
                        new MyMoment(),
                        "1",
                        "1",
                        null
                );
                break;
            }
            case schedule: {    //日程
                entryToEdit = new EntrySchedule(
                        edit_activity_title.getText().toString(),
                        "",
                        new MyMoment(),
                        "",
                        timeStart,
                        timeEnd,
                        edit_remind_picker.getText().toString().equals("不提醒") ? "0" : "1",
                        MyUtil.getAlertTime(timeStart, edit_remind_picker.getText().toString()),
                        edit_location_et.getText().toString()
                );
                break;
            }
            case theseDays: {    //这两天
                entryToEdit = new EntryTheseDays(
                        edit_activity_title.getText().toString(),
                        "",
                        new MyMoment(),
                        "1"
                );
                break;
            }
            case deadLine: {    // DDL
                entryToEdit = new EntryDeadLine(
                        edit_activity_title.getText().toString(),
                        "",
                        new MyMoment(),
                        "1",
                        edit_remind_picker.getText().toString().equals("不提醒") ? "0" : "1",
                        MyUtil.getAlertTime(timeDDL, edit_remind_picker.getText().toString()),
                        edit_location_et.getText().toString(),
                        Integer.valueOf(edit_ddl_expand_et2.getText().toString()) * Integer.valueOf(edit_ddl_expand_et.getText().toString()),
                        Integer.valueOf(edit_ddl_expand_et.getText().toString()),
                        timeDDL
                );
                break;
            }
            case someDay: {//有朝一日
                entryToEdit = new EntrySomeDay(
                        update ? edit_activity_title.getText().toString() : et_someday.getText().toString(),
                        "",
                        new MyMoment(),
                        "1",
                        "",
                        new MyMoment()
                );
            }
        }
        //判断是添加还是修改
        if (update) {
            entryToEdit.setId(preId);
        }
        if (update) {
            SQLMan.getInstance(EntryEditActivity.this).update(entryToEdit);
        } else {
            SQLMan.getInstance(EntryEditActivity.this).create(entryToEdit);
        }
    }

    /*设置是否可见*/
    protected void set_layout_visible(int[] viewVisible) {
        linearlayout_schedule.setVisibility(viewVisible[0]);
        linearlayout_deadling.setVisibility(viewVisible[1]);
        linearlayout_remind.setVisibility(viewVisible[2]);
        linearlayout_location.setVisibility(viewVisible[3]);
        linearlayout_ddl_expand.setVisibility(viewVisible[4]);
    }

    /*选择提醒时间dialog*/
    protected void myDialog() {
        View view = LayoutInflater.from(EntryEditActivity.this).
                inflate(R.layout.dialog_remind, (ViewGroup) findViewById(R.id.dialog_remind), false);
        remindDialog = new AlertDialog.Builder(this).
                setView(view).create();
        View.OnClickListener cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_remind_picker.setText(((Button) v).getText());
                remindDialog.dismiss();
            }
        };
        int[] idArray = new int[]{
                R.id.btn_remind_time1,
                R.id.btn_remind_time2,
                R.id.btn_remind_time3,
                R.id.btn_remind_time4,
                R.id.btn_remind_time5,
                R.id.btn_remind_time6,
                R.id.btn_remind_time7,
        };
        for (int id : idArray) {
            view.findViewById(id).setOnClickListener(cl);
        }
        remindDialog.show();
    }

    /*覆写部分*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            //TODO 考虑activity的启动模式
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
