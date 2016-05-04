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

import adapter.Adapter_main_kind_recyclerView;
import adapter.Adapter_main_viewPager_kind;
import bit.eagzzycsl.smartable2.R;
import database.DatabaseManager;
import decorator.DividerItemDecoration;
import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntrySomeDay;
import entry.EntryTheseDays;
import layout.EdgeDrawerLayout;

public class Fragment_main_smart_classify extends Fragment {
    private View myView;
    private TabLayout tabLayout_kind;
    private ViewPager viewPager_kind;
    private LinearLayout linearLayout_main;
    private EdgeDrawerLayout edgeDrawerLayout;
    /*主界面部分*/
    private Adapter_main_viewPager_kind adapter_main_viewPager_kind;
    private Adapter_main_kind_recyclerView adapter_smart_serialize;
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
        tabLayout_kind.setTabsFromPagerAdapter(adapter_main_viewPager_kind);
//        yu-------
        smart_serilizeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smart_serilizeRecyclerView.setAdapter(adapter_smart_serialize);
        smart_serilizeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

    }

    private void myCreate() {
        int[] sort = new int[]{0, 1, 2, 3};
        final ArrayList<Entry> entry = new ArrayList<Entry>() {
            {
                this.add(new EntryTheseDays("预置-有朝一日1"));
                this.add(new EntryTheseDays("预置-有朝一日2"));
            }
        };
        adapter_main_viewPager_kind = new Adapter_main_viewPager_kind(
                new ArrayList<ArrayList<Entry>>() {
                    {

                        this.add(DatabaseManager.getInstance(getActivity()).getShortHand());
                        this.add(DatabaseManager.getInstance(getActivity()).getDDL());
                        this.add(DatabaseManager.getInstance(getActivity()).getTheseDays());
                        this.add(entry);
                    }
                },
                getActivity(),
                getActivity().getResources().getStringArray(R.array.activity_main_smart_pager_title),
                sort);
        //数据初始化
        mDatas_smart_serilize = new ArrayList<Entry>() {
            {
                this.add(new EntryShortHand("预置-速记1"));
                this.add(new EntryShortHand("预置-速记2"));
                this.add(new EntrySchedule("预置-日程1"));
                this.add(new EntrySchedule("预置-日程2"));
                this.add(new EntrySomeDay("预置-有朝一日1"));
                this.add(new EntrySomeDay("预置-有朝一日2"));
                this.add(new EntrySomeDay("预置-这两天1"));
                this.add(new EntrySomeDay("预置-这两天2"));
                this.add(new EntryDeadLine("预置-截止时间1"));
                this.add(new EntryDeadLine("预置-截止时间2"));
                this.add(new EntrySchedule("预置-日程3"));
                this.add(new EntrySchedule("预置-日程4"));
                this.add(new EntrySchedule("预置-日程5"));
                this.add(new EntrySchedule("预置-日程6"));
            }
        };

        adapter_smart_serialize = new Adapter_main_kind_recyclerView(mDatas_smart_serilize, getActivity());

    }

    public void toggleDrawer() {
        edgeDrawerLayout.toggleDrawer();
    }
}
