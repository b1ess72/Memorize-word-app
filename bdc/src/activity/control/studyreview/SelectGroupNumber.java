package activity.control.studyreview;

import java.util.ArrayList;
import java.util.List;

import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
import database.control.op.GroupingdetalsTableop;
import database.control.op.StudyReviewTableop;
import database.control.op.userInfoop;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectGroupNumber extends Activity {
	Button sconfirm=null;
	Button scancle=null;
	GroupingdetalsTableop gdop=null;
	SettingVariable sv=null;
	TextView scoursename=null;
	StudyReviewTableop srtop=null;
	userInfoop uifop=null;
	List<String> list1=new ArrayList<String>();
	List<String> listfamiliarity=new ArrayList<String>();
	long SelectedGroupID=-1;
	LinearLayout linearlayoutfamiliarity=null;
	private Spinner mySpinner;
	private Spinner spinnerfamiliarity;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapterfamiliarity;
	private String familiarString="";
	int op=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.studygov_selectgroupnumber);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		sv=new SettingVariable();
		srtop=new StudyReviewTableop(this);
		gdop=new GroupingdetalsTableop(this);
		uifop=new userInfoop(this);
		scoursename=(TextView)findViewById(R.id.scoursename);
		setWelcomesText();
		Log.i("ssss17",op+"");
		sconfirm=(Button)findViewById(R.id.sconfirm);
		sconfirm.setOnClickListener(new ButtonListener());
		scancle=(Button)findViewById(R.id.scancle);
		scancle.setOnClickListener(new ButtonListener());
		Log.i("ssss18",op+"");
		Intent intent=getIntent();
		op=intent.getIntExtra("op",0);
		Log.i("ssss",op+"");
		InitDropList();
		if(op==1)
		{
			linearlayoutfamiliarity=(LinearLayout)findViewById(R.id.linearlayoutfamiliarity);
			linearlayoutfamiliarity.setVisibility(View.GONE);
			sv.setFamiliarity("");
		}
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			switch(v.getId()){
			case R.id.sconfirm:
				intent.putExtra("op",op);
				intent.putExtra("content",1);
				intent.putExtra("SelectedGroupID",SelectedGroupID+"");
				intent.putExtra("SelectedArrayID","");
				intent.putExtra("returnDialog","SelectGroupNumber");
				intent.setClass(SelectGroupNumber.this,StudyReviewList.class);
				break;
			case R.id.scancle:
				intent.setClass(SelectGroupNumber.this,Management.class);
				break;
			}
			startActivity(intent);
		}
	}
	void setWelcomesText(){
		scoursename.setText("课程名："+sv.getTableNameChina());
		TextView WelcomeWords=(TextView)findViewById(R.id.sWordPhonogram);
		WelcomeWords.setText(sv.getUName()+"用户，欢迎您！");
	}
	void InitDropList(){
		srtop.setWORDTableID(sv.getTableID());
		uifop.setUsername(sv.getUName());
		uifop.getallbyusername();
		gdop.setUID((int)uifop.getId1());
		if(op==1)
		{
			list1=srtop.getunStudiedGroupingList(uifop.getId1());
		}
		else {
			list1=srtop.getunStudiedGroupingList(uifop.getId1());
		}
		Log.i("iiiki","ss");
		if(list1.isEmpty()){
			if(op==1)
				Toast.makeText(SelectGroupNumber.this,"您没有需要学习的课程，系统将返回！", 0).show();
			else {
				Toast.makeText(SelectGroupNumber.this,"您没有需要复习的课程，系统将返回！", 0).show();
			}
			Intent intent=getIntent();
			intent.setClass(SelectGroupNumber.this,Management.class);
			startActivity(intent);
		}
		else {
			Log.i("iiiki1","ss1");
			spinnerfamiliarity=(Spinner)findViewById(R.id.spinnerfamiliarity);
			initDropListClass idls=new initDropListClass();
			listfamiliarity=idls.getList();
			adapterfamiliarity=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listfamiliarity);
			adapterfamiliarity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Log.i("iiiki","ss11111");
			spinnerfamiliarity.setAdapter(adapterfamiliarity);
			Log.i("iiiki","ss111111");
			spinnerfamiliarity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					familiarString=listfamiliarity.get(arg2);
					Toast.makeText(SelectGroupNumber.this,adapterfamiliarity.getItem(arg2)+"   "+familiarString,0).show();
					sv.setFamiliarity(familiarString);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(SelectGroupNumber.this,"Nothing",0).show();
				}
				});
			mySpinner=(Spinner)findViewById(R.id.seditSetNo);
			Log.i("iiiki","ss1");
			adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
			Log.i("iiiki","ss1");
			mySpinner.setAdapter(adapter);
			Log.i("iiiki","ss1");
			mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					List<String> list2=srtop.getListt();
					SelectedGroupID=Integer.parseInt(list2.get(arg2));
					Toast.makeText(SelectGroupNumber.this,adapter.getItem(arg2)+"  "+arg2+" "+SelectedGroupID,0).show();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(SelectGroupNumber.this,"Nothing",0).show();
				}
			});
		}
	}
}
