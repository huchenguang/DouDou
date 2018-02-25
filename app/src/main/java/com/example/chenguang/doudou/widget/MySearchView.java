package com.example.chenguang.doudou.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by chenguang on 2018/1/23.
 */

public class MySearchView extends SearchView {
    public MySearchView(Context context) {
        this(context, null);
    }

    public MySearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHintTextSize(int fontSize) {
        EditText textView = (EditText) this.findViewById(android.support.v7.appcompat.R.id
                .search_src_text);
        textView.setTextSize(fontSize);
    }
    public void setCursorIcon(@DrawableRes int drawable) {
        try {

            Class cls = Class.forName("android.support.v7.widget.SearchView");
            Field field = cls.getDeclaredField("mSearchSrcTextView");
            field.setAccessible(true);
            TextView tv = (TextView) field.get(this);


            Class[] clses = cls.getDeclaredClasses();
            for (Class cls_ : clses) {
                Log.e("TAG", cls_.toString());
                if (cls_.toString().endsWith("android.support.v7.widget" +
                        ".SearchView$SearchAutoComplete")) {
                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass()
                            .getSuperclass();
                    Field cursorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
                    cursorIconField.setAccessible(true);
                    cursorIconField.set(tv, drawable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "ERROR setCursorIcon = " + e.toString());
        }
    }
}
