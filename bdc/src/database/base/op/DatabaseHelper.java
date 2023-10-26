package database.base.op;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="";
		sql="create table userInfo("+
		"ID           INTEGER PRIMARY KEY autoincrement NOT NULL,"+
		"username     varchar(20) not null,"+
		"password     varchar(20) not null,"+
		"question     varchar(20) not null,"+
		"answer       varchar(20) not null,"+
		"real_name    varchar(10) not null,"+
		"Reg_time     varchar(30) not null,"+
		"UState       varchar(10) not null"+
		")";
		db.execSQL(sql);
		sql="create table userLogin("+
				"ID           INTEGER PRIMARY KEY autoincrement NOT NULL,"+
				"username     varchar(20) not null,"+
				"loginTime    varchar(20) not null,"+
				"loginTimeout     varchar(20),"+
				"state      varchar(20) not null"+
				")";
		db.execSQL(sql);
		sql="create table userRealInfo("+
				"ID           INTEGER PRIMARY KEY autoincrement NOT NULL,"+
				"username     varchar(20) not null,"+
				"student    varchar(20) not null,"+
				"name     varchar(20) not null,"+
				"identity      varchar(20) not null,"+
				"phone     varchar(20) not null,"+
				"email     varchar(20) not null"+
				")";
		db.execSQL(sql);
		sql="create table WordtotalTable("
				+"TableID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
				+"TableName     varchar(100) NOT NULL,"
				+"TotalWord    INTEGER NOT NULL,"
				+"LeadInTime    timestamp NOT NULL,"
				+"GroupCount          INTEGER,"
				+"StudiedWordCount    INTEGER,"
				+"StudiedGroupCount   INTEGER,"
				+"UID                 INTEGER NOT NULL,"
				+"TableNameChina     varchar(100) NOT NULL"
				+")";
		db.execSQL(sql);
		sql="create table ImportWordTable("
				+"WordID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
				+"WordName     varchar(10) NOT NULL,"
				+"Phonogram   varchar(15),"
				+"WordMeaning    varchar(500),"
				+"Familiarity     varchar(15)"
				+")";
		db.execSQL(sql);
		sql="create table Setting("
				+"sID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
				+"settingID     INTEGER NOT NULL,"
				+"settingContent   varchar(100) NOT NULL,"
				+"settingMeaning    varchar(100) NOT NULL,"
				+"settingClass       integer NOT NULL"
				+")";
		db.execSQL(sql);
		sql="create table GroupdetailTable("
				+"GroupingID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
				+"UID     integer NOT NULL,"
				+"WORDTableID   INTEGER NOT NULL,"
				+"GROUPID    INTEGER NOT NULL,"
				+"WordIDList   varchar(1000),"
				+"TotalWordCount     INTEGER"
				+")";
		db.execSQL(sql);
		sql="create table StudyReviewTable("
				+"StudyReviewID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
				+"GroupingID     INTEGER NOT NULL,"
				+"studyreview  varchar(10) NOT NULL,"
				+"studyreviewtime    varchar(30) NOT NULL,"
				+"TrueCount   INTEGER NOT NULL,"
				+"StudiedCount   INTEGER NOT NULL,"
				+"WordIDListStudied    varchar(3000),"
				+"WordIDListTotal    varchar(3000),"
				+"state     varchar(100)"
				+")";
		db.execSQL(sql);
	sql="create table KingsoftWordTable("
			+"WordID           INTEGER PRIMARY KEY autoincrement NOT NULL,"
			+"WordName     varchar(100) NOT NULL,"
			+"PhonogramE  varchar(50) NOT NULL,"
			+"PhonogramA    varchar(50) NOT NULL,"
			+"WordMeaning   varchar(500) NOT NULL,"
			+"PronunciationE   varchar(300) NOT NULL,"
			+"PronunciationA     varchar(300) NOT NULL,"
			+"ExampleSentence    varchar(500) NOT NULL,"
			+"SentenceMeaning    varchar(500) NOT NULL,"
			+"DownLoadTime   timestamp NOT NULL,"
			+"PronunciationPathE    varchar(500),"
			+"PronunciationPathA     varchar(500))";
	db.execSQL(sql);
}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
