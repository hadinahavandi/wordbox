package common;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SweetArrayAdapter extends ArrayAdapter<CharSequence>{

    Context context; 
    int layoutResourceID,ContentResourceID;    
    CharSequence data[] = null;
    Typeface tf; 
	public SweetArrayAdapter(Context context, int layoutResourceID,int ContentResourceID, CharSequence[] data, String FONT ) { 
	    super(context, layoutResourceID,ContentResourceID, data);
	    this.layoutResourceID = layoutResourceID;
	    this.ContentResourceID=ContentResourceID;
	    this.context = context;
	    this.data = data;
	    tf = Typeface.createFromAsset(context.getAssets(), FONT);
	}
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent)
	    {
		 convertView=super.getView(position, convertView, parent);

	        TextView tv = (TextView) convertView.findViewById(ContentResourceID);

	        if(position<data.length && data[position] != null )
	        {
	        	tv.setTypeface(tf);
//	            tv.setTextColor(Color.WHITE);
	            tv.setText(data[position]);
//	            tv.setBackgroundColor(Color.RED); 
//	            int color = Color.argb( 200, 255, 64, 64 );
//	                tv.setBackgroundColor( color );

	        }

	        return convertView;
	    }
}