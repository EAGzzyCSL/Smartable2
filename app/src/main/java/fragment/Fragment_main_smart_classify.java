package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
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
    private Adapter_main_viewPager_kind adapter_main_viewPager_kind;

    //yu------
    private Adapter_main_kind_recyclerView adapter_main_kind_recyclerView;
    private RecyclerView smart_serilizeRecyclerView;
    private ArrayList<Entry> mDatas_smart_serilize;

    private Adapter_main_kind_recyclerView adapter_smart_serialize;
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
        try {
            myCreate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mySetView();
        return myView;
    }

    private void myFindView() {
        tabLayout_kind = (TabLayout) myView.findViewById(R.id.tabLayout_kind);
        viewPager_kind = (ViewPager) myView.findViewById(R.id.viewPager_kind);
        linearLayout_main = (LinearLayout) myView.findViewById(R.id.linearLayout_main);
        edgeDrawerLayout = (EdgeDrawerLayout) myView.findViewById(R.id.edgeDrawerLayout);

        //yu----
        smart_serilizeRecyclerView = (RecyclerView) myView.findViewById(R.id.id_smart_latest);

    }

    private void mySetView() {
        viewPager_kind.setAdapter(adapter_main_viewPager_kind);
        tabLayout_kind.setupWithViewPager(viewPager_kind);
        tabLayout_kind.setTabsFromPagerAdapter(adapter_main_viewPager_kind);
        //yu-------
        smart_serilizeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smart_serilizeRecyclerView.setAdapter(adapter_smart_serialize);
        smart_serilizeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

    }

    private void myCreate() throws ParseException {
        final RecyclerView[] pages = new RecyclerView[]{
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
                (RecyclerView) View.inflate(getActivity(), R.layout.adapter_main_smart_pager, null),
        };
        for (RecyclerView r : pages) {
            r.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            r.setItemAnimator(new DefaultItemAnimator());
            r.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        }
        View[] views = new View[pages.length];
        int[] sort = new int[]{3, 2, 0, 1};

        final Adapter_main_kind_recyclerView[] adapter_main_kind_recyclerView = new Adapter_main_kind_recyclerView[4];

//        for(Adapter_main_kind_recyclerView r : adapter_main_kind_recyclerView){
//            r.setOnItemClickListener(new Adapter_main_kind_recyclerView.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    Log.i("Tag", "click");
//                    Toast.makeText(getActivity(), position + ":" + "test", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

//        for (int i = 1; i < views.length; i++) {
//            pages[i].setAdapter(new Adapter_main_kind_recyclerView(new ArrayList<Entry>() {
//                {
//                    this.add(new EntryShortHand("hello"));
//                }
//            }, getActivity()));
//            views[sort[i]] = pages[i];
//            pages[i].setAdapter(new Adapter_main_kind_recyclerView(DatabaseManager.getInstance(getActivity()).getShortHand(), getActivity()));
//            views[sort[i]] = pages[i];
//            this.add(DatabaseManager.getInstance(getActivity()).getShortHand())
////
//            ArrayList<String> shortHand = new ArrayList<String>();
//            shortHand = DatabaseManager.getInstance(this).getShortHand();
////
////            ArrayAdapter<String> adapter = new ArrayAdapter<>(
////                    testActivity.this, android.R.layout.simple_list_item_1,
////                    shortHand);
////
////            listContent.setAdapter(adapter);
//        }

        //ddl
        adapter_main_kind_recyclerView[0] = new Adapter_main_kind_recyclerView(DatabaseManager.getInstance(getActivity()).getDDL(), getActivity());
        pages[0].setAdapter(adapter_main_kind_recyclerView[0]);
        views[sort[0]] = pages[0];


        //速记
        adapter_main_kind_recyclerView[1] = new Adapter_main_kind_recyclerView(new ArrayList<Entry>(){
            {
                ArrayList<Entry> entry = DatabaseManager.getInstance(getActivity()).getShortHand();
                for(int i = 0; i < entry.size(); i++){
                    EntryShortHand entryShortHand = (EntryShortHand)entry.get(i);
                    this.add(entryShortHand);
                }
                this.add(new EntryShortHand("预置-速记1"));
                this.add(new EntryShortHand("预置-速记2"));
            }
        }, getActivity());
        pages[1].setAdapter(adapter_main_kind_recyclerView[1]);
        views[sort[1]] = pages[1];

        //有朝一日
        adapter_main_kind_recyclerView[2] = new Adapter_main_kind_recyclerView(new ArrayList<Entry>(){
            {
                ArrayList<Entry> entry = DatabaseManager.getInstance(getActivity()).getSchedule();//todo 有朝一日 getSomeday
                for(int i = 0; i < entry.size(); i++){
                    EntrySchedule entrySchedule = (EntrySchedule) entry.get(i);
                    this.add((entrySchedule));
                }
                this.add(new EntrySomeDay("预置-有朝一日1"));
                this.add(new EntrySomeDay("预置-有朝一日2"));
            }
        }, getActivity());
        pages[2].setAdapter(adapter_main_kind_recyclerView[2]);//有朝一日
        views[sort[2]] = pages[2];

        //这两天
        adapter_main_kind_recyclerView[3] = new Adapter_main_kind_recyclerView(new ArrayList<Entry>(){
            {
                ArrayList<Entry> entry = DatabaseManager.getInstance(getActivity()).getTheseDays();//todo
                for(int i = 0; i < entry.size(); i++){
                    EntryTheseDays entryTheseDays = (EntryTheseDays)entry.get(i);
                    this.add(entryTheseDays);
                }
                this.add(new EntryTheseDays("预置-这两天1"));
                this.add(new EntryTheseDays("预置-这两天2"));
            }
        }, getActivity());
        pages[3].setAdapter(adapter_main_kind_recyclerView[3]);
        views[sort[3]] = pages[3];


        adapter_main_viewPager_kind = new Adapter_main_viewPager_kind(views,
                getActivity().getResources().getStringArray(R.array.activity_main_smart_pager_title), sort);


        smart_serilizeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smart_serilizeRecyclerView.setAdapter(adapter_smart_serialize);
        smart_serilizeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        for(Adapter_main_kind_recyclerView r : adapter_main_kind_recyclerView){
            r.setOnItemClickListener(new Adapter_main_kind_recyclerView.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.i("Tag", "click");
                    Toast.makeText(getActivity(), position + ":" + "test", Toast.LENGTH_SHORT).show();
                }
            });
        }
