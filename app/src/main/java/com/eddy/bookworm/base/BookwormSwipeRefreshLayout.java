package com.eddy.bookworm.base;

import android.content.Context;
import android.util.AttributeSet;

import com.eddy.bookworm.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BookwormSwipeRefreshLayout extends SwipeRefreshLayout {
    public BookwormSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public BookwormSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.colorAccent);
    }
}
