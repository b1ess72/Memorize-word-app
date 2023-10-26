package activity.control.user;

import database.control.op.userInfoop;
import general.base.op.EditTextCheck;
import general.base.op.SettingVariable;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_Delete extends Activity {
	TextView wel_word=null;
	EditText edit_username=null;
	TextView txt_dection=null;
	Button btn_delete=null;
	Button user_quit=null;
	SettingVariable sv=null;
	userInfoop uop=null;
	EditTextCheck etcheck=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.user_delete);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		wel_word=(TextView)findViewById(R.id.wel_word);
		edit_username=(EditText)findViewById(R.id.edit_username);
		txt_dection=(TextView)findViewById(R.id.txt_dection);
		btn_delete=(Button)findViewById(R.id.btn_delete);
		user_quit=(Button)findViewById(R.id.user_quit);
		setinitwelcome();
		
		uop=new userInfoop(this);
		etcheck=new EditTextCheck();
		etcheck.setEditTextInhibitInputSpace(edit_username);
		
		btn_delete.setOnClickListener(new MyButton());
		user_quit.setOnClickListener(new MyButton());
	}
	class MyButton implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId())
			{ case R.id.btn_delete:
				if(!edit_username.getText().toString().trim().equals("")){
					uop.setUsername(edit_username.getText().toString().trim());
					if(uop.deletebyusername(User_Delete.this))
					{
						Toast.makeText(User_Delete.this,"用户删除成功！",0).show();
					}
					else 
						Toast.makeText(User_Delete.this,"用户删除失败，"+"用户不存在或 操作失败，请联系管理员！",0).show();
					}
					break;
					case R.id.user_quit:
						intent.setClass(User_Delete.this,User_Main.class);
						startActivity(intent);
						break;
					}}}
void setinitwelcome()
{
	sv=new SettingVariable();
	wel_word.setText(sv.getUName());
}
}
