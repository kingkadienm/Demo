package com.wangzs.tvdemo.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.leanback.widget.VerticalGridView;

import com.wangzs.tvdemo.R;


public class TabVerticalGridView extends VerticalGridView {

    private static final String TAG = "TabVerticalGridView";

    private View mTabView;
    private Group mGroup;
    private Animation mShakeY;
    private boolean isPressUp = false;
    private boolean isPressDown = false;

    public TabVerticalGridView(Context context) {
        this(context, null);
    }

    public TabVerticalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabVerticalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setTabView(View tabView) {
        this.mTabView = tabView;
    }

    public void setGroup(Group mGroup) {
        this.mGroup = mGroup;
    }

    public boolean isPressUp() {
        return isPressUp;
    }

    public boolean isPressDown() {
        return isPressDown;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            boolean hasViewFocus = false;
            View focusView = findFocus();
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                hasViewFocus = hasViewFocus(FOCUS_LEFT, focusView);
//                LogUtils.e("向左", getSelectedPosition(), hasViewFocus);
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                hasViewFocus = hasViewFocus(FOCUS_RIGHT, focusView);
//                LogUtils.e("向右", getSelectedPosition(), hasViewFocus);
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                hasViewFocus = hasViewFocus(FOCUS_UP, focusView);
//                LogUtils.e("向上", getSelectedPosition(), hasViewFocus);
                if (!hasViewFocus) {
                    if (mTabView != null) {
                        mTabView.requestFocus();
                        return true;
                    }
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                hasViewFocus = hasViewFocus(FOCUS_DOWN, focusView);
//                LogUtils.e("向下", getSelectedPosition(), hasViewFocus);
                if (!hasViewFocus) {
                    if (mTabView != null) {
                        mTabView.requestFocus();
                        return true;
                    }
                }
            }
//            if (!hasViewFocus) {
//                if ( focusView != null && getScrollState() == SCROLL_STATE_IDLE) {
//                    if (mShakeY == null) {
//                        mShakeY = AnimationUtils.loadAnimation(getContext(), R.anim.host_shake_y);
//                    }
//                    focusView.clearAnimation();
//                    focusView.startAnimation(mShakeY);
//                }
//                return false;
//            }
        }
        return super.dispatchKeyEvent(event);
    }


    private boolean hasViewFocus(int direction, View currentFocused) {
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
                currentFocused = null;
            }
        }

        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                direction);
        return nextFocused != null;
    }
    //    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            isPressDown = false;
//            isPressUp = false;
//            switch (event.getKeyCode()) {
//                case KeyEvent.KEYCODE_DPAD_RIGHT:
////                    if (getSelectedPosition() == 0) {
////                        if (mTabView != null) {
////                            mTabView.requestFocus();
////                            return true;
////                        }
////                    }
//                    LogUtils.e("向右");
//                    break;
//                case KeyEvent.KEYCODE_DPAD_LEFT:
//                case KeyEvent.KEYCODE_DPAD_DOWN:
//                    isPressDown = true;
//                    break;
//                case KeyEvent.KEYCODE_DPAD_UP:
//                    isPressUp = true;
////                    if (getSelectedPosition() == 0) {
////                        if (mTabView != null) {
////                            mTabView.requestFocus();
////                            return true;
////                        }
////                    }
//                    break;
//                case KeyEvent.KEYCODE_BACK:
//                    backToTop();
//                    return true;
//                default:
//                    break;
//            }
//        }
//        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
//    }

    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    handled = arrowScroll(FOCUS_RIGHT);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    handled = arrowScroll(FOCUS_LEFT);
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    handled = arrowScroll(FOCUS_UP);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    handled = arrowScroll(FOCUS_DOWN);
                    break;
            }
        }
        return handled;
    }

    public boolean arrowScroll(int direction) {
        Log.e(TAG, "arrowScroll direction: " + direction);

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
                currentFocused = null;
            }
        }

        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                direction);

        if (nextFocused == null || nextFocused == currentFocused) {
            if (direction == FOCUS_UP || direction == FOCUS_RIGHT) {
                if (currentFocused != null && getScrollState() == SCROLL_STATE_IDLE) {
                    if (mShakeY == null) {
                        mShakeY = AnimationUtils.loadAnimation(getContext(), R.anim.host_shake_y);
                    }
                    currentFocused.clearAnimation();
                    currentFocused.startAnimation(mShakeY);
                }
                handled = true;
            }
        }
        return handled;
    }

    public void backToTop() {
        if (mTabView != null) {
            if (mGroup != null && mGroup.getVisibility() != View.VISIBLE) {
                mGroup.setVisibility(View.VISIBLE);
            }
            mTabView.requestFocus();
        }
        scrollToPosition(0);
    }
}
