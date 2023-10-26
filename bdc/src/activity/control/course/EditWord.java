package activity.control.course;

import wgy.recitewords.bdc.R;
import database.control.op.ImportWordTableop;
import general.base.op.SettingVariable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditWord extends Activity {
	EditText editWordName=null;
	EditText editWordPhonogram=null;
	EditText editWordMing=null;
	
	Button editButton=null;
	Button editcancel=null;
	Button editselectButton=null;
	
	TextView editCourseSelect=null;
	SettingVariable sv=null;
	long wordID=-1;
	
	int CourseID=0;
	String tableName="";
	String courseName="";
	
	ImportWordTableop iwop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_editword);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		setWelcomesText();
		iwop=new ImportWordTableop(this);
		sv=new SettingVariable();
		
		editCourseSelect=(TextView)findViewById(R.id.editCourseSelect);
		editCourseSelect.setText("已经选择课程："+sv.getTableNameChina());
		
		editWordName=(EditText)findViewById(R.id.editWordName);
		editWordPhonogram=(EditText)findViewById(R.id.editWordPhonogram);
		editWordMing=(EditText)findViewById(R.id.editWordMing);
		editselectButton=(Button)findViewById(R.id.editselectButton);
		
		editButton=(Button)findViewById(R.id.editButton);
		editcancel=(Button)findViewById(R.id.editCancel);
		editButton.setOnClickListener(new ButtonListener());
		editcancel.setOnClickListener(new ButtonListener());
		editselectButton.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.editButton:
				iwop.setOpTable(sv.getTableName());
				iwop.setWordName(editWordName.getText().toString());
				iwop.setPhonogram(editWordPhonogram.getText().toString());
				iwop.setWordMeaning(editWordMing.getText().toString());
				iwop.edit();
				Toast.makeText(EditWord.this,"保存成功！",0).show();
				break;
			case R.id.editCancel:
				Intent intent=new Intent();
				intent.setClass(EditWord.this,Course_gov.class);
				startActivity(intent);
				break;
			case R.id.editselectButton:
				iwop.setOpTable(sv.getTableName());
				iwop.setWordName(editWordName.getText().toString());
				iwop.querrybyname();
				if(iwop.getWordID()==-1){
					Toast.makeText(EditWord.this,"查无此单词！",0).show();
				}
				else{
					editWordPhonogram.setText(iwop.getPhonogram());
					editWordMing.setText(iwop.getWordMeaning());
				}
				break;
			}}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		TextView WelcomeWords=(TextView)findViewById(R.id.editWelcomeWords);
		WelcomeWords.setText(sv.getUName()+"用户，欢迎您！");
	}
}