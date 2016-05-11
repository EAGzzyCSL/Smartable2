package bit.eagzzycsl.smartable2;

import android.view.View;

import java.util.Calendar;

import my.MyMoment;

public class EditActivity extends EntryEditActivity {

    @Override
    protected void myInit() {
        edit_rbtn1.setSelected(true);
        selectedEntryType(EnumEntry.shortHand);
        Calendar c = Calendar.getInstance();
        timeStart = MyMoment.createFromCalendar(c);
        timeEnd = MyMoment.createFromCalendar(c);
        timeDDL = MyMoment.createFromCalendar(c);
        textView_startDate.setText(timeStart.getDate().convertToLocalString());
        textView_endDate.setText(timeEnd.getDate().convertToLocalString());
        textView_startTime.setText(timeStart.getTime().convertToLocalString());
        textView_endTime.setText(timeEnd.getTime().convertToLocalString());
        edit_ddl_datetime_picker.setText(timeDDL.getDate().convertToLocalString());
        edit_ddl_datetime_picker2.setText(timeDDL.getTime().convertToLocalString());
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
