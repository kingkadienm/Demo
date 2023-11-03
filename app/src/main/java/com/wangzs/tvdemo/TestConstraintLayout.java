package com.wangzs.tvdemo;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TestConstraintLayout extends ConstraintLayout {
    public TestConstraintLayout(@NonNull Context context) {
        this(context,null);
    }

    public TestConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }
}
