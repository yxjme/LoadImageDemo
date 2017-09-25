package com.deringmobile.jbh.loadimagedemo.weights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zbsdata on 2017/9/4.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private static final int dividerHeight=5;
    private Paint paint;
    public MyItemDecoration(){
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(dividerHeight);
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=dividerHeight;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount=parent.getChildCount();
        int left=parent.getPaddingLeft();
        int right=parent.getPaddingRight();
        for (int i= 0; i < childCount-1;i++){
            View childView=parent.getChildAt(i);
            int top = childView.getTop();
            int bottom= childView.getBottom()+dividerHeight;
//            c.drawRect(new Rect(left,top,right,bottom),paint);
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
