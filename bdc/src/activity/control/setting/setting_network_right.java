package activity.control.setting;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import wgy.recitewords.bdc.R;

import database.control.op.Settingop;
import general.base.op.SettingVariable;
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

public class setting_network_right extends Activity {
	private CheckBox queryWordG;
	private CheckBox saveWord;
	private CheckBox queryWordWiFi;
	private CheckBox saveFileG;
	private CheckBox playWord;
	private CheckBox saveFileWiFi;
	private CheckBox playFileWiFi;
	private CheckBox computerSynzationWiFi;
	private CheckBox computerSynzationG;
	private RadioButton outsideStorage;
	private RadioButton insideStorage;
	private EditText filePathText;
	Button determineModify=null;
	Button returnModify=null;
	SettingVariable sv=null;
	Intent intent=null;
	Settingop stop=null;
	List<Settingop> list=new ArrayList<Settingop>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.setting_network_right);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title);
		init();
	}
	void init()
	{
		intent=this.getIntent();
		queryWordG=(CheckBox)findViewById(R.id.queryWordG);
		saveWord=(CheckBox)findViewById(R.id.saveWord);
		queryWordWiFi=(CheckBox)findViewById(R.id.queryWordWiFi);
		saveFileG=(CheckBox)findViewById(R.id.saveFileG);
		playWord=(CheckBox)findViewById(R.id.playWord);
		saveFileWiFi=(CheckBox)findViewById(R.id.saveFileWiFi);
		playFileWiFi=(CheckBox)findViewById(R.id.playFileWiFi);
		computerSynzationWiFi=(CheckBox)findViewById(R.id.computerSynzationWiFi);
		computerSynzationG=(CheckBox)findViewById(R.id.computerSynzationG);
		outsideStorage=(RadioButton)findViewById(R.id.outsideStorape);
		insideStorage=(RadioButton)findViewById(R.id.insideStorape);
		filePathText=(EditText)findViewById(R.id.filePathText);
		determineModify=(Button)findViewById(R.id.determineModify);
		returnModify=(Button)findViewById(R.id.returnModify);
		stop=new Settingop(this);
		list=stop.querrySetting_network_right();
		CheckedFalseTrue();
		sv=new SettingVariable(this);
		determineModify.setOnClickListener(new MyButton());
		returnModify.setOnClickListener(new MyButton());
		//setting=cm.settingoneclickop(setting,setting_network_right.this);
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
				setting_network_right.this.setResult(RESULT_OK, intent);
				setting_network_right.this.finish();
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
			System.out.println("数据加载时候打印//////////////"+tt.getSettingClass());
			if(tt.getSettingContent().toString().trim().equals("")==false&&count1<11)
			{
				try{
					check=Integer.parseInt(tt.getSettingContent());
				}catch(Exception e){
				}
			}
			switch(count1){
			case 1:
				if(check==0){
					queryWordG.setChecked(false);
				}
				else {
					queryWordG.setChecked(true);
				}
				break;
			case 2:
				if(check==0)
				{
					saveWord.setChecked(false);
				}
				else {
					saveWord.setChecked(true);
				}
				break;
			case 3:
				if(check==0)
				{
					queryWordWiFi.setChecked(false);
				}
				else {
					queryWordWiFi.setChecked(true);
				}
				break;
			case 4:
				if(check==0)
				{
					saveFileG.setChecked(false);
				}
				else {
					saveFileG.setChecked(true);
				}
				break;
			case 5:
				if(check==0)
				{
					playWord.setChecked(false);
				}
				else
				{
					playWord.setChecked(true);
				}
				break;
			case 6:
				if(check==0)
				{
					saveFileWiFi.setChecked(false);
				}
				else
				{
					saveFileWiFi.setChecked(true);
				}
				break;
			case 7:
				if(check==0)
				{
					playFileWiFi.setChecked(false);
				}
				else
				{
					playFileWiFi.setChecked(true);
				}
				break;
			case 8:
				if(check==0)
				{
					computerSynzationWiFi.setChecked(false);
				}
				else
				{
					computerSynzationWiFi.setChecked(true);
				}
				break;
			case 9:
				if(check==0)
				{
					computerSynzationG.setChecked(false);
				}
				else
				{
					computerSynzationG.setChecked(true);
				}
				break;
			case 10:
				if(check==0)
				{
					outsideStorage.setChecked(false);
				}
				else if(check==1)
				{
					insideStorage.setChecked(true);
				}
				else {
					outsideStorage.setChecked(false);
					insideStorage.setChecked(false);
				}
				break;
			case 11:
				filePathText.setText(tt.getSettingContent());
				break;
				default:
					break;
			}
		}
	}
	public void CheckedEdite(){
		System.out.println("判断文本框是否被选择中："+filePathText.getText().toString().trim());
		if(queryWordG.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(1);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(1);
			stop.edit();
		}
		if(saveWord.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(2);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(2);
			stop.edit();
		}
		if(queryWordWiFi.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(3);
			stop.edit();
		}
		else{
			stop.setSettingContent(0+"");
			stop.setSettingID(3);
			stop.edit();
		}
		if(saveFileG.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(4);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(4);
			stop.edit();
		}
		if(playWord.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(5);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(5);
			stop.edit();
		}
		if(saveFileWiFi.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(6);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(6);
			stop.edit();
		}
		if(playFileWiFi.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(7);
			stop.edit();
		}
		else {
			stop.setSettingContent(0+"");
			stop.setSettingID(7);
			stop.edit();
		}
		if(computerSynzationWiFi.isChecked()==true)
		{
			stop.setSettingContent(1+"");
			stop.setSettingID(8);
			stop.edit();
		}
		else {
			stop.setSettingContent(1+"");
			stop.setSettingID(8);
			stop.edit();
		}
		if(computerSynzationG.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(9);
			stop.edit();
		}
		else
		{
			stop.setSettingContent(0+"");
			stop.setSettingID(9);
			stop.edit();
		}
		if(outsideStorage.isChecked()==true)
		{
			stop.setSettingContent(0+"");
			stop.setSettingID(10);
			stop.edit();
		}
		else if(insideStorage.isChecked()==true){
			stop.setSettingContent(1+"");
			stop.setSettingID(10);
			stop.edit();
		}
		else {
			stop.setSettingContent(-1+"");
			stop.setSettingID(10);
			stop.edit();
		}
		stop.setSettingContent(filePathText.getText().toString().trim()+"");
		stop.setsID(11);
		stop.edit();
		Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
	}
}

