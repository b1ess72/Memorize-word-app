package wgy.recitewords.bdc;

import database.control.op.Settingop;
import database.control.op.WordtotalTableop;
import database.control.op.userLoginop;
import general.base.op.SettingVariable;
import activity.control.course.Course_gov;
import activity.control.kingsoft.KingSoft_gov;
import activity.control.setting.settingmain;
import activity.control.studyreview.Management;
import activity.control.user.User_Login;
import activity.control.user.User_Main;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class BDC_Main extends Activity {
	TextView wel_word=null;
	Button course_gov=null;
	Button net_word_gov=null;
	Button study_review_gov=null;
	Button setting_gov=null;
	Button user_gov=null;
	Button user_quit=null;
	Button database_backup=null;
	SettingVariable sv=null;
	userLoginop ulop=null;
	Settingop stop=null;
	WordtotalTableop wtop=null;
	Button user_return=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.bdc_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	 void init()
	{
		wel_word=(TextView)findViewById(R.id.wel_word);
		course_gov=(Button)findViewById(R.id.course_gov);
		net_word_gov=(Button)findViewById(R.id.net_word_gov);
		study_review_gov=(Button)findViewById(R.id.study_review_gov);
		setting_gov=(Button)findViewById(R.id.setting_gov);
		user_gov=(Button)findViewById(R.id.user_gov);
		user_quit=(Button)findViewById(R.id.user_quit);
		user_return=(Button)findViewById(R.id.user_return);
		database_backup=(Button)findViewById(R.id.database_backup);
		//setting=(ImageView)findViewById(R.id.danwei);
		setinitwelcome();
		ulop=new userLoginop(this);
		
		stop=new Settingop(this);
		wtop=new WordtotalTableop(this);
		course_gov.setOnClickListener(new MyButton());
		net_word_gov.setOnClickListener(new MyButton());
		study_review_gov.setOnClickListener(new MyButton());
		user_gov.setOnClickListener(new MyButton());
		user_quit.setOnClickListener(new MyButton());
		user_return.setOnClickListener(new MyButton());
		database_backup.setOnClickListener(new MyButton());
		if(stop.checkempty())
		{
			stop.initsvinsert();
		}
		if(sv.getInit()==-1)
		{
			sv.querryallv();
			if(sv.getTableName().equals("-1")==false)
			{
				wtop.setTableName(sv.getTableName());
				wtop.querrybyTablename();
				sv.setTableID((int)wtop.getTableID());
				sv.setTableNameChina(wtop.getTableNameChina());
			}
		}	
	}
	class MyButton implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			Intent intent=new Intent();
			switch(v.getId())
			// TODO Auto-generated method stub
			{
			case R.id.course_gov:
				intent.setClass(BDC_Main.this,Course_gov.class);break;
			case R.id.net_word_gov:
				intent.setClass(BDC_Main.this,KingSoft_gov.class);
				break;
			case R.id.study_review_gov:
				intent.setClass(BDC_Main.this,Management.class);
				break;
			case R.id.setting_gov:
				intent.setClass(BDC_Main.this,settingmain.class);
				break;
			case R.id.user_gov:
				intent.setClass(BDC_Main.this,User_Main.class);
				break;
			case R.id.database_backup:
				break;
			case R.id.user_return:
				//intent=cm.doReturnDialog();
				break;
			case R.id.user_quit:
				ulop.setUsername(sv.getUName());
				sv.setUName("");
				sv.setUID("");
				ulop.down();
				intent.putExtra("Loginout","out");
				intent.setClass(BDC_Main.this,User_Login.class);
				break;
			}
			startActivity(intent);
		}}
	void setinitwelcome()
	{
		sv=new SettingVariable(BDC_Main.this);
		wel_word.setText(sv.getUName());		
			}			
		}

