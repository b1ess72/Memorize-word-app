package activity.control.user;

import database.control.op.userInfoop;
import database.control.op.userRealInfoop;
import general.base.op.CAPTCHAcode;
import general.base.op.EditTextCheck;
import general.base.op.SettingVariable;
import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class user_RealRegist extends Activity {
	EditText edit_student,edit_name,edit_identity,edit_phone,edit_email,edit_Yz;
	Button submit,back;
	CAPTCHAcode code=null;
	userRealInfoop urifop=null;
	ImageView vc_image;
	TextView txt_01;
	String getCode=null;
	int [] checkok=new int[10];
	EditTextCheck etcheck=null;
	userInfoop uifop=null;
	SettingVariable sv=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_realregist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	private void init(){
		edit_name=(EditText)findViewById(R.id.edit_name);
		edit_student=(EditText)findViewById(R.id.edit_student);
		edit_identity=(EditText)findViewById(R.id.edit_identity);
		edit_phone=(EditText)findViewById(R.id.edit_phone);
		edit_email=(EditText)findViewById(R.id.edit_email);
		edit_Yz=(EditText)findViewById(R.id.edit_Yz);
		txt_01=(TextView)findViewById(R.id.txt_01);
		txt_01.setTextColor(Color.rgb(255,0,0));
		
		vc_image=(ImageView)findViewById(R.id.vc_image);
		
		submit=(Button)findViewById(R.id.submit);
		back=(Button)findViewById(R.id.back);
		
		sv=new SettingVariable();
		urifop=new userRealInfoop(this);
		uifop=new userInfoop(this);
		code=new CAPTCHAcode();
		etcheck=new EditTextCheck();
		
		etcheck.setEditTextInhibitInputSpace(edit_name);
		etcheck.setEditTextInhibitInputSpace(edit_student);
		etcheck.setEditTextInhibitInputSpace(edit_identity);
		etcheck.setEditTextInhibitInputSpace(edit_phone);
		etcheck.setEditTextInhibitInputSpace(edit_email);
		etcheck.setEditTextInhibitInputSpace(edit_Yz);
		
		edit_name.setOnFocusChangeListener(new FocusChangeListener());
		edit_student.setOnFocusChangeListener(new FocusChangeListener());
		edit_identity.setOnFocusChangeListener(new FocusChangeListener());
		edit_phone.setOnFocusChangeListener(new FocusChangeListener());
		edit_email.setOnFocusChangeListener(new FocusChangeListener());
		edit_Yz.setOnFocusChangeListener(new FocusChangeListener());
		for(int i=0;i<10;i++)checkok[i]=0;
		vc_image.setImageBitmap(code.getBitmap());
		getCode=code.getCode();
		
		vc_image.setOnClickListener(new MyButton());
		submit.setOnClickListener(new MyButton());
		back.setOnClickListener(new MyButton());
	}
	class FocusChangeListener implements OnFocusChangeListener
	{
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(!hasFocus){
				checkEditText(v.getId());
			}
			else{
			}}
			}
	void checkEditText(int id){
		EditText ettemp=(EditText)findViewById(id);
		Boolean checktemp=etcheck.notEmpty(ettemp.getText().toString());
		String errorname=""; int checkid=-1;
		Boolean checkresult=false;
		switch(id)
		{
		case R.id.edit_name:
			errorname="姓名";checkid=0;
			checkresult=etcheck.ischinaname(edit_name.getText().toString());
			break;
		case R.id.edit_student:
			errorname="学号";checkid=1;
			checkresult=etcheck.isNunmber(edit_student.getText().toString());
			break;
		case R.id.edit_identity:
			errorname="身份证号";checkid=2;
			checkresult=etcheck.vId(edit_identity.getText().toString());
			break;
		case R.id.edit_phone:
			errorname="电话号码";checkid=3;
			checkresult=etcheck.isPhone(edit_phone.getText().toString());
			break;
		case R.id.edit_email:
			errorname="E-mail";checkid=4;
			checkresult=etcheck.isE_mail(edit_email.getText().toString());
			break;
		case R.id.edit_Yz:
			errorname="验证码";checkid=5;
			checkresult=true;
			break;
		}
		txt_01.setText("");
		if(!checktemp){
			if(checkresult)
			{ checkok[checkid]=2;}
			else{
				txt_01.setText("输入的"+errorname+"格式错误!");
				checkok[checkid]=1;
			}
		}
		else{txt_01.setText("请输入"+errorname);checkok[checkid]=0;}
	}
	private class MyButton implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.submit:
				checkAllEditText();
				Boolean boolflagbool=true;
				int i=0;
				for(;i<5;i++)
					if(checkok[i]!=2){boolflagbool=false;break;}
				if(boolflagbool)
				{
					urifop.setUsername(sv.getUName());
					urifop.setName(edit_name.getText().toString());
					urifop.setStudent(edit_student.getText().toString());
					urifop.setIdentity(edit_identity.getText().toString());
					urifop.setPhone(edit_phone.getText().toString());
					urifop.setEmail(edit_email.getText().toString());
					
					long idt=urifop.UserRegistOfRealInfo();
					if(idt>0)
					{
						uifop.setUsername(sv.getUName());
						uifop.setReal_name(idt+"");
						uifop.editreal_name();
						Toast.makeText(user_RealRegist.this,"认证成功",
								Toast.LENGTH_SHORT).show();
						intent.setClass(user_RealRegist.this,BDC_Main.class);
						startActivity(intent);}
					else{
						Toast.makeText(user_RealRegist.this,"认证失败，已经认证，"+
					"不能重复认证",Toast.LENGTH_SHORT).show();
					}}
				else {
					Toast.makeText(user_RealRegist.this,"第"+(i+1)+
							"个控件的输入内容检测失败，请检查后重新输入！", 
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.vc_image:
				vc_image.setImageBitmap(code.getBitmap());
				getCode=code.getCode();
				break;
			case R.id.back:
			}}
	}
	void checkAllEditText()
	{   checkEditText(R.id.edit_name);
	    checkEditText(R.id.edit_student);
	    checkEditText(R.id.edit_identity);
	    checkEditText(R.id.edit_phone);
	    checkEditText(R.id.edit_email);
	    checkEditText(R.id.edit_Yz);
	}
}
