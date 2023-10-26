package database.control.op;

import java.util.ArrayList;
import java.util.List;

import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;
import general.base.op.DateTimeOp;
import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class WordtotalTableop {
	DatabaseHelper helper=null;
	String opTable="WordtotalTable";
	long TableID=-1;
	String TableName;
	int TotalWord;
	String LeadInTime;
	int GroupCount;
	int StudiedWordCount;
	int StudiedGroupCount;
	int UID;
	String TableNameChina;
	
	public WordtotalTableop(Context context){
		DataBaseBaseOp Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(context);
		helper=Dbop.getHelper();
	}
	public long Add(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		if(TableID!=-1)
		values.put("TableID",TableID);
		values.put("TableName",TableName);
		values.put("TotalWord",TotalWord);
		values.put("LeadInTime",LeadInTime);
		values.put("GroupCount",GroupCount);
		values.put("StudiedWordCount",StudiedWordCount);
		values.put("StudiedGroupCount",StudiedGroupCount);
		values.put("UID",UID);
		values.put("TableNameChina",TableNameChina);
		TableID=db.insert(opTable, null, values);
		db.close();
		return TableID;
	}
	public String createTable(){
		SQLiteDatabase db=helper.getWritableDatabase();
		DateTimeOp dtop=new DateTimeOp();
		String opTablet="iwt"+dtop.getDateTimeNowTable();
		String sql="create table "+opTablet+" ("
				+"WordID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
				+"WordName     varchar(10) NOT NULL,"
				+"Phonogram    varchar(15),"
				+"WordMeaning     varchar(500),"
				+"Familiarity      varchar(15)"
				+")";
		db.execSQL(sql);
		return opTablet;
	}
	public String getTableNamebyTableNameChina(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select TableName from " + opTable
				+ " where TableNameChina=? ", new String[]{TableNameChina + ""});
		if(c.getCount()>0)
		{ c.moveToNext();
		TableName=c.getString(0);
		}
		c.close();
		db.close();
		return TableName;
	}
	public int deleteTableInfo(){
		String opTable1="";
		opTable1=getTableNamebyTableNameChina();
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="drop table "+opTable+"  ";
		db.execSQL(sql);
		int count=db.delete(opTable, "TableName=?", new String[]{ TableName + 
				""});
		db.close();
		return 1;
	}
	public int delete(int uid){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select TableName from " + opTable + " where UID=? ",new String[]{uid+""});
		if(c.getCount()>0){
			while(c.moveToNext())
			{
				this.TableName=c.getString(0);
				deleteTableInfo();
			}
		}
		c.close();
		db.close();
		return 0;
	}
	/*public int editWordCount(){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("TotalWord",TotalWord);
		int count=db.update(opTable, values, "TableName=?",
				new String[]{ TableName + ""});
		db.close();
		return count;
	}*/
	public int checkTableNameChina(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select TableNameChina from " + opTable
				+ " where TableNameChina=? ",new String[]{ TableNameChina + "" });
		int count=c.getCount();
		c.close();
		db.close();
		return count;
	}
	public void querrybyTablename(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable
				+ " where TableName=? ",new String[] {TableName +"" });
		if(c.getCount()>0){
			c.moveToNext();
			this.TableID=c.getLong(0);
			TableName=c.getString(1);
			TotalWord=c.getInt(2);
			LeadInTime=c.getString(3);
			GroupCount=c.getInt(4);
			StudiedWordCount=c.getInt(5);
			StudiedGroupCount=c.getInt(6);
			UID=c.getInt(7);
			TableNameChina=c.getString(8);
		}
		c.close();
		db.close();
	}
