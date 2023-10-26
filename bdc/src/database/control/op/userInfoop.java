package database.control.op;

import java.security.PublicKey;

import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;

public class userInfoop {
	private long id1=-1;
	private String username;
	private String password;
	private String question;
	private String answer;
	private String real_name;
	private String Reg_time;
	private String UState;
	private String opTable="userInfo";
	private DatabaseHelper helper=null;
	public userInfoop(Context context){
		DataBaseBaseOp Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(context);
		helper=Dbop.getHelper();
	}
	
	public long add(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		if(id1!=-1){values.put("ID",id1);}
		values.put("username",username);
		values.put("password",password);
		values.put("question",question);
		values.put("answer",answer);
		values.put("real_name",real_name);
		values.put("Reg_time",Reg_time);
		values.put("UState",UState);
		id1=db.insert("userInfo",null,values);
		return id1;
	}
	public int checkusername(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery(String.format("select ID from " +
		opTable+" where username=?"),new String[]{username+""});
		if(c.getCount()>0){
			c.moveToNext();
			id1=c.getLong(0);
		}
		return c.getCount();
	}
	public Boolean checknameandpwd()
	{
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select id from " +opTable+" where username=? and password=?",new String[]{username+"",password+""});
		if(c.getCount()>0)
		{c.moveToNext();
		id1=c.getLong(0);
		return true;}
		else return false;	
		}
	public boolean editpassword()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("password",password);
		int count=db.update(opTable, values, "username=?",new String[]{username+""});
		db.close();
		if(count>0) return true; else return false;
	}
	public boolean deletebyusername(Context context)
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		return db.delete(opTable,"username=?",new String[]{username+""})>0;
	}
	public boolean editreal_name()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("real_name",real_name);
		int count=db.update(opTable, values, "username=?", 
				new String[]{username+""});
		db.close();
		if (count>0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public int getallbyusername(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery(String.format("select * from "+opTable+
				" where username=?"),new String[]{username+""});
		if(c.getCount()>0)
			if(c.moveToNext())
			{id1=c.getLong(0);
			username=c.getString(1);
			password=c.getString(2);
			question=c.getString(3);
			answer=c.getString(4);
			real_name=c.getString(5);
			Reg_time=c.getString(6);
			UState=c.getString(7);
			}
		return c.getCount();
			}
	public Boolean qurryqanda()
	{
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select question,answer from "+opTable+
				" where username=?",new String[]{username+""});
		int count=c.getCount();
		if(count>0)
		{
			c.moveToNext();
			question=c.getString(0);
			answer=c.getString(1);
			return true;
		}
		return false;
	}
	public boolean edit_Info()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("question", question);
		values.put("answer",answer);
		int count=db.update(opTable, values, "username=?",
				new String[]{username+""});
		db.close();
		if(count>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public long getId1() {
		return id1;
	}


	public void setId1(long id1) {
		this.id1 = id1;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String getReal_name() {
		return real_name;
	}


	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}


	public String getReg_time() {
		return Reg_time;
	}


	public void setReg_time(String reg_time) {
		Reg_time = reg_time;
	}


	public String getUState() {
		return UState;
	}


	public void setUState(String uState) {
		UState = uState;
	}


}
