package view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


import adapter.Adapter_view_calendric;
import my.MyDate;
import my.MyMoment;

/*继承自viewPager以实现日历的左右无限滚动*/
public class CalendricView extends ViewPager {
    private MyMoment myMoment = new MyMoment();//用来表计三张视图中第一张的起始日期
    /*adapter负责提供数据，日供日历的加减，adapter的数据从item提供器获取，pager提供根据指定日期更新内容的方法*/
    private Adapter_view_calendric myAdapter;
    private Context context;
    /*创建一个自定义的pageChangeListener，以实现无限滚动*/
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private boolean haveScrolled = false;//是否已经滚动
        private int scrolledIndex;

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            /*同步滚动*/
            if (myAdapter != null && myAdapter.getPage_b() != null) {
                int scrollY = myAdapter.getPage_b().myGetScroll();
                myAdapter.getPage_a().mySetScroll(scrollY);
                myAdapter.getPage_c().mySetScroll(scrollY);
            }
        }

        @Override
        public void onPageSelected(int position) {
            /*
            0为最左边的，1为中间的，2为右边的
            当选中最左边的或者最右边的时候就haveScrolled=true，同时日历进行加一天和减一天的操作
             */
            if (position == 0) {
                haveScrolled = true;
                scrolledIndex = 0;
                myMoment.dayAdd(-1 * myAdapter.getEnumViewType().getDiv());
                /*calendar还有一个roll方法也可以增加日期，但是roll的年月日不会同步，即31号加到1号时月不会加1*/
            }
            if (position == 2) {
                scrolledIndex = 2;
                haveScrolled = true;
                myMoment.dayAdd(myAdapter.getEnumViewType().getDiv());
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            /**
             * 1：Indicates that the pager is currently being dragged by the user.
             * 2：Indicates that the pager is in the process of settling to a final position.
             * 0：Indicates that the pager is in an idle, settled state. The current page is fully in view and no animation is in progress.
             *
             * （翻译自我，仅供参考）
             * 页面在滑动的时候正状态的变化的值（i）依次为：1，2，0
             * 1表示正在滑动，2表示将要选定一个位置，应该是发生在当滑动页面使下一个view足够的部分进入屏幕时pager就会把这个页面设为将要进入的页面
             * 0表示停下来闲置的状态，发生在新的页面滑动停下来之后，发生在onPagerSelected之后
             * */
            if (i == 0 && haveScrolled) {
                /*
                如果状态为0了且确实有滑动（因为在该页面轻微滑动一下会有10的状态转换，没有2，即没有选定新的页面）
                此时即是切换到了最左边或者最右边的页面，开始通过伪造的方法来实现无限滚动
                 */
                updatePage();
                haveScrolled = false;
            }

        }

        private void updatePage() {
            /*原来采用重新加载view的做法，现在采用将view中的内容替换的方法*/
            if (scrolledIndex == 2 || scrolledIndex == 0) {
                pagerUpdate();
            }
            CalendricView.this.setCurrentItem(1, false);

        }
    };

    /*在构造方法中设置一个Listener*/
    public CalendricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        /*为viewpager增加滑动监听，为了在pager滑动的时候更换内容*/
        this.addOnPageChangeListener(onPageChangeListener);


    }

    /*设置首日，不管对于月日周，凭借首日都可以知道日历视图需要滚动到那个时期，因此该函数用于设定日历视图跳转到某个时候*/
    public void setFirstDay(MyDate date) {
        myMoment.setDate(date.getYear(), date.getMonth(), date.getDay());
        myAdapter.getPage_b().mySetScroll(300);
        pagerUpdate();
    }


    /*更新page中的内容*/
    private void pagerUpdate() {
        //日历的加减早在pager滚动的时候就完成了，这里只需要负责日历增加产生新的时间传一下再把日历reset了就好

        /*page_a*/
        MyMoment moment_a = myMoment.newSameMoment();
        myAdapter.getPage_a().transData(moment_a,
                myAdapter.getScheduleFromItemProvider(moment_a.getDate()),
                myAdapter.getCalendricViewItemClick());
        myMoment.dayAdd(myAdapter.getEnumViewType().getDiv());
        /*page_b*/
        MyMoment moment_b = myMoment.newSameMoment();
        myAdapter.getPage_b().transData(moment_b,
                myAdapter.getScheduleFromItemProvider(moment_b.getDate()),
                myAdapter.getCalendricViewItemClick());
        myMoment.dayAdd(myAdapter.getEnumViewType().getDiv());

        /*page_c*/
        MyMoment moment_c = myMoment.newSameMoment();
        myAdapter.getPage_c().transData(moment_c,
                myAdapter.getScheduleFromItemProvider(moment_c.getDate()),
                myAdapter.getCalendricViewItemClick()
        );
        /*reset calendar and notify data change*/
        myMoment.dayAdd(-2 * myAdapter.getEnumViewType().getDiv());
        myAdapter.notifyDataSetChanged();
    }


    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        this.myAdapter = (Adapter_view_calendric) adapter;
        setCurrentItem(1, false);
    }

    public void updateItem() {
        pagerUpdate();
    }

    public void scrollToCurrentTime() {
        myAdapter.getPage_b().scrollToCurrentTime(myMoment.getTime());
    }
}