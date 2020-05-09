package com.wuc.location.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.wuc.location.R;


/**
 * @author wuchao
 * @date 2020/4/12 11:05
 * @desciption Header Bar封装
 */
public class HeaderBar extends FrameLayout {
    private AppCompatImageView mLeftIv;
    private AppCompatTextView mTitleTv;
    private AppCompatTextView mRightTv;
    /**
     * 是否显示"返回"图标
     */
    private boolean isShowBack = true;
    /**
     * title文字
     */
    private String mTitleText;
    /**
     * 右侧文字
     */
    private String mRightText;

    public HeaderBar(@NonNull Context context) {
        this(context, null);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true);
        mTitleText = typedArray.getString(R.styleable.HeaderBar_titleText);
        mRightText = typedArray.getString(R.styleable.HeaderBar_rightText);
        initView(context);
        typedArray.recycle();

    }

    private void initView(final Context context) {
        View.inflate(context, R.layout.layout_header_bar, this);
        mLeftIv = findViewById(R.id.iv_left);
        mTitleTv = findViewById(R.id.tv_title);
        mRightTv = findViewById(R.id.tv_right);

        mLeftIv.setVisibility(isShowBack ? VISIBLE : GONE);
        //标题不为空设置值
        if (!TextUtils.isEmpty(mTitleText)) {
            mTitleTv.setText(mTitleText);
        }
        //右侧文字不为空，设置值
        if (!TextUtils.isEmpty(mRightText)) {
            mRightTv.setText(mRightText);
        }
        //返回图标默认实现（关闭Activity）
        mLeftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    /**
     * 获取左侧视图
     */
    public AppCompatImageView getLeftView() {
        return mLeftIv;
    }

    /**
     * 获取右侧视图
     */
    public AppCompatTextView getRightView() {
        return mRightTv;
    }

    /**
     * 获取右侧文字
     */

    public String getRightText() {
        return mRightTv.getText().toString();
    }

    /**
     * 设置标题
     *
     * @param title title
     */
    public void setTitleText(String title) {
        mTitleTv.setText(title);
    }
}
