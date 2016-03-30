package layout;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.ViewCompat;
import android.widget.FrameLayout;


public class EdgeDrawerLayout extends FrameLayout {
    /*继承自FrameLayout或者LinearLayout均可*/
    private boolean drawerOpen = false;
    private View drawerView;
    private View mainView;
    private ViewDragHelper myViewDragHelper;

    public EdgeDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        myViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child == drawerView) {
                    /*当抽屉准备被拉开的时候让它下面的内容变成VISIBLE*/
                    if (mainView.getVisibility() == View.INVISIBLE) {
                        mainView.setVisibility(View.VISIBLE);
                    }
                    return true;
                } else {
                    return false;
                }
            }

            /*left and top，可以用来限制边界*/
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return Math.min(Math.max(0, left), mainView.getRight());
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return 0;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == drawerView) {
                    /*当释放view时根据拖动距离判断该开还是该关*/
                    /*还有拖拽时的速度
                    * 左拉大于一半(left<width/2)或者速度较大(x>5)，开，否则关
                    * 右拉大于一半(left>=width/2)或者速度较大(x<-5)，关，否则开
                    * 速度没有经过计算，随便取的值
                    * */
                    if (xvel > 0) {
                        /*right*/
                        if (releasedChild.getLeft() * 2 > EdgeDrawerLayout.this.getWidth() || xvel > 100) {

                            closeDrawer();
                        } else {
                            openDrawer();
                        }
                    } else {
                        /*left*/
                        if (releasedChild.getLeft() * 2 <= EdgeDrawerLayout.this.getWidth() || xvel < -100) {
                            openDrawer();
                        } else {
                            closeDrawer();
                        }
                    }


                }
            }


            /*目前似乎可有可无*/
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            /*当位置改变时因着抽屉是开还是关来决定要不要显示mainView*/
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == drawerView) {
                    if (left == 0) {
                        if (dx < 0) {
                            mainView.setVisibility(INVISIBLE);
                        }
                    }
                    if (left == mainView.getRight()) {
                        mainView.setVisibility(VISIBLE);
                    }
                }

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*自行覆写的measure方法，觉得直接用super方法也行但是实践后不行*/
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        mainView = getChildAt(0);
        drawerView = getChildAt(1);
        MarginLayoutParams main_lp = (MarginLayoutParams) mainView.getLayoutParams();
        mainView.measure(
                getChildMeasureSpec(
                        widthMeasureSpec,
                        main_lp.leftMargin + main_lp.rightMargin,
                        main_lp.width
                ), getChildMeasureSpec(
                        heightMeasureSpec,
                        main_lp.topMargin + main_lp.bottomMargin,
                        main_lp.height
                )
        );
        MarginLayoutParams drawer_lp = (MarginLayoutParams) drawerView.getLayoutParams();
        drawerView.measure(getChildMeasureSpec(
                        widthMeasureSpec,
                        drawer_lp.leftMargin + drawer_lp.rightMargin,
                        widthSize
                ), getChildMeasureSpec(
                        heightMeasureSpec,
                        drawer_lp.topMargin + drawer_lp.bottomMargin,
                        heightSize
                )
        );

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams main_lp = (MarginLayoutParams) mainView.getLayoutParams();
        mainView.layout(main_lp.leftMargin,
                main_lp.topMargin,
                main_lp.leftMargin + mainView.getMeasuredWidth(),
                main_lp.topMargin + mainView.getMeasuredHeight()
        );
        /*fragment切换时导致的主view不可见的暂时解决方案*/
        if (mainView.getVisibility() == View.INVISIBLE) {
            mainView.setVisibility(View.VISIBLE);
        }
        MarginLayoutParams drawer_lp = (MarginLayoutParams) drawerView.getLayoutParams();
        /*layout的时候把drawerView lay到对应位置*/
        drawerView.layout(
                mainView.getMeasuredWidth(),
                drawer_lp.topMargin,
                mainView.getMeasuredWidth() + this.getMeasuredWidth(),
                drawer_lp.topMargin + drawerView.getMeasuredHeight()
        );

    }

    @Override
    public void computeScroll() {
        if (myViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(EdgeDrawerLayout.this);
            invalidate();
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return myViewDragHelper.shouldInterceptTouchEvent(event);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        myViewDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }


    public void openDrawer() {

        myViewDragHelper.smoothSlideViewTo(drawerView, 0, drawerView.getTop());
        drawerOpen = true;
        invalidate();
    }

    public void closeDrawer() {
        mainView.setVisibility(View.VISIBLE);
        myViewDragHelper.smoothSlideViewTo(drawerView, mainView.getRight(), drawerView.getTop());
        drawerOpen = false;
        invalidate();
    }

    public boolean isDrawerOpen() {

        return this.drawerOpen;
    }

    public boolean toggleDrawer() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            openDrawer();
        }
        return isDrawerOpen();
    }
}
/**
 * measure和layout还需要搞清楚
 * dragHelper一团糟糕
 * fragment切换带来的问题
 * 添加自定义属性以避免在mainView中用marginRight进行调整
 * 左边的drawerLayout导致的问题依然存在，成因大概可以猜测，但目前无法修复
 * 还是必须要设置被覆盖的主view未invisible才能避免它捕捉事件
 * 在4.4下居然会崩溃。。。
 */