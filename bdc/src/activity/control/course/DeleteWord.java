package activity.control.course;

import wgy.recitewords.bdc.R;
import general.base.op.SettingVariable;
import database.control.op.GroupingdetalsTableop;
import database.control.op.ImportWordTableop;
import database.control.op.Settingop;
import database.control.op.WordtotalTableop;
import database.control.op.userInfoop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteWord extends Activity {
	WordtotalTableop wtt=null;
	ImportWordTableop iwop=null;
	SettingVariable sv=null;
	Settingop stop=null;
	userInfoop uifop=null;
	GroupingdetalsTableop gdop=null;
	EditText deleteWordNames=null;
	Button deleteButton=null;
	Button deletecancel=null;
	Button deleteCourseAllWord=null;
	TextView deleteCourseSelect=null;
	String tableName="";
	String CourseName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_deleteword);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		
		setWelcomesText();
		iwop=new ImportWordTableop(this);
		wtt=new WordtotalTableop(this);
		stop=new Settingop(this);
		uifop=new userInfoop(this);
		gdop=new GroupingdetalsTableop(this);
		tableName=sv.getTableName();
		CourseName=sv.getTableNameChina();
		deleteCourseSelect=(TextView)findViewById(R.id.DeleteCourseSelect);
		deleteCourseSelect.setText("已经选择课程："+CourseName);
		deleteWordNames=(EditText)findViewById(R.id.deleteWordNames);
		deleteButton=(Button)findViewById(R.id.deleteButton);
		deletecancel=(Button)findViewById(R.id.deleteCancel);
		deleteCourseAllWord=(Button)findViewById(R.id.deleteCourseAllWord);
		deleteButton.setOnClickListener(new ButtonListener());
		deletecancel.setOnClickListener(new ButtonListener());
		deleteCourseAllWord.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.deleteButton:
				int checkgroup=0;
				uifop.setUsername(sv.getUName());
				uifop.getallbyusername();
				gdop.setUID((int)uifop.getId1());
				gdop.setWORDTableID(sv.getTableID());
				gdop.setGROUPID(1);
				checkgroup=gdop.checkGroupInfo();
				if(checkgroup>0){
					Toast.makeText(DeleteWord.this,"该课程已经分组，无法进行删除操作！如需进" +
							"行本操作，请先删除所有分组信息！",0).show();
				}
				else{
					iwop.setWordName(deleteWordNames.getText().toString());
					iwop.setOpTable(tableName);
					iwop.querrybyname();
					if(iwop.getWordID()>0)
					{
						iwop.delete();
						wtt.setTableName(tableName);
						wtt.edittablecount(-1);
						Toast.makeText(DeleteWord.this,"已经成功删除！",
								0).show();
						deleteWordNames.setText("");
					}
					else {
						Toast.makeText(DeleteWord.this,"无此单词！无法删除！" +
								"",0).show();
					}
				}
				break;
			case R.id.deleteCancel:
				Intent intent=new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(DeleteWord.this,Course_gov.class);
				startActivity(intent);
				break;
			case R.id.deleteCourseAllWord:
				wtt.setTableNameChina(CourseName);
				wtt.deleteTableInfo();
				sv.setTableID(-1);
				sv.setTableName("");
				sv.setTableNameChina("");
				stop.setSettingContent("-1");
				stop.setSettingID(35);
				stop.edit();
				Toast.makeText(DeleteWord.this,"已成功删除！",0).show();
				Intent intent1=new Intent();
				intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent1.setClass(DeleteWord.this,Course_gov.class);
				startActivity(intent1);
				break;
			}}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		TextView WelcomeWords=(TextView)findViewById(R.id.delectWelcomeWord);
		WelcomeWords.setText(sv.getUName()+"用户，欢迎您！");
	}
}
