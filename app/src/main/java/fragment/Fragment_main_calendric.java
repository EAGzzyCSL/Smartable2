package fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import adapter.Adapter_view_calendric;
import bit.eagzzycsl.smartable2.EditActivity;
import bit.eagzzycsl.smartable2.EnumEntry;
import bit.eagzzycsl.smartable2.EnumExtra;
import bit.eagzzycsl.smartable2.ExtraFiled;
import bit.eagzzycsl.smartable2.IntentCode;
import bit.eagzzycsl.smartable2.ModifyDetailActivity;
import bit.eagzzycsl.smartable2.R;
import database.SQLMan;
import entry.Entry;
import entry.EntrySchedule;
import my.MyDate;
import my.MyMoment;
import my.MyUtil;
import view.CalendricView;
import view.CalendricViewItemClick;
import view.CalendricViewItemProvider;
import view.EnumCalendricViewType;


public class Fragment_main_calendric extends Fragment {

    private View myView;
    private CalendricView calendricView_day;
    private CalendricView calendricView_week;
    private Adapter_view_calendric adapter_view_calendric_day;
    private Adapter_view_calendric adapter_view_calendric_week;
    private FrameLayout frameLayout_cal;
    private FloatingActionButton fab_add;
    private CalendricView preCalView;
    private CalendricViewItemClick calendricViewItemClick;
    private View.OnClickListener fab_onclick;

    public Fragment_main_calendric() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_main_calendric, container, false);
        myFindView();
        myCreate();
        mySetView();
        preCalView = calendricView_day;
        return myView;
    }

    public void myFindView() {
        calendricView_day = (CalendricView) myView.findViewById(R.id.calendricView_day);
        calendricView_week = (CalendricView) myView.findViewById(R.id.calendricView_week);
        frameLayout_cal = (FrameLayout) myView.findViewById(R.id.frameLayout_cal);
        fab_add = (FloatingActionButton) myView.findViewById(R.id.fab_add);
    }

    @SuppressWarnings("unchecked")
    private void myCreate() {
        calendricViewItemClick = new CalendricViewItemClick() {
            @Override
            public void onItemClick(View v, EntrySchedule entrySchedule) {
                Intent intent = new Intent(getActivity(), ModifyDetailActivity.class);
                intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
                intent.putExtra(ExtraFiled.entryToEdit, entrySchedule);
                getActivity().startActivityForResult(intent, IntentCode.request_fromMainToEntryEdit);
            }

            @Override
            public void onAddClick(View v, MyMoment m) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(EnumExtra.getName(), EnumExtra.addScheduleWithMoment);
                intent.putExtra(ExtraFiled.myMoment, m);
                getActivity().startActivityForResult(intent, IntentCode.request_fromMainToEntryEdit);
            }
        };
        adapter_view_calendric_day = new Adapter_view_calendric(this.getActivity(),
                EnumCalendricViewType.Day,
                new CalendricViewItemProvider() {
                    @Override
                    public ArrayList<EntrySchedule> readFromDatabase(MyDate myDate) {
                        return (ArrayList<EntrySchedule>) SQLMan.getInstance(getActivity()).read(EnumEntry.schedule, myDate);

                    }
                }, calendricViewItemClick

        );
        adapter_view_calendric_week = new Adapter_view_calendric(this.getActivity(),
                EnumCalendricViewType.Week,
                new CalendricViewItemProvider() {
                    @Override
                    public ArrayList<EntrySchedule> readFromDatabase(MyDate myDate) {
                        //先使用不带日期的read代替
                        return (ArrayList<EntrySchedule>) SQLMan.getInstance(getActivity()).read(EnumEntry.schedule);

                    }
                }, calendricViewItemClick
        );
        fab_onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(EnumExtra.getName(), EnumExtra.addScheduleWithMoment);
                //TODO pager当天的日期和今天的时间
                intent.putExtra(ExtraFiled.myMoment, MyUtil.SuitableTime(new MyMoment()));
                getActivity().startActivityForResult(intent, IntentCode.request_fromMainToEntryEdit);
            }
        };
    }

    private void mySetView() {
        calendricView_day.setAdapter(adapter_view_calendric_day);
        /*这里的date仅用来做数据传递*/
        MyDate myDate = new MyDate();
        myDate.dayAdd(adapter_view_calendric_day.getEnumViewType().getDiv() * -1);
        calendricView_day.setFirstDay(myDate);
        //TODO 此处使用post太麻烦
        calendricView_day.post(new Runnable() {
            @Override
            public void run() {
                calendricView_day.scrollToCurrentTime();
            }
        });
        calendricView_week.setAdapter(adapter_view_calendric_week);
        myDate = new MyDate();
        myDate.dayAdd(adapter_view_calendric_week.getEnumViewType().getDiv() * -1);
        calendricView_week.setFirstDay(myDate);
        //TODO 几种视图模式的日历需要关联起来，这样才可以实现从日视图跳转到周视图时是当天所在的周
        fab_add.setOnClickListener(fab_onclick);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentCode.request_fromMainToEntryEdit
                && resultCode == IntentCode.result_fromEntryEditToMain) {
            Entry entry = (Entry) data.getSerializableExtra(ExtraFiled.entryResult);
            /*只处理日程*/
            if (entry.getType() == EnumEntry.schedule) {
                calendricView_day.updateItem();
            }
        }
    }

    public void showCal(EnumCalendricViewType enumCalendricViewType) {
        if (preCalView != null) {
            preCalView.setVisibility(View.GONE);
        }
        switch (enumCalendricViewType) {
            case Month: {
                break;
            }
            case Week: {
                calendricView_week.setVisibility(View.VISIBLE);
                preCalView = calendricView_week;
                break;
            }
            case ThreeDay: {
                break;
            }
            case Day: {
                calendricView_day.setVisibility(View.VISIBLE);
                preCalView = calendricView_day;
                break;
            }
        }
    }
}
