package bit.eagzzycsl.smartable2;

import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import my.MyLog;
import my.MyMoment;

public class EditActivity extends EntryEditActivity {

    @Override
    protected void myInit() {
        Bundle bundle = this.getIntent().getExtras();
        EnumExtra enumExtra = (EnumExtra) bundle.getSerializable(EnumExtra.getName());
        if (enumExtra != null) {
            switch (enumExtra) {
                case addScheduleWithDate: {
                    selectedRadio(EnumEntry.schedule);
                    selectedEntryType(EnumEntry.schedule);
                    MyMoment myMoment = (MyMoment) bundle.getSerializable(ExtraFiled.myMoment);
                    setTimeStart(myMoment);
                    //TODO 结束时间后推动一小时，涉及到时间计算，需要在MyMoment中提供，装饰者模式引入，将所有时间操作全部放在my包中
                    setTimeEnd(myMoment.add());
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
            Calendar c = Calendar.getInstance();
            timeStart = MyMoment.createFromCalendar(c);
            timeEnd = MyMoment.createFromCalendar(c);
            timeDDL = MyMoment.createFromCalendar(c);
            setTimeEnd(timeEnd);
            setTimeDDL(timeDDL);
            setTimeStart(timeStart);
        }
    }

    @Override
    protected void mySetView() {
        super.mySetView();
        edit_done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveEntryToDB(false);
            }
        });
    }
}
