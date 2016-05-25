package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.Adapter_recyclerView_entry;
import adapter.Adapter_main_viewPager_kind;
import algorithm.Recently;
import bit.eagzzycsl.smartable2.EnumEntry;
import bit.eagzzycsl.smartable2.EnumExtra;
import bit.eagzzycsl.smartable2.R;
import database.SQLMan;
import decorator.DividerItemDecoration;
import entry.Entry;
import layout.EdgeDrawerLayout;
import layout.OnDrawerStateChangeListener;

public class Fragment_main_smart_classify extends Fragment {
    private View myView;
    private TabLayout tabLayout_kind;
    private ViewPager viewPager_kind;
    private LinearLayout linearLayout_main;
    private EdgeDrawerLayout edgeDrawerLayout;
    /*主界面部分*/
    private Adapter_main_viewPager_kind adapter_main_viewPager_kind;
    private Adapter_recyclerView_entry adapter_smart_serialize;
    /*抽屉部分*/
    private RecyclerView smart_serilizeRecyclerView;
    private TextView textView_recent;

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
        myView = inflater.inflate(R.layout.fragment_main_smart_classify, container, false);
        myFindView();
        myCreate();
        mySetView();
        return myView;
    }

    private void myFindView() {
        /*整个露边边的*/
        edgeDrawerLayout = (EdgeDrawerLayout) myView.findViewById(R.id.edgeDrawerLayout);
        /*左侧分类显示部分容器，main*/
        linearLayout_main = (LinearLayout) myView.findViewById(R.id.linearLayout_main);
        /*tab标题*/
        tabLayout_kind = (TabLayout) myView.findViewById(R.id.tabLayout_kind);
        /*tab下面pager*/
        viewPager_kind = (ViewPager) myView.findViewById(R.id.viewPager_kind);
        /*右侧侧滑出来的*/
        smart_serilizeRecyclerView = (RecyclerView) myView.findViewById(R.id.id_smart_latest);
        textView_recent = (TextView) myView.findViewById(R.id.textView_recent);

    }

    private void mySetView() {
        viewPager_kind.setAdapter(adapter_main_viewPager_kind);
        tabLayout_kind.setupWithViewPager(viewPager_kind);
//        yu-------
        smart_serilizeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smart_serilizeRecyclerView.setAdapter(adapter_smart_serialize);
        smart_serilizeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        edgeDrawerLayout.setOnDrawerStateChangeListener(new OnDrawerStateChangeListener() {
            @Override
            public void onDrawerOpen() {
                textView_recent.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClose() {
                textView_recent.setVisibility(View.VISIBLE);
            }
        });
    }

    //使用sort的方法是为四种事情默认预置了一个顺序0123,如果想修改他们的顺序，修改sort即可。

    int[] sort = new int[]{0, 1, 2, 3};
        EnumEntry.shortHand,
        EnumEntry.schedule,
        EnumEntry.theseDays,
        EnumEntry.deadLine,
    };
    private void myCreate() {
        adapter_main_viewPager_kind = new Adapter_main_viewPager_kind(
                new ArrayList<ArrayList<? extends Entry>>() {
                    {
                    }
                },
                getActivity(),
                getActivity().getResources().getStringArray(R.array.activity_main_smart_pager_title),
                sort);
        adapter_smart_serialize = new Adapter_recyclerView_entry(
                Recently.sort(SQLMan.getInstance(getActivity()).readAll())
                , getActivity()
        );

    }

    public void updateEntryListInView(EnumExtra enumExtra, Entry entry) {
        switch (enumExtra) {
            case entryAdded: {
                adapter_smart_serialize.insertEntry(entry);
                adapter_main_viewPager_kind.insertEntry(entry);
                break;
            }
            case entryModified: {
                adapter_smart_serialize.updateEntry(entry);
                adapter_main_viewPager_kind.updateEntry(entry);
                break;
            }
            case entryRemoved: {
                adapter_smart_serialize.deleteEntry(entry);
                adapter_main_viewPager_kind.deleteEntry(entry);
                break;
            }
        }
    }


    public void toggleDrawer() {
        edgeDrawerLayout.toggleDrawer();
    }

    public EnumEntry getCurrentPageEntry() {

        return EnumEntry.fourEnumEntries[sort[tabLayout_kind.getSelectedTabPosition()]];
    }
}
