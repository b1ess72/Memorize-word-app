package database.control.op;

import general.base.op.BaseSplit;
import general.base.op.DateTimeOp;
import general.base.op.GetReviewDate;
import general.base.op.SettingVariable;

import java.util.ArrayList;
import java.util.List;

import database.base.op.DataBaseBaseOp;
import database.base.op.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StudyReviewTableop {
	DatabaseHelper helper=null;
	String opTable="StudyReviewTable";
	
	long StudyReviewID=-1;
	int GroupingID;
	int WORDTableID;
	String studyreview;
	int TrueCount;
	int StudiedCount;
	String WordIDListStudied="1";
	String WordIDListTotal="";
	String state="";
	String studyreviewtime="";
	List<String> listt=new ArrayList<String>();
	int GroupID;
	
	ArrayList<StudyReviewTableop> listrecommend=new ArrayList<StudyReviewTableop>();
	List<StudyReviewTableop> list=null;
	
	public StudyReviewTableop(Context content){
		DataBaseBaseOp Dbop;
		Dbop=new DataBaseBaseOp();
		Dbop.initsqlite3(content);
		helper=Dbop.getHelper();
	}	
	public StudyReviewTableop(int groupingID,int groupID,int studiedCount,String
			wordIDListTotal,String wordIDListStudied,String state){
		super();
		GroupingID=groupingID;
		GroupID=groupID;
		StudiedCount=studiedCount;
		WordIDListTotal=wordIDListTotal;
		WordIDListStudied=wordIDListStudied;
		this.state=state;
	}	
	public List<StudyReviewTableop> querrystudiedGroupdetailsbytableID(
			int WORDTableID,long uid){
		int flag=1;
		int count=0;
		BaseSplit bs=new BaseSplit();
		SQLiteDatabase db=helper.getReadableDatabase();
		List<StudyReviewTableop> gdopList=new ArrayList<StudyReviewTableop>();
		Cursor c=db.rawQuery("select GroupdetailTable,GroupingID,GroupID,StudiedCount,WordIDListTotal,WordIDListStudied,state from " 
		+ opTable +",GroupdetailTable"+" where UID=? and GroupdetailTable.WORDTableID=? "+
		" and "+ opTable +".GroupingID=GroupdetailTable.GroupingID and "+
		"studyreview is not '0' order by GroupdetailTable.GROUPID asc",
		new String[]{ uid +"",WORDTableID+""});
		if(c.getCount()>0){
			while(c.moveToNext())
			{GroupingID=c.getInt(0);
			int GroupID=c.getInt(1);
			StudiedCount=c.getInt(2);
			WordIDListTotal=c.getString(3);
			WordIDListStudied=c.getString(4);
			state=c.getString(5);
			int tatolCount=bs.getlenth(WordIDListTotal);
			StudyReviewTableop srtop=new StudyReviewTableop(GroupingID,GroupID,
					StudiedCount,WordIDListTotal,WordIDListStudied,state);
			gdopList.add(srtop);
			}}
		c.close();
		db.close();
		return gdopList;
	}
	public List<String> getStudiedGroupingList(long uid)
	{
		List<String> list=new ArrayList<String>();
		SQLiteDatabase db=helper.getReadableDatabase();
		Log.i("iiiiiiiii","select GroupdetailTable.GroupingID,GroupdetailTable.GROUPID,state from " + opTable + ",GroupdetailTable"+ " where UID=? and GroupdetailTable.WORDTableID=? and studyreview='1' and " + opTable +".GroupingID=GroupdetailTable.GroupingID order by GroupdetailTable.GROUPID asc "+uid + ","+WORDTableID+"");
		Cursor c=db.rawQuery("select GroupdetailTable.GroupingID,GroupdetailTable.GROUPID,state from " + opTable +",GroupdetailTable"+ " where UID=? and GroupdetailTable.WORDTableID=? and studyreview='1' and " +opTable +".GroupingID=GroupdetailTable.GroupingID order by GroupdetailTable.GROUPID asc",new String[] {uid+"",WORDTableID+""});
		Log.i("iiiiiiiiii","ss");
		if(c.getCount()>0){
			Log.i("iiiillllllllllllll","ss");
			while(c.moveToNext())
			{
				GroupingID=c.getInt(0);
				GroupID=c.getInt(1);
				state=c.getString(2);
				String a=GroupingID+"";
				String b=GroupID+"";
				if(Integer.parseInt(state)>=1)
				{listt.add(a);
				list.add(b);
				}
			}
		}
		c.close();
		db.close();
		return list;
	}
	public List<String> getunStudiedGroupingList(long uid)
	{
		List<String> list=new ArrayList<String>();
		SQLiteDatabase db=helper.getReadableDatabase();
		String sqlstringString="select GroupdetailTable.GroupingID from GroupdetailTable where UID=? and WORDTableID=? and GroupingID not in (select GroupdetailTable.GroupingID from " + opTable +",GroupdetailTable" + " where UID=? and GroupdetailTable.WORDTableID=? and state='1' and studyreview='1' and " + opTable +".GroupingID=GroupdetailTable.GroupingID order by GroupdetailTable.GROUPID asc) order by GROUPID asc"+ " "+ uid +","+WORDTableID+","+uid + ","+WORDTableID+"";
		Log.i("sss",sqlstringString);
		Cursor c=db.rawQuery("select GroupdetailTable.GroupingID,GroupdetailTable.GROUPID from GroupdetailTable where UID=? and WORDTableID=? and GroupingID not in (select GroupdetailTable.GroupingID from "+ opTable +",GroupdetailTable"+" where UID=? and GroupdetailTable.WORDTableID=? and state is not '0' and studyreview='1' and " + opTable+".GroupingID=GroupdetailTable.GroupingID order by GroupdetailTable.GROUPID asc) order by GROUPID asc",new String[] { uid+"",WORDTableID+"",uid+"",WORDTableID+""});
		Log.i("iiii","ss");
		if(c.getCount()>0){
			Log.i("iiiil","ss");
			while(c.moveToNext())
			{
				GroupingID=c.getInt(0);
				String a=GroupingID+"";
				GroupID=c.getInt(1);
				String b=GroupID+"";
				listt.add(a);
				list.add(b);
			}
		}
		c.close();
		db.close();
		return list;
	}
	public ArrayList<StudyReviewTableop> getRecommendReviewGroupingIDList(long uid)
	{
		GetReviewDate grd=new GetReviewDate();
		grd.getReviewDate(20);
		String[] getdate=grd.getDateList();
		String tempstrString="";
		SQLiteDatabase db=helper.getReadableDatabase();
		for(int i=0;i<getdate.length;i++)
		{
			if(getdate[i]==null)continue;
			if(tempstrString.equals(""))tempstrString="('"+getdate[i]+"'";
			else {
				tempstrString+=","+"'"+getdate[i]+"'";
			}
		}
		tempstrString+=")";
		String hhString="select StudyReviewID,GroupdetailTable.GroupingID,date(studyreviewtime),GroupID from " + opTable+",GroupdetailTable" + " where UID="+ uid + " and GroupdetailTable.WORDTableID="+WORDTableID+" and state is not '0' and " +opTable
				+".GroupingID=GroupdetailTable.GroupingID and studyreview='1' and date(studyreviewtime) in "+tempstrString+" order by GroupdetailTable.GROUPID asc";
		Log.i("dddddd",hhString);
		Cursor c=db.rawQuery(hhString,new String[]{    });
		if(c.getCount()>0){
			while(c.moveToNext())
			{this.StudyReviewID=c.getLong(0);
			GroupingID=c.getInt(1);
			Log.i("aaa","kkkk1 "+GroupingID);
			studyreviewtime=c.getString(2);
			Log.i("aaa","kkkk1 "+studyreviewtime);
			GroupID=(int)c.getLong(3);
			Log.i("aaa","kkkk1 "+GroupID);
			listrecommend.add(new StudyReviewTableop(StudyReviewID,GroupingID,studyreviewtime,GroupID));
			Log.i("aaa","kkkk4 "+"ff");
			}
		}
		c.close();
		db.close();
		return listrecommend;
	}
	public StudyReviewTableop(long studyReviewID,int groupingID,String studyreviewtime,int groupID){
		super();
		StudyReviewID=studyReviewID;
		GroupingID=groupingID;
		this.studyreviewtime=studyreviewtime;
		GroupID=groupID;
	}
	public long getTodayContentID(long uid)
	{
		int flag=1;
		SQLiteDatabase db=helper.getReadableDatabase();
		String hh="select GroupdetailTable.GroupingID,state from " + opTable +",GroupdetailTable " + " where UID="+uid + " and GroupdetailTable.WORDTableID="+WORDTableID+" and " + opTable +".GroupingID=GroupdetailTable.GroupingID order by GroupdetailTable.GROUPID asc";
		Cursor c=db.rawQuery(hh,new String[] {  });
		if(c.getCount()>0){
			while(c.moveToNext())
			{GroupingID=c.getInt(0);
			state=c.getString(1);
			Log.i("ffffffffff1",GroupingID+" "+flag+" "+state);
			if(state.equals("")==true||Integer.parseInt(state)<1)
			{flag=0;
			break;
			}else{
				continue;
			}
			}
		}
		else {
			GroupingID=1;
			state="0";
		}
		Log.i("ffffffffff",GroupingID+"  "+flag+hh);
		c.close();
		if(flag==1){
			hh="select GroupingID from GroupdetailTable"+" where UID="+ uid + " and WORDTableID="+WORDTableID +" and GroupingID >"+GroupingID+" order by GROUPID asc";
			Cursor c1=db.rawQuery(hh,new String[] {});
			Log.i("ffffffffff",GroupingID+" "+hh+"  "+c1.getCount());
			if(c1.getCount()>0){
				Log.i("ffffffffff",GroupingID+"  "+flag);
				c1.moveToNext();
				GroupingID=c1.getInt(0);
			}
			c1.close();
		}
		db.close();
		Log.i("ffffffffff",GroupingID+"  "+hh);
		return GroupingID;
	}
	public int getBrouseWordID(long StudyReviewID,long currentID,int flagop,boolean flagcircle,String strwordlist){
		int locationlocal=0;
		Log.i("1aaaaa",currentID+"  "+locationlocal+" "+WordIDListStudied+" "+strwordlist);
		BaseSplit bs=new BaseSplit();
		SQLiteDatabase db=helper.getReadableDatabase();
		Log.i("2aaaaa",currentID+" "+locationlocal+" "+WordIDListStudied+" "+strwordlist);
		Cursor c=db.rawQuery("select WordIDListStudied, WordIDListTotal,state from " + opTable +" "+ " where StudyReviewID=? ",new String[] {StudyReviewID +""});
		Log.i("3aaaaa",currentID+" "+locationlocal+" "+WordIDListStudied+" "+strwordlist);
		if(c.getCount()>0){
			Log.i("4aaaaa",currentID+" "+locationlocal+" "+WordIDListStudied+" "+strwordlist);
			c.moveToNext();
			{
				WordIDListTotal=c.getString(1);
				WordIDListStudied=c.getString(0);
				if(!strwordlist.equals(""))WordIDListTotal=strwordlist;
				state=c.getString(2);
			}}
		else {
			Log.i("5aaaaa",currentID+"  "+locationlocal+" "+WordIDListStudied+"  "+strwordlist);
			return -1;
		}
		c.close();
		db.close();
		Log.i("5dddddddddddddddd",currentID+"  "+locationlocal+" "+WordIDListStudied+"  "+strwordlist);
		if(WordIDListTotal.equals(WordIDListStudied)&&flagop==1&&currentID==Integer.parseInt(bs.getLast(WordIDListTotal)))
			editWordIDListStudied("",StudyReviewID);
		locationlocal=findsubString(currentID,flagop,flagcircle,WordIDListTotal);
		Log.i("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",currentID+" "+locationlocal+" "+WordIDListStudied+" "+strwordlist);
		return locationlocal;
	}
	public int findsubString(long currentID,int flagop,boolean flagcircle,String WordIDListTotal)
	{
		int lWordIDListTotal;
		int t=0;
		BaseSplit bs=new BaseSplit();
		bs.Split_temp(WordIDListTotal,'#');
		String[] str_WordIDListTotal=bs.getStr_temp();
		lWordIDListTotal=str_WordIDListTotal.length;
		int i=0;
		if(currentID==-1)
		{
			t=Integer.parseInt(str_WordIDListTotal[0]);
		}
		else {
			for(;i<lWordIDListTotal;i++)
			{
				if(str_WordIDListTotal[i].equals(currentID+""))break;
			}
			if(flagop==1)
			{
				if(i+1>=lWordIDListTotal)
				{
					if(flagcircle)
					{
						t=Integer.parseInt(str_WordIDListTotal[0]);
					}
					else 
						t=-1;
				}
				else{
					t=Integer.parseInt(str_WordIDListTotal[i+1]);
				}
			}
			else {
				if(i-1<0)
				{
					if(flagcircle)
					{
						t=Integer.parseInt(str_WordIDListTotal[lWordIDListTotal-1]);
					}
					else {
						t=1;
					}
				}
				else{
					t=Integer.parseInt(str_WordIDListTotal[i-1]);
				}
			}
		}
		return t;
	}
	private void editWordIDListStudied(String str, long studyReviewID2) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=helper.getReadableDatabase();
		ContentValues values=new ContentValues();
		values.put("StudiedCounr",0);
		values.put("WordIDListStudied",str);
		Log.i("444444444444444444444444444","444"+str);
		int count=db.update(opTable, values, "StudyReviewID=?",new String[] {StudyReviewID+""});
		db.close();
	}
	public void setWordIDListStudiedop(String wordIDtemp,String wordTotal,long StudyReviewID,int brouseop,int op,Context context)
	{
		Log.i("23356","789");
		WordtotalTableop wtop=new WordtotalTableop(context);
		SettingVariable sv=new SettingVariable();
		userInfoop uifop=new userInfoop(context);
		uifop.setUsername(sv.getUName());
		uifop.getallbyusername();
		long uid=uifop.getId1();
		String WordIDListStudiedtemp="";
		DateTimeOp dt=new DateTimeOp();
		studyreviewtime=dt.getDateTimeNowLong();
		Log.i("ffff1","123");
		getStudyReviewInfo(StudyReviewID);
		Log.i("ffff2","123");
		if(wordTotal.equals("")==false)
			WordIDListTotal=wordTotal;
		Log.i("ffffffffffff",WordIDListStudied+"  "+wordTotal+"  "+op);
		if(wordIDtemp.equals(""))
		{Log.i("ffffffffffffffffffffffffffffffff3","123");
		StudiedCount=0;
		WordIDListStudied="";
		state="0";
		}
		else {
			if(WordIDListStudied.equals(""))
			{
				WordIDListStudied=wordIDtemp+"";
				Log.i("ffff5","123");
				Log.i("ffff7",WordIDListStudied+"123"+wordIDtemp);
			}
			else {
				WordIDListStudied+="#"+wordIDtemp;
				Log.i("ffff6","123");
			}
			StudiedCount++;
			if(op==1&&state.equals("0"))
			{wtop.addStudiedWordCount(1,sv.getTableName(),(int)uid);
			}
			Log.i("ffff444444444",StudiedCount+"  123");
		}
		WordIDListStudiedtemp=WordIDListStudied;
		if(WordIDListTotal.indexOf(WordIDListStudied)!=-1)
		{
			Log.i("ffff77777777777","123"+state+"   aa  "+WordIDListStudied+"  bb  "+WordIDListTotal);
			if(WordIDListStudied.equals(WordIDListTotal)&&state.equals("0"))
			{state="1";
			Log.i("ooo","pp");
			}
			else
				if(WordIDListStudied.equals(WordIDListTotal)&&Integer.parseInt(state)>=1)
				{
					int temp=0;
					temp=Integer.parseInt(state);
					temp++;state=temp+"";
					Log.i("ffff66666666666666666","1234"+state);
				}
		}
		else {
			StudiedCount=0;
			WordIDListStudied="";
			Log.i("ffffffffffffffff6","1234");
		}
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("StudiedCount",StudiedCount);
		values.put("studyreviewtime",studyreviewtime);
		values.put("WordIDListStudied",WordIDListStudied);
		values.put("state",state);
		int count=db.update(opTable, values,"StudyReviewID=?",new String[] {StudyReviewID+""});
		Log.i("ffff7",WordIDListStudied+"123"+wordIDtemp+" "+StudiedCount);
		db.close();
	}
	public void getStudyReviewInfo(long StudyReviewID){
		SQLiteDatabase db=helper.getReadableDatabase();
		String string="select WordIDListStudied,WordIDListTotal,state from "+ opTable +""+" where StudyReviewID="+StudyReviewID + "";
		Log.i("jjjj",string);
		Cursor c=db.rawQuery("select * from " + opTable +""+ " where StudyReviewID=? ",new String[]{StudyReviewID+""});
		if(c.getCount()>0){
			c.moveToNext();
			{WordIDListStudied=c.getString(c.getColumnIndex("WordIDListStudied"));
			String aa=c.getString(c.getColumnIndex("WordIDListStudied"));
			WordIDListTotal=c.getString(c.getColumnIndex("WordIDListTotal"));
			Log.i("iiiiiiiiiiii",aa);
			Log.i("i",WordIDListTotal+" "+c.getColumnIndex("WordIDListStudied"));
			state=c.getString(c.getColumnIndex("state"));;
			}}
		c.close();
		db.close();
	}
	public int checkstudystate(long uid,int op,int brouseop,int groupIDtemp,String strtemp){
		DateTimeOp dt=new DateTimeOp();
		int flag=-1;
		studyreview="1";
		if(op==1)studyreview="1";
		else studyreview="2";
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor c=null;
		if(strtemp.equals(""))
		{
			if(groupIDtemp>0)
			{
				c=db.rawQuery("select StudyReviewID,GroupdetailTable.GroupingID,state,studyreviewtime,WordIDListStudied from " + opTable +",GroupdetailTable" + " where UID=? and GroupdetailTable.WORDTableID=? and " +opTable
						+".GroupingID=GroupdetailTable.GroupingID and GroupdetailTable.GroupingID=? and studyreview="+studyreview+" order by GroupdetailTable.GROUPID desc",new String[] {uid+"",WORDTableID+"",groupIDtemp+""});
				String sqll="select StudyReviewID,GroupdetailTable.GroupingID,state,studyreviewtime,WordIDListStudied from " + opTable +",GroupdetailTable"
						+ " where UID=? and GroupdetailTable.WORDTableID=? and " + opTable + ".GroupingID=GroupdetailTable.GroupingID and GroupdetailTable.GroupingID=? and studyreview="+studyreview+" order by GroupdetailTable.GROUPID desc"+ uid + " "+WORDTableID +" "+groupIDtemp+" ";
				Log.i("ccccccssss",sqll);
			}
			else {
				c=db.rawQuery("select StudyReviewID,GroupdetailTable.GroupingID,state,studyreviewtime,WordIDListStudied from " + opTable +",GroupdetailTable" + " where UID=? and GroupdetailTable.WORDTableID=? and " +opTable
						+".GroupingID=GroupdetailTable.GroupingID and studyreview="+studyreview+" order by GroupdetailTable.GROUPID desc",new String[] {uid + "",WORDTableID +""});
			}
		}
		else {
			c=db.rawQuery("select StudyReviewID,GroupdetailTable.GroupingID,state,studyreviewtime,WordIDListStudied from " + opTable +",GroupdetailTable" + " where UID=? and GroupdetailTable.WORDTableID=? and " +opTable
					+".GroupingID=GroupdetailTable.GroupingID and "+ opTable +".WordIDListTotal=? and studyreview="+studyreview+" order by GroupdetailTable.GROUPID desc",new String[] {uid+"",WORDTableID+"",strtemp+""});
			String sqll="select StudyReviewID,GroupdetailTable.GroupingID,state,studyreviewtime,WordIDListStudied from " + opTable +",GroupdetailTable"
					+ " where UID=? and GroupdetailTable.WORDTableID=? and " + opTable + ".GroupingID=GroupdetailTable.GroupingID and "+ opTable +".WordIDListTotal=? and studyreview="+studyreview+" order by GroupdetailTable.GROUPID desc"+ uid +" "+WORDTableID +" "+strtemp+" ";
			Log.i("ccccccssss",sqll);
		}
		if(c.getCount()>0){
			c.moveToNext();
			StudyReviewID=c.getLong(0);
			GroupingID=c.getInt(1);
			state=c.getString(2);
			studyreviewtime=c.getString(3);
			WordIDListStudied=c.getString(4);
			if(op==2&&dt.compareDate(studyreviewtime))
			{
				state=(Integer.parseInt(state)+1)+"";
			}
		}
		else {
			flag=1;
			StudyReviewID=-1;
			state="1";
		}
		c.close();
		Log.i("cccdd",flag+"dddd");
		if(flag==1)
		{
			if(strtemp.equals(""))
			{
				if(groupIDtemp>0)
				{Log.i("cccdd1",flag+"dddd");
				c=db.rawQuery("select * from GroupdetailTable" + " where GroupingID=? order by GROUPID asc",new String[] {groupIDtemp+""});
				}
				else {
					Log.i("cccdd2",flag+"dddd");
					c=db.rawQuery("select * from GroupdetailTable" + " where UID=? and WORDTableID=? and GroupingID=? order by GROUPID asc",new String[]{uid + "",WORDTableID +"",GroupingID +""});
				}
			}
			else {
				flag=5;
			}
			if(c.getCount()>0||flag==5){
				if(c.getCount()>0){
					Log.i("cccdd3",flag+"dddd");
				c.moveToNext();
				long GroupingID1=c.getLong(0);
				int UID=c.getInt(1);
				int WORDTableID=c.getInt(2);
				int GROUPID=c.getInt(3);
				String WordIDList=c.getString(4);
				int TotalWordCount=c.getInt(5);
				setGroupID((int)GroupingID1);
				setStudyreview(studyreview);
				setWordIDListTotal(WordIDList);
			}
			if(flag==5){
				setGroupID((int)-2);
				setStudyreview("");
				setWordIDListTotal(strtemp);
			}
			setTrueCount(0);
			setStudiedCount(0);
			studyreviewtime=dt.getDateTimeNowLong();
			WordIDListStudied="";
			Log.i("ffffffffffffffffffffffffffff","1111");
			setState("0");
		}else{
			flag=1;
			StudyReviewID=-1;
			GroupingID=-1;
			state="1";
		}
		c.close();
		if(StudyReviewID<0)
		{
			Log.i("54444444444444444444444444444",WordIDListStudied);
			StudyReviewID=(int)Add();
			Log.i("644444444444444444444444444",WordIDListStudied);
		}
		else {
			ContentValues values=new ContentValues();
			if(op==1&&brouseop>1||op==2)
			{
				editWordIDListStudied("",StudyReviewID);
			}
		}
	}
	if(state.equals("")==false&&Integer.parseInt(state)>=1||WordIDListStudied.equals("")||op==2)
	{
		Log.i("744444444444444444444444444",WordIDListStudied);
		flag=3;
	}else{
		flag=0;
	}
	db.close();
	return flag;
}
public long Add(){
	int check=0;
	SQLiteDatabase db=helper.getReadableDatabase();
	ContentValues values=new ContentValues();
	DateTimeOp dt=new DateTimeOp();
	studyreviewtime=dt.getDateTimeNowLong();
	if(check==0){
		if(StudyReviewID!=-1)
			values.put("StudyReviewID",StudyReviewID);
		values.put("GroupingID",GroupingID);
		values.put("studyreview",studyreview);
		values.put("TrueCount",TrueCount);
		values.put("StudiedCount",StudiedCount);
		values.put("studyreviewtime",studyreviewtime);
		values.put("WordIDListStudied",WordIDListStudied);
		values.put("WordIDListTotal",WordIDListTotal);
		values.put("state","0");
		StudyReviewID=db.insert(opTable, null, values);
	}
	db.close();
	return StudyReviewID;
}
public void querryWordIDListGrouping(long uid){
	SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select GroupdetailTable.GroupingID,state from " + opTable +",GroupdetailTable" + " where UID=? and GroupdetailTable.WORDTable=? and state='1' and studyreview='1' order by GroupdetailTable.GROUPID asc",new String[]{uid +","+WORDTableID+""});
	if(c.getCount()>0){
		c.moveToNext();
		this.StudyReviewID=c.getLong(0);
		GroupingID=c.getInt(1);
		studyreview=c.getString(2);
		TrueCount=c.getInt(3);
		StudiedCount=c.getInt(4);
		WordIDListStudied=c.getString(5);
		WordIDListTotal=c.getString(6);
		state=c.getString(5);
	}
	c.close();
	db.close();
}
public void querryWordIDListArray(long uid)
{
	SQLiteDatabase db=helper.getReadableDatabase();
	Cursor c=db.rawQuery("select GroupdetailTable.GroupingID,state from " + opTable +",GroupdetailTable" + " where UID=? and GroupdetailTable.GROUPID asc",new String[]{uid+ ","+WORDTableID+""});
	if(c.getCount()>0){
		c.moveToNext();
		this.StudyReviewID=c.getLong(0);
		GroupingID=c.getInt(1);
		studyreview=c.getString(2);
		TrueCount=c.getInt(3);
		StudiedCount=c.getInt(4);
		WordIDListStudied=c.getString(5);
		WordIDListTotal=c.getString(5);
		state=c.getString(6);
	}
	c.close();
	db.close();
}

	public long getStudyReviewID() {
		return StudyReviewID;
	}
	public void setStudyReviewID(long studyReviewID) {
		StudyReviewID = studyReviewID;
	}
	public int getGroupingID() {
		return GroupingID;
	}
	public void setGroupingID(int groupingID) {
		GroupingID = groupingID;
	}
	public int getWORDTableID() {
		return WORDTableID;
	}
	public void setWORDTableID(int wORDTableID) {
		WORDTableID = wORDTableID;
	}
	public String getStudyreview() {
		return studyreview;
	}
	public void setStudyreview(String studyreview) {
		this.studyreview = studyreview;
	}
	public int getTrueCount() {
		return TrueCount;
	}
	public void setTrueCount(int trueCount) {
		TrueCount = trueCount;
	}
	public int getStudiedCount() {
		return StudiedCount;
	}
	public void setStudiedCount(int studiedCount) {
		StudiedCount = studiedCount;
	}
	public String getWordIDListStudied() {
		return WordIDListStudied;
	}
	public void setWordIDListStudied(String wordIDListStudied) {
		WordIDListStudied = wordIDListStudied;
	}
	public String getWordIDListTotal() {
		return WordIDListTotal;
	}
	public void setWordIDListTotal(String wordIDListTotal) {
		WordIDListTotal = wordIDListTotal;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStudyreviewtime() {
		return studyreviewtime;
	}
	public void setStudyreviewtime(String studyreviewtime) {
		this.studyreviewtime = studyreviewtime;
	}
	public List<String> getListt() {
		return listt;
	}
	public void setListt(List<String> listt) {
		this.listt = listt;
	}
	public int getGroupID() {
		return GroupID;
	}
	public void setGroupID(int groupID) {
		GroupID = groupID;
	}
	public ArrayList<StudyReviewTableop> getListrecommend() {
		return listrecommend;
	}
	public void setListrecommend(ArrayList<StudyReviewTableop> listrecommend) {
		this.listrecommend = listrecommend;
	}
	public List<StudyReviewTableop> getList() {
		return list;
	}
	public void setList(List<StudyReviewTableop> list) {
		this.list = list;
	}
	public void editWordIDLisStudied(String string, long studyReviewID2) {
		// TODO Auto-generated method stub
		
	}
	}













