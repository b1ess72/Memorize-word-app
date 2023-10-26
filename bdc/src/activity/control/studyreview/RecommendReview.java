package activity.control.studyreview;

import java.util.ArrayList;
import java.util.List;

import general.base.op.KeyBean;
import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
import database.control.op.GroupingdetalsTableop;
import database.control.op.StudyReviewTableop;
import database.control.op.userInfoop;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendReview extends Activity {
	GroupingdetalsTableop gdop=null;
	SettingVariable sv=null;
	userInfoop uifop=null;
	TextView rrcoursename=null;
	TextView rrrecitationtime=null;
	TextView rrreviewed=null;
	StudyReviewTableop srtop=null;
	private String toRedWord="";
	List<String> listfamiliarity=new ArrayList<String>();
	ArrayList<StudyReviewTableop> listt=new ArrayList<StudyReviewTableop>();
	private Spinner spinnerfamiliarity;
	Button rrreturn=null;
	private ArrayAdapter<String> adapterfamiliarity;
	private String familiarityString="";
	private List<String> list1=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.studygov_recommendreview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		gdop=new GroupingdetalsTableop(this);
		uifop=new userInfoop(this);
		rrcoursename=(TextView)findViewById(R.id.rrcoursename);
		rrrecitationtime=(TextView)findViewById(R.id.rrreciationtime);
		rrreviewed=(TextView)findViewById(R.id.rrreviewed);
		rrreviewed.setText("");
		setWelcomesText();
		srtop=new StudyReviewTableop(this);
		srtop.setWORDTableID(sv.getTableID());
		uifop.setUsername(sv.getUName());
		uifop.getallbyusername();
		gdop.setWORDTableID(sv.getTableID());
		listt=srtop.getRecommendReviewGroupingIDList((int)uifop.getId1());
		InitDropList();
		if(listt==null||listt.size()==0)
		{
			Log.i("aaa","kkkk5");
			Toast.makeText(RecommendReview.this,"您没有需要复习的课程！系统将返回！",0).show();
			Intent intent=getIntent();
			intent.setClass(RecommendReview.this,Management.class);
			startActivity(intent);
		}
		else {
			Log.i("aaa","kkkk6");
			String content="";
			String redStr="";
			ArrayList<KeyBean> keyBeanList=new ArrayList<KeyBean>();
			if(listt!=null&&listt.size()>0){
				for(int i=0;i<listt.size();i++){
					keyBeanList.add(new KeyBean("第"+listt.get(i).getGroupID()+"组",listt.get(i).getGroupingID()+""));
					if(content.equals(""))content=listt.get(i).getStudyreviewtime()+":   第"+listt.get(i).getGroupID()+"组";
					else content+="\n"+listt.get(i).getStudyreviewtime()+":     第"+listt.get(i).getGroupID()+"组";
				}
			}
			insertData(content,redStr,keyBeanList);
			rrrecitationtime.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showKeyBoard();
				}
			});
		}
		rrreturn=(Button)findViewById(R.id.rrreturn);
		rrreturn.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.rrreturn:
				intent.setClass(RecommendReview.this,Management.class);
				break;
			}
			startActivity(intent);
		}
	}
	void InitDropList()
	{
		srtop.setWORDTableID(sv.getTableID());
		spinnerfamiliarity=(Spinner)findViewById(R.id.spinnerfamiliarity);
		initDropListClass idls=new initDropListClass();
		listfamiliarity=idls.getList();
		adapterfamiliarity=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listfamiliarity);
		adapterfamiliarity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerfamiliarity.setAdapter(adapterfamiliarity);
		Log.i("iiiki","ss111111");
		spinnerfamiliarity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				familiarityString=listfamiliarity.get(arg2);
				Toast.makeText(RecommendReview.this,adapterfamiliarity.getItem(arg2)+"   "+familiarityString,0).show();
				sv.setFamiliarity(familiarityString);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(RecommendReview.this,"Nothing",0).show();
			}
		});
	}
	void setWelcomesText(){
		sv=new SettingVariable();
		rrcoursename.setText("课程名称："+sv.getTableNameChina());
		TextView WelcomeWords=(TextView)findViewById(R.id.rrWordPhonogram);
		WelcomeWords.setText(sv.getUName()+"用户，欢迎您!");
	}
	public void insertData(String str,String redStr,final ArrayList<KeyBean> keyBeanList){
		toRedWord=redStr;
		//ExpressionUtil.getSpannableString(str,context);
		SpannableString spannableString=new SpannableString(str);
		String content=spannableString.toString();
		if(keyBeanList!=null&&keyBeanList.size()>0){
			for(int i=0;i<keyBeanList.size();i++){
				final String data=keyBeanList.get(i).getContent();
				final String hide_content=keyBeanList.get(i).getHide_content();
				String temp=content;
				int startNew=0;
				int startOld=0;
				if(temp.contains(data)){
					while (temp.contains(data)) {
						spannableString.setSpan(new ClickableSpan() {						
							@Override
							public void updateDrawState(TextPaint ds) {
								// TODO Auto-generated method stub
								super.updateDrawState(ds);
								ds.setColor(RecommendReview.this.getResources().getColor(R.color.linkcolor));
								ds.setUnderlineText(false);
							}
							@Override
							public void onClick(View widget) {
								// TODO Auto-generated method stub
								Toast.makeText(RecommendReview.this,hide_content+"",Toast.LENGTH_SHORT).show();
								Intent intent=getIntent();
								intent.putExtra("op",2);
								intent.putExtra("content",1);
								intent.putExtra("SelectedGroupID",hide_content+""+"");
								intent.putExtra("SelectedArrayID","");
								intent.putExtra("returnDialog","RecommendReview");
								intent.setClass(RecommendReview.this,StudyReviewList.class);
								startActivity(intent);
							}
						}, startOld + temp.indexOf(data),startOld + temp.indexOf(data) + data.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						rrrecitationtime.setText(spannableString);
						rrrecitationtime.setMovementMethod(LinkMovementMethod.getInstance());
						startNew=temp.indexOf(data)+data.length();
						startOld+=startNew;
						temp=temp.substring(startNew);
					}
				}else {
					rrrecitationtime.setText(spannableString);
				}
			}
		}else {
			rrrecitationtime.setText(spannableString);
		}
				if(!TextUtils.isEmpty(toRedWord)){
					String temp=content;
					int startNew=0;
					int startOld=0;
					while(temp.contains(toRedWord)){
						spannableString.setSpan(new ClickableSpan() {
							public void updateDrawState(TextPaint ds){
								super.updateDrawState(ds);
								ds.setColor(0xffff0000);
								ds.setUnderlineText(false);
							}
							@Override
							public void onClick(View widget) {
								// TODO Auto-generated method stub
							}
						}, startOld + temp.indexOf(toRedWord),startOld + temp.indexOf(toRedWord) + toRedWord.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						rrrecitationtime.setText(spannableString);
						rrrecitationtime.setMovementMethod(LinkMovementMethod.getInstance());
						startNew=temp.indexOf(toRedWord)+toRedWord.length();
						startOld+=startNew;
						temp=temp.substring(startNew);
					}
				}
				else{
					rrrecitationtime.setText(spannableString);
				}
			}
			public void showKeyBoard(){
				InputMethodManager inputManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}