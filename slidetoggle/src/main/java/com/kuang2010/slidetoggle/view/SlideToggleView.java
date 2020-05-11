package com.kuang2010.slidetoggle.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kuang2010.slidetoggle.R;

import androidx.annotation.Nullable;

/**
 * author: kuangzeyu2019
 * desc: 自定义滑动开关
 */
public class SlideToggleView extends View {

    private Bitmap mBitmap_slide_button_background;
    private Bitmap mBitmap_switch_background;
    private int mSlide_width;
    private int mView_width;
    private float mTouchX;
    private Paint mPaint;
    private State draw_state = State.DRAW_STATE_INIT;
    private boolean isToggleOpen;//开关状态是否打开
    private OnToggleChangeListener mOnToggleChangeListener;

    public void setOnToggleChangeListener(OnToggleChangeListener onToggleChangeListener){
        mOnToggleChangeListener = onToggleChangeListener;
    }

    enum State{
        DRAW_STATE_INIT,DRAW_STATE_DOWN,DRAW_STATE_MOVE,DRAW_STATE_UP
    }

    public SlideToggleView(Context context) {
        this(context,null);
    }

    public SlideToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化资源
     * 两张图片
     * */
    private void init(){
        mBitmap_slide_button_background = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button_background);
        mBitmap_switch_background = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        mSlide_width = mBitmap_slide_button_background.getWidth();
        mView_width = mBitmap_switch_background.getWidth();

        mPaint = new Paint();
    }

    //大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mView_width,mBitmap_switch_background.getHeight());
    }


    //样子
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画开关的底座
        canvas.drawBitmap(mBitmap_switch_background,0,0,mPaint);

        if (draw_state == State.DRAW_STATE_INIT){
            //画滑动盖子,初始化在最左边
            canvas.drawBitmap(mBitmap_slide_button_background,0,0,mPaint);

        }else {

            changeSlideButton(canvas);
        }

        //始终在canvas上只画了两次bitmap
    }

    private void changeSlideButton(Canvas canvas) {

        if (draw_state==State.DRAW_STATE_DOWN || draw_state==State.DRAW_STATE_MOVE){
            //按下和移动时
            if (mTouchX <mSlide_width/2 ){//左边界
                //盖子在最左边
                canvas.drawBitmap(mBitmap_slide_button_background,0,0,mPaint);
            }else if (mTouchX >mView_width-mSlide_width/2){//右边界
                //盖子在最右边
                canvas.drawBitmap(mBitmap_slide_button_background,mView_width-mSlide_width,0,mPaint);
            }else {
                canvas.drawBitmap(mBitmap_slide_button_background, mTouchX - mSlide_width / 2,0,mPaint);

            }
        }else {
            //松开时
            if (mTouchX<mView_width/2){
                canvas.drawBitmap(mBitmap_slide_button_background,0,0,mPaint);
                if (isToggleOpen){
                    isToggleOpen=false;
                    if (mOnToggleChangeListener!=null){
                        mOnToggleChangeListener.onToggleChange(this,isToggleOpen);
                    }
                }
            }else {
                canvas.drawBitmap(mBitmap_slide_button_background,mView_width-mSlide_width,0,mPaint);
                if (!isToggleOpen){
                    isToggleOpen=true;
                    if (mOnToggleChangeListener!=null){
                        mOnToggleChangeListener.onToggleChange(this,isToggleOpen);
                    }
                }
            }

        }
    }

    public boolean getToggleState(){
        return isToggleOpen;
    }

    public void setToggleState(boolean open){
        if (isToggleOpen==open){
            return;
        }
        if (open){
            mTouchX = mView_width;
        }else {
            mTouchX = 0;
        }
        draw_state = State.DRAW_STATE_UP;
        invalidate();
    }


    public interface OnToggleChangeListener{
        void onToggleChange(View view,boolean open);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                draw_state = State.DRAW_STATE_DOWN;
                mTouchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                draw_state = State.DRAW_STATE_MOVE;
                mTouchX = event.getX();
                break;
            case MotionEvent.ACTION_UP://处理最终边界结果
                if (draw_state!=State.DRAW_STATE_UP){
                    draw_state = State.DRAW_STATE_UP;
                    mTouchX = event.getX();
                }
                break;
        }
        invalidate();// 触发onDraw的调用
        return true;// 自己消费
    }


}
