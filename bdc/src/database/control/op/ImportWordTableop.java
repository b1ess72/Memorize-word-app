package database.control.op;

import general.base.op.BaseSplit;

import java.util.ArrayList;
import java.util.List;

import wgy.recitewords.bdc.R.string;

//import Intnet.importword.kingsoft.DownloadKingsoft;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;

public class ImportWordTableop {
	DatabaseHelper helper=null;
	String opTable="ImportWordTable";
	long WordID=-1;
	String WordName;
	String Phonogram;
	String PhonogramE;
	String PhonogramA;
	String WordMeaning;
	String Familiarity;
	String WordIDString;
	String WordMeaningkingsoft;
	String PronunciationE;
	String PronunciationA;
	String ExampleSentence;
	String DownLoadTime;
	String PronunciationPathE;
	String PronunciationPathA;
	
	List<ImportWordTableop> list=new ArrayList<ImportWordTableop>();
	public ImportWordTableop(Context context){
		DataBaseBaseOp Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(context);
		helper=Dbop.getHelper();
	}
	public long Add(){
			SQLiteDatabase db=helper.getWritableDatabase();
			ContentValues values=new ContentValues();
			if(WordID!=-1)
			values.put("WordID",WordID);
			values.put("WordName",WordName);
			values.put("Phonogram",Phonogram);
			values.put("WordMeaning",WordMeaning);
			values.put("Familiarity",Familiarity);
			WordID=db.insert(opTable, null, values);
			db.close();
			return WordID;
		}
	public int edit(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("WordName",WordName);
		values.put("Phonogram",Phonogram);
		values.put("WordMeaning",WordMeaning);
		values.put("Familiarity",Familiarity);
		int count=db.update(opTable, values, "WordID=?",
				new String[]{ WordID +"" });
		db.close();
		return count;
	}
	public int checkWordName(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select WordID,WordName from " + opTable
				+ " where WordName=? ",new String[]{ WordName+"" });
		int count=c.getCount();
		if(count>0)
		{
			c.moveToNext();
			this.WordID=c.getLong(0);
		}
		else{
			this.WordID=-1;
		}
		c.close();
		db.close();
		return (int)(this.WordID);
	}
	public void querrybyname(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable
				+ " where WordName=? ", new String[] { WordName + ""});
		this.WordID=-1;
		if(c.getCount()>0){
			c.moveToNext();
			this.WordID=c.getLong(0);
			WordName=c.getString(1);
			Phonogram=c.getString(2);
			WordMeaning=c.getString(3);
			Familiarity=c.getString(4);
		}
		c.close();
		db.close();
	}
	public int delete(){
		SQLiteDatabase db=helper.getWritableDatabase();
		int count=db.delete(opTable,"WordID=?",new String[]{ WordID+"" });
		db.close();
		return count;
	}
	public long getMaxWordID(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select WordID from " + opTable
				+ " order by WordID desc limit 0,1", new String[] { });
		int count=c.getCount();
		if(count>0)
		{ c.moveToNext();
		WordID=c.getLong(0);
		}
		else{WordID=-1;
		}
		c.close();
		db.close();
		return WordID;
	}
	public String querryWordIDList(int begin,int n,int end){
		SQLiteDatabase db=helper.getReadableDatabase();
		int end1=n;
		String strTempString="";
		Cursor c=db.rawQuery("select WordID from " + opTable+ " where WordID>=? "
				+" order by WordID limit 0,? ",new String[] { begin+"" ,end1+"" });
		if(c.getCount()>0){
			while(c.moveToNext()){
				this.WordID=c.getLong(0);
				if(this.WordID<=end){
					if(strTempString.equals(""))
						strTempString=(int)this.WordID+"";
					else 
						strTempString=strTempString+"#"+(int)(this.WordID)+"";
				}}}
		c.close();
		db.close();
		return strTempString;
	}
	public List<ImportWordTableop> querrybyWordList(String str){
		SQLiteDatabase db=helper.getReadableDatabase();
		int count=0;
		Cursor c=null;
		BaseSplit bSplit=new BaseSplit();
		bSplit.Split_temp(str, '#');
		String[] str_temp=bSplit.getStr_temp();
		str="";
		for(int i=0;i<str_temp.length;i++)
			if(str.equals(""))str=str_temp[i];
			else 
				str+=","+str_temp[i];
		String sqlString="select * from " + opTable + " where WordID in ("+str+")   ";
		Log.i("hhhjhjhj",sqlString);
		c=db.rawQuery("select * from " + opTable + " where WordID in ("+str+")  ",new String[] {   });
		if(c.getCount()>0){
			while(c.moveToNext())
			{  WordIDString="±àºÅ£º"+c.getLong(0);
			WordName="µ¥´Ê£º"+c.getString(1);
			Phonogram="Òô±ê£º"+c.getString(2);
			WordMeaning="½âÊÍ£º"+c.getString(3);
			Familiarity=c.getString(4);
			Log.i("ddeee",WordIDString+WordName+Phonogram+WordMeaning+Familiarity+"");
			list.add(new ImportWordTableop(WordIDString,WordName,Phonogram,WordMeaning,Familiarity));
			}
		}
		else 
			Log.i("ddeee","jjj");
		c.close();
		db.close();
		return list;
	}
	public ImportWordTableop(String WordIDString,String wordName,String phonogram,String wordMeaning,String familiarity){
		WordName=wordName;
		Phonogram=phonogram;
		WordMeaning=wordMeaning;
		Familiarity=familiarity;
		this.WordIDString=WordIDString;
	}
	public String querrrybyidlist(String WORDTableID,String familiarity){
		String tempstrString="";
		SQLiteDatabase db=helper.getReadableDatabase();
		BaseSplit bSplit=new BaseSplit();
		WORDTableID=bSplit.replaceCode(WORDTableID,'#',',');
		String ffString="select WordID from " + opTable + " where Familiarity='"+ familiarity +"' and WordID in ("+WORDTableID+") order by WordID ";
		Log.i("ss",ffString);
		Cursor c1=db.rawQuery(ffString, new String[] { });
		Log.i("111",c1.getCount()+"");
		if(c1.getCount()>0){
			while(c1.moveToNext())
			{
				this.WordID=c1.getLong(0);
				if(tempstrString.equals(""))tempstrString=WordID+"";
				else {
					tempstrString+="#"+WordID;
				}
			}
		}
		Log.i("111","23");
		return tempstrString;
	}
	public int editfamiliarity(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("Familiarity",Familiarity);
		int count=db.update(opTable, values, "WordID=?",new String[] {WordID+" "});
		db.close();
		return count;
	}
	public int querrybyWordIDFirst(long wordIDtemp) {
		// TODO Auto-generated method stub
		int flag=1;
		Cursor c=null;
		SQLiteDatabase db=helper.getReadableDatabase();
		BaseSplit bSplit=new BaseSplit();
		String str=""+wordIDtemp;
		String sqlString="select " + opTable+".WordID,"+ opTable+".WordName,Phonogram,"+ opTable+".WordMeaning,Familiarity,PhonogrsmE,PhonogramA,KingsoftTable.WordMeaning,PronunciationE,PronunciationA,ExampleSentence,SentenceMeaning,DownLoadTime,PronunciationPathE,PronunciationPathA from " + opTable
				+",KingsoftTable " + " where " + opTable+".WordID in ("+str+") and " + opTable+".WordName=KingsoftTable.WordName order by " +opTable+".WordID ";
		Log.i("hhhjhjhj",sqlString);
		c=db.rawQuery("select " + opTable+".WordID," + opTable+".WordName,Phonogram,"+ opTable+".WordMeaning,Familiarity,PhonogramE,PhonogramA,KingsoftTable.WordMeaning,"+"PronunciationE,PronunciationA,ExampleSentence,SentenceMeaning,DownLoadTime,PronunciationPathE,PronunciationPathA from " +opTable+",KingsoftTable " + " where "
		+ opTable+".WordID in ("+str+") and " + opTable+".WordName=KingsoftTable.WordName order by " + opTable+".WordID ", new String[]{  });
		c=db.rawQuery("select WordID,WordName,Phonogram,WordMeaning,Familiarity from " + opTable+" "+ " where WordID in ("+str+") order by " +opTable+".WordID ",new String[] { });
		if(c.getCount()>0){
			c.moveToNext();
			WordID=c.getLong(0);
			WordName=c.getString(1);
			Phonogram=c.getString(2);
			WordMeaning=c.getString(3);
			Familiarity=c.getString(4);
			Log.i("ddeee",WordID+WordName+Phonogram+WordMeaning+Familiarity+" "+DownLoadTime+" "+ PronunciationPathE+ " "+PronunciationPathA);
		}
		else {
			Log.i("ddeee","jjj");
			WordID=-1;
			flag=2;
		}
		c.close();
		if(flag==2)
		{
			c=db.rawQuery("select * from " + opTable+""
		+ " where WordID in ("+str+") order by WordID",new String[]{  });
			if(c.getCount()>0){
				c.moveToNext();
				WordID=c.getLong(0);
				WordName=c.getString(1);
				Phonogram=c.getString(2);
				WordMeaning=c.getString(3);
				Familiarity=c.getString(4);
				PhonogramE="";
				PhonogramA="";
				WordMeaningkingsoft="";
				PronunciationE="";
				PronunciationA="";
				ExampleSentence="";
				DownLoadTime="";
				PronunciationPathE="";
				PronunciationPathA="";
				Log.i("ddeee",WordID+WordName+Phonogram+WordMeaning+Familiarity+"");
			}
			else {
				Log.i("ddeee","jjj");
				WordID=-1;
				flag=3;
			}
			c.close();
		}
		db.close();
		return flag;
	}
	public String getOpTable() {
		return opTable;
	}
	public void setOpTable(String opTable) {
		this.opTable = opTable;
	}
	public long getWordID() {
		return WordID;
	}
	public void setWordID(long wordID) {
		WordID = wordID;
	}
	public String getWordName() {
		return WordName;
	}
	public void setWordName(String wordName) {
		WordName = wordName;
	}
	public String getPhonogram() {
		return Phonogram;
	}
	public void setPhonogram(String phonogram) {
		Phonogram = phonogram;
	}
	public String getWordMeaning() {
		return WordMeaning;
	}
	public void setWordMeaning(String wordMeaning) {
		WordMeaning = wordMeaning;
	}
	public String getFamiliarity() {
		return Familiarity;
	}
	public void setFamiliarity(String familiarity) {
		Familiarity = familiarity;
	}
	public String getWordIDString() {
		return WordIDString;
	}
	public void setWordIDString(String wordIDString) {
		WordIDString = wordIDString;
	}
}

	/*public String querrybyidlist(String string, String familiarity2) {
		// TODO Auto-generated method stub
		return null;
	}*/


