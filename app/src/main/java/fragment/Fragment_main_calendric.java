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
import bit.eagzzycsl.smartable2.R;
import entry.Entry;
import entry.EntrySchedule;
import my.MyDate;
import my.MyTime;
import view.CalendricView;
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

    private void myCreate() {
        adapter_view_calendric = new Adapter_view_calendric(this.getActivity(),
                EnumCalendricViewType.Day,
                new CalendricViewItemProvider() {
                    @Override
                    public ArrayList<EntrySchedule> readFromDatabase(int i) {
                        return new ArrayList<EntrySchedule>() {
                            {
                                this.add(new EntrySchedule(1, "3:00-8:00", new MyTime(3, 0), new MyTime(8, 0)));

                            }
                        };
                    }
                }
        );
    }

    private void mySetView() {
        calendricView_day.setAdapter(adapter_view_calendric);
        calendricView_day.setFirstDay(new MyDate(2016,4,29));
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditActivity.class));
            }
        });
    }


}
