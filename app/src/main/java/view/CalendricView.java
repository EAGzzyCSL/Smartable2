package view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.LinkedList;

import adapter.Adapter_view_calendric;
import test.ScheduleCaseProvider;


public class CalendricView extends ViewPager {
    private Calendar viewCalendar;//不管按月看按日看都使用同一个calendar

    private Context context;
    private LinkedList<CalendricCombineDayView> pagers;
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private boolean haveScrolled = false;//是否已经滚动
        private int scrolledIndex;

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            /*同步滚动*/
            int scrollY = pagers.get(1).myGetScroll();
            pagers.get(2).mySetScroll(scrollY);
            pagers.get(0).mySetScroll(scrollY);
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
                viewCalendar.add(Calendar.DAY_OF_MONTH, -1);
                /*calendar还有一个roll方法也可以增加日期，但是roll的年月日不会同步，即31号加到1号时月不会加1*/
            }
            if (position == 2) {
                scrolledIndex = 2;
                haveScrolled = true;
                viewCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            /**
             * 1：Indicates that the pager is currently being dragged by the user.
             * 2：Indicates that the pager is in the process of settling to a final position.
             * 0：Indicates that the pager is in an idle, settled state. The current page is fully in view and no animation is in progress.
             */
            /**
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
            switch (scrolledIndex) {
                case 2:
                    pagers.get(0).setEntrySchedule(pagers.get(1).schedules);
                    pagers.get(1).setEntrySchedule(pagers.get(2).schedules);
                    pagers.get(2).setEntrySchedule(ScheduleCaseProvider.getSchedule(viewCalendar.get(Calendar.DAY_OF_MONTH) + 1));
                    break;
                case 0:
                    pagers.get(2).setEntrySchedule(pagers.get(1).schedules);
                    pagers.get(1).setEntrySchedule(pagers.get(0).schedules);
                    pagers.get(2).setEntrySchedule(ScheduleCaseProvider.getSchedule(viewCalendar.get(Calendar.DAY_OF_MONTH) - 1));
                    break;
            }
            pagers.get(0).setTextView_showDay(viewCalendar.get(Calendar.DAY_OF_MONTH) - 1 + "");
            pagers.get(1).setTextView_showDay(viewCalendar.get(Calendar.DAY_OF_MONTH) + "");
            pagers.get(2).setTextView_showDay(viewCalendar.get(Calendar.DAY_OF_MONTH) + 1 + "");
            getAdapter().notifyDataSetChanged();
            CalendricView.this.setCurrentItem(1, false);

        }
    };

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
        setCurrentItem(1, false);
        this.pagers = ((Adapter_view_calendric) adapter).getMySimpleByDayViews();
    }
}