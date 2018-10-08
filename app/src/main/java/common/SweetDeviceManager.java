package common;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Patterns;

import java.util.regex.Pattern;

public class SweetDeviceManager {


	public static String getDeviceID(Context ApplicationContext)
	{
		String android_id = Secure.getString(ApplicationContext.getContentResolver(),
                Secure.ANDROID_ID);
		return android_id;
	}
	public static int getSDKVersion()
	{
        int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
        return SDK_VERSION;
	}
	public static String GetAccounts(Context AppContext)
	{
		String Accounts="";
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; 
		boolean found=false;
		Account[] accounts = AccountManager.get(AppContext).getAccounts();
		for (Account account : accounts) {
			Accounts+=account.name+"\n";
		}
		return Accounts;
	}
	public static String getDeviceModel(Context ApplicationContext)
	{
		String DeviceModel=android.os.Build.MODEL;
		return DeviceModel;
	}
	public static String getDevicePassword(Context ApplicationContext,String DeviceID,String AppPrefix)
	{
	    int DevicePass1=-1;
	    String textToEncrypt=AppPrefix+DeviceID;
	    
	    for(int i=0;i<textToEncrypt.length();i++)
	    	DevicePass1+=((int)textToEncrypt.substring(i, i+1).charAt(0))%24;
	    DevicePass1*=8;
	    DevicePass1+=888;
	    String DevicePass=DeviceID.substring(5)+String.valueOf(DevicePass1);
	    return DevicePass;
	}
	public static String getDevicePassword(Context ApplicationContext,String AppPrefix)
	{
		String DeviceID= SweetDeviceManager.getDeviceID(ApplicationContext);
	    return SweetDeviceManager.getDevicePassword(ApplicationContext, DeviceID,AppPrefix);
	}
	
}
