package activity.control.course;

import database.control.op.GroupingdetalsTableop;
import database.control.op.userInfoop;
import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;
import general.base.op.SettingVariable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Course_gov extends Activity {
	Button importCourse=null;
	Button mmCoursesofstudy=null;
	Button addWord=null;
	Button editWord=null;
	Button deleteWord=null;
	Button groupWord=null;
	Button studyState=null;
	Button returntousermain=null;
	TextView WelcomeWords=null;
	SettingVariable sv=null;
	
	GroupingdetalsTableop gdop=null;
	userInfoop uifop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_gov);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		setWelcomeText();
		gdop=new GroupingdetalsTableop(this);
		uifop=new userInfoop(this);
		importCourse=(Button)findViewById(R.id.importCourse);
		mmCoursesofstudy=(Button)findViewById(R.id.mmCoursesofstudy);
		addWord=(Button)findViewById(R.id.addWord);
		editWord=(Button)findViewById(R.id.editWord);
		deleteWord=(Button)findViewById(R.id.deleteWord);
		groupWord=(Button)findViewById(R.id.groupWord);
		studyState=(Button)findViewById(R.id.studystate);
		returntousermain=(Button)findViewById(R.id.returntousermain);
		
		importCourse.setOnClickListener(new ButtonListener());
		mmCoursesofstudy.setOnClickListener(new ButtonListener());
		addWord.setOnClickListener(new ButtonListener());
		editWord.setOnClickListener(new ButtonListener());
		deleteWord.setOnClickListener(new ButtonListener());
		groupWord.setOnClickListener(new ButtonListener());
		studyState.setOnClickListener(new ButtonListener());
		returntousermain.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int flag=1;
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.importCourse:
				intent.setClass(Course_gov.this,ImportCourse.class);break;
			case R.id.mmCoursesofstudy:
				intent.putExtra("op",1);
				intent.setClass(Course_gov.this,SelectCourse.class);
				break;
			case R.id.addWord:
				if(sv.getTableID()==-1){
					intent.putExtra("op",2);
					intent.setClass(Course_gov.this,SelectCourse.class);
				}else{
					intent.setClass(Course_gov.this,AddWord.class);
				}
				break;
			case R.id.editWord:
				if(sv.getTableID()==-1)
				{ intent.putExtra("op",3);
				intent.setClass(Course_gov.this,SelectCourse.class);
				}else{
					intent.setClass(Course_gov.this,EditWord.class);
				}
				break;
			case R.id.deleteWord:
				if(sv.getTableID()==-1)
				{
					intent.putExtra("op",4);
					intent.setClass(Course_gov.this,SelectCourse.class);
				}else{
					intent.setClass(Course_gov.this,DeleteWord.class);
				}
				break;
			case R.id.groupWord:
				if(sv.getTableID()==-1)
				{
					intent.putExtra("op",5);
					intent.setClass(Course_gov.this,SelectCourse.class);
				}else{
					uifop.setUsername(sv.getUName());
					uifop.getallbyusername();
					gdop.setUID((int)uifop.getId1());
					gdop.setWORDTableID(sv.getTableID());
					gdop.setGROUPID(1);
					if(gdop.checkGroupInfo()>0)
					{intent.putExtra("op",5);
					intent.setClass(Course_gov.this,WordGroupDetals.class);
					}
					else{
					intent.setClass(Course_gov.this,GroupWord.class);
				}}
				break;
			case R.id.studystate:
				intent.putExtra("op",6);
				if(sv.getTableID()==-1)
				{intent.setClass(Course_gov.this,SelectCourse.class);
				}else{
					intent.setClass(Course_gov.this,WordGroupDetals.class);
				}
				break;
			case R.id.returntousermain:
				intent.setClass(Course_gov.this,BDC_Main.class);
				break;
				default: break;
			}
			startActivity(intent);
		}
	}
	void setWelcomeText()
	{
		sv=new SettingVariable();
		WelcomeWords=(TextView)findViewById(R.id.welcomeWords);
		WelcomeWords.setText(sv.getUName()+"ÓÃ»§£¬»¶Ó­Äú£¡");
	}
}

