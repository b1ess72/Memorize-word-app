package activity.control.kingsoft;


import java.sql.Date;
import java.text.SimpleDateFormat;
import database.control.op.kingsofttableop;
import general.base.op.BaseSplit;
import general.base.op.SettingVariable;
//import wgy.recitewords.bdc.ClickMenu;
import wgy.recitewords.bdc.R;
import Intnet.importword.kingsoft.DownloadKingsoft;
import Intnet.importword.kingsoft.DownloadMp3File;
import Intnet.importword.kingsoft.IntnetCheck;
import Intnet.importword.kingsoft.WordValue;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class KingSoftSelect extends Activity {
	DownloadKingsoft dlks=null;
	String wordnameString="";
	WordValue w=null;
	MediaPlayer mediaPlayer;
	boolean checkintnetwordState=false;
	EditText wordNameselect=null;
	TextView WordName=null;
	TextView PhonogramE=null;
	TextView PhonogramA=null;
	TextView WordMeaning=null;
	TextView PronunciationE=null;
	TextView PronunciationA=null;
	TextView ExampleSentence=null;
	TextView SentenceMeaning=null;
	EditText errorstring=null;
	kingsofttableop dcop=null;
	Button selectButton=null;
	Button kingsoftmessage=null;
	LinearLayout LinearLayouttext=null;
	String urlStr="";
	String path="file1";
	String storageState="1";
	BaseSplit bs=null;
	DownloadMp3File dw=null;
	SettingVariable sv=null;
	Button kingsoftcancel=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.kingsoft_select);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		dcop=new kingsofttableop(this);
		bs=new BaseSplit();
		dw=new DownloadMp3File();
		sv=new SettingVariable();
		wordNameselect=(EditText)findViewById(R.id.WordNameselselect);
		WordName=(TextView)findViewById(R.id.TextViewWordName);
		PhonogramE=(TextView)findViewById(R.id.TextViewPhonogramE);
		PhonogramA=(TextView)findViewById(R.id.TextViewPhonogramA);
		WordMeaning=(TextView)findViewById(R.id.TextViewWordMeaning);
		PronunciationE=(TextView)findViewById(R.id.TextViewPronunciationE);
		PronunciationA=(TextView)findViewById(R.id.TextViewPronunciationA);
		ExampleSentence=(TextView)findViewById(R.id.TextViewExampleSentence);
		SentenceMeaning=(TextView)findViewById(R.id.TextViewSentenceMeaning);
		LinearLayouttext=(LinearLayout)findViewById(R.id.LinearLayouttext);
		errorstring=(EditText)findViewById(R.id.errorstring);
		selectButton=(Button)findViewById(R.id.kingsoftselectquerry);
		kingsoftmessage=(Button)findViewById(R.id.kingsoftmessage);
		kingsoftcancel=(Button)findViewById(R.id.kingsoftcancel);
		selectButton.setOnClickListener(new ButtonListener());
		kingsoftmessage.setOnClickListener(new ButtonListener());
		showNon();
	}
	void show(){
		wordNameselect.setText("");
		WordName.setText("单词："+dcop.getWordName());
		if(!dcop.getPhonogramE().equals("")){PhonogramE.setVisibility(View.VISIBLE);
		PhonogramE.setText("英式音标:["+dcop.getPhonogramE()+"]");}else
			PhonogramE.setVisibility(View.GONE);
		if(!dcop.getPhonogramA().equals("")){PhonogramA.setVisibility(View.VISIBLE);
		PhonogramA.setText("美式音标:["+dcop.getPhonogramA()+"]");}else
			PhonogramA.setVisibility(View.GONE);
		if(!dcop.getWordMeaning().equals("")){WordMeaning.setVisibility(View.VISIBLE);
		WordMeaning.setText("解释:["+dcop.getWordMeaning()+"]");}else
			WordMeaning.setVisibility(View.GONE);
		if(!dcop.getPronunciationE().equals("")){PronunciationE.setVisibility(View.VISIBLE);
		PronunciationE.setText("英式读音:["+dcop.getPronunciationE()+"]");}else
			PronunciationE.setVisibility(View.GONE);
		if(!dcop.getPronunciationA().equals("")){PronunciationA.setVisibility(View.VISIBLE);
		PronunciationA.setText("美式读音:["+dcop.getPronunciationA()+"]");}else
			PronunciationA.setVisibility(View.GONE);
		if(!dcop.getExampleSentence().equals("")){ExampleSentence.setVisibility(View.VISIBLE);
		ExampleSentence.setText("例句:["+dcop.getExampleSentence()+"]");}else
			ExampleSentence.setVisibility(View.GONE);
		if(!dcop.getSentenceMeaning().equals("")){SentenceMeaning.setVisibility(View.VISIBLE);
		SentenceMeaning.setText("例句解释:["+dcop.getSentenceMeaning()+"]");}else
			SentenceMeaning.setVisibility(View.GONE);
	}
	void showNon(){
		wordNameselect.setText("");
		WordName.setText("");
		PhonogramE.setText("");
		PhonogramA.setText("");
		WordMeaning.setText("");
		PronunciationE.setText("");
		PronunciationA.setText("");
		ExampleSentence.setText("");
		SentenceMeaning.setText("");
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.kingsoftselectquerry:
				Log.i("wwwwwwwwwwwwww","dddddddddd");
				wordnameString=wordNameselect.getText().toString();
				dlks=new DownloadKingsoft();
				int flag=dlks.getdownloadword(KingSoftSelect.this,wordnameString);
				dcop=dlks.getDcop();
				Log.i("wwwwwwwwwwwwww",dcop.getWordName());
				if(flag==1)show();
				if(flag==2)showNon();
				if(!dlks.getErrorString().equals(""))
				errorstring.setText(sv.errorStringall(dlks.getErrorString(),wordnameString));
				wordNameselect.setText("");
				break;
			case R.id.kingsoftmessage:
				if(kingsoftmessage.getText().toString().trim().equals("显示提醒信息"))
				{
					LinearLayouttext.setVisibility(View.VISIBLE);
					kingsoftmessage.setText("隐藏提醒信息");
				}
				else {
					LinearLayouttext.setVisibility(View.GONE);
					kingsoftmessage.setText("显示提醒信息");
				}
				break;
			case R.id.kingsoftcancel:
				Intent intent=new Intent();
				intent.setClass(KingSoftSelect.this,KingSoft_gov.class);
				startActivity(intent);
				break;
			}
		}
	}
}
