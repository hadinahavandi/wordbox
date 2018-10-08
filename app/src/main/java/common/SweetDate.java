package common;

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
}
