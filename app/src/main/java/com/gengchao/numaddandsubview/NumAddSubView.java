package com.gengchao.numaddandsubview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ocean on 2016/9/26.
 */

public class NumAddSubView extends LinearLayout implements View.OnClickListener {

    private LayoutInflater mInflate;
    private Button btAdd;
    private Button btSub;
    private TextView mNumTv;

    private int mValue;
    private int minValue;
    private int maxValue;

    private OnButtonClickListener mListener;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_bt) {
            numAdd();
            if (mListener != null) {
                mListener.onButtonAddClick(v, mValue);
            }
        } else if (v.getId() == R.id.sub_bt) {
            numSub();
            if (mListener != null) {
                mListener.onButtonSubClick(v, mValue);
            }
        }
    }

    private void numAdd() {
        if (mValue < maxValue) {
            mValue++;
        }
        mNumTv.setText(mValue + "");
    }

    private void numSub() {
        if (mValue > minValue) {
            mValue--;
        }
        mNumTv.setText(mValue + "");
    }


    public interface OnButtonClickListener {
        void onButtonAddClick(View view, int value);

        void onButtonSubClick(View view, int value);
    }

    //提供set方法
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.mListener = onButtonClickListener;
    }

    public int getmValue() {
        String value = mNumTv.getText().toString().trim();
        if (value != null && !"".equals(value)) {
            this.mValue = Integer.parseInt(value);
        }
        return mValue;
    }

    public void setmValue(int mValue) {
        mNumTv.setText(mValue + "");
        this.mValue = mValue;
    }


    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public NumAddSubView(Context context) {
        this(context, null);
    }

    public NumAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflate = LayoutInflater.from(context);
        initView();
        if (attrs != null) {
            TintTypedArray tta = TintTypedArray.obtainStyledAttributes
                    (context, attrs, R.styleable.NumAddSubView, defStyleAttr, 0);
            int value=tta.getInt(R.styleable.NumAddSubView_value,0);
            setmValue(value);
            int minValue=tta.getInt(R.styleable.NumAddSubView_minValue,0);
            setMinValue(minValue);
            int maxValue=tta.getInt(R.styleable.NumAddSubView_maxValue,0);
            setMaxValue(maxValue);

            Drawable addBtDrawable=tta.getDrawable(R.styleable.NumAddSubView_addBtBg);
            setAddBtBackGround(addBtDrawable);

            Drawable subBtDrawable=tta.getDrawable(R.styleable.NumAddSubView_subBtBg);
            setSubBtBackGround(subBtDrawable);

            int numDrawable=tta.getResourceId(R.styleable.NumAddSubView_numTvBg,android.R.color.transparent);
            setNumTvBackGround(numDrawable);
            //自定义资源用完之后必须回收
            tta.recycle();
        }
    }

    private void setAddBtBackGround(Drawable drawable){
        this.btAdd.setBackground(drawable);
    }

    private void setSubBtBackGround(Drawable drawable){
        this.btSub.setBackground(drawable);
    }

    private void setNumTvBackGround(int drawable){
        this.mNumTv.setBackgroundResource(drawable);
    }


    private void initView() {
        View view = mInflate.inflate(R.layout.weight_num_add_sub, this, true);
        btAdd = (Button) view.findViewById(R.id.add_bt);
        btSub = (Button) view.findViewById(R.id.sub_bt);
        mNumTv = (TextView) view.findViewById(R.id.num_tv);
        btAdd.setOnClickListener(this);
        btSub.setOnClickListener(this);
    }
}
