package activity.control.user;

import wgy.recitewords.bdc.R;
import general.base.op.CAPTCHAcode;
import database.control.op.userInfoop;
import database.control.op.userRealInfoop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class User_ForEmail extends Activity {
	EditText fgp_email,fgp_Yz;
	ImageView fgp_image;
	Button btn_rgp_complete;
	
	userRealInfoop userRealInfo=null;
	userInfoop userInfo=null;
	CAPTCHAcode code=null;
	
	String getCode=null;
	String UserName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_foremail);
		init();
	}
	void init()
	{
		userInfo=new userInfoop(this);
		userRealInfo=new userRealInfoop(this);
		
		fgp_email=(EditText)findViewById(R.id.fpg_email);
		fgp_Yz=(EditText)findViewById(R.id.fgp_Yz);
		fgp_image=(ImageView)findViewById(R.id.fgp_image);
		btn_rgp_complete=(Button)findViewById(R.id.btn_rgp_complete);
		
		Intent intent=getIntent();
		UserName=intent.getStringExtra("UserName");
		
		code=new CAPTCHAcode();
		fgp_image.setImageBitmap(code.getBitmap());
		getCode=code.getCode();
		
		fgp_image.setOnClickListener(new MyButton());
		btn_rgp_complete.setOnClickListener(new MyButton());
	}
	private class MyButton implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btn_rgp_complete:
				if(EditTextDetection()==true){
					userRealInfo.setUsername(UserName);
					if(userRealInfo.checkUserName()==true){
						if(userRealInfo.getEmail().equals(fgp_email.getText().toString())){
							Intent intent=new Intent();
							intent.putExtra("hide",true);
							intent.putExtra("UserName",UserName);
							intent.setClass(User_ForEmail.this,User_EditPWD.class);
							startActivity(intent);
						}else{
							Toast.makeText(User_ForEmail.this,"信息错误",
									Toast.LENGTH_SHORT).show();
						}
					}
					else{
						Toast.makeText(User_ForEmail.this,
								"您未进行实名认证，无法通过此方法找回密码，请选择其他方式找回！",
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.fgp_image:
				fgp_image.setImageBitmap(code.getBitmap());
				getCode=code.getCode();
				break;
			}
		}
	}
	private boolean EditTextDetection(){
		String v_code=fgp_Yz.getText().toString().trim();
		if(fgp_email.getText().toString().equals("")){
			Toast.makeText(User_ForEmail.this,"邮箱不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if(v_code==null||v_code.equals("")){
			Toast.makeText(User_ForEmail.this,"验证码为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if(!exChangeLower(v_code).equals(exChangeLower(getCode))){
			Toast.makeText(User_ForEmail.this,"验证码错误",
					Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}
	public static String exChangeLower(String str){
		String s=str.toLowerCase();
		return s;
	}
}

