package com.wangzs.tvdemo;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    public static final String TAG = "TabViewPager";
    private final Rect mTempRect = new Rect();

    /**
     * 垂直滑动的ViewPager 构造函数
     *
     * @param context 上下文
     */
    public MyViewPager(Context context) {
        this(context, null);
    }

    /**
     * 垂直滑动的ViewPager 构造函数
     *
     * @param context 上下文
     * @param attrs   自定义属性
     */
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置viewpage的切换动画,这里设置才能真正实现垂直滑动的viewpager
        setPageTransformer(true, new DefaultTransformer());
    }

    /**
     * 拦截touch事件
     *
     * @param ev 获取事件类型的封装类MotionEvent
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(swapEvent(ev));
        swapEvent(ev);
        return intercept;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    /**
     * 触摸点击触发该方法
     *
     * @param ev 获取事件类型的封装类MotionEvent
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapEvent(ev));
    }

    /**
     * 交换x轴和y轴的移动距离
     *
     * @param event 获取事件类型的封装类MotionEvent
     */
    private MotionEvent swapEvent(MotionEvent event) {
        //获取宽高
        float width = getWidth();
        float height = getHeight();
        //将Y轴的移动距离转变成X轴的移动距离
        float swappedX = (event.getY() / height) * width;
        //将X轴的移动距离转变成Y轴的移动距离
        float swappedY = (event.getX() / width) * height;
        //重设event的位置
        event.setLocation(swappedX, swappedY);
        return event;
    }

    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    handled = arrowScroll(FOCUS_LEFT);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    handled = arrowScroll(FOCUS_RIGHT);
                    break;
            }
        }
        return handled;
    }

    /**
     * 自定义 ViewPager 切换动画
     * 如果不设置切换动画，还会是水平方向的动画
     */
    public class DefaultTransformer implements PageTransformer {
        public static final String TAG = "simple";

        @Override
        public void transformPage(View view, float position) {
            float alpha = 0;
            if (0 <= position && position <= 1) {
                alpha = 1 - position;
            } else if (-1 < position && position < 0) {
                alpha = position + 1;
            }
            view.setAlpha(alpha);
            float transX = view.getWidth() * -position;
            view.setTranslationX(transX);
            float transY = position * view.getHeight();
            view.setTranslationY(transY);
        }
    }

    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup;
                 parent = parent.getParent()) {
                if (parent == this) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                // This would cause the focus search down below to fail in fun ways.
                final StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup;
                     parent = parent.getParent()) {
                    sb.append(" => ").append(parent.getClass().getSimpleName());
                }
                Log.e(TAG, "arrowScroll tried to find focus based on non-child "
                        + "current focused view " + sb.toString());
                currentFocused = null;
            }
        }

        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                direction);
        if (nextFocused != null && nextFocused != currentFocused) {
            if (direction == View.FOCUS_LEFT) {
                // If there is nothing to the left, or this is causing us to
                // jump to the right, then what we really want to do is page left.
                final int nextLeft = getChildRectInPagerCoordinates(mTempRect, nextFocused).left;
                final int currLeft = getChildRectInPagerCoordinates(mTempRect, currentFocused).left;
                if (currentFocused != null && nextLeft >= currLeft) {
                    handled = pageLeft();
                } else {
                    handled = nextFocused.requestFocus();
                }
            } else if (direction == View.FOCUS_RIGHT) {
                // If there is nothing to the right, or this is causing us to
                // jump to the left, then what we really want to do is page right.
                final int nextLeft = getChildRectInPagerCoordinates(mTempRect, nextFocused).left;
                final int currLeft = getChildRectInPagerCoordinates(mTempRect, currentFocused).left;
                if (currentFocused != null && nextLeft <= currLeft) {
                    handled = pageRight();
                } else {
                    handled = nextFocused.requestFocus();
                }
            }
        } else if (direction == FOCUS_LEFT || direction == FOCUS_BACKWARD) {
            // Trying to move left and nothing there; try to page.
            handled = pageLeft();
            if (goToNextPage != null) {
                goToNextPage.goToNetxPage();
            }
        } else if (direction == FOCUS_RIGHT || direction == FOCUS_FORWARD) {
            // Trying to move right and nothing there; try to page.
            handled = pageRight();
        } else if (direction == FOCUS_DOWN || direction == FOCUS_BACKWARD) {
            // Trying to move left and nothing there; try to page.
            handled = pageLeft();
            if (goToNextPage != null) {
                goToNextPage.goToNetxPage();
            }
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect getChildRectInPagerCoordinates(Rect outRect, View child) {
        if (outRect == null) {
            outRect = new Rect();
        }
        if (child == null) {
            outRect.set(0, 0, 0, 0);
            return outRect;
        }
        outRect.left = child.getLeft();
        outRect.right = child.getRight();
        outRect.top = child.getTop();
        outRect.bottom = child.getBottom();

        ViewParent parent = child.getParent();
        while (parent instanceof ViewGroup && parent != this) {
            final ViewGroup group = (ViewGroup) parent;
            outRect.left += group.getLeft();
            outRect.right += group.getRight();
            outRect.top += group.getTop();
            outRect.bottom += group.getBottom();

            parent = group.getParent();
        }
        return outRect;
    }

    boolean pageLeft() {
        return true;
    }

    boolean pageRight() {
        return true;
    }

    private GoToNextPage goToNextPage;

    public interface GoToNextPage {
        void goToNetxPage();
    }

    public void setGoToNextPage(GoToNextPage goToNextPage) {
        this.goToNextPage = goToNextPage;
    }
}