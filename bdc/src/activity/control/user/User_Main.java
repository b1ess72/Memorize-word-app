package activity.control.user;

import database.control.op.userRealInfoop;
import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;
import general.base.op.SettingVariable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class User_Main extends Activity {
	TextView wel_word=null;
	Button user_editpassword=null;
	Button user_forgetpassword=null;
	Button user_editinformation=null;
	Button user_realcertification=null;
	Button user_delete=null;
	Button user_quit=null;
	SettingVariable sv=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	private void init()
	{
		wel_word=(TextView)findViewById(R.id.wel_word);
		user_editpassword=(Button)findViewById(R.id.user_editpassword);
		user_forgetpassword=(Button)findViewById(R.id.user_forgetpassword);
		user_editinformation=(Button)findViewById(R.id.user_editinfomation);
		user_realcertification=(Button)findViewById(R.id.user_realcertification);
		user_delete=(Button)findViewById(R.id.user_delete);
		user_quit=(Button)findViewById(R.id.user_quit);
		setinitwelcome();
		
		user_editpassword.setOnClickListener(new MyButton());
		user_forgetpassword.setOnClickListener(new MyButton());
		user_editinformation.setOnClickListener(new MyButton());
		user_realcertification.setOnClickListener(new MyButton());
		user_delete.setOnClickListener(new MyButton());
		user_quit.setOnClickListener(new MyButton());
		if(!sv.getUName().equals("admin"))
			user_delete.setVisibility(View.GONE);
	}
	class MyButton implements OnClickListener
	{
		public void onClick(View v){
			Intent intent=new Intent();
			userRealInfoop urifop=new userRealInfoop(User_Main.this);
			switch(v.getId())
			{
			case R.id.user_editpassword:
				intent.setClass(User_Main.this,User_EditPWD.class);
				break;
			case R.id.user_forgetpassword:
				intent.setClass(User_Main.this,User_ForgetPassword.class);break;
			case R.id.user_editinfomation:
				urifop.setUsername(sv.getUName());
				if(urifop.checkUserName())
					intent.setClass(User_Main.this,User_EditInfo.class);
				else 
					intent.setClass(User_Main.this,user_RealRegist.class);
				break;
			case R.id.user_realcertification:
				urifop.setUsername(sv.getUName());
				if(urifop.checkUserName())
					intent.setClass(User_Main.this,User_EditInfo.class);
				else
					intent.setClass(User_Main.this,user_RealRegist.class);
				break;
			case R.id.user_delete:
				intent.setClass(User_Main.this,User_Delete.class);break;
			case R.id.user_quit:
			intent.setClass(User_Main.this,BDC_Main.class);break;
			}
			startActivity(intent);
		}}
	void setinitwelcome()
	{
		sv=new SettingVariable();
		wel_word.setText(sv.getUName());
	}
}

