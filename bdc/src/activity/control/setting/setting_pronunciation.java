package activity.control.setting;

import java.util.ArrayList;
import java.util.List;

import wgy.recitewords.bdc.R;

import database.control.op.Settingop;
import general.base.op.SettingVariable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class setting_pronunciation extends Activity {
	private EditText soundRight;
	private CheckBox showWord;
	Button determineModify=null;
	Button returnModify=null;
	SettingVariable sv=null;
	Intent intent=null;
	Settingop stop=null;
	List<Settingop> list=new ArrayList<Settingop>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.setting_pronunciation);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title);
		init();
	}
	void init()
	{
		soundRight=(EditText)findViewById(R.id.soundRight);
		showWord=(CheckBox)findViewById(R.id.showWord);
		determineModify=(Button)findViewById(R.id.determineModify);
		returnModify=(Button)findViewById(R.id.returnModify);
		//setting=(ImageView)findViewById(R.id.danwel);
		stop=new Settingop(this);
		list=stop.querrysetting_word_pronunciation();
		CheckedFalseTrue();
		sv=new SettingVariable(this);
		Intent intent=null;
		determineModify.setOnClickListener(new MyButton());
		returnModify.setOnClickListener(new MyButton());
		//setting=cm.settingonclickop(setting,setting_word_pronunciation.this);
	}
	private class MyButton implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.determineModify:
				CheckedEdite();
				sv.querryallv();
				setting_pronunciation.this.setResult(RESULT_OK,intent);
				setting_pronunciation.this.finish();
				break;
			case R.id.returnModify:
				/*if(cm.getReturnboolean())
				{
					intent=cm.doReturnDialog();
				}
				else{
					intent.setClass(setting_pronunciation.this,settingmain.class);
				}*/
				startActivity(intent);
				break;
			}
		}
	}
	public void CheckedEdite(){
		stop.setSettingContent(soundRight.getText().toString().trim()+"");
		stop.setSettingID(12);
		stop.edit();
		if(showWord.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(13);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(13);
			stop.edit();
		}
		Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
	}
	public void CheckedFalseTrue(){
		int check=-1;
		int count1=0;
		for(Settingop tt:list){
			check=-1;
			count1++;
			System.out.println("数据加载时候打印"+tt.getSettingContent());
			if(tt.getSettingContent().toString().trim().equals("")==false)
			{
				try{
					check=Integer.parseInt(tt.getSettingContent());
				}catch(Exception e){
				}}
			switch(count1){
			case 1:
				soundRight.setText(tt.getSettingContent());
				break;
			case 2:
				if(check>0)
				{
					showWord.setChecked(true);
				}
				else {
					showWord.setChecked(false);
				}
				break;
				default:
					break;
			}
		}
	}
}

