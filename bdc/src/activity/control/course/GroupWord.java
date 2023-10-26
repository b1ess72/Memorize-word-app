package activity.control.course;

import wgy.recitewords.bdc.R;
import general.base.op.SettingVariable;
import database.control.op.GroupingdetalsTableop;
import database.control.op.ImportWordTableop;
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

public class GroupWord extends Activity {
	WordtotalTableop wtt=null;
	GroupingdetalsTableop gdop=null;
	ImportWordTableop iwop=null;
	int CourseID=0;
	String tableName="";
	String CourseName="";
	int getTotalWord=0;
	SettingVariable sv=null;
	TextView groupCoursenames=null;
	TextView groupCourseCount=null;
	
	EditText groupCount=null;
	Button previewGroupInfo=null;
	Button groupCancelButton=null;
	Button SaveviewGroupinfo=null;
	
	TextView groupInfo1=null;
	TextView groupInfo2=null;
	TextView groupInfo3=null;
	userInfoop uifop=null;
	int op=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_groupword);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		setWelcomesText();
		
		wtt=new WordtotalTableop(this);
		gdop=new GroupingdetalsTableop(this);
		iwop=new ImportWordTableop(this);
		uifop=new userInfoop(this);
		
		Intent intent=getIntent();
		op=intent.getIntExtra("op",0);
		CourseID=intent.getIntExtra("CourseID",0);
		tableName=intent.getStringExtra("TableName");
		CourseName=intent.getStringExtra("CourseName");
		sv=new SettingVariable();
		CourseID=sv.getTableID();
		tableName=sv.getTableName();
		CourseName=sv.getTableNameChina();
		
		groupCoursenames=(TextView)findViewById(R.id.groupCourseName);
		groupCourseCount=(TextView)findViewById(R.id.groupCourseCount);
		groupCount=(EditText)findViewById(R.id.groupCount);
		
		groupInfo1=(TextView)findViewById(R.id.groupInfo1);
		groupInfo2=(TextView)findViewById(R.id.groupInfo2);
		groupInfo3=(TextView)findViewById(R.id.groupInfo3);
		
		previewGroupInfo=(Button)findViewById(R.id.previewGrouInfo);
		groupCancelButton=(Button)findViewById(R.id.groupCancel);
		SaveviewGroupinfo=(Button)findViewById(R.id.saveViewGroupInfo);
		
		previewGroupInfo.setOnClickListener(new ButtonListener());
		groupCancelButton.setOnClickListener(new ButtonListener());
		SaveviewGroupinfo.setOnClickListener(new ButtonListener());
		groupCoursenames.setText("�γ�����"+CourseName);
		wtt.setTableName(tableName);
		wtt.querrybyTablename();
		getTotalWord=0;
		if(wtt.getTableID()!=-1)getTotalWord=wtt.getTotalWord();
		groupCourseCount.setText("�ܵ�������"+getTotalWord);
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int countgroup=0;
			if(!groupCount.getText().toString().equals(""))
				countgroup=Integer.parseInt(groupCount.getText().toString());
			int counttotal=getTotalWord;
			switch(v.getId()){
			case R.id.previewGrouInfo:
			if(counttotal%countgroup==0)
			{
				int tt=counttotal/countgroup;
				groupInfo1.setText("���Ʒ�Ϊ"+countgroup+"�飬ÿ��"+tt+"������");
			}
			else{
				int tt=counttotal/countgroup+1;
				int temp=countgroup-1;
				int least=counttotal-(countgroup-1)*tt;
				while(least<0)
				{
					temp--;
					countgroup--;
					least+=tt;
				}
				groupInfo1.setText("���Ʒ�Ϊ"+countgroup+"��");
				groupInfo2.setText("ǰ"+temp+"�飬ÿ��"+tt+"������");
				groupInfo3.setText("���һ�鵥����Ϊ"+least+"����");
			}
			break;
			case R.id.groupCancel:
				Intent intent=new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case R.id.saveViewGroupInfo:
				iwop.setOpTable(tableName);
				uifop.setUsername(sv.getUName());
				uifop.getallbyusername();
				gdop.setUID((int)uifop.getId1());
				gdop.setWORDTableID(CourseID);
				int groupSize=0;
				if(counttotal%countgroup==0)
				{groupSize=counttotal/countgroup;
				}
				else{
					groupSize=counttotal/countgroup+1;
					int temp=countgroup-1;
					int least=counttotal-(countgroup-1)*groupSize;
					while(least<0)
					{
						temp--;
						countgroup--;
						least+=groupSize;
					}
				}
				gdop.AddList(1,-1,1,groupSize,iwop);
				wtt.setTotalWord(counttotal);
				wtt.setGroupCount(countgroup);
				wtt.setStudiedWordCount(0);
				wtt.setStudiedGroupCount(0);
				wtt.setTableName(tableName);
				wtt.editGroup();
				Toast.makeText(GroupWord.this,"������Ϣ����ɹ���",0).show();
				break;
			}}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		TextView WelcomeWords=(TextView)findViewById(R.id.groupWelcomeWords);
		WelcomeWords.setText(SettingVariable.getUName()+"�û�����ӭ����");
	}
}

