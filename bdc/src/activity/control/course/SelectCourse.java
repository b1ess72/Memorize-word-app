package activity.control.course;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import wgy.recitewords.bdc.R;

import database.control.op.GroupingdetalsTableop;
import database.control.op.Settingop;
import database.control.op.WordtotalTableop;
import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectCourse extends Activity {
	int op=0;
	Button selectButton=null;
	Button selectcancel=null;
	TextView courseSelectInfo=null;
	private Spinner mySpinner;
	SettingVariable sv=null;
	WordtotalTableop wtt=null;
	Settingop stop=null;
	private List<String> list1=new ArrayList<String>();
	List<int[]> list2=new ArrayList<int[]>();
	private ArrayAdapter<String> adapter;
	int CourseID=-1;
	String tableName="";
	String tablenameChina="";
	GroupingdetalsTableop gdop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_selectcourse);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		courseSelectInfo=(TextView)findViewById(R.id.courseSelectInfo);
		selectButton=(Button)findViewById(R.id.selectButton);
		selectcancel=(Button)findViewById(R.id.selectCancel);
		
		wtt=new WordtotalTableop(this);
		stop=new Settingop(this);
		Intent intent=getIntent();
		gdop=new GroupingdetalsTableop(this);
		op=intent.getIntExtra("op",0);
		setWelcomesText();
		InitDropList();
		selectButton.setOnClickListener(new ButtonListener());
		selectcancel.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.selectButton:
				Intent intent=new Intent();
				intent.putExtra("CourseID",CourseID);
				intent.putExtra("TableName",tableName);
				intent.putExtra("CourseName",tablenameChina);
				SettingVariable sv=new SettingVariable();
				sv.setTableID(CourseID);
				sv.setTableName(tableName);
				sv.setTableNameChina(tablenameChina);
				
				stop.setSettingContent(sv.getTableName()+"");
				stop.setSettingID(35);
				stop.edit();
				switch(op)
				{
				case 1:intent.setClass(SelectCourse.this,Course_gov.class);break;
				case 2:intent.setClass(SelectCourse.this,AddWord.class);break;
				case 3:intent.setClass(SelectCourse.this,EditWord.class);break;
				case 4:intent.setClass(SelectCourse.this,DeleteWord.class);break;
				case 5:
					if(gdop.checkGroupInfo()>0)
					{intent.putExtra("op",5);
					intent.setClass(SelectCourse.this,WordGroupDetals.class);
					}
					else{
						intent.setClass(SelectCourse.this,GroupWord.class);
					}
					break;
				case 6:
					intent.putExtra("op",6);
					intent.setClass(SelectCourse.this,WordGroupDetals.class);
					break;
				}
				startActivity(intent);
				break;
			case R.id.selectCancel:
				Intent intent2=new Intent();
				intent2.setClass(SelectCourse.this,Course_gov.class);
				startActivity(intent2);
				break;
				default:
					break;
			}
		}
	}
	void InitDropList()
	{
		SettingVariable sv=new SettingVariable(this);
		wtt.setUID((int)Integer.parseInt(sv.getUID()));
		list1=wtt.querrycourseList();
		list2=wtt.getList2();
		if(list1.isEmpty())
		{
			Toast.makeText(SelectCourse.this,"您没有导入的词库，请先导入词库",0).show();
			Intent intent1=new Intent(SelectCourse.this,ImportCourse.class);
			startActivity(intent1);
		}
		else {
			mySpinner=(Spinner)findViewById(R.id.selectCourseWords);
			adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mySpinner.setAdapter(adapter);
			mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> arg0,View arg1,
						int arg2,long arg3){
					int[] integer1=list2.get(arg2);
					wtt.setTableID(integer1[0]);
					wtt.querrybyTableID();
					Toast.makeText(SelectCourse.this,integer1[0]+"",0).show();
					Toast.makeText(SelectCourse.this,adapter.getItem(arg2)+" "+arg2,0).show();
					
					String details="词库详细信息:\n\n";
					details+="词库名："+wtt.getTableNameChina()+"\n";
					details+="表的编号："+wtt.getTableID()+"\n";
					details+="表名："+wtt.getTableName()+"\n";
					details+="表的总单词数："+wtt.getTotalWord()+"\n";
					details+="表的导入时间："+wtt.getLeadInTime()+"\n";
					details+="表的分组数："+wtt.getGroupCount()+"\n";
					details+="已经学习的单词数："+wtt.getStudiedWordCount()+"\n";
					details+="已经学习的组数："+wtt.getStudiedGroupCount()+"\n";
					
					CourseID=(int)wtt.getTableID();
					tableName=wtt.getTableName();
					tablenameChina=wtt.getTableNameChina();
					courseSelectInfo.setText(details);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(SelectCourse.this,"Nothing",0).show();
				}
			});
		}
	}			
			void setWelcomesText(){
				sv=new SettingVariable();
				TextView WelcomeWords=(TextView)findViewById(R.id.selectWelComeWords);
				WelcomeWords.setText(sv.getUName()+"用户，欢迎您！");
			}
}
