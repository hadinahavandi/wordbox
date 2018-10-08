package common;

import android.content.Context;
import android.graphics.Typeface;

public class SweetFonts {
	public static String bHoma="fonts/bhoma.ttf";
	
	public static String bRoya="fonts/broya.ttf";
	public static String bZar="fonts/bzar.ttf";
	public static String bYekan="fonts/byekan.ttf";
	public static String CODELIGHT="fonts/CODELight.otf";
	public static String CODEBOLD="fonts/CODEBold.otf";
	public static String SEGOEUI="fonts/segoeui.ttf";
	public static String IRANSans="fonts/IRANSansMobile.ttf";
	public static String IRANSansBOLD="fonts/IRANSansMobile_Bold.ttf";
	public static String IRANSansLight="fonts/IRANSansMobile_Light.ttf";
	public static String GOOGLESansRegular="fonts/GoogleSans_Regular.ttf";
	public static String GOOGLESansMedium="fonts/GoogleSans_Medium.ttf";
	public static String GOOGLESansItalic="fonts/GoogleSans_Italic.ttf";

	public static Typeface getFont(Context context,String FontName)
	{
		Typeface face=Typeface.createFromAsset(context.getAssets(), FontName);
		return face;
	}
}
