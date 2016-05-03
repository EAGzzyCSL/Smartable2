package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import database.DatabaseManager;
import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntrySomeDay;
import entry.EntryTheseDays;
import my.MyTime;
import my.MyUtil;
public class ModifyDetailActivity extends AppCompatActivity {

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
    private Button edit_done_btn;//yu---
    private Integer flag_nowWhatPage = 0;//yu---
    private int id_forModify = -1;

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

    //new
    private LinearLayout linearlaayout_rbtngroup;
    private LinearLayout linearlayout_aboveKey;
    private Button btn_modify_delete;

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
        edit_done_btn = (Button) findViewById(R.id.edit_done_btn);//yu---

        edit_barlayout = (android.support.design.widget.AppBarLayout) findViewById(R.id.edit_barlayout);
        textView_startDate = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_startDate);
        textView_endDate = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_endDate);
        textView_startTime = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_startTime);
        textView_endTime = (android.support.v7.widget.AppCompatTextView) findViewById(R.id.textView_endTime);

        //new
        linearlaayout_rbtngroup = (LinearLayout)findViewById(R.id.linearlaayout_rbtngroup);
        linearlayout_aboveKey = (LinearLayout)findViewById(R.id.linearlayout_aboveKey);
        btn_modify_delete = (Button)findViewById(R.id.btn_modify_delete);
        //new END
    }

    //new
    //toolbar确定按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_modify_detail, menu);
        return true;
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            switch (flag_nowWhatPage) {
                case 0: {   //速记-改
                    EntryShortHand shortHand = new EntryShortHand(edit_activity_title.getText().toString());
                    shortHand.setTitle(edit_activity_title.getText().toString());
                    shortHand.setAnnotation("");
                    shortHand.setDate_create(simpleDateFormat.format(now.getTime()));
                    shortHand.setStatus("1");
                    DatabaseManager.getInstance(ModifyDetailActivity.this).updateShortHand(id_forModify, shortHand);
                    break;
                }
                case 1: {    //日程-改
                    EntrySchedule entrySchedule = new EntrySchedule(edit_activity_title.getText().toString());
                    entrySchedule.setTitle(edit_activity_title.getText().toString());
                    entrySchedule.setAnnotation("");//todo 暂时没有数据
                    entrySchedule.setDate_create(simpleDateFormat.format(now.getTime()));//创建时间
                    entrySchedule.setStatus("1");//未完成标记

                    String date_begin = simpleDateFormat.format(new Date(timeStart.getYear() - 1900, timeStart.getMonth() - 1, timeStart.getDay(), timeStart.getHour(), timeStart.getMinute()));//年份要减去1900；月份要注意转换， 1月是0
                    String date_end = simpleDateFormat.format(new Date(timeStart.getYear() - 1900, timeStart.getMonth() - 1, timeStart.getDay(), timeStart.getHour(), timeStart.getMinute()));//年份要减去1900；月份要注意转换， 1月是0

                    entrySchedule.setDate_begin(date_begin);
                    entrySchedule.setDate_end(date_end);
                    if (edit_remind_picker.getText().toString().equals("不提醒")) { //判断是否有提醒时间
                        entrySchedule.setAlert("0");
                    } else {
                        entrySchedule.setAlert("1");//提醒标记
                        try {
                            entrySchedule.setDate_alert(MyUtil.getAlertTime(date_begin, edit_remind_picker.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    entrySchedule.setLocation(edit_location_et.getText().toString());

                    DatabaseManager.getInstance(ModifyDetailActivity.this).updateSchedule(id_forModify, entrySchedule);
                    break;
                }
                case 2: {    //这两天
                    EntryTheseDays theseDays = new EntryTheseDays(edit_activity_title.getText().toString());
                    theseDays.setTitle(edit_activity_title.getText().toString());
                    theseDays.setAnnotation("");
                    theseDays.setDate_create(simpleDateFormat.format(now.getTime()));
                    theseDays.setStatus("1");
                    DatabaseManager.getInstance(ModifyDetailActivity.this).updateTheseDays(id_forModify, theseDays);
                    break;
                }
                case 3: {    // DDL
                    EntryDeadLine ddl = new EntryDeadLine(edit_activity_title.getText().toString());
                    ddl.setTitle(edit_activity_title.getText().toString());
                    ddl.setAnnotation("");//todo 暂时没有数据
                    ddl.setDate_create(simpleDateFormat.format(now.getTime()));//创建时间
                    ddl.setStatus("1");//未完成标记

                    String date_ddl = simpleDateFormat.format(new Date(timeDDL.getYear() - 1900, timeDDL.getMonth() - 1, timeDDL.getDay(), timeDDL.getHour(), timeDDL.getMinute()));//年份要减去1900；月份要注意转换， 1月是0
                    if (edit_remind_picker.getText().toString().equals("不提醒")) { //判断是否有提醒时间
                        ddl.setAlert("0");
                    } else {
                        ddl.setAlert("1");//提醒标记
                        try {
                            ddl.setDate_alert(MyUtil.getAlertTime(date_ddl, edit_remind_picker.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    ddl.setLocation(edit_location_et.getText().toString());

                    ddl.setDate_ddl(date_ddl);//截止时间
                    ddl.setTodo_numbers(Integer.valueOf(edit_ddl_expand_et.getText().toString()));//准备做几次
                    ddl.setTodo_duration(Integer.valueOf(edit_ddl_expand_et2.getText().toString()) * Integer.valueOf(edit_ddl_expand_et.getText().toString()));//总共做多长时间

                    DatabaseManager.getInstance(ModifyDetailActivity.this).updateDDL(id_forModify, ddl);

                    //todo 判断是否有分几次，每次多长时间
                    break;
                }
                default: Toast.makeText(ModifyDetailActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    private void myInit() {
        //new
        //判断是哪一类事物的页面
        Bundle bundle = this.getIntent().getExtras();
        Entry entry = (Entry)bundle.getSerializable("entry");
        if (entry instanceof EntryShortHand) {//速记
            set_layout_visible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
            edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyOrange));
            flag_nowWhatPage = 0;
        } else if (entry instanceof EntrySchedule) {//日程
            set_layout_visible(View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE);
            edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyYellow));
            flag_nowWhatPage = 1;
        } else if (entry instanceof EntrySomeDay) {//有朝一日
            set_layout_visible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
            edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyRed));
            //TODO:有朝一日flag_nowWhatPage?
        } else if (entry instanceof EntryTheseDays) {//这两天
            set_layout_visible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
            edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyPurple));
            flag_nowWhatPage = 2;
        } else if (entry instanceof EntryDeadLine) {//DDL
            set_layout_visible(View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
            edit_barlayout.setBackgroundColor(getResources().getColor(R.color.colorMyBlue));
            flag_nowWhatPage = 3;
        }

        linearlaayout_rbtngroup.setVisibility(View.GONE);//隐藏rbtn
        linearlayout_aboveKey.setVisibility(View.GONE);//隐藏键盘上面的一条
        btn_modify_delete.setVisibility(View.VISIBLE);
        show_entry_infor(entry);
        //new END

        //yu---预置数据
//        DatabaseManager.getInstance(EditActivity.this).insertDDL(presetData.testDDL1());
//        Toast.makeText(EditActivity.this, "成功ye", Toast.LENGTH_SHORT).show();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_ddl = new Date(timeDDL.getYear() - 1900, timeDDL.getMonth() - 1, timeDDL.getDay(), timeDDL.getHour(), timeDDL.getMinute());//年份要减去1900；月份要注意转换， 1月是0


        Calendar c = Calendar.getInstance();
//        Date date2 = c.getTime();
//        String date2_2 = simpleDateFormat.format(date2);
//        Log.i("TAG_date2", date2.toString());
//        Log.i("TAG_date2_2", date2_2);

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
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        //toolbar设置按钮

        textView_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(ModifyDetailActivity.this, textView_startDate, timeStart)
                        .show();
            }
        });

        textView_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(ModifyDetailActivity.this, textView_endDate, timeEnd)
                        .show();
            }
        });
        textView_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(ModifyDetailActivity.this, textView_startTime, timeStart)
                        .show(R.style.MyTimePickerDialogYellowTheme);
            }
        });
        textView_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(ModifyDetailActivity.this, textView_endTime, timeEnd)
                        .show(R.style.MyTimePickerDialogYellowTheme);
            }
        });

        edit_ddl_datetime_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(ModifyDetailActivity.this, edit_ddl_datetime_picker, timeDDL)
                        .show();
            }
        });

        edit_ddl_datetime_picker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(ModifyDetailActivity.this, edit_ddl_datetime_picker2, timeDDL)
                        .show(R.style.MyTimePickerDialogYellowTheme);
            }
        });

        edit_remind_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog();
            }
        });

        //yu---
        //点击确定按钮，绑定事件  edit_done_btn （新建事项）
        edit_done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar now = Calendar.getInstance();

                Log.i("TAG666", flag_nowWhatPage.toString());
                switch (flag_nowWhatPage) {
                    case 0: {//速记-新建
                        EntryShortHand shortHand = new EntryShortHand(edit_activity_title.getText().toString());
                        shortHand.setTitle(edit_activity_title.getText().toString());
                        shortHand.setAnnotation("");
                        shortHand.setDate_create(simpleDateFormat.format(now.getTime()));
                        shortHand.setStatus("1");
                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertShortHand(shortHand);

                        //跳转到显示界面
                        //Intent intent = new Intent(EditActivity.this, Fragment_main_smart_classify.class);
                        //startActivity(intent);
                        break;
                    }
                    case 1: {    //日程-新建
                        EntrySchedule entrySchedule = new EntrySchedule(edit_activity_title.getText().toString());
                        entrySchedule.setTitle(edit_activity_title.getText().toString());
                        entrySchedule.setAnnotation("");//todo 暂时没有数据
                        entrySchedule.setDate_create(simpleDateFormat.format(now.getTime()));//创建时间
                        entrySchedule.setStatus("1");//未完成标记

                        String date_begin = simpleDateFormat.format(new Date(timeStart.getYear() - 1900, timeStart.getMonth() - 1, timeStart.getDay(), timeStart.getHour(), timeStart.getMinute()));//年份要减去1900；月份要注意转换， 1月是0
                        String date_end = simpleDateFormat.format(new Date(timeStart.getYear() - 1900, timeStart.getMonth() - 1, timeStart.getDay(), timeStart.getHour(), timeStart.getMinute()));//年份要减去1900；月份要注意转换， 1月是0

                        entrySchedule.setDate_begin(date_begin);
                        entrySchedule.setDate_end(date_end);
                        if (edit_remind_picker.getText().toString().equals("不提醒")) { //判断是否有提醒时间
                            entrySchedule.setAlert("0");
                        } else {
                            entrySchedule.setAlert("1");//提醒标记
                            try {
                                entrySchedule.setDate_alert(MyUtil.getAlertTime(date_begin, edit_remind_picker.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        entrySchedule.setLocation(edit_location_et.getText().toString());

                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertSchedule(entrySchedule);
                        Toast.makeText(ModifyDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {    //这两天-新建
                        EntryTheseDays theseDays = new EntryTheseDays(edit_activity_title.getText().toString());
                        theseDays.setTitle(edit_activity_title.getText().toString());
                        theseDays.setAnnotation("");
                        theseDays.setDate_create(simpleDateFormat.format(now.getTime()));
                        theseDays.setStatus("1");
                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertTheseDays(theseDays);
                        Toast.makeText(ModifyDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3: {    // DDL-新建

                        EntryDeadLine ddl = new EntryDeadLine(edit_activity_title.getText().toString());
                        ddl.setTitle(edit_activity_title.getText().toString());
                        ddl.setAnnotation("");//todo 暂时没有数据
                        ddl.setDate_create(simpleDateFormat.format(now.getTime()));//创建时间
                        ddl.setStatus("1");//未完成标记

                        String date_ddl = simpleDateFormat.format(new Date(timeDDL.getYear() - 1900, timeDDL.getMonth() - 1, timeDDL.getDay(), timeDDL.getHour(), timeDDL.getMinute()));//年份要减去1900；月份要注意转换， 1月是0
                        if (edit_remind_picker.getText().toString().equals("不提醒")) { //判断是否有提醒时间
                            ddl.setAlert("0");
                        } else {
                            ddl.setAlert("1");//提醒标记
                            try {
                                ddl.setDate_alert(MyUtil.getAlertTime(date_ddl, edit_remind_picker.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        ddl.setLocation(edit_location_et.getText().toString());

                        ddl.setDate_ddl(date_ddl);//截止时间
                        ddl.setTodo_numbers(Integer.valueOf(edit_ddl_expand_et.getText().toString()));//准备做几次
                        ddl.setTodo_duration(Integer.valueOf(edit_ddl_expand_et2.getText().toString()) * Integer.valueOf(edit_ddl_expand_et.getText().toString()));//总共做多长时间

                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertDDL(ddl);
                        Toast.makeText(ModifyDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();

                        //todo 判断是否有分几次，每次多长时间
                        break;
                    }
                    default:
                        Log.i("TAG666", flag_nowWhatPage.toString());
                }
            }
        });

        //yu---
        //点击确定按钮，绑定事件  btn_modify_delete （删除事项）
        btn_modify_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar now = Calendar.getInstance();

                switch (flag_nowWhatPage) {
                    case 0: {//速记-删除
                        DatabaseManager.getInstance(ModifyDetailActivity.this).deleteShortHand(id_forModify);
                        break;
                    }
                    case 1: {    //日程-删除
                        DatabaseManager.getInstance(ModifyDetailActivity.this).deleteSchedule(id_forModify);
                        break;
                    }
                    case 2: {    //这两天-删除
                        DatabaseManager.getInstance(ModifyDetailActivity.this).deleteTheseDays(id_forModify);
                        break;
                    }
                    case 3: {    // DDL-删除
                        DatabaseManager.getInstance(ModifyDetailActivity.this).deleteDDL(id_forModify);
                        break;
                    }
                    default:
                        Log.i("TAG666", flag_nowWhatPage.toString());
                }
            }
        });

        //yu---
        //点击确定按钮，绑定事件  edit_done_btn （修改事项）
        //todo 还没有判别是否有变动，修改后，创建时间会更新
        edit_done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar now = Calendar.getInstance();

                Log.i("TAG666", flag_nowWhatPage.toString());
                switch (flag_nowWhatPage) {
                    case 0: {//速记-新建
                        EntryShortHand shortHand = new EntryShortHand(edit_activity_title.getText().toString());
                        shortHand.setTitle(edit_activity_title.getText().toString());
                        shortHand.setAnnotation("");
                        shortHand.setDate_create(simpleDateFormat.format(now.getTime()));
                        shortHand.setStatus("1");
                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertShortHand(shortHand);

                        //跳转到显示界面
                        //Intent intent = new Intent(EditActivity.this, Fragment_main_smart_classify.class);
                        //startActivity(intent);
                        break;
                    }
                    case 1: {    //日程-新建
                        EntrySchedule entrySchedule = new EntrySchedule(edit_activity_title.getText().toString());
                        entrySchedule.setTitle(edit_activity_title.getText().toString());
                        entrySchedule.setAnnotation("");//todo 暂时没有数据
                        entrySchedule.setDate_create(simpleDateFormat.format(now.getTime()));//创建时间
                        entrySchedule.setStatus("1");//未完成标记

                        String date_begin = simpleDateFormat.format(new Date(timeStart.getYear() - 1900, timeStart.getMonth() - 1, timeStart.getDay(), timeStart.getHour(), timeStart.getMinute()));//年份要减去1900；月份要注意转换， 1月是0
                        String date_end = simpleDateFormat.format(new Date(timeStart.getYear() - 1900, timeStart.getMonth() - 1, timeStart.getDay(), timeStart.getHour(), timeStart.getMinute()));//年份要减去1900；月份要注意转换， 1月是0

                        entrySchedule.setDate_begin(date_begin);
                        entrySchedule.setDate_end(date_end);
                        if (edit_remind_picker.getText().toString().equals("不提醒")) { //判断是否有提醒时间
                            entrySchedule.setAlert("0");
                        } else {
                            entrySchedule.setAlert("1");//提醒标记
                            try {
                                entrySchedule.setDate_alert(MyUtil.getAlertTime(date_begin, edit_remind_picker.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        entrySchedule.setLocation(edit_location_et.getText().toString());

                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertSchedule(entrySchedule);
                        Toast.makeText(ModifyDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {    //这两天-新建
                        EntryTheseDays theseDays = new EntryTheseDays(edit_activity_title.getText().toString());
                        theseDays.setTitle(edit_activity_title.getText().toString());
                        theseDays.setAnnotation("");
                        theseDays.setDate_create(simpleDateFormat.format(now.getTime()));
                        theseDays.setStatus("1");
                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertTheseDays(theseDays);
                        Toast.makeText(ModifyDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3: {    // DDL-新建

                        EntryDeadLine ddl = new EntryDeadLine(edit_activity_title.getText().toString());
                        ddl.setTitle(edit_activity_title.getText().toString());
                        ddl.setAnnotation("");//todo 暂时没有数据
                        ddl.setDate_create(simpleDateFormat.format(now.getTime()));//创建时间
                        ddl.setStatus("1");//未完成标记

                        String date_ddl = simpleDateFormat.format(new Date(timeDDL.getYear() - 1900, timeDDL.getMonth() - 1, timeDDL.getDay(), timeDDL.getHour(), timeDDL.getMinute()));//年份要减去1900；月份要注意转换， 1月是0
                        if (edit_remind_picker.getText().toString().equals("不提醒")) { //判断是否有提醒时间
                            ddl.setAlert("0");
                        } else {
                            ddl.setAlert("1");//提醒标记
                            try {
                                ddl.setDate_alert(MyUtil.getAlertTime(date_ddl, edit_remind_picker.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        ddl.setLocation(edit_location_et.getText().toString());

                        ddl.setDate_ddl(date_ddl);//截止时间
                        ddl.setTodo_numbers(Integer.valueOf(edit_ddl_expand_et.getText().toString()));//准备做几次
                        ddl.setTodo_duration(Integer.valueOf(edit_ddl_expand_et2.getText().toString()) * Integer.valueOf(edit_ddl_expand_et.getText().toString()));//总共做多长时间

                        DatabaseManager.getInstance(ModifyDetailActivity.this).insertDDL(ddl);
                        Toast.makeText(ModifyDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();

                        //todo 判断是否有分几次，每次多长时间
                        break;
                    }
                    default:
                        Log.i("TAG666", flag_nowWhatPage.toString());
                }
            }
        });

    }

    //lily
    //初始化entry具体信息
    private void show_entry_infor(Entry entry){
        id_forModify = entry.getId();
        edit_activity_title.setText(entry.getName());
        switch (flag_nowWhatPage){
            case 0: {//速记
                break;
            }
            case 1: {//日程
                EntrySchedule myentry = (EntrySchedule)entry;
                if(myentry.getDate_begin()!="" && myentry.getDate_begin()!=null){
                    String date_begin[] = myentry.getDate_begin().split(" ");
                    textView_startDate.setText(date_begin[0]);
                    textView_startTime.setText(date_begin[1]);
                }
                if(myentry.getDate_end()!="" && myentry.getDate_end()!=null){
                    String date_end[] = myentry.getDate_end().split(" ");
                    textView_endDate.setText(date_end[0]);
                    textView_endDate.setText(date_end[1]);
                }
                if(myentry.getAlert()=="0" || myentry.getAlert()==null){
                    edit_remind_picker.setText("不提醒");
                }else{
                    edit_remind_picker.setText(myentry.getDate_alert());
                }
                edit_location_et.setText(myentry.getLocation());
                break;
            }
            case 2: {//这两天
                break;
            }
            case 3: {// DDL
                EntryDeadLine myentry = (EntryDeadLine)entry;
                if(myentry.getDate_ddl()!="" && myentry.getDate_ddl()!=null){
                    String date_ddl[] = myentry.getDate_ddl().split(" ");
                    edit_ddl_datetime_picker.setText(date_ddl[0]);
                    edit_ddl_datetime_picker2.setText(date_ddl[1]);
                }
                if(myentry.getAlert()=="0"){
                    edit_remind_picker.setText("不提醒");
                }else{
                    edit_remind_picker.setText(myentry.getDate_alert());
                }
                edit_location_et.setText(myentry.getLocation());
                if(myentry.getTodo_duration()!=null && myentry.getTodo_duration()!=null){
                    edit_ddl_expand_et.setText(""+myentry.getTodo_numbers());
                    int ddl_expand_et2 = myentry.getTodo_duration() / myentry.getTodo_numbers();
                    edit_ddl_expand_et2.setText(""+ddl_expand_et2);
                }

                break;
            }
            default:break;
        }
    }
    //控制不同类别entry的各种属性的显示隐藏
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
            startActivity(new Intent(ModifyDetailActivity.this, MainActivity.class));
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
