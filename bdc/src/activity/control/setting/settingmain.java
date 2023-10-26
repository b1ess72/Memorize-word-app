package activity.control.setting;


import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;
import general.base.op.SettingVariable;
import database.control.op.Settingop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class settingmain extends Activity {
	Button display_interface=null;
	Button word_pronumciation=null;
	Button network_right=null;
	Button learning_mode=null;
	Button returnMain=null;
	TextView WelcomeWords=null;
	Settingop adus=null;
	SettingVariable sv=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.setting_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		setWelcomesText();
		display_interface=(Button)findViewById(R.id.display_interface);
		word_pronumciation=(Button)findViewById(R.id.word_pronumcion);
		network_right=(Button)findViewById(R.id.network_right);
		learning_mode=(Button)findViewById(R.id.learning_mode);
		returnMain=(Button)findViewById(R.id.returnMain);
		//setting=(ImageView)findViewById(R.id.danwei);
		display_interface.setOnClickListener(new MyButton());
		word_pronumciation.setOnClickListener(new MyButton());
		network_right.setOnClickListener(new MyButton());
		learning_mode.setOnClickListener(new MyButton());
		returnMain.setOnClickListener(new MyButton());
	}
	private class MyButton implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.display_interface:
				intent.setClass(settingmain.this,setting_display_interface.class);
				break;
			case R.id.word_pronumcion:
				intent.setClass(settingmain.this,setting_pronunciation.class);
				break;
			case R.id.network_right:
				intent.setClass(settingmain.this,setting_network_right.class);
				break;
			case R.id.learning_mode:
				intent.setClass(settingmain.this,setting_learning_mode.class);
				break;
			case R.id.returnMain:
				intent.setClass(settingmain.this,BDC_Main.class);
				break;
			}
			startActivityForResult(intent, 0);
		}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		WelcomeWords=(TextView)findViewById(R.id.smwelComeWords);
		WelcomeWords.setText(sv.getUName()+"ÓÃ»§£¬»¶Ó­Äú£¡");
	}
}
