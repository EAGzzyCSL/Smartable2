package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;


import bit.eagzzycsl.smartable2.R;


public class CalendricCombineWeekView<CalendricSimpleWeekView> extends CalendricPagerView {
    public CalendricCombineWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    //传说中的钩子函数
    @Override
    public int getLayoutId() {
        return R.layout.view_calendric_week;
    }


}
