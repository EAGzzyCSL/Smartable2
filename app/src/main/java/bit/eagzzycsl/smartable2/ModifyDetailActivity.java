package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import database.SQLMan;
import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;

public class ModifyDetailActivity extends EntryEditActivity {
    //这个界面的代码真是把我看凌乱了。
    private LinearLayout linearLayout_rbtngroup;
    private LinearLayout linearLayout_aboveKey;
    private Button btn_modify_delete;


    @Override
    protected void myFindView() {
        super.myFindView();
        linearLayout_rbtngroup = (LinearLayout) findViewById(R.id.linearlaayout_rbtngroup);
        linearLayout_aboveKey = (LinearLayout) findViewById(R.id.linearlayout_aboveKey);
        btn_modify_delete = (Button) findViewById(R.id.btn_modify_delete);
    }

    @Override
    protected void myInit() {
        Bundle bundle = this.getIntent().getExtras();
        EnumExtra enumExtra = (EnumExtra) bundle.getSerializable(EnumExtra.getName());
        if (enumExtra != null) {

            switch (enumExtra) {
                case modifyEntry: {
                    entryToEdit = (Entry) bundle.getSerializable(ExtraFiled.entryToEdit);
                    enumEntry = entryToEdit == null ? null : entryToEdit.getType();
                    selectedEntryType(enumEntry);
                    show_entry_infor(entryToEdit);
                    break;
                }

            }

        } else {
            //貌似什么也不需要做。
        }
        linearLayout_rbtngroup.setVisibility(View.GONE);//隐藏rbtn
        linearLayout_aboveKey.setVisibility(View.GONE);//隐藏键盘上面的一条
        btn_modify_delete.setVisibility(View.VISIBLE);

    }

    protected void mySetView() {
        super.mySetView();
        btn_modify_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLMan.getInstance(ModifyDetailActivity.this).delete(entryToEdit);
                Intent intent = new Intent();
                intent.putExtra(EnumExtra.getName(), EnumExtra.entryRemoved);
                intent.putExtra(ExtraFiled.entryResult, entryToEdit);
                setResult(IntentCode.result_fromEntryEditToMain, intent);
                finish();
            }
        });
    }

    /*根据传入的entry展示entry的信息*/
    private void show_entry_infor(Entry entry) {
        edit_activity_title.setText(entry.getTitle());
        switch (enumEntry) {
            case shortHand: {//速记
                break;
            }
            case schedule: {//日程
                EntrySchedule schedule = (EntrySchedule) entryToEdit;

                setTimeStart(schedule.getDate_begin());
                setTimeEnd(schedule.getDate_end());
                //TODO 重构提醒，重复次数等的设置view
                if (schedule.getAlert() == "0" || schedule.getAlert() == null) {
                    edit_remind_picker.setText("不提醒");
                } else {
                    edit_remind_picker.setText(schedule.getDate_alert().convertToString());
                }
                edit_location_et.setText(schedule.getLocation());
                break;
            }
            case theseDays: {//这两天
                break;
            }
            case deadLine: {// DDL
                EntryDeadLine deadLine = (EntryDeadLine) entryToEdit;
                setTimeDDL(deadLine.getDate_ddl());
                if (deadLine.getAlert() == "0") {
                    edit_remind_picker.setText("不提醒");
                } else {
                    edit_remind_picker.setText(deadLine.getDate_alert().convertToString());
                }
                edit_location_et.setText(deadLine.getLocation());
                if (String.valueOf(deadLine.getTodo_duration()) != "" && String.valueOf(deadLine.getTodo_duration()) != "") {
                    edit_ddl_expand_et.setText("" + deadLine.getTodo_numbers());
                    int ddl_expand_et2 = deadLine.getTodo_duration() / deadLine.getTodo_numbers();
                    edit_ddl_expand_et2.setText("" + ddl_expand_et2);
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_modify_detail, menu);
        return true;
    }

    /*点击菜单上的完成钩时保存修改到数据库*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_modify_done: {
                if(edit_activity_title.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "标题未填写任何东西", Toast.LENGTH_LONG).show();
                }
                else{
                    saveEntryToDB(true);
                    Intent intent = new Intent();
                    intent.putExtra(EnumExtra.getName(), EnumExtra.entryModified);
                    intent.putExtra(ExtraFiled.entryResult, entryToEdit);
                    setResult(IntentCode.result_fromEntryEditToMain, intent);
                    finish();
                    break;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
