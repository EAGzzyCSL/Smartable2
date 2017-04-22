package layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import bit.eagzzycsl.smartable2.R;


public class EdgeDrawerLayout extends ViewGroup {
    private boolean drawerOpen = false;
    private View drawerView;
    private View mainView;
    private ViewDragHelper myViewDragHelper;
    private int edgeSize = 20;//所露边边的默认值
    private OnDrawerStateChangeListener onDrawerStateChangeListener;

    public EdgeDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.edge);
            edgeSize = ta.getDimensionPixelSize(R.styleable.edge_edgeSize, edgeSize);
            ta.recycle();
        }
        // 灵敏度
        myViewDragHelper = ViewDragHelper.create(this, 4.0f, new ViewDragHelper.Callback() {
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

            /*边界检测，提高灵敏度，不知会不会有用*/
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                myViewDragHelper.captureChildView(drawerView, pointerId);
            }

            /*left and top，可以用来限制边界*/
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return Math.min(Math.max(0, left), mainView.getRight() - edgeSize);
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


            /*还是不知道该怎么设置，不过可以凑活用*/
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight();
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
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return myViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        myViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*自行覆写的measure方法，子measure那块还是没弄明白*/
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        mainView = getChildAt(0);
        drawerView = getChildAt(1);

        LayoutParams parent_lp = this.getLayoutParams();
        mainView.measure(
                getChildMeasureSpec(
                        widthMeasureSpec,
                        0,
                        parent_lp.width
                ), getChildMeasureSpec(
                        heightMeasureSpec,
                        0,
                        parent_lp.height
                )
        );
        drawerView.measure(getChildMeasureSpec(
                widthMeasureSpec,
                0,
                parent_lp.width
                ), getChildMeasureSpec(
                heightMeasureSpec,
                0,
                parent_lp.width
                )
        );

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mainView = getChildAt(0);
        drawerView = getChildAt(1);
        mainView.layout(0,
                0,
                this.getMeasuredWidth(),
                this.getMeasuredHeight()
        );
        /*fragment切换时导致的主view不可见的暂时解决方案*/
        if (mainView.getVisibility() == View.INVISIBLE) {
            mainView.setVisibility(View.VISIBLE);
        }
        /*layout的时候把drawerView lay到对应位置*/
        drawerView.layout(
                this.getMeasuredWidth() - edgeSize,
                0,
                this.getMeasuredWidth() * 2 - edgeSize,
                this.getMeasuredHeight()
        );


    }

    public void openDrawer() {

        myViewDragHelper.smoothSlideViewTo(drawerView, 0, drawerView.getTop());
        drawerOpen = true;
        invalidate();
        if (onDrawerStateChangeListener != null) {
            onDrawerStateChangeListener.onDrawerOpen();
        }
    }

    public void closeDrawer() {
        mainView.setVisibility(View.VISIBLE);
        myViewDragHelper.smoothSlideViewTo(drawerView, this.getWidth() - edgeSize, drawerView.getTop());
        drawerOpen = false;
        invalidate();
        if (onDrawerStateChangeListener != null) {
            onDrawerStateChangeListener.onDrawerClose();
        }
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

    @Override
    public void computeScroll() {
        if (myViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(EdgeDrawerLayout.this);
            invalidate();
        }
    }

    public void setOnDrawerStateChangeListener(OnDrawerStateChangeListener onDrawerStateChangeListener) {
        this.onDrawerStateChangeListener = onDrawerStateChangeListener;
    }
}
/**
 * measure和layouts稍微清楚了一些
 * dragHelper也没有那么糟糕了
 * fragment切换带来的问题
 * 已经有自定义属性edgeSize
 * 左边的drawerLayout导致的问题依然存在，成因大概可以猜测，但目前无法修复
 * 还是必须要设置被覆盖的主view未invisible才能避免它捕捉事件
 * 在4.4下还是会崩溃。。。
 * 和右侧drawer的内部控件捕捉事件的冲突解决
 */