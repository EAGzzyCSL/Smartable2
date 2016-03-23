package fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import adapter.Adapter_main_kind_recyclerView;
import adapter.Adapter_main_viewPager_kind;
import bit.eagzzycsl.smartable2.R;
import entry.Entry;
import entry.EntryNote;
import view.EdgeDrawerLayout;

public class Fragment_main_smart_classify extends Fragment {
    private View myView;
    private TabLayout tabLayout_kind;
    private ViewPager viewPager_kind;
    private LinearLayout linearLayout_main;
    private EdgeDrawerLayout edgeDrawerLayout;
    private Adapter_main_viewPager_kind adapter_main_viewPager_kind;
    public Fragment_main_smart_classify() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_main_smart_classify, container, false);
        myFindView();
        myCreate();
        mySetView();
        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        edgeDrawerLayout.closeDrawer();
    }

    private void myFindView() {
        tabLayout_kind = (TabLayout) myView.findViewById(R.id.tabLayout_kind);
        viewPager_kind = (ViewPager) myView.findViewById(R.id.viewPager_kind);
        linearLayout_main=(LinearLayout)myView.findViewById(R.id.linearLayout_main);
        edgeDrawerLayout=(EdgeDrawerLayout)myView.findViewById(R.id.edgeDrawerLayout);
        linearLayout_main.setVisibility(View.INVISIBLE);

    }
    private void mySetView() {
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
    // TODO: Rename method, update argument and hook method into UI event

    public void closeDraw(){
        edgeDrawerLayout.toggleDrawer();
    }
}
