package database.control.op;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;
import general.base.op.DateTimeOp;

public class userLoginop {
	long id1=-1;
	String username="";
	String state="";
	DateTimeOp dtop=null;
	String loginTime="";
	private String opTable="userLogin";
	private DatabaseHelper helper=null;

	public userLoginop(Context context){
		DataBaseBaseOp DbOp;
		DbOp=new DataBaseBaseOp();
		DbOp.initsqlite3(context);
		helper=DbOp.getHelper();
		dtop=new DateTimeOp();
	}
	public long add(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		if(id1!=-1){values.put("ID",id1);}
		values.put("username",username);
		values.put("loginTime",dtop.getDateTimeNowLong());
		values.put("loginTimeout","");
		values.put("state","µÇÂ¼");
		id1=db.insert(opTable, null, values);
		return id1;
	}
	public boolean down(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("loginTimeout",dtop.getDateTimeNowLong());
		values.put("state","ÏÂÏß");
		int count=db.update(opTable, values, "username=? and state='µÇÂ¼'", 
				new String[] {username+""});
		db.close();
		if(count>0) return true;else return false;
	}
	public boolean checkuserState(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select username from "+opTable+
				" where state='µÇÂ¼'",new String[]{});
		int count=c.getCount();
		if(count>0)
		{ c.moveToNext();
		username=c.getString(0);
		c.close(); db.close();
		return true;
		}
		else{c.close(); db.close();
		return false;
		}
	}
	public Boolean checktime(int n)
	{ SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select loginTime from "+opTable+
			" where state='µÇÂ¼'",new String[]{});
	int count=c.getCount();
	if(count>0)
	{ c.moveToNext();
	loginTime=c.getString(0);
	c.close(); db.close();
	if(dtop.delaynday(loginTime, n))
	{ return true;}
	else
		return false;
	}
	else{c.close(); db.close(); return false;}
	}
	public boolean tabbleIsExist(String tableName){
		SQLiteDatabase db=helper.getReadableDatabase();
		boolean result=false;
		if(tableName==null){return false;}
		Cursor cursor=null;
		try{String sql="select count(*) as c from Sqlite_master "+
				" where type='table' and name='"+tableName.trim()+"' ";
		cursor=db.rawQuery(sql, null);
		if(cursor.moveToNext()){
			int count=cursor.getInt(0);
		if(count>0){result=true;}
		}}catch(Exception e){
		}
		return result;
		}
	public boolean login_new(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("loginTime",dtop.getDateTimeNowLong());
		int count=db.update(opTable, values, "username=? and state='µÇÂ¼'",
				new String[] {username+""});
		db.close();
		if(count>0)return true;else return false;
	}
	
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public String getLoginTime(){
		return loginTime;
	}
	public void setLoginTime(String loginTime){
		this.loginTime=loginTime;
	}
}
