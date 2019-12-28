package com.anhtam.gate9.v2.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.anhtam.gate9.R;

public class HalfCircle extends View {
    private Paint paint = new Paint();
    private final RectF oval = new RectF();

    private int radius = 100;
    private int strokeWidth = 5;
    private int strokeColor = Color.GRAY;

    private Paint halfCirclePaint = new Paint();

    public HalfCircle(Context context) {
        super(context);
    }

    public HalfCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HalfCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HalfCircle);
        radius = ta.getDimensionPixelSize(R.styleable.HalfCircle_radius, 100);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.HalfCircle_strokeWidth, 5);
        strokeColor = ta.getColor(R.styleable.HalfCircle_strokeColor, Color.GRAY);
        ta.recycle();

        halfCirclePaint.setStyle(Paint.Style.FILL);
        halfCirclePaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(strokeColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        float width = (float) getWidth();
        float height = (float) getHeight();
        float center_x, center_y;

        center_x = width / 2;
        center_y = height;

        oval.set(center_x - radius - strokeWidth,
                center_y - radius + strokeWidth,
                center_x + radius + strokeWidth,
                center_y + radius - strokeWidth);
        canvas.drawArc(oval, 210, 120, false, halfCirclePaint);
        canvas.drawArc(oval, 210, 120, false, paint);
        int lineW = (int) ((width - radius * 2) / 2);
        canvas.drawLine(0, height * 0.5f , lineW + 2 * strokeWidth, height * 0.5f, paint);
        canvas.drawLine(lineW + 2 * radius - 2 * strokeWidth, height * 0.5f, width, height * 0.5f, paint);
    }
}