//yu-----
        //数据初始化
        mDatas_smart_serilize = new ArrayList<Entry>(){
            {
//                ArrayList<Entry> entry = DatabaseManager.getInstance(getActivity()).getShortHand();
//                for(int i = 0; i < entry.size(); i++){
//                    EntryShortHand entryShortHand = (EntryShortHand) entry.get(i);
//                    this.add(entryShortHand);
//                }

//                ArrayList<Entry> entry1 = DatabaseManager.getInstance(getActivity()).getSchedule();
//                for(int i = 0; i < entry1.size(); i++){
//                    this.add(entry1.get(i));
//                }
//
//                ArrayList<Entry> entry2 = DatabaseManager.getInstance(getActivity()).getTheseDays();
//                for(int i = 0; i < entry2.size(); i++){
//                    this.add(entry2.get(i));
//                }
//
//                ArrayList<Entry> entry3 = DatabaseManager.getInstance(getActivity()).getDDL();
//                for(int i = 0; i < entry3.size(); i++){
//                    this.add(entry3.get(i));
//                }

//                this.add(new EntryNote("笔记"));
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

        adapter_smart_serialize.setOnItemClickListener(new Adapter_main_kind_recyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("Tag", "click");
                Toast.makeText(getActivity(), position + ":" + "test", Toast.LENGTH_SHORT).show();
            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event

    public void toggleDrawer() {
        edgeDrawerLayout.toggleDrawer();
    }
}
