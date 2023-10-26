package general.base.op;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetReviewDate {
	String[] dateList=new String[30];
	public String[] getDateList(){
		return dateList;
	}
	Calendar cal=Calendar.getInstance();
	public GetReviewDate(){
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH);
		int day=cal.get(Calendar.DATE);
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month);
		cal.set(Calendar.DAY_OF_MONTH,day);
	}
	public void preDate(int datemins){
		cal.add(Calendar.DATE,0-datemins);
	}
	public void getReviewDate(int n){
		int i;
		int Between[]={0,1,2,4,7,15,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,};
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date date=cal.getTime();
		System.out.println(df.format(date));
		for(i=0;i<n;i++)
		{
			cal.add(Calendar.DATE,0-Between[i]);
			date=cal.getTime();
			dateList[i]=df.format(cal.getTime());
			System.out.println("aa   "+dateList[i]);
		}
	}
}
