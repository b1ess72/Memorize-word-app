package activity.control.user;

import general.base.op.SettingVariable;
import database.control.op.userInfoop;
import database.control.op.userLoginop;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore.Action;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import wgy.recitewords.bdc.R;

public class User_ForgetPassword extends ActivityGroup{
	Button btn_ForEncrypted,btn_ForPhone,btn_ForEmail;
	FrameLayout fl_RetrievePassword;
	FrameLayout user_usernamefl;
	TextView textShow;
	EditText edit_fgp_username;
	Button btn_Next;
	Button btn_pre=null;
	userInfoop userInfo=null;
	String UserName="";
	SettingVariable sv=null;
	userLoginop ulop=null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_forgetpassword);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init(){
		btn_ForEncrypted=(Button)findViewById(R.id.btn_ForEncrypted);
		btn_ForPhone=(Button)findViewById(R.id.btn_ForPhone);
		btn_ForEmail=(Button)findViewById(R.id.btn_ForEmail);
		fl_RetrievePassword=(FrameLayout)findViewById(R.id.fl_RetrievePassword);
		user_usernamefl=(FrameLayout)findViewById(R.id.user_usernamef1);
		userInfo=new userInfoop(this);
		ulop=new userLoginop(this);
		sv=new SettingVariable();
		textShow=(TextView)findViewById(R.id.textShow);
		edit_fgp_username=(EditText)findViewById(R.id.edit_fgp_username);
		btn_Next=(Button)findViewById(R.id.btn_Next);
		btn_pre=(Button)findViewById(R.id.btn_pre);
		btn_ForEncrypted.setOnClickListener(new MyButton());
		btn_ForPhone.setOnClickListener(new MyButton());
		btn_ForEmail.setOnClickListener(new MyButton());
		btn_Next.setOnClickListener(new MyButton());
		btn_pre.setOnClickListener(new MyButton());
	}
	private class MyButton implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btn_ForEncrypted:
				if(!UserName.equals("")){
					StartFrameActivity(fl_RetrievePassword,
							User_ForgetPassword.this,User_ForEncrypted.class);
				}else{
					Toast.makeText(User_ForgetPassword.this,"请写正确的用户名",
							Toast.LENGTH_SHORT).show();
				}break;
			case R.id.btn_ForPhone:
				if(!UserName.equals("")){
					StartFrameActivity(fl_RetrievePassword,
							User_ForgetPassword.this,User_ForPhone.class);
				}else{
					Toast.makeText(User_ForgetPassword.this,"请写正确的用户名",
							Toast.LENGTH_SHORT).show();
				}break;
			case R.id.btn_ForEmail:
				if(!UserName.equals("")){
					StartFrameActivity(fl_RetrievePassword,
							User_ForgetPassword.this,User_ForEmail.class);
				}else{
					Toast.makeText(User_ForgetPassword.this,"请写正确的用户名",
							Toast.LENGTH_SHORT).show();
				}break;
			case R.id.btn_Next:
				userInfo.setUsername(edit_fgp_username.getText().toString());
				if(userInfo.checkusername()>0){
					edit_fgp_username.setVisibility(View.GONE);
					btn_Next.setVisibility(View.GONE);
					textShow.setText("请选择找回方式");
					btn_ForEncrypted.setVisibility(View.VISIBLE);
					btn_ForPhone.setVisibility(View.VISIBLE);
					btn_ForEmail.setVisibility(View.VISIBLE);
					btn_pre.setText("上一步");
					UserName=edit_fgp_username.getText().toString();
				}else{
					Toast.makeText(User_ForgetPassword.this,"该用户名不存在",
							Toast.LENGTH_SHORT).show();
				}break;
			case R.id.btn_pre:
				if(btn_pre.getText().toString().trim().equals("返回"))
				{Intent intent=new Intent();
				intent.setClass(User_ForgetPassword.this,User_Login.class);
				startActivity(intent);
				}else 
				{
					fl_RetrievePassword.setVisibility(View.GONE);
					fl_RetrievePassword.removeAllViews();
					btn_ForEncrypted.setVisibility(View.GONE);
					btn_ForPhone.setVisibility(View.GONE);
					btn_ForEmail.setVisibility(View.GONE);
					user_usernamefl.setVisibility(View.VISIBLE);
					edit_fgp_username.setVisibility(View.VISIBLE);
					btn_Next.setVisibility(View.VISIBLE);
					textShow.setText("第一步：输入用户名");
					btn_pre.setText("返回");
				}break;
			}}}
	private void StartFrameActivity(FrameLayout fl,Context conthis,
			Class setclass){
		user_usernamefl.setVisibility(View.GONE);
		fl.setVisibility(View.VISIBLE);
		fl.removeAllViews();
		Intent intent=null;
		intent=new Intent(conthis,setclass);
		intent.putExtra("UserName",UserName);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window StartActivity=getLocalActivityManager().
				startActivity("StartActivity", intent);
		fl.addView(StartActivity.getDecorView(),
				ActionBar.LayoutParams.FILL_PARENT,
				ActionBar.LayoutParams.FILL_PARENT);
	}}

