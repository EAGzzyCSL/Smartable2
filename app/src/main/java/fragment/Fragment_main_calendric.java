package fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import bit.eagzzycsl.smartable2.R;


public class Fragment_main_calendric extends Fragment {

    private Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar;
    }

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
        View v = inflater.inflate(R.layout.fragment_main_calendric, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        return v;
    }


}
