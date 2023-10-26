package database.control.op;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;

public class userRealInfoop {
	long ID=-1;
	String username;
	String student;
	String name;
	String identity;
	String phone;
	String email;
	private String opTable="userRealInfo";
	private DatabaseHelper helper=null;
	public userRealInfoop(Context context){
		DataBaseBaseOp Dbop;
		Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(context);
		helper=Dbop.getHelper();
	}
	public boolean checkUserName(){
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor c=db.rawQuery(String.format("select username,email,"+
		"phone from userRealInfo where username=?"),
		new String[]{username=""});
		if(c.getCount()>0)
		{ if(c.moveToNext()){
			username=c.getString(0);
			email=c.getString(1);
			phone=c.getString(2);
		}
		return true;
		}
		else{return false;}
		}
		public long UserRegistOfRealInfo(){
			SQLiteDatabase db=helper.getWritableDatabase();
			ContentValues values=new ContentValues();
			if(!checkUserName())
			{if(ID!=1){values.put("ID",ID);}
			values.put("username",username);
			values.put("student",student);
			values.put("name",name);
			values.put("identity",identity);
			values.put("phone",phone);
			values.put("email",email);
			ID=db.insert(opTable,null, values);
			db.close();
			}
			return ID;
		}
		public Boolean qurry_single()
		{
			SQLiteDatabase db=helper.getReadableDatabase(); 
			Cursor c=db.rawQuery("select student,name,identity,phone,email from "
					+opTable+" where username=?",new String[]{username+""});
			int count=c.getCount();
			if(count>0)
			{
				c.moveToNext();
				student=c.getString(0);
				name=c.getString(1);
				identity=c.getString(2);
				phone=c.getString(3);
				email=c.getString(4);
				return true;
			}
			return false;
		}
		public boolean editReal_Info()
		{
			SQLiteDatabase db=helper.getWritableDatabase();
			ContentValues values=new ContentValues();
			values.put("student",student);
			values.put("name",name);
			values.put("identity",identity);
			values.put("phone",phone);
			values.put("email",email);
			int count=db.update(opTable, values,"username=?",
					new String[]{username+""});
			db.close();
			if(count>0)
			{
				return true;
			}
			else {
				return false;
			}
		}
		public long getID(){
			return ID;
		}
		public void setID(long iD){
			ID=iD;
		}
		public String getUsername(){
			return username;
		}
		public void setUsername(String username){
			this.username=username;
		}
		public String getStudent(){
			return student;
		}
		public void setStudent(String student){
			this.student=student;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name=name;
		}
		public String getIdentity(){
			return identity;
		}
		public void setIdentity(String identity){
			this.identity=identity;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
}
