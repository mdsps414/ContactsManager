package ru.mdsps.contacts.core.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Paint.FontMetrics;

import ru.mdsps.contacts.R;


public class CircleTextView extends View {

    private static final String TAG = CircleTextView.class.getSimpleName();

    private String mText;
    private int mBackgroundColorResource;
    private int mBorderColorResource;
    private float mBorderSizeResource;
    private int mTextColorResource;
    private float mTextSize;

    private Paint mBackgroundPainter;
    private Paint mTextPainter;
    private Paint mBorderPainter;


    public CircleTextView(Context context) {
        super(context);

        init();
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleTextView,
                0, 0);

        try {
            mBackgroundColorResource = a.getInt(R.styleable.CircleTextView_background_color, R.color.colorPrimary);
            mTextColorResource = a.getInt(R.styleable.CircleTextView_text_color, R.color.md_white_1000);
            mText = a.getString(R.styleable.CircleTextView_text);
            mTextSize = a.getDimension(R.styleable.CircleTextView_text_size, R.dimen.mds_font_size_large);
            mBorderColorResource = a.getInt(R.styleable.CircleTextView_border_color, R.color.md_grey_400);
            mBorderSizeResource = a.getDimension(R.styleable.CircleTextView_border_size, 1f);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init(){
        mBackgroundPainter = new Paint();

        mTextPainter = new Paint();
        mTextPainter.setTextAlign(Paint.Align.CENTER);
        mTextPainter.setColor(mTextColorResource);
        mTextPainter.setTextSize(mTextSize);
        mTextPainter.setAntiAlias(true);

        mBorderPainter = new Paint();
        mBorderPainter.setColor(mBorderColorResource);
        mBorderPainter.setStyle(Paint.Style.FILL_AND_STROKE);
        mBorderPainter.setAntiAlias(true);
    }

    // Set view text
    public void setText(String text){
        mText = text;
        invalidate();
        requestLayout();
    }

    public void setText(int textResource){
        mText = getContext().getResources().getString(textResource);
        invalidate();
        requestLayout();
    }

    // Set view text color
    public void setTextColor(int textColor){
        mTextColorResource = textColor;
        invalidate();
        requestLayout();
    }

    // Set view background color
    public void setBackgroundColor(int backgroundColor){
        mBackgroundColorResource = backgroundColor;
        invalidate();
        requestLayout();
    }

    public void setBackgroundResource(int backgroundResource){
        mBackgroundColorResource = ContextCompat.getColor(getContext(), backgroundResource);
        invalidate();
        requestLayout();
    }

    // Set view border color
    public void setViewBorderColor(int borderColor){
        mBorderColorResource = borderColor;
        invalidate();
        requestLayout();
    }

    // Set view border size
    public void setViewBorderSize(float borderSize){
        mBorderSizeResource = borderSize;
        invalidate();
        requestLayout();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        mBackgroundPainter.setColor(mBackgroundColorResource);
        mBackgroundPainter.setStyle(Paint.Style.FILL_AND_STROKE);
        mBackgroundPainter.setAntiAlias(true);

        int mRadius = Math.min(getHeight(), getWidth()) / 2;
        int viewWidthHalf = getWidth() / 2;
        int viewHeightHalf = getHeight() / 2;

        // Get the size of the text to draw, based on resource or string
        FontMetrics fontMetrics = mTextPainter.getFontMetrics();
        float textX = mRadius; // - (textWidth * 0.5f);
        float textY = mRadius - ((fontMetrics.ascent + fontMetrics.descent) * 0.5f);

        Rect textRect = new Rect();
        mTextPainter.getTextBounds(mText, 0, mText.length(), textRect);

        float widthPadding = getPaddingLeft() + getPaddingRight();

        Log.d(TAG, "padding width: " + widthPadding);

        /* Background Drawing - once we know the text size */
        canvas.drawCircle(viewWidthHalf, viewHeightHalf , mRadius, mBorderPainter);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf , mRadius - mBorderSizeResource, mBackgroundPainter);// X, y, radius, Paint
        canvas.drawText(mText, textX, textY, mTextPainter); // text, x , y, Paint

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int myHeight = hSpecSize;

        if(hSpecMode == MeasureSpec.EXACTLY){
            myHeight = hSpecSize;
        }else if(hSpecMode == MeasureSpec.AT_MOST){
            // wrap_content
            float heightPadding = getPaddingTop() + getPaddingBottom();
            myHeight = (int) mTextPainter.measureText(mText) + (int) heightPadding ;
        }

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int myWidth = wSpecSize;

        if(wSpecMode == MeasureSpec.EXACTLY){
            myWidth = wSpecSize;
        }else if(hSpecMode == MeasureSpec.AT_MOST){
            // wrap_content
            float widthPadding = getPaddingLeft() + getPaddingRight();
            myWidth = (int) mTextPainter.measureText(mText) + (int) widthPadding;
        }

        setMeasuredDimension(myWidth, myHeight);
    }



}
