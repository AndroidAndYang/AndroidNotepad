package com.yjz.load.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import java.util.Random;

/**
 * author： YJZ
 * date:  2018/11/2
 * des: 图形验证码生成
 */

public class CaptchaUtil {

    public static CaptchaUtil build() {
        if (captchaUtil == null) {
            captchaUtil = new CaptchaUtil();
        }
        return captchaUtil;
    }

    private static final char[] CHARS_NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] CHARS_LETTER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    private static final char[] CHARS_ALL = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    private static CaptchaUtil captchaUtil;

    private TYPE type = TYPE.CHARS;

    public enum TYPE {
        NUMBER, LETTER, CHARS
    }

    // default settings
    private static final int DEFAULT_CODE_LENGTH = 4;// 验证码的长度 这里是4位
    private static final int DEFAULT_FONT_SIZE = 60;// 字体大小
    private static final int DEFAULT_LINE_NUMBER = 0;// 多少条干扰线
    private static final int BASE_PADDING_LEFT = 20; // 左边距
    private static final int RANGE_PADDING_LEFT = 20;// 左边距范围值
    private static final int BASE_PADDING_TOP = 42;// 上边距
    private static final int RANGE_PADDING_TOP = 15;// 上边距范围值
    private static int DEFAULT_WIDTH = 200;// 默认宽度.图片的总宽
    private static int DEFAULT_HEIGHT = 70;// 默认高度.图片的总高
    private int DEFAULT_COLOR = 0xdf;// 默认背景颜色值

    // settings decided by the layout xml
    // canvas width and height
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    // random word space and padding_top
    private int base_padding_left = BASE_PADDING_LEFT;
    private int range_padding_left = RANGE_PADDING_LEFT;
    private int base_padding_top = BASE_PADDING_TOP;
    private int range_padding_top = RANGE_PADDING_TOP;

    // number of chars, lines; font size
    private int codeLength = DEFAULT_CODE_LENGTH;
    private int line_number = DEFAULT_LINE_NUMBER;
    private int font_size = DEFAULT_FONT_SIZE;

    // variables
    private String code;// 保存生成的验证码
    private int padding_left, padding_top;
    private Random random = new Random();

    private Bitmap mBitmapCode; // 验证码Bitmap

    private CaptchaUtil() {

    }

    private CaptchaUtil(TYPE types) {
        this.type = types;
    }

    public static CaptchaUtil getInstance(TYPE types) {
        if (captchaUtil == null) {
            captchaUtil = new CaptchaUtil(types);
        }
        return captchaUtil;
    }

    /**
     * @param length 验证码的长度
     */
    public CaptchaUtil codeLength(int length) {
        codeLength = length;
        return captchaUtil;
    }

    /**
     * @param size 字体大小
     */
    public CaptchaUtil fontSize(int size) {
        font_size = size;
        return captchaUtil;
    }

    /**
     * @param number 干扰线 数量
     */
    public CaptchaUtil lineNumber(int number) {
        line_number = number;
        return captchaUtil;
    }

    /**
     * @param colorInt 背景颜色值
     */
    public CaptchaUtil backColor(int colorInt) {
        DEFAULT_COLOR = colorInt;
        return captchaUtil;
    }

    public CaptchaUtil type(TYPE type) {
        this.type = type;
        return captchaUtil;
    }

    public CaptchaUtil size(int width, int height) {
        this.width = width;
        this.height = height;
        return captchaUtil;
    }

    private Bitmap makeBitmap() {
        padding_left = 0;

        Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bp);

        code = makeCode();

        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
        Paint paint = new Paint();
        paint.setTextSize(font_size);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }

        for (int i = 0; i < line_number; i++) {
            drawLine(canvas, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
        canvas.restore();//
        return bp;
    }

    public String getCode() {
        return code.toLowerCase();
    }

    public Bitmap into(ImageView imageView) {
        Bitmap bitmap = createBitmap();
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        return bitmap;
    }

    public String createCode() {
        return makeCode();
    }

    public Bitmap createBitmap() {
        mBitmapCode = makeBitmap();
        return mBitmapCode;
    }

    private String makeCode() {
        StringBuilder buffer = new StringBuilder();
        switch (type) {
            case NUMBER:
                for (int i = 0; i < codeLength; i++) {
                    buffer.append(CHARS_NUMBER[random.nextInt(CHARS_NUMBER.length)]);
                }
                break;
            case LETTER:
                for (int i = 0; i < codeLength; i++) {
                    buffer.append(CHARS_LETTER[random.nextInt(CHARS_LETTER.length)]);
                }
                break;
            case CHARS:
                for (int i = 0; i < codeLength; i++) {
                    buffer.append(CHARS_ALL[random.nextInt(CHARS_ALL.length)]);
                }
                break;
            default:
                for (int i = 0; i < codeLength; i++) {
                    buffer.append(CHARS_ALL[random.nextInt(CHARS_ALL.length)]);
                }
                break;
        }

        return buffer.toString();
    }

    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean()); // true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        // float类型参数，负数表示右斜，整数左斜
        // paint.setTextSkewX(skewX);
        //true为下划线，false为非下划线
        // paint.setUnderlineText(true);
        //true为删除线，false为非删除线
        // paint.setStrikeThruText(true);
    }

    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }
}
