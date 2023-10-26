package general.base.op;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

public class DateTimeOp {
	public DateTimeOp(){  }
	public String getDateTimeNowLong()
	{ SimpleDateFormat formatter=
	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date curDate=new Date(System.currentTimeMillis());
	String DownLoadTime1=formatter.format(curDate);
	return DownLoadTime1;
	}
	public String getDateTimeNowShort()
	{ SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	Date curDate=new Date(System.currentTimeMillis());
	String DownLoadTime1=formatter.format(curDate);
	return DownLoadTime1;
	}
	public String getDateTimeNowTable()
	{ SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
	Date curDate=new Date(System.currentTimeMillis());
	String DownLoadTime1=formatter.format(curDate);
	return DownLoadTime1;
	}
	public boolean compareDate(String dateString)
	{ SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	Date curDate=new Date(System.currentTimeMillis());
	String DownLoadTime1=formatter.format(curDate);
	return dateString.indexOf(DownLoadTime1)==-1;
	}
	public boolean delaynday(String day,int n)
	{ Format format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date today=new Date(System.currentTimeMillis());
	Date oldday=null;
	Calendar todayc=Calendar.getInstance();
	todayc.setTime(today);
	Calendar olddayc=Calendar.getInstance();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try{
		java.util.Date olddayutil=sdf.parse(day);
		oldday=new java.sql.Date(olddayutil.getTime());
	}
	catch(ParseException e){
		e.printStackTrace();
	}
	olddayc.setTime(oldday);
	olddayc.add(Calendar.DAY_OF_MONTH,n);
	if(olddayc.before(todayc)){
		return true;
	}else return false;
	}
}
