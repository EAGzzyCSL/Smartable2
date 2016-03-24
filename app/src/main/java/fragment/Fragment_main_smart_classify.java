package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.Adapter_main_kind_recyclerView;
import adapter.Adapter_main_viewPager_kind;
import bit.eagzzycsl.smartable2.R;
import entry.Entry;
import entry.EntryNote;
import layout.EdgeDrawerLayout;

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

    private void myFindView() {
        tabLayout_kind = (TabLayout) myView.findViewById(R.id.tabLayout_kind);
        viewPager_kind = (ViewPager) myView.findViewById(R.id.viewPager_kind);
        linearLayout_main = (LinearLayout) myView.findViewById(R.id.linearLayout_main);
        edgeDrawerLayout = (EdgeDrawerLayout) myView.findViewById(R.id.edgeDrawerLayout);
        Button button_test = (Button) myView.findViewById(R.id.button_test);
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "要是有时间的话，下一步这两件事是可以做的，郭宇可以给抽屉里面写个listview之类的，黄凌云可以改个佩斯换个图标什么，如果需要的话我接下来去看那个滑动删除", Toast.LENGTH_LONG).show();
            }
        });

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

    public void toggleDrawer() {
        edgeDrawerLayout.toggleDrawer();
    }
}
