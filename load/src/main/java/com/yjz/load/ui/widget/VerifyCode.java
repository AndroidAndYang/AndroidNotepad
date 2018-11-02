package com.yjz.load.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yjz.load.R;

import java.util.Random;

/**
 * author： YJZ
 * date:  2018/10/31
 * des: 图形验证码
 */

public class VerifyCode extends View {

    /**
     * 文本内容
     */
    private String mCodeText;
    /**
     * 文本大小
     */
    private int mCodeTextSize;
    /**
     * 验证码长度
     */
    private int mCodeLength;
    /**
     * 背景色
     */
    private int mCodeBackground;
    /**
     * 验证码是否包含字母
     */
    private boolean isContainChar;
    /**
     * 干扰点数
     */
    private int mPointNum;
    /**
     * 干扰线数
     */
    private int mLineNum;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 绘制范围
     */
    private Rect mBound;
    /**
     * 验证码图片
     */
    private Bitmap bitmap;

    /**
     * 控件的宽度
     */
    private static int mWidth;
    /**
     * 控件的高度
     */
    private static int mHeight;
    /**
     * 随机数
     */
    private static Random mRandom = new Random();

    public VerifyCode(Context context) {
        super(context, null);
    }

    public VerifyCode(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public VerifyCode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrValues(context, attrs);
        init();
    }

    private void init() {
        String validationCode = getValidationCode(mCodeLength, isContainChar);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBound = new Rect();
        // 计算文字所在的矩形
        mPaint.getTextBounds(validationCode, 0, mCodeText.length(), mBound);
    }

    /**
     * 初始化属性集合
     */
    private void initAttrValues(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerifyCode);
        mCodeTextSize = typedArray.getDimensionPixelSize(R.styleable.VerifyCode_codeTextSize, 16);
        mCodeBackground = typedArray.getColor(R.styleable.VerifyCode_codeBackground, Color.parseColor("#ffffff"));
        mCodeLength = typedArray.getInteger(R.styleable.VerifyCode_codeLength, 4);
        isContainChar = typedArray.getBoolean(R.styleable.VerifyCode_isContainChar, true);
        mPointNum = typedArray.getInteger(R.styleable.VerifyCode_pointNum, 100);
        mLineNum = typedArray.getInteger(R.styleable.VerifyCode_linNum, 3);
        typedArray.recycle();
    }

    /**
     * 获取验证码
     *
     * @param length   生成随机数的长度
     * @param contains 是否包含字符串
     * @return 验证码
     */
    public String getValidationCode(int length, boolean contains) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if (contains) {
                //字母或数字
                String code = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //字符串
                if ("char".equalsIgnoreCase(code)) {
                    //大写或小写字母
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val.append((char) (choice + random.nextInt(26)));
                } else if ("num".equalsIgnoreCase(code)) {
                    val.append(String.valueOf(random.nextInt(10)));
                }
            } else {
                val.append(String.valueOf(random.nextInt(10)));
            }

        }
        return val.toString();
    }

    /**
     * 创建图片验证码
     *
     * @return bitmap
     */
    private Bitmap createBitmapValidate() {
        if (bitmap != null && !bitmap.isRecycled()) {
            //回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        //创建图片
        Bitmap sourceBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(sourceBitmap);
        //画上背景颜色
        canvas.drawColor(mCodeBackground);
        //初始化文字画笔
        mPaint.setStrokeWidth(3f);
        mPaint.setTextSize(mCodeTextSize);
        //测量验证码字符串显示的宽度值
        float textWidth = mPaint.measureText(mCodeText);
        //画上验证码
        int length = mCodeText.length();
        //计算一个字符的所占位置
        float charLength = textWidth / length;
        for (int i = 1; i <= length; i++) {
            int offsetDegree = mRandom.nextInt(15);
            //这里只会产生0和1，如果是1那么正旋转正角度，否则旋转负角度
            offsetDegree = mRandom.nextInt(2) == 1 ? offsetDegree : -offsetDegree;
            //用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
            canvas.save();
            //设置旋转
            canvas.rotate(offsetDegree, mWidth / 2, mHeight / 2);
            //给画笔设置随机颜色
            mPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20,
                    mRandom.nextInt(200) + 20);
            //设置字体的绘制位置
            canvas.drawText(String.valueOf(mCodeText.charAt(i - 1)), (i - 1) * charLength + 5,
                    mHeight * 4 / 5f, mPaint);
            //用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
            canvas.restore();
        }

        //重新设置画笔
        mPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20,
                mRandom.nextInt(200) + 20);
        mPaint.setStrokeWidth(1);
        //产生干扰效果1 －－ 干扰点
        for (int i = 0; i < mPointNum; i++) {
            drawPoint(canvas, mPaint);
        }
        //生成干扰效果2 －－ 干扰线
        for (int i = 0; i < mLineNum; i++) {
            drawLine(canvas, mPaint);
        }
        return sourceBitmap;
    }


    /**
     * 生成干扰点
     */
    private static void drawPoint(Canvas canvas, Paint paint) {
        PointF pointF = new PointF(mRandom.nextInt(mWidth) + 10, mRandom.nextInt(mHeight) + 10);
        canvas.drawPoint(pointF.x, pointF.y, paint);
    }

    /**
     * 生成干扰线
     */
    private static void drawLine(Canvas canvas, Paint paint) {
        int startX = mRandom.nextInt(mWidth);
        int startY = mRandom.nextInt(mHeight);
        int endX = mRandom.nextInt(mWidth);
        int endY = mRandom.nextInt(mHeight);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    /**
     * 判断验证码是否一致 忽略大小写
     */
    public Boolean isEqualsIgnoreCase(String CodeString) {
        return mCodeText.equalsIgnoreCase(CodeString);
    }

    /**
     * 判断验证码是否一致 不忽略大小写
     */
    public Boolean isEquals(String CodeString) {
        return mCodeText.equals(CodeString);
    }

    /**
     * 提供外部调用的刷新方法
     */
    public void refresh() {
        mCodeText = getValidationCode(mCodeLength, isContainChar);
        bitmap = createBitmapValidate();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        if (bitmap == null) {
            bitmap = createBitmapValidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 按下刷新
            case MotionEvent.ACTION_DOWN:
                refresh();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件宽高的显示模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 获取宽高的尺寸值，固定值的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //设置宽高默认为建议的最小宽高
        int width;
        int height;

        // MeasureSpec父布局传递给后代的布局要求 包含 确定大小和三种模式
        // EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
        // AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
        // UNSPECIFIED：表示子布局想要多大就多大，很少使用
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            // 获取到文字的宽度
            mPaint.setTextSize(mCodeTextSize);
            mPaint.getTextBounds(mCodeText, 0, mCodeText.length(), mBound);
            float textWidth = mBound.width();
            // 控件的宽度为 文字的宽度加上左、右边的padding值
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mCodeTextSize);
            mPaint.getTextBounds(mCodeText, 0, mCodeText.length(), mBound);
            // 控件的高度为 文字的高度加上上、下边的padding值
            float textHeight = mBound.height();
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }
        // 设置测量的宽高值
        setMeasuredDimension(width, height);
    }
}
