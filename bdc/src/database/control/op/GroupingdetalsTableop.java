package database.control.op;

import java.util.ArrayList;
import java.util.List;


import general.base.op.BaseSplit;
import general.base.op.SettingVariable;
import activity.control.course.WordGroupDetals_items;
import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;

public class GroupingdetalsTableop {
	DatabaseHelper helper=null;
	String opTable="GroupdetailTable";
	long GroupingID=-1;
	int UID;
	int WORDTableID;
	int GROUPID;
	String WordIDList;
	int TotalWordCount;
	String state;
	
	public GroupingdetalsTableop(long groupingID,int uID,int wORDTableID,
			int gROUPID,String wordIDList,int totalWordCount,String state){
		super();
		GroupingID=groupingID;
		UID=uID;
		WORDTableID=wORDTableID;
		GROUPID=gROUPID;
		WordIDList=wordIDList;
		TotalWordCount=totalWordCount;
		this.state=state;
	}
	public GroupingdetalsTableop(Context content){
		DataBaseBaseOp Dbop;
		Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(content);
		helper=Dbop.getHelper();
	}
	public int checkGroupInfo(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from " + opTable+ " where UID=? and "
				+"WORDTableID=? AND GROUPID=? ",
				new String[] { UID +"",WORDTableID + "",GROUPID+"" });
		int count=c.getCount();
		c.close();
		db.close();
		return count;
	}
	public int delete(String WORDTableID){
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="delete from StudyReviewTable where GroupingID in " + "(select GroupingID from "+opTable+" where WORDTableID="+WORDTableID+")";
		db.execSQL(sql);
		int count=db.delete(opTable,"WORDTableID=?",new String[]{WORDTableID+""});
		db.close();
		return count;
	}
	public int AddList(int begionWordID,int endWordID,int begingroupID,int groupSize,
			ImportWordTableop iwmt){
		String sql="";
		BaseSplit bs=new BaseSplit();
		if(begionWordID==1)
		{SQLiteDatabase db=helper.getWritableDatabase();
		sql="delete from StudyReviewTable where GroupingID in(select GroupingID from " +opTable+" where WORDTableID="+WORDTableID+" and UID="+UID+") ";
		db.execSQL(sql);
		int count=db.delete(opTable, "WORDTableID=? and UID=?",new String[] {WORDTableID+"",UID+""});
		}
		else{
			querryWordDbygroupingID(begingroupID-1);
			int findlocation=bs.bsfind(WordIDList, (begionWordID-1)+"");
			WordIDList=bs.trimletf(WordIDList, findlocation);
			sql="update "+opTable+" set WordIDList = '"+WordIDList+
					"',TotalWordCount="+findlocation+" where GroupingID = "+GroupingID;
			SQLiteDatabase db=helper.getWritableDatabase();
			db.execSQL(sql);
			sql="update StudyReviewTable set state='1',WordIDListTotal = '"+WordIDList+
					"' where GroupingID in (select GroupingID from "+opTable+ " where WORDTableID="+WORDTableID+" and UID="+UID+" and state='0') ";
			db.execSQL(sql);
			sql="delete from StudyReviewTable where GroupingID in(select GroupingID from "+opTable+" where WORDTableID="+WORDTableID+" and UID="+UID+" and GROUPID>="+begingroupID+") ";
			db.execSQL(sql);
			int count=db.delete(opTable, "WORDTableID=? and GROUPID>=? and UID=?",new String[]{WORDTableID+"",begingroupID+"",UID+""});
			db.close();			
		}		
		if(endWordID==-1)
		{endWordID=(int)(iwmt.getMaxWordID());}
		for(int i=begingroupID;;)
		{
			WordIDList=iwmt.querryWordIDList(i,groupSize,endWordID);
			GROUPID=begingroupID;
			TotalWordCount=bs.getlenth(WordIDList);
			GroupingID=-1;
			Add();
			i=Integer.parseInt(bs.getLast(WordIDList));
			if(i==endWordID)
				break;
			else{
				begingroupID++;
				i++;
			}}
		return 1;
	}
	public long Add(){
		int check=0;
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		if(check==0){
			if(GroupingID!=-1)
			values.put("GroupingID",GroupingID);
			values.put("UID",UID);
			values.put("WORDTableID",WORDTableID);
			values.put("GROUPID",GROUPID);
			values.put("WordIDList",WordIDList);
			values.put("TotalWordCount",TotalWordCount);
			GroupingID=db.insert(opTable, null, values);
		}
		db.close();
		return GroupingID;
	}
	public List<WordGroupDetals_items> querryGroupwordCount(int tableID,long uid){
		SQLiteDatabase db=helper.getReadableDatabase();
		List<WordGroupDetals_items> gdoplist=new ArrayList<WordGroupDetals_items>();
		int flagtemp=0;
		long gnumber1=0;
		int words1=0;
		String studies1="";
		long gnumber2=0;
		int words2=0;
		String studies2="";
		WordGroupDetals_items gdtoptemp1=new WordGroupDetals_items("组号","单词数",
				"已背数","组号","单词数","已背数");
		gdoplist.add(gdtoptemp1);
		Cursor c=db.rawQuery("select * from " + opTable
				+ " where WORDTableID=? and uid=?"
				+" order by GROUPID",new String[]{ tableID + "",uid+"" });
		if(c.getCount()>0){
			Cursor c1=db.rawQuery("select GroupingID,StudiedCount,state" +
					" from StudyReviewTable "+opTable+ " where studyreview=1",new String[]{ });
							while(c.moveToNext()){
								this.GroupingID=c.getLong(0);
								UID=c.getInt(1);
								WORDTableID=c.getInt(2);
								GROUPID=c.getInt(3);
								WordIDList=c.getString(4);
								TotalWordCount=c.getInt(5);
								int studiedcount=0;
								if(c1.getCount()>0)
								{
									c1.moveToFirst();
									do{
										long GroupingID1=c1.getLong(0);
										int StudiedCount=c1.getInt(1);
										String state=c1.getString(2);
										if(this.GroupingID==GroupingID1)
											if(state!=null&&state.trim().equals("")==false&&Integer.parseInt(state)>0)
											{
												studiedcount=TotalWordCount;
											}else{
												studiedcount=StudiedCount;
											}
									}while(c1.moveToNext());
								}
								if(flagtemp==0)
								{
									gnumber1=GROUPID;
									words1=TotalWordCount;
									studies1=studiedcount+"";
									flagtemp=1;
								}
								else{
									gnumber2=GROUPID;
									words2=TotalWordCount;
									studies2=studiedcount+"";
									flagtemp=3;
								}
								if(flagtemp==3)
								{
									WordGroupDetals_items gdtoptemp=new WordGroupDetals_items(gnumber1+"",
											words1+"",studies1+"",gnumber2+"",words2+"",studies2+"");
									gdoplist.add(gdtoptemp);
									flagtemp=0;
								}
							}
		if(flagtemp==1&&gnumber2==0){
			WordGroupDetals_items gdtoptemp=new WordGroupDetals_items(
					gnumber1+"", words1+"", studies1+"","","","");
			gdoplist.add(gdtoptemp);
			flagtemp=0;
		}}
	c.close();db.close();return gdoplist;
}
	public int delete(int uid){
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="delete from StudyReviewTable where GroupingID in " +
				"(select GroupingID from " +opTable+" where UID="+uid+")";
		db.execSQL(sql);
		int count=db.delete(opTable,"UID=?",new String[] {uid+""});
		db.close();
		return count;
	}
	public void querryWordDbygroupingID(int GroupingIDtemp)
	{
	SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select * from " + opTable
			+ " where WORDTableID=? and uid=? and GROUPID=? order by GROUPID",
			new String[]{ WORDTableID + "",UID+"",GroupingIDtemp+""});
	if(c.getCount()>0){
		c.moveToNext();
		this.GroupingID=c.getLong(0);
		UID=c.getInt(1);
		WORDTableID=c.getInt(2);
		GROUPID=c.getInt(3);
		WordIDList=c.getString(4);
		TotalWordCount=c.getInt(5);
	}
	else{
		this.GroupingID=-1;
	}
	c.close();
	db.close();
	}
	public void querryWordIDbygroupingID(String familiarity,Context context)
	{SettingVariable sv=new SettingVariable();
	SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select * from " + opTable + " where WORDTableID=? and uid=? and GroupingID=? order by GROUPID",new String[]{WORDTableID + "",UID+"" ,GroupingID+""});
	if(c.getCount()>0){
		c.moveToNext();
		this.GroupingID=c.getLong(0);
		UID=c.getInt(1);
		WORDTableID=c.getInt(2);
		GROUPID=c.getInt(3);
		WordIDList=c.getString(4);
		TotalWordCount=c.getInt(5);
		//GroupingdetalsTableop gdop=new GroupingdetalsTableop(this.GroupingID,UID,WORDTableID,GROUPID,WordIDList,TotalWordCount,state);
		if(familiarity.equals("")==false)
		{
			ImportWordTableop iwop=new ImportWordTableop(context);
			iwop.setOpTable(sv.getTableName());
			WordIDList=iwop.querrrybyidlist(WordIDList+"",familiarity);
		}
	}
	else {
		this.GroupingID=-1;
	}
	c.close();
	db.close();
	}
	public int checkstudiedwordcount(int tableID,long uid,int brouseop){
		BaseSplit bs=new BaseSplit();
		Log.i("bbbbbbbbbbbbbbbbbbbbb",tableID+" "+uid+"");
		int sum=0;String sumtempString="";
		int flag=1;
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=db.rawQuery("select GroupingID from " + opTable + " where WORDTableID=? and uid=? order by GROUPID",new String[]{tableID+"",uid+""});
		if(c.getCount()>0){
			while(c.moveToNext()){
				if(sumtempString.equals(""))sumtempString=c.getLong(0)+"";
				else sumtempString=sumtempString+","+c.getLong(0)+"";
			}}
		c.close();
		Log.i("fbbbbbbbbbbbbbbbbbbbbb",sumtempString+" "+tableID+" "+uid+" "+opTable);
		String strString="select GroupingID,WordIDListStudied,WordIDListTotal,state from StudyReviewTable " + " where GroupingID in ("+sumtempString+") order by GroupingID";
		Log.i("ffbbbbbbbbbbbbbbbbbbbbb",strString+" ");
		Cursor c1=db.rawQuery("select GroupingID,WordIDListStudied,WordIDListTotal,state from StudyReviewTable " + " where GroupingID in ("+sumtempString+") order by GroupingID",new String[]{});
		long GroupingIDtemp=0;
		int sumcount=0;
		int i=0;
		while(c1.moveToNext()){
			i=i+1;
			Log.i("lll+",c1.getCount()+"");
			this.GroupingID=c1.getLong(0);
			String WordIDListStudied=c1.getString(1);
			String WordIDListTotal=c1.getString(2);
			String state=c1.getString(3);
			int counttemp=0;
			if(state.trim().equals("")||state.trim().equals("0"))
			{counttemp=bs.getlenth(WordIDListStudied);
			if(i==c1.getCount()&&brouseop>1)counttemp=0;
			Log.i("kkjjjj1",i+"  "+brouseop+"  "+counttemp);
			}
			else {
				int statetemp=Integer.parseInt(state.trim());
				if(statetemp>=1)counttemp=bs.getlenth(WordIDListTotal);
			}
			if(GroupingIDtemp==0){
				sumcount=sumcount+counttemp;
				GroupingIDtemp=this.GroupingID;
			}
			else if(GroupingIDtemp!=this.GroupingID){
				sumcount=sumcount+counttemp;
				GroupingIDtemp=this.GroupingID;
			}
		}
		return sumcount;
	}
	public long getGroupingID() {
		return GroupingID;
	}
	public void setGroupingID(long groupingID) {
		GroupingID = groupingID;
	}
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public int getWORDTableID() {
		return WORDTableID;
	}
	public void setWORDTableID(int wORDTableID) {
		WORDTableID = wORDTableID;
	}
	public int getGROUPID() {
		return GROUPID;
	}
	public void setGROUPID(int gROUPID) {
		GROUPID = gROUPID;
	}
	public String getWordIDList() {
		return WordIDList;
	}
	public void setWordIDList(String wordIDList) {
		WordIDList = wordIDList;
	}
	public int getTotalWordCount() {
		return TotalWordCount;
	}
	public void setTotalWordCount(int totalWordCount) {
		TotalWordCount = totalWordCount;
	}
}
