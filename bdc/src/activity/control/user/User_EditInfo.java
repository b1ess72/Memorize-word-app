package activity.control.user;

import wgy.recitewords.bdc.R;
import database.control.op.userInfoop;
import database.control.op.userRealInfoop;
import general.base.op.CAPTCHAcode;
import general.base.op.EditTextCheck;
import general.base.op.Passwordop;
import general.base.op.SettingVariable;
import activity.control.user.User_Delete.MyButton;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class User_EditInfo extends Activity {
	EditText edit_student,edit_name,edit_identity,edit_phone,edit_email,edit_Yz;
	EditText edit_question,edit_answer;
	TextView txt_01;
	Button submit,pre;
	ImageView vc_image;
	CAPTCHAcode code=null;
	userRealInfoop urifop=null;
	userInfoop uop=null;
	String getCode=null;
	int [] checkok=new int[10];
	EditTextCheck etcheck=null;
	
	SettingVariable sv=null;
	Passwordop pwop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_editinfo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		pwop=new Passwordop();
		urifop=new userRealInfoop(this);
		uop=new userInfoop(this);
		sv=new SettingVariable();
		MyButton click=new MyButton();
		edit_question=(EditText)findViewById(R.id.edit_question);
		edit_answer=(EditText)findViewById(R.id.edit_answer);
		edit_name=(EditText)findViewById(R.id.edit_name);
		edit_student=(EditText)findViewById(R.id.edit_student);
		edit_identity=(EditText)findViewById(R.id.edit_identity);
		edit_phone=(EditText)findViewById(R.id.edit_phone);
		edit_email=(EditText)findViewById(R.id.edit_email);
		vc_image=(ImageView)findViewById(R.id.vc_image);
		edit_Yz=(EditText)findViewById(R.id.edit_Yz);
		submit=(Button)findViewById(R.id.submit);
		txt_01=(TextView)findViewById(R.id.txt_01);
		txt_01.setTextColor(Color.rgb(255,0,0));
		pre=(Button)findViewById(R.id.pre);
		
		etcheck=new EditTextCheck();
		etcheck.setEditTextInhibitInputSpace(edit_question);
		etcheck.setEditTextInhibitInputSpace(edit_answer);
		etcheck.setEditTextInhibitInputSpace(edit_name);
		etcheck.setEditTextInhibitInputSpace(edit_student);
		etcheck.setEditTextInhibitInputSpace(edit_identity);
		etcheck.setEditTextInhibitInputSpace(edit_phone);
		etcheck.setEditTextInhibitInputSpace(edit_email);
		etcheck.setEditTextInhibitInputSpace(edit_Yz);
		
		edit_question.setOnFocusChangeListener(new FocusChangeListener());
		edit_answer.setOnFocusChangeListener(new FocusChangeListener());
		edit_name.setOnFocusChangeListener(new FocusChangeListener());
		edit_student.setOnFocusChangeListener(new FocusChangeListener());
		edit_identity.setOnFocusChangeListener(new FocusChangeListener());
		edit_phone.setOnFocusChangeListener(new FocusChangeListener());
		edit_email.setOnFocusChangeListener(new FocusChangeListener());
		edit_Yz.setOnFocusChangeListener(new FocusChangeListener());
		for(int i=0;i<10;i++)checkok[i]=0;
		
		submit.setOnClickListener(click);
		pre.setOnClickListener(click);
		vc_image.setOnClickListener(click);
		code=new CAPTCHAcode();
		vc_image.setImageBitmap(code.getBitmap());
		getCode=code.getCode();
		
		showInfo();
	}
	void showInfo()
	{
		uop.setUsername(sv.getUName());
		urifop.setUsername(sv.getUName());
		if(uop.qurryqanda()&&urifop.qurry_single()){
			edit_question.setText(pwop.getFromBase64(uop.getQuestion()));
			edit_answer.setText(pwop.getFromBase64(uop.getAnswer()));
			
			edit_name.setText(urifop.getName());
			edit_student.setText(urifop.getStudent());
			edit_identity.setText(urifop.getIdentity());
			edit_phone.setText(urifop.getPhone());
			edit_email.setText(urifop.getEmail());
		}else{
			Toast.makeText(User_EditInfo.this,
					"操作失败，可能由于未进行实名操作，请联系管理员！",0).show();
		}
	}
	private class MyButton implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.submit:
				checkAllEditText();
				Boolean boolflagbool=true;
				int i=0;
				for(;i<8;i++)
					if(checkok[i]!=2){boolflagbool=false;break;}
				if(boolflagbool)
				{ if(!exChangeLower(edit_Yz.getText().toString()).
						equals(exChangeLower(getCode))){
					Toast.makeText(User_EditInfo.this,"验证码错误",
							Toast.LENGTH_SHORT).show();
				}else{
					uop.setUsername(sv.getUName());
					uop.setQuestion(pwop.getBase64(edit_question.getText().toString()));
					uop.setAnswer(pwop.getBase64(edit_answer.getText().toString()));
					if(uop.edit_Info())
					{
						urifop.setUsername(sv.getUName());
						urifop.setName(edit_name.getText().toString());
						urifop.setStudent(edit_student.getText().toString());
						urifop.setIdentity(edit_identity.getText().toString());
						urifop.setPhone(edit_phone.getText().toString());
						urifop.setEmail(edit_email.getText().toString());
						boolean idt=urifop.editReal_Info();
						if(idt)
						{
							Toast.makeText(User_EditInfo.this,"修改成功",0).show();
							intent.setClass(User_EditInfo.this,User_Main.class);
							startActivity(intent);
						}
						else{
							Toast.makeText(User_EditInfo.this,"修改失败，请与管理员联系！",
									Toast.LENGTH_SHORT).show();
						}
					}}
				}else{
					Toast.makeText(User_EditInfo.this,"修改失败，第"+(i+1)+"个控件检测失败！",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.vc_image:
				vc_image.setImageBitmap(code.getBitmap());
				getCode=code.getCode();
				break;
			case R.id.pre:
				intent.setClass(User_EditInfo.this,User_Main.class);
				startActivity(intent);
				break;
			}
		}
	}
	String exChangeLower(String str){
		String s=str.toLowerCase();
		return s;
	}
	class FocusChangeListener implements OnFocusChangeListener
	{
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(!hasFocus){
				checkEditText(v.getId());
			}else{
			}
		}
	}
	void checkAllEditText()
	{
		checkEditText(R.id.edit_question);
		checkEditText(R.id.edit_answer);
		checkEditText(R.id.edit_name);
		checkEditText(R.id.edit_student);
		checkEditText(R.id.edit_identity);
		checkEditText(R.id.edit_phone);
		checkEditText(R.id.edit_email);
		checkEditText(R.id.edit_Yz);
	}
	void checkEditText(int id){
		EditText ettemp=(EditText)findViewById(id);
		Boolean checktemp=etcheck.notEmpty(ettemp.getText().toString());
		String errorname="";int checkid=-1;
		Boolean checkresult=false;txt_01.setText("");
		switch(id)
		{
		case R.id.edit_question:
			errorname="密保问题";checkid=0;
			checkresult=true;break;
		case R.id.edit_answer:
			errorname="密保答案";checkid=1;
			checkresult=true;break;
		case R.id.edit_name:
			errorname="姓名";checkid=2;
			checkresult=etcheck.ischinaname(edit_name.getText().toString());
			break;
		case R.id.edit_student:
			errorname="学号";checkid=3;
			checkresult=etcheck.isNunmber(edit_student.getText().toString());
			break;
		case R.id.edit_identity:
			errorname="身份证";checkid=4;
			checkresult=etcheck.vId(edit_identity.getText().toString());
			break;
		case R.id.edit_phone:
			errorname="电话号码";checkid=5;
			checkresult=etcheck.isPhone(edit_phone.getText().toString());
			break;
		case R.id.edit_email:
			errorname="E-mail";checkid=6;
			checkresult=etcheck.isE_mail(edit_email.getText().toString());
			break;
		case R.id.edit_Yz:
			errorname="验证码";checkid=7;
			checkresult=true;
			break;
		}
		txt_01.setText("");
		if(!checktemp){
			if(checkresult)
			{ checkok[checkid]=2;}
			else {
				txt_01.setText("输入的"+errorname+"格式错误！");
				checkok[checkid]=1;
			}
		}
		else{txt_01.setText("请输入"+errorname);checkok[checkid]=0;}
	}
}

	
	
