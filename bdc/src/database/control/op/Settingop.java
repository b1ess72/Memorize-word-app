package database.control.op;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;
import android.util.Log;

public class Settingop {
	DatabaseHelper createdatabase=null;
	String opTable="Setting";
	long sID=-1;
	long settingID=-1;
	String settingContent;
	String settingMeaning;
	int settingClass;
	List<Settingop> list=new ArrayList<Settingop>();
	public Settingop(Context content){
		DataBaseBaseOp Dbop;
		Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(content);
		createdatabase=Dbop.getHelper();
	}
	public Settingop(long settingID,String settingContent,
			String settingMeaning,int settingClass){
		super();
		this.settingID=settingID;
		this.settingContent=settingContent;
		this.settingMeaning=settingMeaning;
		this.settingClass=settingClass;
	}
	public boolean checkempty()
	{ SQLiteDatabase db=createdatabase.getReadableDatabase();
	Cursor c=db.rawQuery("select * from " + opTable
			+"   ",new String[]{  });
	int count=c.getCount();
	if(count==0)
		return true;
	else 
		return false;
	}
	public void initsvinsert()
	{ SQLiteDatabase db=createdatabase.getWritableDatabase();
	String sql="";
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(1,1,'允许2G/3G/4G通过网络查询单词，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(2,0,'允许保存查询的单词，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(3,1,'允许WiFi环境下通过网络查询单词，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(4,1,'允许2G/3G/4G保存读音文件，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(5,1,'允许2G/3G/4G播放单词读音，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(6,1,'允许WiFi保存读音文件，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(7,1,'允许WiFi播放读音文件，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(8,1,'WiFi条件下允许与电脑同步，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(9,0,'2G/3G/4G条件下允许与电脑同步，0表示未选中，1表示选中',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(10,'0','读音文件保存存储器',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(11,'temp','读音文件保存路径',1);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(12,'50','声音',2);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(13,'1','声音显示选择时自动播放',2);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(14,1,'学习模式，0表示未选中，1表示选中',3);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(15,0,'记忆模式，0表示未选中，1表示选中',3);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(16,'3000','单词音标解释缓出多个秒',3);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(17,1,'单词自动循环播放，0表示未选中，1表示选中',3);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(18,0,'单词自动播放，0表示未选中，1表示选中',3);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(19,'4000','自动播放间隔时间',3);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(20,'12','单词',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(21,'12','音标',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(22,'12','解释',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(23,'12','例句',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(24,'#00000a','单词颜色',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(25,'#00000a','音标颜色',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(26,'#00000a','解释颜色',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(27,'#00000a','例句颜色',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(28,0,'显示小组进度，0表示未选中，1表示选中',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(29,0,'显示整体进度，0表示未选中，1表示选中',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(30,1,'显示顺序，0表示未选中，1表示顺序显示，2逆序显示，3乱序显示',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(31,0,'单词需要显示的列单词，0表示未选中，1表示选中',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(32,0,'单词需要显示的列音标，1表示选中',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(33,0,'单词需要显示的列解释，1表示选中',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(34,0,'单词需要显示的列例句，0表示未选中，1表示选中',4);";
	db.execSQL(sql);
	sql="insert into "+opTable+
			" (settingID,settingContent,settingMeaning,settingClass)"
			+" values(35,'','保存当前正在操作的课程名',5);\n";
	db.execSQL(sql);
	}
	public List<Settingop> querrySettingClass4(){
		list.clear();
		SQLiteDatabase db=createdatabase.getReadableDatabase();
		Cursor c=db.rawQuery("select * from "+opTable
				+" where settingClass=? ",new String[]{ 5 +""});
		int ps=(int)c.getCount();
		if(c.getCount()>0){
			for(;ps>0;ps--){
				c.moveToNext();
				sID=c.getLong(c.getColumnIndex("sID"));
				this.settingID=c.getLong(c.getColumnIndex("settingID"));
				settingContent=c.getString(2);
				byte[] byte1=c.getBlob(3);
				try{
					settingMeaning=new String(byte1,"gbk");
					settingMeaning=settingMeaning.substring(0, settingMeaning.length()-1);
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				settingClass=c.getInt(3);
				Settingop t1=new Settingop(settingID,settingContent,settingMeaning,settingClass);
				list.add(t1);
			}
		}
		c.close();
		db.close();
		return list;
	}
	public int edit(){
		SQLiteDatabase db=createdatabase.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("settingContent",settingContent);
		System.out.println("edit数据测试1"+settingContent+"ID"+settingID);
		int count=db.update(opTable, values, "settingID=?",
				new String[]{settingID+""});
		db.close();
		return count;
	}
	public List<Settingop> querrySetting_network_right(){
		list.clear();
		SQLiteDatabase db=createdatabase.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable + " where settingClass=? ",new String[] {1+""});
		int ps=(int)c.getCount();
		if(c.getCount()>0){
			for(;ps>0;ps--){
				c.moveToNext();
				sID=c.getLong(c.getColumnIndex("sID"));
				this.settingID=c.getLong(c.getColumnIndex("settingID"));
				settingContent=c.getString(2);
				byte[] byte1=c.getBlob(3);
				try{
					settingMeaning=new String(byte1,"gbk");
					settingMeaning=settingMeaning.substring(0, settingMeaning.length()-2);
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				settingClass=c.getInt(3);
				Settingop t1=new Settingop(settingID,settingContent,settingMeaning,settingClass);
				list.add(t1);
			}
		}
		for(Settingop tt:list){
			System.out.println("查询打印---+++"+tt.getSettingID()+"--"+tt.getSettingContent()+"--"+tt.getSettingMeaning()+"--"+tt.getSettingClass());
		}
		c.close();
		db.close();
		return list;
	}
	public List<Settingop> querrysetting_word_pronunciation(){
		list.clear();
		SQLiteDatabase db=createdatabase.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable + " where settingClass=? ",new String[] {2+""});
		int ps=(int)c.getCount();
		if(c.getCount()>0){
			for(;ps>0;ps--){
				c.moveToNext();
				sID=c.getLong(c.getColumnIndex("sID"));
				this.settingID=c.getLong(c.getColumnIndex("settingID"));
				settingContent=c.getString(2);
				byte[] byte1=c.getBlob(3);
				try{
					settingMeaning=new String(byte1,"gbk");
					settingMeaning=settingMeaning.substring(0, settingMeaning.length()-2);
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				settingClass=c.getInt(3);
				Settingop t1=new Settingop(settingID,settingContent,settingMeaning,settingClass);
				list.add(t1);
			}
		}
		for(Settingop tt:list){
			System.out.println("查询打印---+++"+tt.getSettingID()+"--"+tt.getSettingContent()+"--"+tt.getSettingMeaning()+"--"+tt.getSettingClass());
		}
		c.close();
		db.close();
		return list;
	}
	public List<Settingop> querrysetting_learning_mode(){
		list.clear();
		SQLiteDatabase db=createdatabase.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable + " where settingClass=? ",new String[] {3+""});
		int ps=(int)c.getCount();
		if(c.getCount()>0){
			for(;ps>0;ps--){
				c.moveToNext();
				sID=c.getLong(c.getColumnIndex("sID"));
				this.settingID=c.getLong(c.getColumnIndex("settingID"));
				settingContent=c.getString(2);
				byte[] byte1=c.getBlob(3);
				try{
					settingMeaning=new String(byte1,"gbk");
					settingMeaning=settingMeaning.substring(0, settingMeaning.length()-2);
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				settingClass=c.getInt(3);
				Settingop t1=new Settingop(settingID,settingContent,settingMeaning,settingClass);
				list.add(t1);
			}
		}
		for(Settingop tt:list){
			System.out.println("查询打印---+++"+tt.getSettingID()+"--"+tt.getSettingContent()+"--"+tt.getSettingMeaning()+"--"+tt.getSettingClass());
		}
		c.close();
		db.close();
		return list;
	}
	public List<Settingop> querrysetting_display_interface(){
		list.clear();
		SQLiteDatabase db=createdatabase.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable + " where settingClass=? ",new String[] {4+""});
		int ps=(int)c.getCount();
		if(c.getCount()>0){
			for(;ps>0;ps--){
				c.moveToNext();
				sID=c.getLong(c.getColumnIndex("sID"));
				this.settingID=c.getLong(c.getColumnIndex("settingID"));
				settingContent=c.getString(2);
				byte[] byte1=c.getBlob(3);
				try{
					settingMeaning=new String(byte1,"gbk");
					settingMeaning=settingMeaning.substring(0, settingMeaning.length()-1);
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				settingClass=c.getInt(3);
				Settingop t1=new Settingop(settingID,settingContent,settingMeaning,settingClass);
				list.add(t1);
			}
		}
		for(Settingop tt:list){
			System.out.println("查询打印---+++"+tt.getSettingID()+"--"+tt.getSettingContent()+"--"+tt.getSettingMeaning()+"--"+tt.getSettingClass());
		}
		c.close();
		db.close();
		return list;
	}
	
	public long getsID() {
		return sID;
	}
	public void setsID(long sID) {
		this.sID = sID;
	}
	public long getSettingID() {
		return settingID;
	}
	public void setSettingID(long settingID) {
		this.settingID = settingID;
	}
	public String getSettingContent() {
		return settingContent;
	}
	public void setSettingContent(String settingContent) {
		this.settingContent = settingContent;
	}
	public String getSettingMeaning() {
		return settingMeaning;
	}
	public void setSettingMeaning(String settingMeaning) {
		this.settingMeaning = settingMeaning;
	}
	public int getSettingClass() {
		return settingClass;
	}
	public void setSettingClass(int settingClass) {
		this.settingClass = settingClass;
	}
}


