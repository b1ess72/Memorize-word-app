package database.control.op;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;

public class kingsofttableop {
	DatabaseHelper helper=null;
	String opTable="KingsoftWordTable";
	long WordID=-1;
	String WordName;
	String PhonogramE;
	String PhonogramA;
	String WordMeaning;
	String PronunciationE;
	String PronunciationA;
	String ExampleSentence;
	String SentenceMeaning;
	String DownLoadTime;
	String PronunciationPathE;
	String PronunciationPathA;
	
	public kingsofttableop(Context content){
		DataBaseBaseOp Dbop;
		Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(content);
		helper=Dbop.getHelper();
	}
	public long Add(){
		int check=checkName();
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		if(check==0){
			if(WordID!=-1)
				values.put("WordID",WordID);
			values.put("WordName",WordName);
			values.put("PhonogramE",PhonogramE);
			values.put("PhonogramA",PhonogramA);
			values.put("WordMeaning",WordMeaning);
			values.put("PronunciationE",PronunciationE);
			values.put("PronunciationA",PronunciationA);
			values.put("ExampleSentence",ExampleSentence);
			values.put("SentenceMeaning",SentenceMeaning);
			values.put("DownLoadTime",DownLoadTime + "");
			WordID=db.insert(opTable, null, values);
		}
		db.close();
		return WordID;
	}
	public int checkName(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable + " where WordName=? ",
				new String[] { WordName + "" });
		int count=c.getCount();
		c.close();
		db.close();
		return count;
	}
	public int delete(){
		SQLiteDatabase db=helper.getWritableDatabase();
		int count=db.delete(opTable, "WordName=?",new String[]{ WordName+"" });
		db.close();
		return count;
	}
	public int editPronunciationPath(){
		int count=0;
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		if(PronunciationPathE.equals("")==false)values.put("PronunciationPathE",PronunciationPathE);
		if(PronunciationPathA.equals("")==false)values.put("PronunciationPathA",PronunciationPathA);
		Log.i("sssssssssssss",PronunciationA+"         ffffffffffffff"
				+PronunciationPathE+  "    "+WordName);
		if(PronunciationPathE.equals("")==false||PronunciationPathA.equals("")==false)
		{
			count=db.update(opTable, values,"WordName=?",new String[]{ WordName+"" });
			db.close();
		}
		return count;
	}
	public int querrybyname(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " +opTable + " where WordName=? ",
				new String[]{WordName +"" });
		if(c.getCount()>0){
			c.moveToNext();
			this.WordID=c.getLong(0);
			WordName=c.getString(1);
			PhonogramE=c.getString(2);
			PhonogramA=c.getString(3);
			WordMeaning=c.getString(4);
			PronunciationE=c.getString(5);
			PronunciationA=c.getString(6);
			ExampleSentence=c.getString(7);
			SentenceMeaning=c.getString(8);
			DownLoadTime=c.getString(9);
			PronunciationPathE=c.getString(10);
			PronunciationPathA=c.getString(11);
		}
		c.close();
		db.close();
		return (int)WordID;
	}
	public int edit(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("PhonogramE",PhonogramE);
		values.put("PhonogramA",PhonogramA);
		values.put("WordMeaning",WordMeaning);
		values.put("PronunciationE",PronunciationE);
		values.put("PronunciationA",PronunciationA);
		values.put("ExampleSentence",ExampleSentence);
		values.put("SentenceMeaning",SentenceMeaning);
		int count=db.update(opTable, values,"WordName=?",
				new String[]{ WordName + "" });
		db.close();
		return count;
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
	public String getPhonogramE() {
		return PhonogramE;
	}
	public void setPhonogramE(String phonogramE) {
		PhonogramE = phonogramE;
	}
	public String getPhonogramA() {
		return PhonogramA;
	}
	public void setPhonogramA(String phonogramA) {
		PhonogramA = phonogramA;
	}
	public String getWordMeaning() {
		return WordMeaning;
	}
	public void setWordMeaning(String wordMeaning) {
		WordMeaning = wordMeaning;
	}
	public String getPronunciationE() {
		return PronunciationE;
	}
	public void setPronunciationE(String pronunciationE) {
		PronunciationE = pronunciationE;
	}
	public String getPronunciationA() {
		return PronunciationA;
	}
	public void setPronunciationA(String pronunciationA) {
		PronunciationA = pronunciationA;
	}
	public String getExampleSentence() {
		return ExampleSentence;
	}
	public void setExampleSentence(String exampleSentence) {
		ExampleSentence = exampleSentence;
	}
	public String getSentenceMeaning() {
		return SentenceMeaning;
	}
	public void setSentenceMeaning(String sentenceMeaning) {
		SentenceMeaning = sentenceMeaning;
	}
	public String getDownLoadTime() {
		return DownLoadTime;
	}
	public void setDownLoadTime(String downLoadTime) {
		DownLoadTime = downLoadTime;
	}
	public String getPronunciationPathE() {
		return PronunciationPathE;
	}
	public void setPronunciationPathE(String pronunciationPathE) {
		PronunciationPathE = pronunciationPathE;
	}
	public String getPronunciationPathA() {
		return PronunciationPathA;
	}
	public void setPronunciationPathA(String pronunciationPathA) {
		PronunciationPathA = pronunciationPathA;
	}
}


