package common;
/*
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Net {
	Context theContext;
	public Net(Context theContext)
	{
		this.theContext=theContext;
	}
	public boolean isNetworkConnected() {
	  ConnectivityManager cm = (ConnectivityManager)theContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	  NetworkInfo ni = cm.getActiveNetworkInfo();
	  if (ni == null) {
	   // There are no active networks.
		  return false;
	  } else
		  return true;
	}
	public boolean UrlFound(String Url)
	{
        HttpGet requestForTest = new HttpGet(Url);
        try {
            new DefaultHttpClient().execute(requestForTest); 
            return true;
        } 
        catch (Exception e) {
        	return false;
        }
	}
	public boolean IsInternetAvailable()
	{
        return UrlFound("http://m.google.com");
	}
}
*/