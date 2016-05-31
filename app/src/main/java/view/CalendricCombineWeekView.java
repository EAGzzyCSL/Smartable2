package view;

import android.content.Context;
import android.util.AttributeSet;


import bit.eagzzycsl.smartable2.R;


public class CalendricCombineWeekView extends CalendricPageView<CalendricSimpleWeekView> {
    public CalendricCombineWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public int getLayoutId() {
        return R.layout.view_calendric_week;
    }


}
