package activity.control.user;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import database.control.op.userInfoop;
import general.base.op.DateTimeOp;
import general.base.op.EditTextCheck;
import general.base.op.Passwordop;
import general.base.op.SettingVariable;
import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;

public class User_Regist extends Activity {
	EditText edit_username,edit_password,edit_repassword,edit_question,edit_answer;
	Button submit,back;
	userInfoop uop=null;
	TextView txt_01;
	EditTextCheck etcheck=null;
	int [] checkok=new int[10];
	Passwordop pwop=null;
	DateTimeOp dtop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_regist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	private void init(){
		
		edit_username=(EditText)findViewById(R.id.edit_username);
		edit_password=(EditText)findViewById(R.id.edit_password);
		edit_repassword=(EditText)findViewById(R.id.edit_repassword);
		edit_question=(EditText)findViewById(R.id.edit_question);
		edit_answer=(EditText)findViewById(R.id.edit_answer);
		submit=(Button)findViewById(R.id.submit);
		back=(Button)findViewById(R.id.back);
		txt_01=(TextView)findViewById(R.id.txt_01);
		txt_01.setTextColor(Color.rgb(255,0,0));
		
		uop=new userInfoop(this);
		pwop=new Passwordop();
		dtop=new DateTimeOp();
		submit.setOnClickListener(new MyButton());
		back.setOnClickListener(new MyButton());
		for(int i=0;i<10;i++)checkok[i]=0;
		etcheck=new EditTextCheck();
		etcheck.setEditTextInhibitInputSpace(edit_username);
		etcheck.setEditTextInhibitInputSpace(edit_password);
		etcheck.setEditTextInhibitInputSpace(edit_repassword);
		etcheck.setEditTextInhibitInputSpace(edit_question);
		etcheck.setEditTextInhibitInputSpace(edit_answer);
		
		edit_username.setOnFocusChangeListener(new FocusChangeListener());
		edit_password.setOnFocusChangeListener(new FocusChangeListener());
		edit_repassword.setOnFocusChangeListener(new FocusChangeListener());
		edit_question.setOnFocusChangeListener(new FocusChangeListener());
		edit_answer.setOnFocusChangeListener(new FocusChangeListener());		
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
		void checkEditText(int id){
			Boolean flag=true;
			EditText ettemp=(EditText)findViewById(id);
			Boolean checktemp=etcheck.notEmpty(ettemp.getText().toString());
			txt_01.setText("");
			switch (id) {
			case R.id.edit_username:
				etcheck.setErrorString("用户名");
				etcheck.setPwdcheck(-1);
				checkok[0]=etcheck.checkString(ettemp,true);
				if(checkok[0]==2)
				{
					uop.setUsername(edit_username.getText().toString().trim());
					if(uop.checkusername()>0)
					{txt_01.setText("用户名已被占用，请重新输入！");
					checkok[0]=1;
					}
				}else
				txt_01.setText(etcheck.getErrorString());
				break;
			case R.id.edit_password:
				flag=!edit_password.getText().toString().equals(edit_repassword.getText().toString());
				etcheck.setErrorString("密码");
				etcheck.setPwdcheck(checkok[2]);
				checkok[1]=etcheck.checkString(ettemp, flag);
				checkok[2]=etcheck.getPwdcheck();
				txt_01.setText(etcheck.getErrorString());
				break;
			case R.id.edit_repassword:
				flag=!edit_password.getText().toString().equals(edit_repassword.getText().toString());
				etcheck.setErrorString("确认密码");
				etcheck.setPwdcheck(checkok[1]);
				checkok[2]=etcheck.checkString(ettemp, flag);
				checkok[1]=etcheck.getPwdcheck();
				txt_01.setText(etcheck.getErrorString());
				break;
			case R.id.edit_question:
				etcheck.setErrorString("密保问题");
				etcheck.setPwdcheck(-1);
				checkok[3]=etcheck.checkString(ettemp, true);
				txt_01.setText(etcheck.getErrorString());
				break;
			case R.id.edit_answer:
				etcheck.setErrorString("密保问题答案");
				etcheck.setPwdcheck(-1);
				checkok[4]=etcheck.checkString(ettemp,true);
				txt_01.setText(etcheck.getErrorString());
				break;
			}
		}

	public class MyButton implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.submit:
				checkAllEditText();
				Boolean boolflagbool=true;
				int i=0;
				for(;i<5;i++)
					if(checkok[i]!=2){boolflagbool=false;break;}
				if(boolflagbool)
				{
					uop.setUsername(edit_username.getText().toString());
					uop.setPassword(pwop.md5Password(edit_password.getText().toString()));
					uop.setQuestion(pwop.getBase64(edit_question.getText().toString()));
					uop.setAnswer(pwop.getBase64(edit_answer.getText().toString()));
					uop.setReal_name("no");
					uop.setReg_time(dtop.getDateTimeNowLong());
					uop.setReg_time(getDateTimeNowLong());
					uop.setUState("1");
					long id1=uop.add();
					if(id1>=1){
						AlertDialog.Builder builder=
								new AlertDialog.Builder(User_Regist.this);
						SettingVariable sv=new SettingVariable();
						sv.setUName(edit_username.getText().toString());
						uop.setUsername(edit_username.getText().toString());
						uop.checkusername();
						sv.setUID(uop.getId1()+"");
						builder.setTitle("提示消息");
						builder.setMessage("注册成功，是否进行实名认证?");
						builder.setPositiveButton("是",
								new DialogInterface.OnClickListener() {		
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Intent intents=new Intent();
										intents.setClass(User_Regist.this,user_RealRegist.class);
										startActivity(intents);
									}
								});
						builder.setNegativeButton("否",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Intent intents=new Intent();
										intents.setClass(User_Regist.this,
												BDC_Main.class);
										startActivity(intents);
									}
								});
						builder.show();
					}
						else
							Toast.makeText(User_Regist.this,"注册失败！",Toast.LENGTH_SHORT).show();	
					}
				else{
					Toast.makeText(User_Regist.this,"第"+(i+1)+"个控件验证失败",0).show();	
				}
						break;
						case R.id.back:
							Intent intent=new Intent();
							intent.setClass(User_Regist.this,User_Login.class);
							startActivity(intent);
							break;
				}		
			}}
	void checkAllEditText()
	{
		checkEditText(R.id.edit_username);
		checkEditText(R.id.edit_password);
		checkEditText(R.id.edit_repassword);
		checkEditText(R.id.edit_question);
		checkEditText(R.id.edit_answer);
		}
	public String getDateTimeNowLong()
	{
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate=new Date(System.currentTimeMillis());
		String DownLoadTime1=formatter.format(curDate);
		return DownLoadTime1;
		
	}
}  
