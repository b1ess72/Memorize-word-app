package activity.control.user;

import database.control.op.userInfoop;
import database.control.op.userLoginop;
import general.base.op.EditTextCheck;
import general.base.op.Passwordop;
import general.base.op.SettingVariable;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_EditPWD extends Activity {
	TextView text_tilte=null;
	TextView wel_word=null;
	EditText edit_username=null;
	EditText edit_password_old=null;
	EditText edit_password_first=null;
	EditText edit_password_second=null;
	TextView txt_dection=null;
	Button btn_editPassword_finish=null;
	Button btn_repwd=null;
	Button user_quit=null;
	SettingVariable sv=null;
	userInfoop uop=null;
	EditTextCheck etcheck=null;
	int [] checkok=new int[10];
	Passwordop pwop=null;
	
	boolean hide=false;
	public String UserName="";
	userLoginop ulop=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_editpwd);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	private void init()
	{
		text_tilte=(TextView)findViewById(R.id.text_tilte);
		wel_word=(TextView)findViewById(R.id.wel_word);
		edit_username=(EditText)findViewById(R.id.edit_username);
		edit_password_old=(EditText)findViewById(R.id.edit_password_old);
		edit_password_first=(EditText)findViewById(R.id.edit_password_first);
		edit_password_second=(EditText)findViewById(R.id.edit_password_second);
		txt_dection=(TextView)findViewById(R.id.txt_dection);
		btn_editPassword_finish=(Button)findViewById(R.id.btn_editPassword_finish);
		btn_repwd=(Button)findViewById(R.id.btn_repwd);
		user_quit=(Button)findViewById(R.id.user_quit);
		setinitwelcome();
		
		pwop=new Passwordop();
		uop=new userInfoop(this);
		etcheck=new EditTextCheck();
		etcheck.setEditTextInhibitInputSpace(edit_password_old);
		etcheck.setEditTextInhibitInputSpace(edit_password_first);
		etcheck.setEditTextInhibitInputSpace(edit_password_second);
		for(int i=0;i<10;i++)checkok[i]=0;
		ulop=new userLoginop(this);
		Intent intent=getIntent();
		hide=intent.getBooleanExtra("hide",false);
		UserName=intent.getStringExtra("UserName");
		
		if(hide)
		{text_tilte.setText("请输入 新密码");
		edit_password_old.setVisibility(View.GONE);
		edit_username.setVisibility(View.GONE);
		btn_repwd.setVisibility(View.GONE);
		}
		else {
		if(!sv.getUName().equals("admin"))
		{edit_username.setVisibility(View.GONE);
		btn_repwd.setVisibility(View.GONE);
		}
		else{
			edit_password_old.setVisibility(View.GONE);
			edit_password_second.setVisibility(View.GONE);
			btn_editPassword_finish.setVisibility(View.GONE);
		}
		}
		edit_password_old.setOnFocusChangeListener(new FocusChangeListener());
		edit_password_first.setOnFocusChangeListener(new FocusChangeListener());
		edit_password_second.setOnFocusChangeListener(new FocusChangeListener());
		
		btn_editPassword_finish.setOnClickListener(new MyButton());
		user_quit.setOnClickListener(new MyButton());
		btn_repwd.setOnClickListener(new MyButton());
	}
	class FocusChangeListener implements OnFocusChangeListener
	{
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(!hasFocus){
				checkEditText(v.getId());
			}else{ }
		}}
	void checkEditText(int id){
		Boolean flag=true;
		EditText ettemp=(EditText)findViewById(id);
		Boolean checktemp=etcheck.notEmpty(ettemp.getText().toString());
		txt_dection.setText("");
		switch(id)
		{ case R.id.edit_password_old:
			if(!checktemp){txt_dection.setText("");checkok[0]=2;}
			else{txt_dection.setText("旧密码不能为空");checkok[0]=0;}break;
		case R.id.edit_password_first:
			flag=!edit_password_first.getText().toString().equals(
					edit_password_second.getText().toString());
			etcheck.setErrorString("密码");
			etcheck.setPwdcheck(checkok[2]);
			checkok[1]=etcheck.checkString(ettemp, flag);
			checkok[2]=etcheck.getPwdcheck();
			txt_dection.setText(etcheck.getErrorString());
			break;
		case R.id.edit_password_second:
			flag=!edit_password_first.getText().toString().equals(edit_password_second.getText().toString());
			etcheck.setErrorString("确认密码");
			etcheck.setPwdcheck(checkok[1]);
			checkok[2]=etcheck.checkString(ettemp, flag);
			checkok[1]=etcheck.getPwdcheck();
			txt_dection.setText(etcheck.getErrorString());
			break;
		}}
	class MyButton implements OnClickListener
	{
		public void onClick(View v){
			Intent intent=new Intent();
			switch(v.getId())
			{
			case R.id.btn_editPassword_finish:
				checkAllEditText();
				Boolean boolflagbool=true;
				if(hide)
				{ boolflagbool=false;
				if(checkok[1]==2&&checkok[2]==2) boolflagbool=true;}
				else{
				for(int i=0;i<=2;i++)
					if(checkok[i]!=2){boolflagbool=false;break;}
					}
				if(boolflagbool){
					if(hide)
					{ editpwd(3);
					intent.putExtra("Loginout","out");
					intent.setClass(User_EditPWD.this,User_Login.class);
					}
					else {
						editpwd(1);
						intent.setClass(User_EditPWD.this,User_Main.class);
						}
					    startActivity(intent);	
				}else{
					Toast.makeText(User_EditPWD.this,
							"输入的密码为空或格式不正确，请重新输入！",0).show();
				}
				break;
			case R.id.user_quit:
				if(hide)
				{ulop.setUsername(sv.getUName());
				sv.setUName("");
				sv.setUID("");
				ulop.down();
				intent.putExtra("Loginout","out");
				intent.setClass(User_EditPWD.this,User_Login.class);
				}
				else{
				intent.setClass(User_EditPWD.this,User_Main.class);}
				startActivity(intent);
				break;
			case R.id.btn_repwd:
				if(edit_username.getText().toString().trim().equals("")||
				edit_password_first.getText().toString().trim().equals(""))
					Toast.makeText(User_EditPWD.this,"用户名或密码为空，请重新输入！",0).show();
				else {
					editpwd(2);
				}}
		}
		void editpwd(int flag)
		{ String errorString="";
		if(flag==1)
		{errorString="修改";
		uop.setUsername(sv.getUName());
		uop.setPassword(pwop.md5Password(
				edit_password_old.getText().toString().trim()));
		if(uop.checknameandpwd())
		{
			uop.setPassword(pwop.md5Password(
					edit_password_first.getText().toString().trim()));
			flag=4;
		}
		else 
			Toast.makeText(User_EditPWD.this,
					"旧密码输入错误，请重新输入！",0).show();
		}
		else if(flag==2){
			errorString="重置";
			uop.setUsername(edit_username.getText().toString().trim());
			uop.setPassword(pwop.md5Password
					(edit_password_first.getText().toString().trim()));
			flag=4;
		}else if(flag==3)
		{errorString="找回";
		uop.setUsername(UserName);
		uop.setPassword(pwop.md5Password
				(edit_password_first.getText().toString().trim()));
		flag=4;
		}
		if(flag==4)
		{
			if(uop.editpassword())
			Toast.makeText(User_EditPWD.this,"密码"+errorString+"成功！",0).show();
			else
				Toast.makeText(User_EditPWD.this,"密码"+errorString+"失败，请联系管理员！",0).show();
			}}
		}
	void checkAllEditText()
	{
		checkEditText(R.id.edit_password_old);
		checkEditText(R.id.edit_password_first);
		checkEditText(R.id.edit_password_second);
		}
	void setinitwelcome()
	{
		sv=new SettingVariable();
		wel_word.setText(sv.getUName());
		
	}
}

	