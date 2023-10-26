package activity.control.kingsoft;

import wgy.recitewords.bdc.R;
import database.control.op.kingsofttableop;
import Intnet.importword.kingsoft.DownloadKingsoft;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KingSoftEdit extends Activity {
	EditText wordNameselect=null;
	EditText WordName=null;
	EditText PhonogramE=null;
	EditText PhonogramA=null;
	EditText WordMeaning=null;
	EditText PronunciationE=null;
	EditText PronunciationA=null;
	EditText ExampleSentence=null;
	EditText SentenceMeaning=null;
	kingsofttableop dcop=null;
	Button selectButton=null;
	Button editButton=null;
	Button kingsofteditturn=null;
	DownloadKingsoft dlks=null;
	String wordnameString="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.kingsoft_edit);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		dcop=new kingsofttableop(this);
		wordNameselect=(EditText)findViewById(R.id.WordNameeditselect);
		WordName=(EditText)findViewById(R.id.editWordName);
		PhonogramE=(EditText)findViewById(R.id.editPhonogramE);
		PhonogramA=(EditText)findViewById(R.id.editPhonogramA);
		WordMeaning=(EditText)findViewById(R.id.editWordMeaning);
		PronunciationE=(EditText)findViewById(R.id.editPronunciationE);
		PronunciationA=(EditText)findViewById(R.id.editPronunciationA);
		ExampleSentence=(EditText)findViewById(R.id.editExampleSentence);
		SentenceMeaning=(EditText)findViewById(R.id.editSentenceMeaning);
		selectButton=(Button)findViewById(R.id.kingsofteditquerry);
		editButton=(Button)findViewById(R.id.kingsoftedit);
		kingsofteditturn=(Button)findViewById(R.id.kingsofteditturn);
		selectButton.setOnClickListener(new ButtonListener());
		editButton.setOnClickListener(new ButtonListener());
		kingsofteditturn.setOnClickListener(new ButtonListener());
	}
	void show(){
		WordName.setText(dcop.getWordName());
		PhonogramE.setText(dcop.getPhonogramE());
		PhonogramA.setText(dcop.getPhonogramA());
		WordMeaning.setText(dcop.getWordMeaning());
		PronunciationE.setText(dcop.getPronunciationE());
		PronunciationA.setText(dcop.getPronunciationA());
		ExampleSentence.setText(dcop.getExampleSentence());
		SentenceMeaning.setText(dcop.getSentenceMeaning());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.kingsofteditquerry:
				Log.i("aa",wordNameselect.getText()+"");
				dcop.setWordName(wordNameselect.getText()+"");
				dcop.querrybyname();
				if(dcop.getWordID()!=-1)
					show();
				else {
					Toast.makeText(KingSoftEdit.this,"没有查到该单词！请重新检索！",0).show();
				}
				wordNameselect.setText("");
				break;
			case R.id.kingsoftedit:
				dcop.setWordName(WordName.getText()+"");
				dcop.setPhonogramE(PhonogramE.getText()+"");
				Log.i("aab",PhonogramE.getText()+"");
				dcop.setPhonogramA(PhonogramA.getText()+"");
				dcop.setWordMeaning(WordMeaning.getText()+"");
				dcop.setPronunciationE(PronunciationE.getText()+"");
				dcop.setPronunciationA(PronunciationA.getText()+"");
				dcop.setExampleSentence(ExampleSentence.getText()+"");
				dcop.setSentenceMeaning(SentenceMeaning.getText()+"");
				dcop.edit();
				dcop.querrybyname();
				Toast.makeText(KingSoftEdit.this,"修改成功",0).show();
				show();
				break;
			case R.id.kingsofteditturn:
				Intent intent=new Intent();
				intent.setClass(KingSoftEdit.this,KingSoft_gov.class);
				startActivity(intent);
				break;
				default:
					break;
			}
		}
	}
}
