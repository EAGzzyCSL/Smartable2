package view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.Calendar;

import adapter.Adapter_view_calendric;
import my.MyDate;


public class CalendricView extends ViewPager {
    private Calendar viewCalendar;//一个日历，用来表计三张视图中第一张的起始日期
    /*adapter负责提供数据，日供日历的加减，adapter的数据从item提供器获取，pager提供根据指定日期更新内容的方法*/
    private Adapter_view_calendric myAdapter;
    private Context context;

    public void setFirstDay(MyDate date) {
        viewCalendar.set(Calendar.YEAR, date.getYear());
        viewCalendar.set(Calendar.MONTH, date.getMonth());
        viewCalendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        pagerUpdate();
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private boolean haveScrolled = false;//是否已经滚动
        private int scrolledIndex;

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            /*同步滚动*/
            int scrollY = myAdapter.getPage_b().myGetScroll();
            myAdapter.getPage_a().mySetScroll(scrollY);
            myAdapter.getPage_c().mySetScroll(scrollY);

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
                viewCalendar.add(Calendar.DAY_OF_MONTH, -1 * myAdapter.getEnumViewType().getDiv());
                /*calendar还有一个roll方法也可以增加日期，但是roll的年月日不会同步，即31号加到1号时月不会加1*/
            }
            if (position == 2) {
                scrolledIndex = 2;
                haveScrolled = true;
                viewCalendar.add(Calendar.DAY_OF_MONTH, myAdapter.getEnumViewType().getDiv());
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

    private void pagerUpdate() {
        //日历的加减早在pager滚动的时候就完成了，这里只需要负责日历增加产生新的时间传一下再把日历reset了就好

        /*page_a*/
        myAdapter.getPage_a().setEntrySchedule(MyDate.createFromCalendar(viewCalendar),
                myAdapter.getScheduleFromItemProvider());
        viewCalendar.add(Calendar.DAY_OF_MONTH, myAdapter.getEnumViewType().getDiv());
        /*page_b*/
        myAdapter.getPage_b().setEntrySchedule(MyDate.createFromCalendar(
                viewCalendar
        ), myAdapter.getScheduleFromItemProvider());
        viewCalendar.add(Calendar.DAY_OF_MONTH, myAdapter.getEnumViewType().getDiv());
        /*page_c*/
        myAdapter.getPage_c().setEntrySchedule(MyDate.createFromCalendar(
                viewCalendar
        ), myAdapter.getScheduleFromItemProvider());
        /*reset calendar and notify data change*/
        viewCalendar.add(Calendar.DAY_OF_MONTH, -2 * myAdapter.getEnumViewType().getDiv());
        myAdapter.notifyDataSetChanged();
    }

    public CalendricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        viewCalendar = Calendar.getInstance();
        viewCalendar.set(Calendar.DAY_OF_MONTH, 15);//15是我找了个数来测试
        this.setOffscreenPageLimit(3);//设置viewpager的限制为3，有助于优化滑动
        /*为viewpager增加滑动监听，为了在pager滑动的时候更换内容*/
        this.addOnPageChangeListener(onPageChangeListener);

    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        this.myAdapter = (Adapter_view_calendric) adapter;
        setCurrentItem(1, false);
    }
}