package common;


import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import android.text.format.DateFormat;
import java.util.Calendar;

public class SweetDate {

	public static String getDayName(int CivilDayOfWeek)
	{
		String DayName="";
		switch (CivilDayOfWeek) {
		case 7:
			DayName="شنبه";
			break;
		case 1:
			DayName="یکشنبه";
			break;
		case 2:
			DayName="دوشنبه";
			break;
		case 3:
			DayName="سه شنبه";
			break;
		case 4:
			DayName="چهارشنبه";
			break;
		case 5:
			DayName="پنجشنبه";
			break;
		case 6:
			DayName="جمعه";
			break;
		default:
			break;
		}
		return DayName;
	}
	private CivilDate GetDateFromTime(Long Time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Time);
		CivilDate cd=new CivilDate();
		String year = DateFormat.format("yyyy", cal).toString();
		String month = DateFormat.format("MM", cal).toString();
		String day = DateFormat.format("dd", cal).toString();
		cd.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
		return cd;
	}
    private static String Date2String(CivilDate cd)
    {
        PersianDate PDate=DateConverter.civilToPersian(cd);
        String DateSTR=String.valueOf(PDate.getYear())+"/"+String.valueOf(PDate.getMonth())+"/"+String.valueOf(PDate.getDayOfMonth());
        return DateSTR;
    }
    public static String Time2String(Long Time,boolean URLFriendly)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Time);
        CivilDate cd=new CivilDate();
        String year = DateFormat.format("yyyy", cal).toString();
        String month = DateFormat.format("MM", cal).toString();
        String day = DateFormat.format("dd", cal).toString();
        String Hour=DateFormat.format("k", cal).toString();
        String Minute=DateFormat.format("mm", cal).toString();
        String Seconds=DateFormat.format("ss", cal).toString();
        cd.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        PersianDate PDate=DateConverter.civilToPersian(cd);
        String DateSTR="";
        if(URLFriendly)
            DateSTR=String.valueOf(PDate.getYear())+"-"+String.valueOf(PDate.getMonth())+"-"+String.valueOf(PDate.getDayOfMonth() + " "+Hour+"-"+Minute+"-"+Seconds);
        else
            DateSTR=String.valueOf(PDate.getYear())+"/"+String.valueOf(PDate.getMonth())+"/"+String.valueOf(PDate.getDayOfMonth() + " "+Hour+":"+Minute);
        return DateSTR;

    }
}
