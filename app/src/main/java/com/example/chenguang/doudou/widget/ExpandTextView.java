package com.example.chenguang.doudou.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

/**
 * Created by chenguang on 2018/2/23.
 */

public class ExpandTextView extends AppCompatTextView {
    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int lineCount = getLineCount();
        int showLineNum = 4;
        String mText = getText().toString();
        String mOldText = mText;
        String lastText = "展开";

        if (lineCount > showLineNum && !isSpan) {
            //大于4行就省略，并显示 展开
            int lineStart = getLayout().getLineStart(showLineNum - 1);
            int lineEnd = getLayout().getLineEnd(showLineNum - 1);
            //获取第4行的文本
            String endText = mText.substring(lineStart, lineEnd);
            if (endText.length() - lastText.length() > 0) {
                mText = mText.substring(0, lineStart) + endText.substring(0, endText.length() -
                        lastText.length()) + lastText;
            }
            SpannableString spanText = new SpannableString(mText);
            spanText.setSpan(new ForegroundColorSpan(Color.GREEN), mText.length() - lastText
                            .length()
                    , mText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(spanText);
        }

        super.onDraw(canvas);
    }

    boolean isSpan;//是否展开

    public void setText(String text, boolean isSpan) {
        this.isSpan = isSpan;
        this.setText(text);
    }
}
