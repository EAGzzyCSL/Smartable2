package bit.eagzzycsl.smartable2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import my.MyLog;
import my.MyMoment;
import my.MyUtil;

public class EditActivity extends EntryEditActivity {


    @Override
    protected void myFindView(){
        super.myFindView();


    }
    protected void myInit() {
        Bundle bundle = this.getIntent().getExtras();
        EnumExtra enumExtra = (EnumExtra) bundle.getSerializable(EnumExtra.getName());
        if (enumExtra != null) {
            switch (enumExtra) {
                case addScheduleWithMoment: {
                    selectedRadio(EnumEntry.schedule);
                    selectedEntryType(EnumEntry.schedule);
                    MyMoment myMoment = (MyMoment) bundle.getSerializable(ExtraFiled.myMoment);
                    setTimeStart(myMoment);
                    setTimeDDL(MyUtil.SuitableTime(new MyMoment()));
                    //TODO 结束时间后推动一小时，涉及到时间计算，需要在MyMoment中提供，装饰者模式引入，将所有时间操作全部放在my包中
                    setTimeEnd(myMoment == null ? null : myMoment.newSameMoment().hourAdd(1));
                    break;
                }
                case addEntryWithEntryType: {
                    enumEntry = (EnumEntry) bundle.getSerializable(ExtraFiled.entryEnum);
                    if (enumEntry != null) {
                        //TODO it is strange that this three set must before selected
                        setTimeStart(MyUtil.SuitableTime(new MyMoment()));
                        setTimeEnd(MyUtil.SuitableTime((new MyMoment()).hourAdd(1)));
                        setTimeDDL(MyUtil.SuitableTime(new MyMoment()));
                        selectedRadio(enumEntry);
                        selectedEntryType(enumEntry);
                    }
                    break;
                }
                default: {
                    MyLog.i("新建entry", "不属于任何一个枚举");
                }
            }
        } else {
            //初始化
            edit_rbtn1.setSelected(true);
            selectedEntryType(EnumEntry.shortHand);
            setTimeStart(new MyMoment());
            setTimeEnd(new MyMoment());
            setTimeDDL(new MyMoment());
        }
    }

    @Override
    protected void mySetView() {
        super.mySetView();
        edit_done_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(edit_activity_title.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "标题未填写任何东西", Toast.LENGTH_LONG).show();
                }
                else{
                    saveEntryToDB(false);
                    Intent intent = new Intent();
                    intent.putExtra(EnumExtra.getName(), EnumExtra.entryAdded);
                    intent.putExtra(ExtraFiled.entryResult, entryToEdit);
                    setResult(IntentCode.result_fromEntryEditToMain, intent);
                    finish();
                }
            }
        });

    }



    public void onClick(View v){
        switch (v.getId()){
            case R.id.edit_someday_btn:
                edit_rbtn5.setChecked(true);
                dailog_someday();
                break;
            case R.id.edit_note_btn:
                startActivity(new Intent(this, NoteBookActivity.class));//TODO:这里,按返回为什么跳转回日视图的界面?
                break;
            default:
                break;
        }
    }
    private void dailog_someday(){
        et_someday = new EditText(this);

        dialog_someday = new AlertDialog.Builder(this).setTitle("有朝一日")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et_someday).setPositiveButton("保存",onDialogbtnlickListener(1))
                .setNegativeButton("取消", onDialogbtnlickListener(0)).show();
    }

    private DialogInterface.OnClickListener onDialogbtnlickListener(int isSave){
        switch (isSave){
            case 1://保存
                return new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveEntryToDB(false);
                        Intent intent = new Intent();
                        intent.putExtra(EnumExtra.getName(), EnumExtra.entryAdded);
                        intent.putExtra(ExtraFiled.entryResult, entryToEdit);
                        setResult(IntentCode.result_fromEntryEditToMain, intent);
                        finish();
                    }
                };
            case 0://取消
                return new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_rbtn1.setChecked(true);
                    }
                };


        }
        return null;
    }
}
