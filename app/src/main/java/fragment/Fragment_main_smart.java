package fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapter.Adapter_main_kind_recyclerView;
import adapter.Adapter_main_viewPager_kind;
import bit.eagzzycsl.smartable2.R;
import entry.Entry;
import entry.EntryNote;

public class Fragment_main_smart extends Fragment {
    private View myView;
    private Toolbar toolbar;
    private FloatingActionButton fab_add;
    private TabLayout tabLayout_kind;
    private ViewPager viewPager_kind;
    private Adapter_main_viewPager_kind adapter_main_viewPager_kind;

    public Fragment_main_smart() {
        // Required empty public constructor
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.fragment_main_smart, container, false);
        myFindView();
        myCreate();
        mySetView();
        return myView;
    }

    private void myFindView() {
        toolbar = (Toolbar) myView.findViewById(R.id.toolbar);
        fab_add = (FloatingActionButton) myView.findViewById(R.id.fab);
        tabLayout_kind = (TabLayout) myView.findViewById(R.id.tabLayout_kind);
        viewPager_kind = (ViewPager) myView.findViewById(R.id.viewPager_kind);
    }

    private void mySetView() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        viewPager_kind.setAdapter(adapter_main_viewPager_kind);
        tabLayout_kind.setupWithViewPager(viewPager_kind);
        tabLayout_kind.setTabsFromPagerAdapter(adapter_main_viewPager_kind);

    }

    private void myCreate() {
        final RecyclerView[] pages = new RecyclerView[]{
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
        };
        for (RecyclerView r : pages) {
            r.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            r.setItemAnimator(new DefaultItemAnimator());
        }
        View[] views = new View[pages.length];
        int[] sort = new int[]{3, 2, 0, 1};
        for (int i = 0; i < views.length; i++) {
            pages[i].setAdapter(new Adapter_main_kind_recyclerView(new ArrayList<Entry>() {
                {
                    this.add(new EntryNote("hello"));
                }
            }, getActivity()));
            views[sort[i]] = pages[i];
        }
        adapter_main_viewPager_kind
                = new Adapter_main_viewPager_kind(views,
                getActivity().getResources().
                        getStringArray(R.array.activity_main_smart_pager_title), sort);


    }



}
