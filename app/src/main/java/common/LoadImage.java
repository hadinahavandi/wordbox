package common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private LevelListDrawable mDrawable;
    private int Width,Height;
    public int getWidth() {
		return Width;
	}
	public void setWidth(int width) {
		Width = width;
	}
	public int getHeight() {
		return Height;
	}
	public void setHeight(int height) {
		Height = height;
	}

	private TextView tv;
    public LoadImage(TextView tv)
    {
    	this.tv=tv;
    	Width=-1;
    	Height=-1;
    }
    @Override
    protected Bitmap doInBackground(Object... params) {
        String source = (String) params[0];
        mDrawable = (LevelListDrawable) params[1];
        //Log.d(TAG, "doInBackground " + source);
        try {
            InputStream is = new URL(source).openStream();
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
       // Log.d(TAG, "onPostExecute drawable " + mDrawable);
       // Log.d(TAG, "onPostExecute bitmap " + bitmap);
        if (bitmap != null) {
            BitmapDrawable d = new BitmapDrawable(bitmap);
            mDrawable.addLevel(1, 1, d);
            Double Ratio= ((double)bitmap.getWidth()/bitmap.getHeight());
            if(Width<=40 || Ratio<0.1){
            	Width=bitmap.getWidth();
            }
            else
            {
            	Height=(int) (Width/Ratio);
            	Log.d("ImageGetter",String.valueOf(bitmap.getHeight()));
            }
            if(Height<=40)
            	Height=bitmap.getHeight();
            mDrawable.setBounds(0, 0,Width ,Height );
            mDrawable.setLevel(1);
            // i don't know yet a better way to refresh TextView
            // mTv.invalidate() doesn't work as expected
            CharSequence t =tv.getText();
            tv.setText(t);
        }
    }
}
