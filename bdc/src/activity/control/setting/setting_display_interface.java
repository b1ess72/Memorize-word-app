package activity.control.setting;

import java.util.ArrayList;
import java.util.List;

import database.control.op.Settingop;
import general.base.op.ColorPickerDialog;
import general.base.op.EditTextCheck;
import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
//import wgy.recitewords.bdc.ClickMenu;
import wgy.recitewords.bdc.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class setting_display_interface extends Activity {
	private EditText wordSize;
	private EditText phonogramSize;
	private EditText explainSize;
	private EditText exampleSize;
	private EditText wordColor;
	private EditText phonogramColor;
	private EditText explainColor;
	private EditText exampleColor;
	private CheckBox overallProgress;
	private CheckBox group;
	private RadioButton order;
	private RadioButton reverse;
	private RadioButton outOfOrder;
	private CheckBox wordShow;
	private CheckBox phonogramShow;
	private CheckBox explainShow;
	private CheckBox exampleShow;
	
	Button display_btn=null;
	Button returnModify=null;
	Button btn_confirm=null;
	Button returnMain=null;
	LinearLayout RelativeLayout35=null;
	LinearLayout RelativeLayout34=null;
	SettingVariable sv=null;
	Intent intent=null;
	Settingop stop=null;
	List<Settingop> list=new ArrayList<Settingop>();
	EditTextCheck etcheck=null;
	private Spinner spinner;
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter=null;
	String droplistselectString="";
	private ColorPickerDialog dialog;
	EditText ettemp=null;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.setting_display_interface);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title);
		init();
	}
	void init()
	{
		wordSize=(EditText)findViewById(R.id.wordSize);
		phonogramSize=(EditText)findViewById(R.id.phonogramSize);
		explainSize=(EditText)findViewById(R.id.explainSize);
		exampleSize=(EditText)findViewById(R.id.exampleSize);
		wordColor=(EditText)findViewById(R.id.wordColor);
		phonogramColor=(EditText)findViewById(R.id.phonogramColor);
		explainColor=(EditText)findViewById(R.id.explainColor);
		exampleColor=(EditText)findViewById(R.id.exampleColor);
		overallProgress=(CheckBox)findViewById(R.id.overallProgress);
		group=(CheckBox)findViewById(R.id.group);
		order=(RadioButton)findViewById(R.id.order);
		reverse=(RadioButton)findViewById(R.id.reverse);
		outOfOrder=(RadioButton)findViewById(R.id.outOfOrder);
		wordShow=(CheckBox)findViewById(R.id.wordShow);
		phonogramShow=(CheckBox)findViewById(R.id.phonogramShow);
		explainShow=(CheckBox)findViewById(R.id.explainShow);
		exampleShow=(CheckBox)findViewById(R.id.exampleShow);
		display_btn=(Button)findViewById(R.id.display_btn);
		returnModify=(Button)findViewById(R.id.returnModify);
		btn_confirm=(Button)findViewById(R.id.btn_confirm);
		returnMain=(Button)findViewById(R.id.returnMain);
		RelativeLayout35=(LinearLayout)findViewById(R.id.RelativeLayout35);
		RelativeLayout34=(LinearLayout)findViewById(R.id.RelativeLayout34);
		stop=new Settingop(this);
		etcheck=new EditTextCheck();
		list=stop.querrysetting_display_interface();
		CheckedFalseTrue();
		
		display_btn.setOnClickListener(new MyButton());
		returnModify.setOnClickListener(new MyButton());
		btn_confirm.setOnClickListener(new MyButton());
		returnMain.setOnClickListener(new MyButton());
		wordColor.setOnClickListener(new oncolorListener());
		phonogramColor.setOnClickListener(new oncolorListener());
		explainColor.setOnClickListener(new oncolorListener());
		exampleColor.setOnClickListener(new oncolorListener());
		wordSize.setOnClickListener(new onFontListener());
		phonogramSize.setOnClickListener(new onFontListener());
		explainSize.setOnClickListener(new onFontListener());
		exampleSize.setOnClickListener(new onFontListener());
		wordColor.setOnFocusChangeListener(new FocusChangeListener());
		phonogramColor.setOnFocusChangeListener(new FocusChangeListener());
		explainColor.setOnFocusChangeListener(new FocusChangeListener());
		exampleColor.setOnFocusChangeListener(new FocusChangeListener());
		//setting=cm.settingonclickop(setting,setting_display_interface.this);
	}
	class FocusChangeListener implements OnFocusChangeListener{
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(!hasFocus){
				checkEditText(v.getId());
			}else{
			}}
	}
	void checkEditText(int id){
		EditText ettempcolor=(EditText)findViewById(id);
		Boolean checktemp=etcheck.isColor(ettempcolor.getText().toString());
		if(checktemp)
			ettempcolor.setTextColor(Color.parseColor(ettempcolor.getText().toString()));
		else{
			Toast.makeText(setting_display_interface.this,"颜色格式错误，请重新输入！",0).show();
		}
	}
	class onFontListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getFontsize(v.getId());			
		}}
	void getFontsize(int id){
		ettemp=(EditText)findViewById(id);
		RelativeLayout34.setVisibility(View.GONE);
		RelativeLayout35.setVisibility(View.VISIBLE);
		spinner=(Spinner)findViewById(R.id.spinner);
		initDropListClass idListClass=new initDropListClass(5,30);
		data_list=idListClass.getList();
		arr_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list);
		arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arr_adapter);
		droplistselectString=arr_adapter.getItem(0);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				droplistselectString=arr_adapter.getItem(arg2);
				Toast.makeText(setting_display_interface.this,arr_adapter.getItem(arg2)+"",0).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(setting_display_interface.this,"Nothing",0).show();
			}
		});
	}
	class oncolorListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getEditText(v.getId());
		}
	}
	void getEditText(int id){
		ettemp=(EditText)findViewById(id);
		ettemp.setFocusable(true);
		ettemp.setFocusableInTouchMode(true);
		ettemp.requestFocus();
		ettemp.requestFocusFromTouch();
		InputMethodManager inputManager=(InputMethodManager)ettemp.getContext().getSystemService(this.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(ettemp, 0);
		dialog=new ColorPickerDialog(this,ettemp.getTextColors().getDefaultColor(),
				"颜色选择器",new ColorPickerDialog.OnColorChangedListener() {
					
					@Override
					public void colorChanged(int color) {
						// TODO Auto-generated method stub
						ettemp.setTextColor(color);
						String alpha=Integer.toHexString((color&0xff000000)>>>24);
						String red=((((color&0x00ff0000)>>
						16)<16)?"0"+Integer.toHexString((color&0x00ff0000)>>
						16):Integer.toHexString((color&0x00ff0000)>>16));
						String green=((((color&0x0000ff00)>>
						8)<16)?"0"+Integer.toHexString((color&0x0000ff00)>>
						8):Integer.toHexString((color&0x0000ff00)>>8));
						String blue=((((color&0x000000ff))<16)?"0"+Integer.toHexString((
								color&0x000000ff)):Integer.toHexString((color&0x0000ff00)));
						ettemp.setText("#"+red+green+blue);
						ettemp.setSelection(("#"+red+green+blue).length());
						Toast.makeText(setting_display_interface.this,"您选中了#"+red+green+blue+"颜色",0).show();
					}
		});
		dialog.show();
	}
	private class MyButton implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.display_btn:
				CheckedEdite();
				sv.querryallv();
				setting_display_interface.this.setResult(RESULT_OK,intent);
				break;
			case R.id.returnModify:
				intent.setClass(setting_display_interface.this,settingmain.class);
				startActivity(intent);
				break;
			case R.id.btn_confirm:
				ettemp.setText(droplistselectString);
				ettemp.setTextSize(Integer.parseInt(droplistselectString));
				RelativeLayout35.setVisibility(View.GONE);
				RelativeLayout34.setVisibility(View.VISIBLE);
				break;
			case R.id.returnMain:
				RelativeLayout35.setVisibility(View.GONE);
				RelativeLayout34.setVisibility(View.VISIBLE);
				break;
			}
		}
	}
		public void CheckedEdite(){
			stop.setSettingContent(wordSize.getText().toString().trim()+"");
			stop.setSettingID(20);
			stop.edit();
			stop.setSettingContent(phonogramSize.getText().toString().trim()+"");
			stop.setSettingID(21);
			stop.edit();
			stop.setSettingContent(explainSize.getText().toString().trim()+"");
			stop.setSettingID(22);
			stop.edit();
			stop.setSettingContent(exampleSize.getText().toString().trim()+"");
			stop.setSettingID(23);
			stop.edit();
			stop.setSettingContent(wordColor.getText().toString().trim()+"");
			stop.setSettingID(24);
			stop.edit();
			stop.setSettingContent(phonogramColor.getText().toString().trim()+"");
			stop.setSettingID(25);
			stop.edit();
			stop.setSettingContent(explainColor.getText().toString().trim()+"");
			stop.setSettingID(26);
			stop.edit();
			stop.setSettingContent(exampleColor.getText().toString().trim()+"");
			stop.setSettingID(27);
			stop.edit();
			if(overallProgress.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(28);
				stop.edit();
			}
			else 
			{
				stop.setSettingContent(0+"");
				stop.setSettingID(28);
				stop.edit();
			}
			if(group.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(29);
				stop.edit();
			}
			else {
				stop.setSettingContent(0+"");
				stop.setSettingID(29);
				stop.edit();
			}
			if(order.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(30);
				stop.edit();
			}else if(reverse.isChecked()==true){
				stop.setSettingContent(2+"");
				stop.setSettingID(30);
				stop.edit();
			}else if(outOfOrder.isChecked()==true){
				stop.setSettingContent(3+"");
				stop.setSettingID(30);
				stop.edit();
			}else if(outOfOrder.isChecked()==true){
				stop.setSettingContent(3+"");
				stop.setSettingID(30);
				stop.edit();}
			if(wordShow.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(31);
				stop.edit();
			}
			else {
					stop.setSettingContent(0+"");
					stop.setSettingID(31);
					stop.edit();
			}
			if(phonogramShow.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(32);
				stop.edit();
			}
			else {
					stop.setSettingContent(0+"");
					stop.setSettingID(32);
					stop.edit();
			}
			if(explainShow.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(33);
				stop.edit();
			}
			else {
					stop.setSettingContent(0+"");
					stop.setSettingID(33);
					stop.edit();
			}
			if(exampleShow.isChecked()==true){
				stop.setSettingContent(1+"");
				stop.setSettingID(34);
				stop.edit();
			}
			else {
					stop.setSettingContent(0+"");
					stop.setSettingID(34);
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
				System.out.println("数据加载时候打印"+tt.getSettingContent()+" 数据库  ");
				if(tt.getSettingContent().toString().trim().equals("")==false)
				{
					try{
						check=Integer.parseInt(tt.getSettingContent());
					}catch(Exception e){
					}
				}
				Boolean checktemp1=
						etcheck.isNunmber(tt.getSettingContent().toString().trim());
				Boolean checktemp2=
						etcheck.isColor(tt.getSettingContent().toString().trim());
				switch(count1){
				case 1:
					wordSize.setText(tt.getSettingContent());
					if(checktemp1)
						wordSize.setTextSize(Integer.parseInt(tt.getSettingContent().toString().trim()));
					break;
				case 2:
					phonogramSize.setText(tt.getSettingContent());
					if(checktemp1)
						phonogramSize.setTextSize(Integer.parseInt(tt.getSettingContent().toString().trim()));
					break;
				case 3:
					explainSize.setText(tt.getSettingContent());
					if(checktemp1)
						explainSize.setTextSize(Integer.parseInt(tt.getSettingContent().toString().trim()));
					break;
				case 4:
					exampleSize.setText(tt.getSettingContent());
					if(checktemp1)
						exampleSize.setTextSize(Integer.parseInt(tt.getSettingContent().toString().trim()));
					break;
				case 5:
					wordColor.setText(tt.getSettingContent());
					if(checktemp2)
						wordColor.setTextColor(Color.parseColor(tt.getSettingContent().toString().trim()));
					break;
				case 6:
					phonogramColor.setText(tt.getSettingContent());
					if(checktemp2)
						phonogramColor.setTextColor(Color.parseColor(tt.getSettingContent().toString().trim()));
					break;
				case 7:
					explainColor.setText(tt.getSettingContent());
					if(checktemp2)
						explainColor.setTextColor(Color.parseColor(tt.getSettingContent().toString().trim()));
					break;
				case 8:
					exampleColor.setText(tt.getSettingContent());
					if(checktemp2)
						exampleColor.setTextColor(Color.parseColor(tt.getSettingContent().toString().trim()));
					break;
				case 9:
					if(check==0){
						overallProgress.setChecked(false);
					}else if(check==1){
						overallProgress.setChecked(true);
					}
					break;
				case 10:
					if(check==0){
						group.setChecked(false);
					}else if(check==1){
						group.setChecked(true);
					}
					break;
				case 11:
					if(check==1){
						order.setChecked(true);
						reverse.setChecked(false);
						outOfOrder.setChecked(false);
					}else if(check==2){
						order.setChecked(false);
						reverse.setChecked(true);
						outOfOrder.setChecked(false);
					}
					else if(check==3){
						order.setChecked(false);
						reverse.setChecked(false);
						outOfOrder.setChecked(true);
					}
					break;
				case 12:
					if(check==1){
						wordShow.setChecked(true);
					}else if(check==0){
						wordShow.setChecked(false);
					}
					break;
				case 13:
					if(check==1){
						phonogramShow.setChecked(true);
					}else if(check==0){
						phonogramShow.setChecked(false);
					}
					break;
				case 14:
					if(check==1){
						explainShow.setChecked(true);
					}else if(check==0){
						explainShow.setChecked(false);
					}
					break;
				case 15:
					if(check==1){
						exampleShow.setChecked(true);
					}else if(check==0){
						exampleShow.setChecked(false);
					}
					break;
					default:
						break;
				}
			}
		}
}

