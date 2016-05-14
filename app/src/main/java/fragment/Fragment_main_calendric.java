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

import java.util.ArrayList;

import adapter.Adapter_view_calendric;
import bit.eagzzycsl.smartable2.EditActivity;
import bit.eagzzycsl.smartable2.EnumEntry;
import bit.eagzzycsl.smartable2.EnumExtra;
import bit.eagzzycsl.smartable2.ExtraFiled;
import bit.eagzzycsl.smartable2.ModifyDetailActivity;
import bit.eagzzycsl.smartable2.R;
import database.SQLMan;
import entry.EntrySchedule;
import my.MyDate;
import my.MyLog;
import my.MyMoment;
import view.CalendricView;
import view.CalendricViewItemClick;
import view.CalendricViewItemProvider;
import view.EnumCalendricViewType;


public class Fragment_main_calendric extends Fragment {

    private View myView;
    private CalendricView calendricView_day;
    private Adapter_view_calendric adapter_view_calendric;
    private FloatingActionButton fab_add;

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
        return myView;
    }

    public void myFindView() {
        calendricView_day = (CalendricView) myView.findViewById(R.id.calendricView_day);
        fab_add = (FloatingActionButton) myView.findViewById(R.id.fab_add);
    }

    @SuppressWarnings("unchecked")
    private void myCreate() {
        adapter_view_calendric = new Adapter_view_calendric(this.getActivity(),
                EnumCalendricViewType.Day,
                new CalendricViewItemProvider() {
                    @Override
                    public ArrayList<EntrySchedule> readFromDatabase(MyDate myDate) {
//                        return new ArrayList<EntrySchedule>();
                        return (ArrayList<EntrySchedule>) SQLMan.getInstance(getActivity()).read(EnumEntry.schedule, myDate);

                    }
                },
                new CalendricViewItemClick() {
                    @Override
                    public void onItemClick(View v, EntrySchedule entrySchedule) {
                        Intent intent = new Intent(getActivity(), ModifyDetailActivity.class);
                        intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
                        intent.putExtra(ExtraFiled.entryToEdit, entrySchedule);
                        startActivity(intent);
                    }

                    @Override
                    public void onAddClick(View v, MyMoment m) {
                        Intent intent = new Intent(getActivity(), EditActivity.class);
                        intent.putExtra(EnumExtra.getName(), EnumExtra.addScheduleWithMoment);
                        MyLog.i("点击时的时间", m.convertToLocalString());
                        //pager当天的日期和时间

                        intent.putExtra(ExtraFiled.myMoment, m);
                        startActivity(intent);
                    }
                }
        );
    }

    private void mySetView() {
        calendricView_day.setAdapter(adapter_view_calendric);
        MyDate myDate = new MyDate();
        myDate.dayAdd(adapter_view_calendric.getEnumViewType().getDiv() * -1);
        calendricView_day.setFirstDay(myDate);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(EnumExtra.getName(), EnumExtra.addScheduleWithMoment);
                //TODO pager当天的日期和今天的时间
                intent.putExtra(ExtraFiled.myMoment, new MyMoment());
                startActivity(intent);
            }
        });
    }


}
