package general.base.op;

import java.util.Random;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;;


public class CAPTCHAcode {
	private static final char[] CHARS={'0','1','2','3','4','5','6',
		'7','8','9','a','b','c','d','e','f','g','h','i','j',
		'k','l','m','n','o','p','q','r','s','t','u','v','w',
		'x','y','z','A','B','C','D','E','F','G','H','I','J',
		'K','L','M','N','O','P','Q','R','S','T','U','V','W',
		'X','Y','Z'};
	private static final int DEFAULT_CODE_LENGTH=4;
	private static final int DEFAULT_FONT_SIZE=40;
	private static final int DEFAULT_LINE_NUMBER=3;
	private static final int BASE_PADDING_LEFT=25;
	private static final int RANGE_PADDING_LEFT=20;
	private static final int BASE_PADDING_TOP=30;
	private static final int RANGE_PADDING_TOP=15;
	private static final int DEFAULT_WIDTH=200;
	private static final int DEFAULT_HEIGHT=50;
	private final int DEFAULT_COLOR=0xdf;
	private int width=DEFAULT_WIDTH;
	private int height=DEFAULT_HEIGHT;
	private int base_padding_left=BASE_PADDING_LEFT;
	private int range_padding_left=RANGE_PADDING_LEFT;
	private int base_padding_top=BASE_PADDING_TOP;
	private int range_padding_top=RANGE_PADDING_TOP;
	private int codeLength=DEFAULT_CODE_LENGTH;
	private int line_number=DEFAULT_LINE_NUMBER;
	private int font_size=DEFAULT_FONT_SIZE;
	private String code;
	private int padding_left,padding_top;
	private Random random=new Random();
	
	public String getCode(){
		return code;
	}
	public Bitmap getBitmap(){
		return createBitmap();
	}
	private Bitmap createBitmap(){
		padding_left=0;
		Bitmap bp=Bitmap.createBitmap(width,height,Config.ARGB_8888);
		Canvas c=new Canvas(bp);
		code=createCode();
		c.drawColor(Color.rgb(DEFAULT_COLOR,DEFAULT_COLOR,DEFAULT_COLOR));
		Paint paint=new Paint();
		paint.setTextSize(font_size);
		for(int i=0;i<code.length();i++){
			randomTextStyle(paint);
			randomPadding();
			c.drawText(code.charAt(i)+"",padding_left,padding_top,paint);
		}
		for (int i = 0; i <line_number; i++) {
			drawLine(c,paint);
		}
		c.save(Canvas.ALL_SAVE_FLAG);
		c.restore();
		return bp;
	}
	private String createCode(){
			StringBuilder buffer=new StringBuilder();
			for(int i=0;i<codeLength;i++){
				buffer.append(CHARS[random.nextInt(CHARS.length)]);
			}
			return buffer.toString();
	}
	private void drawLine(Canvas canvas,Paint paint){
		int color=randomColor();
		int startX=random.nextInt(width);
		int startY=random.nextInt(height);
		int stopX=random.nextInt(width);
		int stopY=random.nextInt(height);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}
	private void randomTextStyle(Paint paint){
		int color=randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(random.nextBoolean());
		float skewX=random.nextInt(11)/10;
		skewX=random.nextBoolean()?skewX:-skewX;
		paint.setTextSkewX(skewX);
	}
	private int randomColor(){
		int red=random.nextInt(256);
		int green=random.nextInt(256);
		int blue=random.nextInt(256);
		return Color.rgb(red, green, blue);
	}
	private void randomPadding(){
		padding_left+=base_padding_left+random.nextInt(range_padding_left);
		padding_top=base_padding_top+random.nextInt(range_padding_top);	
		}			
		}
