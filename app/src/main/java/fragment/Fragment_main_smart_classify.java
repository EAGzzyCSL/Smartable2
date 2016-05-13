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

import java.util.ArrayList;

import adapter.Adapter_recyclerView_entry;
import adapter.Adapter_main_viewPager_kind;
import bit.eagzzycsl.smartable2.EnumEntry;
import bit.eagzzycsl.smartable2.R;
import database.SQLMan;
import decorator.DividerItemDecoration;
import entry.Entry;
import layout.EdgeDrawerLayout;
import my.MyLog;

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
    /*预置数据*/
    private ArrayList<Entry> mDatas_smart_serilize;

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

    }

    private void mySetView() {
        viewPager_kind.setAdapter(adapter_main_viewPager_kind);
        tabLayout_kind.setupWithViewPager(viewPager_kind);
//        yu-------
        smart_serilizeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smart_serilizeRecyclerView.setAdapter(adapter_smart_serialize);
        smart_serilizeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

    }

    //使用sort的方法是为四种事情默认预置了一个顺序0123,如果想修改他们的顺序，修改sort即可。

    EnumEntry[] enumEntries = new EnumEntry[]{
            EnumEntry.shortHand,
            EnumEntry.deadLine,
            EnumEntry.theseDays,
            EnumEntry.someDay
    };
    int[] sort = new int[]{0, 1, 2, 3};

    private void myCreate() {
        adapter_main_viewPager_kind = new Adapter_main_viewPager_kind(
                new ArrayList<ArrayList<? extends Entry>>() {
                    {

                        this.add((SQLMan.getInstance(getActivity()).read(EnumEntry.shortHand)));
                        this.add(SQLMan.getInstance(getActivity()).read(EnumEntry.deadLine));
                        this.add(SQLMan.getInstance(getActivity()).read(EnumEntry.theseDays));
                        this.add(SQLMan.getInstance(getActivity()).read(EnumEntry.someDay));
                    }
                },
                getActivity(),
                getActivity().getResources().getStringArray(R.array.activity_main_smart_pager_title),
                sort);
        adapter_smart_serialize = new Adapter_recyclerView_entry(SQLMan.getInstance(getActivity()).readAll(), getActivity());

    }

    public void toggleDrawer() {
        edgeDrawerLayout.toggleDrawer();
    }

    public EnumEntry getCurrentPageEntry() {
        MyLog.i("选中下标：", tabLayout_kind.getSelectedTabPosition() + "#");

        return enumEntries[sort[tabLayout_kind.getSelectedTabPosition()]];
    }
}
