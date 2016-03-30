package fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.Adapter_view_calendric;
import bit.eagzzycsl.smartable2.R;
import view.CalendricView;


public class Fragment_main_calendric extends Fragment {

    private View myView;
    private CalendricView calendricView_day;
    private Adapter_view_calendric adapter_view_calendric;

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
    }

    private void myCreate() {
        adapter_view_calendric = new Adapter_view_calendric(this.getActivity(),null);
    }

    private void mySetView() {
        calendricView_day.setAdapter(adapter_view_calendric);
    }


}
