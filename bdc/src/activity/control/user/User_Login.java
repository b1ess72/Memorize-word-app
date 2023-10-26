package activity.control.user;

import general.base.op.CAPTCHAcode;
import general.base.op.EditTextCheck;
import general.base.op.Passwordop;
import general.base.op.SettingVariable;
import database.control.op.userInfoop;
import database.control.op.userLoginop;
import android.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;

public class User_Login extends Activity {
	EditText edit_username,edit_password;
	Button btn_Login;
	TextView txt_forgetpassword=null;
	TextView txt_register;
	
	userInfoop uop=null;
	EditTextCheck etcheck=null;
	Passwordop pwop=null;
	private boolean mIsExit=false;
	SettingVariable sv=null;
	
	ImageView vc_image;
	String getCode=null;
	EditText vc_code;
	CAPTCHAcode code=null;
	userLoginop ulop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		edit_username=(EditText)findViewById(R.id.edit_username);
		edit_password=(EditText)findViewById(R.id.edit_password);
		btn_Login=(Button)findViewById(R.id.btn_Login);
		txt_forgetpassword=(TextView)findViewById(R.id.txt_ForgetPassword);
		txt_register=(TextView)findViewById(R.id.txt_Register);
		vc_image=(ImageView)findViewById(R.id.vc_image);
		vc_code=(EditText)findViewById(R.id.vc_code);
		
		MyOnclick click=new MyOnclick();
		btn_Login.setOnClickListener(click);
		txt_forgetpassword.setOnClickListener(click);
		txt_register.setOnClickListener(click);
		vc_image.setOnClickListener(click);
		
		sv=new SettingVariable();
		uop=new userInfoop(this);
		pwop=new Passwordop();
		etcheck=new EditTextCheck();
		code=new CAPTCHAcode();
		vc_image.setImageBitmap(code.getBitmap());
		getCode=code.getCode();
		etcheck.setEditTextInhibitInputSpace(edit_username);
		etcheck.setEditTextInhibitInputSpace(edit_password);
		
		ulop=new userLoginop(this);
		Intent intent=getIntent();
		String Loginout=intent.getStringExtra("Loginout");
		if(Loginout==null||(!Loginout.equals("out")))
			if(ulop.tabbleIsExist("userLogin"))
			{ Boolean checkBoolean=ulop.checkuserState();
			if(checkBoolean)
			{ if(!ulop.checktime(7))
			{
				sv.setUName(ulop.getUsername());
				uop.setUsername(ulop.getUsername());
				uop.checkusername();
				sv.setUID(uop.getId1()+"");
				ulop.login_new();
				Intent intent1=new Intent();
				intent1.setClass(User_Login.this,BDC_Main.class);
				startActivity(intent1);
			}
			}
			}
	}	
		class MyOnclick implements OnClickListener{

			@Override
			public void onClick(View v) {
				int flag=1;
				// TODO Auto-generated method stub
				String usernameString=edit_username.getText().toString();
				String passwordString=edit_password.getText().toString();
				String v_code=vc_code.getText().toString();
				Intent intent=new Intent();
				switch(v.getId()){
				case R.id.vc_image:
					vc_image.setImageBitmap(code.getBitmap());
					getCode=code.getCode();
					flag=0;
					break;			
				case R.id.btn_Login:
					if(etcheck.notEmpty(usernameString)||etcheck.notEmpty(passwordString)||etcheck.notEmpty(v_code))
					{
						Toast.makeText(User_Login.this,"输入的用户名、密码或验证码为空，请重新输入！",Toast.LENGTH_SHORT).show();
					}
					else 
						if(!exChangeLower(v_code).equals(exChangeLower(getCode)))
						{
							Toast.makeText(User_Login.this,"验证码错误",
									Toast.LENGTH_SHORT).show();
							flag=0;
						}
						else{
							uop.setPassword(pwop.md5Password(passwordString));
							uop.setUsername(usernameString);
							if(uop.checknameandpwd())
							{
								sv.setUName(usernameString);
								sv.setUID(uop.getId1()+"");
								ulop.setUsername(usernameString);
								long id=ulop.add();
								if(id>0)
								{
								intent.setClass(User_Login.this,BDC_Main.class);
								startActivity(intent);
							}
							}
						else{
							Toast.makeText(User_Login.this,"输入的用户名或密码错误，请重新输入！！",
									Toast.LENGTH_SHORT).show();
							flag=0;
						}
					}
				break;
				case R.id.txt_ForgetPassword:
					intent.setClass(User_Login.this, User_ForgetPassword.class);
					break;
				case R.id.txt_Register:
				   intent.setClass(User_Login.this,User_Regist.class);
				   break;
				}
				if(flag==1)startActivity(intent);
			}
		}
		public static String exChangeLower(String str){
			String s=str.toLowerCase();
			return s;
		}
		public boolean onKeyDown(int keyCode,KeyEvent event){
			if(keyCode==KeyEvent.KEYCODE_BACK){
				if(mIsExit){
					this.finish();
				}else{
					Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
					mIsExit=true;
					new Handler().postDelayed(new Runnable() {	
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mIsExit=false;
						}
					},2000);
				}
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
	}


