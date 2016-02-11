package fragment;


import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.zip.Inflater;

import adapter.Adapter_main_kind_recyclerView;
import adapter.Adapter_main_viewPager_kind;
import bit.eagzzycsl.smartable2.R;
import entry.Entry;
import entry.EntryNote;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_main_smart extends Fragment {


    public Fragment_main_smart() {
        // Required empty public constructor
    }

    private Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_smart, container, false);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TabLayout tabLayout_kind = (TabLayout) v.findViewById(R.id.tabLayout_kind);
        ViewPager viewPager_kind = (ViewPager) v.findViewById(R.id.viewPager_kind);

        View[] viewArray = new View[]{
                View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
        };
        int[] sort = new int[]{0, 1, 2, 3};
        ArrayList<View> views = new ArrayList<>(4);

        for (int i = 0; i < sort.length; i++) {
//            RecyclerView r = (RecyclerView) viewArray[i].findViewById(R.id.recyclerView_tmp);
//            r.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
//            r.setItemAnimator(new DefaultItemAnimator());
//            r.setAdapter(new Adapter_main_kind_recyclerView(new ArrayList<Entry>() {
//                {
//                    this.add(new EntryNote("hello"));
//                }
//            }, getActivity()));
            views.add(viewArray[i]);
//            Log.i("hi", "##" + r.getHeight());
        }
        Adapter_main_viewPager_kind adapter_main_viewPager_kind
                = new Adapter_main_viewPager_kind(views);
        viewPager_kind.setAdapter(adapter_main_viewPager_kind);
        tabLayout_kind.setupWithViewPager(viewPager_kind);
        tabLayout_kind.setTabsFromPagerAdapter(adapter_main_viewPager_kind);
        return v;
    }


}
