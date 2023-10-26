package activity.control.kingsoft;

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

public class KingSoft_gov extends Activity {
	Button selectButton=null;
	Button editButton=null;
	Button deleteButton=null;
	Button returntomain=null;
	SettingVariable sv=null;
	TextView welcomeWords=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.kingsoft_gov);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		setWelcomesText();
		selectButton=(Button)findViewById(R.id.kingsoftgovselect);
		editButton=(Button)findViewById(R.id.kingsoftgovedit);
		deleteButton=(Button)findViewById(R.id.kingsoftgovdel);
		returntomain=(Button)findViewById(R.id.returntomain);
		welcomeWords=(TextView)findViewById(R.id.welcomeWords);
		selectButton.setOnClickListener(new ButtonListener());
		editButton.setOnClickListener(new ButtonListener());
		deleteButton.setOnClickListener(new ButtonListener());
		returntomain.setOnClickListener(new ButtonListener());
		//setting=cm.settingonclickop(setting,KingSoft_gov.this);
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.kingsoftgovselect:
				Intent intent1=new Intent(KingSoft_gov.this,KingSoftSelect.class);
				startActivity(intent1);
				break;
			case R.id.kingsoftgovedit:
				Intent intent2=new Intent(KingSoft_gov.this,KingSoftEdit.class);
				startActivity(intent2);
				break;
			case R.id.kingsoftgovdel:
				Intent intent3=new Intent(KingSoft_gov.this,kingsoft_delete.class);
				startActivity(intent3);
				break;
			case R.id.returntomain:
				Intent intent4=new Intent();
				intent4.setClass(KingSoft_gov.this,BDC_Main.class);
				startActivity(intent4);break;
				default:
					break;
			}
		}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		welcomeWords=(TextView)findViewById(R.id.welcomeWords);
		welcomeWords.setText(sv.getUName()+"ÓÃ»§£¬»¶Ó­Äú£¡");
	}
}

