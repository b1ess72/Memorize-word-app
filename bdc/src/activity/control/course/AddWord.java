package activity.control.course;

import wgy.recitewords.bdc.R;
import general.base.op.DateTimeOp;
import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
import database.control.op.GroupingdetalsTableop;
import database.control.op.ImportWordTableop;
import database.control.op.WordtotalTableop;
import database.control.op.userInfoop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddWord extends Activity {
	WordtotalTableop wtop=null;
	ImportWordTableop iwop=null;
	DateTimeOp dtop=null;
	SettingVariable sv=null;
	userInfoop uifop=null;
	GroupingdetalsTableop gdop=null;
	
	TextView addCourseSelect=null;
	EditText addWordName=null;
	EditText addWordPhonogram=null;
	EditText addWordMing=null;
	CheckBox addCheckRepeat=null;
	
	Button addButton=null;
	Button addCancel=null;
	
	int CourseID=0;
	String tableName="";
	String CourseName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_addword);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		addWordName=(EditText)findViewById(R.id.addWordName);
		addWordPhonogram=(EditText)findViewById(R.id.addWordPhonogram);
		addWordMing=(EditText)findViewById(R.id.addWordMing);
		addCheckRepeat=(CheckBox)findViewById(R.id.addCheckRepeat);
		addCourseSelect=(TextView)findViewById(R.id.addCourseSelect);
		
		sv=new SettingVariable();
		CourseName=sv.getTableNameChina();
		addCourseSelect.setText("已经选择课程："+CourseName);
		wtop=new WordtotalTableop(this);
		
		uifop=new userInfoop(this);
		gdop=new GroupingdetalsTableop(this);
		iwop=new ImportWordTableop(this);
		dtop=new DateTimeOp();
		setWelcomesText();
		
		//uifop.setUsername(sv.getUName());
		//uifop.getallbyusername();
		gdop.setUID((int)Integer.parseInt(sv.getUID()));
		gdop.setWORDTableID(sv.getTableID());
		gdop.setGROUPID(1);
		if(gdop.checkGroupInfo()>0)
		{
			Intent intent =new Intent();
			Toast.makeText(AddWord.this,"本课程已经分组，无法再添加新单词！",0).show();
			intent.setClass(AddWord.this,Course_gov.class);
			startActivity(intent);
		}
		addButton=(Button)findViewById(R.id.addButton);
		addButton.setOnClickListener(new ButtonListener());
		
		addCancel=(Button)findViewById(R.id.addCancel);
		addCancel.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.addButton:
				getLayoutTypewtop();
				break;
			case R.id.addCancel:
				Intent intent=new Intent();
				intent.setClass(AddWord.this,Course_gov.class);
				startActivity(intent);
				break;
			}
		}
	}
	void getLayoutTypewtop(){
		iwop.setWordName(addWordName.getText().toString());
		iwop.setPhonogram(addWordPhonogram.getText().toString());
		iwop.setWordMeaning(addWordMing.getText().toString());
		iwop.setOpTable(sv.getTableName());
		if(iwop.checkWordName()>=0)
		{
			if(addCheckRepeat.isChecked())
			{
				iwop.edit();
				Toast.makeText(AddWord.this,"覆盖新增成功！",0).show();
			}
			else {
				Toast.makeText(AddWord.this,"已有该单词，无法添加！",0).show();
			}
		}
		else {
			iwop.setOpTable(sv.getTableName());
			iwop.Add();
			String TableName;
			
			TableName=sv.getTableName();
			wtop.setTableName(TableName);
			wtop.editWordCount(1);
			Toast.makeText(AddWord.this,"添加成功！",0).show();
		}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		TextView welcomeWords=(TextView)findViewById(R.id.addWelComeWords);
		welcomeWords.setText(sv.getUName()+"用户，欢迎您！");
	}
}

		
		
