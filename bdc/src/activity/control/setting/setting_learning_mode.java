package activity.control.setting;

import general.base.op.SettingVariable;

import java.util.ArrayList;
import java.util.List;

import database.control.op.Settingop;
import general.base.op.SettingVariable;
//import wgy.recitewords.bdc.ClickMenu;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class setting_learning_mode extends Activity {
	private RadioButton learningStudy;
	private RadioButton momeryModel;
	private EditText phoneicInterpretation;
	private CheckBox loopPlayback;
	private CheckBox autoPlay;
	private EditText intervaPlay;
	
	Button determine=null;
	Button returnModify=null;
	
	SettingVariable sv=null;
	Intent intent=null;
	Settingop add=null;
	List<Settingop> list=new ArrayList<Settingop>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.setting_learning_mode);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title);
		init();
	}
	void init()
	{
		learningStudy=(RadioButton)findViewById(R.id.learningStudy);
		momeryModel=(RadioButton)findViewById(R.id.momeryModel);
		phoneicInterpretation=(EditText)findViewById(R.id.phoneicInterpretation);
		loopPlayback=(CheckBox)findViewById(R.id.loopPlayback);
		autoPlay=(CheckBox)findViewById(R.id.autoPlay);
		intervaPlay=(EditText)findViewById(R.id.intervaPlay);
		determine=(Button)findViewById(R.id.determine);
		returnModify=(Button)findViewById(R.id.returnModify);
		add=new Settingop(this);
		list=add.querrysetting_learning_mode();
		CheckedFalseTrue();
		sv=new SettingVariable(this);
		determine.setOnClickListener(new MyButton());
		returnModify.setOnClickListener(new MyButton());
		//setting=cm.settingoneclickop(setting,setting_network_right.this);
	}
	private class MyButton implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.determine:
				CheckedEdite();
				sv.querryallv();
				setting_learning_mode.this.setResult(RESULT_OK, intent);
				setting_learning_mode.this.finish();
				break;
			case R.id.returnModify:
				/*if(cm.getReturnboolean())
				{
					intent=cm.doReturnDialog();
				}
				else {
					intent.setClass(setting_network_right.this,settingmain.class);
				}*/
				startActivity(intent);
				break;
			}
		}
	}
	public void CheckedFalseTrue(){
		int check=-1;
		int count1=0;
		for(Settingop tt:list){
			check=-1;
			count1++;
			System.out.println("数据加载时候打印"+tt.getSettingClass());
			if(tt.getSettingContent().toString().trim().equals("")==false)
			{
				try{
					check=Integer.parseInt(tt.getSettingContent());
				}catch(Exception e){
				}
			}
			System.out.println("数据加载时候打印11 "+check);
			switch(count1){
			case 1:
				if(check==1){
					learningStudy.setChecked(true);
				}
				else if(check==0){
					momeryModel.setChecked(true);
				}else {
					learningStudy.setChecked(false);
					momeryModel.setChecked(false);
				}
				break;
			case 3:
				System.out.println("数据加载时候打印123 "+tt.getSettingContent());
				phoneicInterpretation.setText(tt.getSettingContent()+"");
				break;
			case 4:
				if(check==1)
				{
					loopPlayback.setChecked(true);
				}
				else {
					loopPlayback.setChecked(false);
				}
				break;
			case 5:
				if(check==1)
				{
					autoPlay.setChecked(true);
				}
				else {
					autoPlay.setChecked(false);
				}
				break;
			case 6:
				intervaPlay.setText(tt.getSettingContent()+"");
				break;
				default:
					break;
			}
		}
	}
	public void CheckedEdite(){
		if(learningStudy.isChecked()==true){
			add.setSettingContent(0+"");
			add.setSettingID(15);
			add.edit();
			add.setSettingContent(1+"");
			add.setSettingID(14);
			add.edit();
		}
		else if(momeryModel.isChecked()==true){
			add.setSettingContent(1+"");
			add.setSettingID(15);
			add.edit();
			add.setSettingContent(0+"");
			add.setSettingID(14);
			add.edit();
		}else {
			add.setSettingContent(""+"");
			add.setSettingID(15);
			add.edit();
		}
		add.setSettingContent(phoneicInterpretation.getText().toString().trim()+"");
		add.setSettingID(16);
		add.edit();
		if(loopPlayback.isChecked()==true){
			add.setSettingContent(1+"");
			add.setSettingID(17);
			add.edit();
		}
		else {
			add.setSettingContent(0+"");
			add.setSettingID(17);
			add.edit();
		}
		if(autoPlay.isChecked()==true){
			add.setSettingContent(1+"");
			add.setSettingID(18);
			add.edit();
		}
		else{
			add.setSettingContent(0+"");
			add.setSettingID(18);
			add.edit();
		}
		add.setSettingContent(intervaPlay.getText().toString().trim()+"");
		add.setSettingID(19);
		add.edit();
		Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
	}
}
