package common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;

@SuppressLint("NewApi")
public class SweetDisplayScaler {
	private Activity AppActivity;
	private int ScreenWidth,ScreenHeight;
	public SweetDisplayScaler(Activity AppActivity)
	{
		this.AppActivity=AppActivity;
		Display display = AppActivity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
        
		if(SDK_VERSION>=13)
		{
			display.getSize(size);
			ScreenWidth = size.x;
			ScreenHeight = size.y;
		}
		else
		{
			ScreenWidth=display.getWidth();
			ScreenHeight=display.getHeight();
		}
	}
	public float getScreenWidth()
	{
		return ScreenWidth;
	}
	public float getScreenHeight()
	{
		return ScreenHeight;
	}
	public int HeightPercentToPixel(float Percent)
	{
		float AbsolutePixel=(Percent/100)*ScreenHeight;
		return (int)AbsolutePixel;
	}
	public int WidthPercentToPixel(float Percent)
	{
		float AbsolutePixel=(Percent/100)*ScreenWidth;
		return (int)AbsolutePixel;
	}
	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{    
		// Raw height and width of image    
		final int height = options.outHeight;    
		final int width = options.outWidth;    
		int inSampleSize = 1;    
		if (height > reqHeight || width > reqWidth) 
		{        
			final int halfHeight = height / 2;        
			final int halfWidth = width / 2;          
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) 
			{            
				inSampleSize *= 2;        
			}    
		}
	    return inSampleSize;
	}
	public Bitmap decodeBitmapFromResource(Resources resources,int resId,int reqWidth, int reqHeight) 
	{    
		// First decode with inJustDecodeBounds=true to check dimensions    
		final BitmapFactory.Options options = new BitmapFactory.Options();    
		options.inJustDecodeBounds = true;    
		BitmapFactory.decodeResource(resources, resId, options);    
		// Calculate inSampleSize    
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// Decode bitmap with inSampleSize set    
		options.inJustDecodeBounds = false;    
		return BitmapFactory.decodeResource(resources, resId, options);
	}
}
