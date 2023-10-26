package database.base.op;

import android.content.Context;

public class DataBaseBaseOp {
	DatabaseHelper helper=null;
	public DatabaseHelper getHelper(){return helper;}
	public void SetHelper(DatabaseHelper helper){this.helper=helper;}
	public void initsqlite3(Context content){
		helper=new DatabaseHelper(content,"tqy.db",null,5);
	}
}
