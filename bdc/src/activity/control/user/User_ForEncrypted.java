package activity.control.user;

import wgy.recitewords.bdc.R;
import database.control.op.userInfoop;
import database.control.op.userRealInfoop;
import general.base.op.CAPTCHAcode;
import general.base.op.Passwordop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class User_ForEncrypted extends Activity {
	EditText fpg_answer,fgp_Yz;
	TextView txt_question;
	ImageView fgp_image;
	Button btn_rgp_complete;
	Passwordop psop=null;
	String getCode=null;
	userRealInfoop userRealInfo=null;
	userInfoop userInfo=null;
	CAPTCHAcode code=null;
	String answer="";
	String UserName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_forencrypted);
		init();
	}
	void init(){
		fpg_answer=(EditText)findViewById(R.id.fpg_answer);
		fgp_Yz=(EditText)findViewById(R.id.fgp_Yz);
		txt_question=(TextView)findViewById(R.id.txt_question);
		fgp_image=(ImageView)findViewById(R.id.fgp_image);
		btn_rgp_complete=(Button)findViewById(R.id.btn_rgp_complete);
		
		userInfo=new userInfoop(this);
		userRealInfo=new userRealInfoop(this);
		psop=new Passwordop();
		
		Intent intent=getIntent();
		UserName=intent.getStringExtra("UserName");
		userInfo.setUsername(UserName);
		userInfo.getallbyusername();
		txt_question.setText("密保问题："
				+psop.getFromBase64(userInfo.getQuestion()));
		code=new CAPTCHAcode();
		fgp_image.setImageBitmap(code.getBitmap());
		getCode=code.getCode();
		
		fgp_image.setOnClickListener(new MyButton());
		btn_rgp_complete.setOnClickListener(new MyButton());
	}
	private class MyButton implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btn_rgp_complete:
				if(EditTextDetection()==true){
					if(fpg_answer.getText().toString().
							equals(psop.getFromBase64(userInfo.getAnswer()))){
						Intent intent=new Intent();
						intent.setClass(User_ForEncrypted.this,User_EditPWD.class);
						intent.putExtra("hide",true);
						intent.putExtra("UserName",UserName);
						startActivity(intent);
					}else{
						Toast.makeText(User_ForEncrypted.this,"密保错误",
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
		if(fpg_answer.getText().toString().equals("")){
			Toast.makeText(User_ForEncrypted.this,
					"密保答案不能为空",Toast.LENGTH_SHORT).show();
			return false;
		}else if(v_code==null||v_code.equals("")){
			Toast.makeText(User_ForEncrypted.this,
					"验证码为空",Toast.LENGTH_SHORT).show();
			return false;
		}else if(!exChangeLower(v_code).equals(exChangeLower(getCode))){
			Toast.makeText(User_ForEncrypted.this,
					"验证码错误",Toast.LENGTH_SHORT).show();
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

