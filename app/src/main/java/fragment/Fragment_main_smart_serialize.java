package fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.Adapter_recyclerView_entry;
import bit.eagzzycsl.smartable2.R;
import database.SQLMan;
import decorator.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_main_smart_serialize extends Fragment {
    private RecyclerView recycler_view_serialize;
    private Adapter_recyclerView_entry adapter;

    public Fragment_main_smart_serialize() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_smart_serialize, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_view_serialize=(RecyclerView)view.findViewById(R.id.recycler_view_serialize);
        recycler_view_serialize.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view_serialize.addItemDecoration(new DividerItemDecoration(getActivity()));
        adapter=new Adapter_recyclerView_entry(SQLMan.getInstance(getActivity()).readAll(),getActivity());
        recycler_view_serialize.setAdapter(adapter);
    }
}