List<int[]> list2=null;
public List<String> querrycourseList(){
	SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select TableID,TableNameChina from " + opTable
			+ " where UID=? order by TableNameChina",new String[]{UID+""});
	List<String> list1=new ArrayList<String>();
	list2=new ArrayList<int[]>();
	if(c.getCount()>0){
		while(c.moveToNext()){
			this.TableID=c.getLong(0);
			int t=(int)this.TableID;
			TableNameChina=c.getString(1);
			list1.add(TableNameChina);
			int[] a=new int[1];
			a[0]=t;
			list2.add(a);
		}}
	c.close();
	db.close();
	return list1;
}
public void querrybyTableID(){
	SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select * from "+opTable
			+ " where TableID=? ",new String[]{ TableID+"" });
	if(c.getCount()>0){
		c.moveToNext();
		this.TableID=c.getLong(0);
		TableName=c.getString(1);
		TotalWord=c.getInt(2);
		LeadInTime=c.getString(3);
		GroupCount=c.getInt(4);
		StudiedWordCount=c.getInt(5);
		StudiedGroupCount=c.getInt(6);
		UID=c.getInt(7);
		TableNameChina=c.getString(8);
	}
	c.close();
	db.close();
}
public int editWordCount(int n)
{
	SQLiteDatabase db=helper.getWritableDatabase();
	String sql="update " + opTable+" set TotalWord = TotalWord "
			+"+"+(n)+" where TableName='"+TableName+"'";
	db.execSQL(sql);
	return 1;
}
public int editGroup(){
	SQLiteDatabase db=helper.getWritableDatabase();
	ContentValues values=new ContentValues();
	values.put("TotalWord",TotalWord);
	values.put("GroupCount",GroupCount);
	values.put("StudiedWordCount",StudiedWordCount);
	values.put("StudiedGroupCount",StudiedGroupCount);
	int count=db.update(opTable, values,"TableID=?",
			new String[] { TableID + "" });
	db.close();
	return count;
}
public int editStudiedWordCount(){
	SQLiteDatabase db=helper.getWritableDatabase();
	ContentValues values=new ContentValues();
	values.put("StudiedWordCount",StudiedWordCount);
	int count=db.update(opTable, values,"TableID=?",new String[]{TableID+""});
	db.close();
	return count;
}
public void addStudiedWordCount(int n, String tablename, int uid) {
	// TODO Auto-generated method stub
	if(n!=0)
	{
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select TableID,StudiedWordCount from " + opTable + " where UID=? and tablename=? order by TableNameChina",new String[]{uid+"",tablename+""});
		Log.i("dhd1","aa");
		if(c.getCount()>0){
			c.moveToNext();
			this.TableID=c.getLong(0);
			StudiedWordCount=c.getInt(1)+n;
		}
		c.close();
		db.close();
	}
	SQLiteDatabase db1=helper.getWritableDatabase();
	ContentValues values=new ContentValues();
	values.put("StudiedWordCount",StudiedWordCount);
	int count=db1.update(opTable, values, "TableID=?",new String[]{TableID+""});
	db1.close();
}
public long getTableID() {
		return TableID;
	}
	public void setTableID(long tableID) {
		TableID = tableID;
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	public int getTotalWord() {
		return TotalWord;
	}
	public void setTotalWord(int totalWord) {
		TotalWord = totalWord;
	}
	public String getLeadInTime() {
		return LeadInTime;
	}
	public void setLeadInTime(String leadInTime) {
		LeadInTime = leadInTime;
	}
	public int getGroupCount() {
		return GroupCount;
	}
	public void setGroupCount(int groupCount) {
		GroupCount = groupCount;
	}
	public int getStudiedWordCount() {
		return StudiedWordCount;
	}
	public void setStudiedWordCount(int studiedWordCount) {
		StudiedWordCount = studiedWordCount;
	}
	public int getStudiedGroupCount() {
		return StudiedGroupCount;
	}
	public void setStudiedGroupCount(int studiedGroupCount) {
		StudiedGroupCount = studiedGroupCount;
	}
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public String getTableNameChina() {
		return TableNameChina;
	}
	public void setTableNameChina(String tableNameChina) {
		TableNameChina = tableNameChina;
	}
	public List<int[]> getList2() {
		return list2;
	}
	public void setList2(List<int[]> list2) {
		this.list2 = list2;
	}
	public void edittablecount(int i) {
		// TODO Auto-generated method stub
		
	}
}
