package activity.control.studyreview;

import general.base.op.BaseSplit;
import general.base.op.DateTimeOp;
import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
import java.util.ArrayList;
import java.util.List;

import database.control.op.GroupingdetalsTableop;
import database.control.op.ImportWordTableop;
import database.control.op.StudyReviewTableop;
import database.control.op.WordtotalTableop;
import database.control.op.kingsofttableop;
import database.control.op.userInfoop;
import database.control.op.userLoginop;
import wgy.recitewords.bdc.R;
import Intnet.importword.kingsoft.DownloadKingsoft;

import Intnet.importword.kingsoft.WordValue;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class WordBrowse extends Activity {
	ImageView wbautoplayimg=null;
	ImageView wbLoopplayimg=null;
	ImageView wborderimg=null;
	ImageView wbpreimg=null;
	ImageView wbnextimg=null;
	ImageView wbup=null;
	ImageView wbdown=null;
	ImageView wbnewword=null;
	Button wbcheckconfirm=null;
	Button wbcheckcancle=null;
	int op;
	int content=1;
	String SelectedGroupID;
	String SelectedArrayID;
	String returnDialog="";
	Button srlcancle=null;
	Button srlWordstobrowse=null;
	TextView wbClassgroup=null;
	TextView wbNumbering=null;
	TextView wbThewords=null;
	TextView wbPhoneticsymbol=null;
	TextView wbInterpret=null;
	TextView wbExample=null;
	TextView wbfamiliarity=null;
	EditText errorstring=null;
	LinearLayout LinearLayouttext=null;
	LinearLayout rr3=null;
	LinearLayout rr4=null;
	Button showorhide=null;
	long WordID=-1;
	String WordName="";
	String PhonogramE="";
	String PhonogramA="";
	String WordMeaning="";
	String PronunciationE="";
	String PronunciationA="";
	String ExampleSentence="";
	String SentenceMeaning="";
	SettingVariable sv=null;
	ImportWordTableop iwop=null;
	BaseSplit bs=null;
	GroupingdetalsTableop gdop=null;
	WordtotalTableop wtop=null;
	StudyReviewTableop srop=null;
	kingsofttableop dcop=null;
	initDropListClass idls=null;
	userInfoop uifop=null;
	DateTimeOp dt=null;
	String urlStr="";
	String path="file1";
	String storageState="1";
	DownloadKingsoft dlks=null;
	String wordnameString="";
	String idlsfamiliarity="";
	WordValue w=null;
	MediaPlayer mediaPlayer;
	String strwordlist="";
	int groupIDtemp=-1;
	long StudyReviewID=-1;
	long wordIDtemp=-1;
	boolean continueflag=false;
	boolean circleState=false;
	boolean timerflag=false;
	int delayautoplay=0;
	int delayshow=0;
	long currentID=-1;
	boolean checkintnetwordState=false;
	final int RIGHT=0;
	final int LEFT=1;
	final int DOWN=2;
	final int UP=3;
	int checkflag=1;
	int flagupdatetemp=0;
	int checkState=0;
	int brouseop=1;
	int delayflag=0;
	private List<String> list1=new ArrayList<String>();
	int tttt=17;
	int checkIntnetFlag=1;
	long uid=0;
	ScrollView tt;
	private long mCurrentClickTime;
	private Handler mBaseHandler=new Handler();
	private static final long LONG_PRESS_TIME=2000;
	ImageView setting=null;
	String memuString="";
	int menucount=0;
	userLoginop ulop=null;
	LinearLayout layout_temp_old=null;
	LinearLayout layout_temp_new=null;
	ImageView imageviewtemp_old=null;
	ImageView imageviewtemp_new=null;
	String memuStringtemp="";
	String LeariningStudymode="";
	int volume=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.studygov_wordbrowse);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init(){
		gdop=new GroupingdetalsTableop(this);
		iwop=new ImportWordTableop(this);
		long StudyReviewID=-1;
		dcop=new kingsofttableop(this);
		wtop=new WordtotalTableop(this);
		srop=new StudyReviewTableop(this);
		idls=new initDropListClass();
		sv=new SettingVariable();
		bs=new BaseSplit();
		ulop=new userLoginop(this);
		uifop=new userInfoop(this);
		uifop.setUsername(sv.getUName());
		uifop.getallbyusername();
		uid=uifop.getId1();
		dt=new DateTimeOp();
		volume=Integer.parseInt(sv.getSoundRight());
		if(sv.getOrder().trim().equals(""))brouseop=1;
		else 
			brouseop=Integer.parseInt(sv.getOrder());
		int studiedcount=gdop.checkstudiedwordcount(sv.getTableID(),uid, brouseop);
		wtop.setTableID(sv.getTableID());
		Log.i("sshhhhhhh",studiedcount+" "+sv.getTableID());
		wtop.setStudiedWordCount(studiedcount);
		wtop.editStudiedWordCount();
		Log.i("nnn4667",srop.getWordIDListStudied());
		Log.i("nnn46678",srop.getWordIDListStudied());
		Log.i("ffffff",sv.getTableName());
		Log.i("nnn46679",srop.getWordIDListStudied());
		Log.i("nnn4667",srop.getWordIDListStudied());
		wbClassgroup=(TextView)findViewById(R.id.wbClassgroup);
		wbNumbering=(TextView)findViewById(R.id.wbNumbering);
		wbThewords=(TextView)findViewById(R.id.wbThewords);
		wbPhoneticsymbol=(TextView)findViewById(R.id.wbPhoneticsymbol);
		wbInterpret=(TextView)findViewById(R.id.wbInterpret);
		wbExample=(TextView)findViewById(R.id.wbExample);
		wbfamiliarity=(TextView)findViewById(R.id.wbfamiliarity);
		errorstring=(EditText)findViewById(R.id.errorstring);
		showorhide=(Button)findViewById(R.id.showorhide);
		LinearLayouttext=(LinearLayout)findViewById(R.id.LinearLayouttext);
		rr3=(LinearLayout)findViewById(R.id.rr3);
		rr4=(LinearLayout)findViewById(R.id.rr4);
		wbnewword=(ImageView)findViewById(R.id.wbnewword);
		Log.i("nnn1","nnn");
		Log.i("nnn4668",srop.getWordIDListStudied());
		Intent intent=getIntent();
		op=intent.getIntExtra("op",0);
		content=intent.getIntExtra("content",0);
		if(intent.getStringExtra("SelectedGroupID")==null)SelectedGroupID="";
		else SelectedGroupID=intent.getStringExtra("SelectedGroupID");
		if(intent.getStringExtra("SelectedArrayID")==null)SelectedArrayID="";
		else SelectedArrayID=intent.getStringExtra("SelectedArrayID");
		returnDialog=intent.getStringExtra("returnDialog");
		Log.i("kkknnn2",SelectedGroupID+" "+SelectedArrayID);
		wbcheckconfirm=(Button)findViewById(R.id.wbcheckconfirm);
		wbcheckconfirm.setOnClickListener(new MyButton());
		Log.i("nnn2","nnn");
		wbcheckcancle=(Button)findViewById(R.id.wbcheckcancle);
		wbcheckcancle.setOnClickListener(new MyButton());
		Log.i("nnn222222222222","nnn"+op);
		wbautoplayimg=(ImageView)findViewById(R.id.wbautoplayimg);
		wbLoopplayimg=(ImageView)findViewById(R.id.wbLoopplayimg);
		wborderimg=(ImageView)findViewById(R.id.wborderimg);
		wbpreimg=(ImageView)findViewById(R.id.wbpreimg);
		wbnextimg=(ImageView)findViewById(R.id.wbnextimg);
		wbup=(ImageView)findViewById(R.id.wbadd);
		wbdown=(ImageView)findViewById(R.id.wbplus);
		wbautoplayimg.setOnClickListener(new MyButton());
		wbLoopplayimg.setOnClickListener(new MyButton());
		wborderimg.setOnClickListener(new MyButton());
		wbpreimg.setOnClickListener(new MyButton());
		wbnextimg.setOnClickListener(new MyButton());
		wbup.setOnClickListener(new MyButton());
		wbdown.setOnClickListener(new MyButton());
		showorhide.setOnClickListener(new MyButton());
		wbnewword.setOnClickListener(new MyButton());
		Log.i("nnn3","nnn");
		tt=(ScrollView)findViewById(R.id.tt);
		initList(true);
	}
	void showpre()
	{
		Log.i("nnn5","nnn"+checkState);
		if(checkState>=1)
		{
			Log.i("nnn55","kk");
			Log.i("nnn","nnnm");
			iwop.setOpTable(sv.getTableName());
			Log.i("nnn","nnn0");
			Log.i("nnn55",sv.getTableName());
			iwop.setOpTable(sv.getTableName());
			Log.i("nnn","nnn0");
			Log.i("nnn56",strwordlist);
			wordIDtemp=srop.getBrouseWordID(StudyReviewID, currentID,2, circleState, strwordlist);
			Log.i("nnn56",wordIDtemp+"");
			int flagkingsoft=iwop.querrybyWordIDFirst(wordIDtemp);
			getKingSoftDis(wordIDtemp+"");
			if(iwop.getWordID()==-1)
			{ if(circleState==true)
			{
				Log.i("nnn50nnnnnnn","nnn");
				srop.setWORDTableID(sv.getTableID());
				srop.setGroupingID(groupIDtemp);
				srop.setWordIDListStudiedop("","",StudyReviewID,brouseop,op,this);
				iwop.querrybyWordIDFirst(wordIDtemp);
				showwordall(2);
			}
			else {
				Toast.makeText(WordBrowse.this,"已经是第一条记录！",0).show();
				timerflag=false;
				wbautoplayimg.setImageResource(R.drawable.playimg);
			}
			}
			else 
				showwordall(2);
			Log.i("nnn9","nnn");
			setWelcomesText();
			Log.i("nnn101","nnn");
			if(checkState==3)
			{
				LinearLayout l1=null;
				l1=(LinearLayout)findViewById(R.id.l1);
				l1.setVisibility(View.VISIBLE);
				LinearLayout r2=null;
				LinearLayout r1=null;
				r2=(LinearLayout)findViewById(R.id.r2);
				r2.setVisibility(View.GONE);
			}
		}
		checkState=1;
	}
	private void getKingSoftDis(String wordName) {
		// TODO Auto-generated method stub
		
	}
	void initList(boolean brousemethod)
	{
		Log.i("nnn455","nnn"+SelectedGroupID+"  "+content);
		if(op<=2){
			Log.i("nnn455","nnn"+SelectedGroupID);
			if(!SelectedGroupID.equals(""))
			{
				gdop.setGroupingID(Integer.parseInt(SelectedGroupID));
				groupIDtemp=Integer.parseInt(SelectedGroupID);
				gdop.setWORDTableID(sv.getTableID());
				gdop.setUID((int)uid);
				gdop.querryWordIDbygroupingID(sv.getFamiliarity(),this);
				strwordlist=gdop.getWordIDList();
				checkState=checkstate(groupIDtemp,"");
				Log.i("nnn5","nnn "+checkState);
			}
		}
		else if(op==3)
		{
			strwordlist=SelectedArrayID;
			checkState=checkstate(-1,strwordlist);
		}
		if(op==1&&brouseop==1&&currentID!=-1||op==2&&sv.getFamiliarity().equals("-1")&&brouseop==1)
		{
			strwordlist="";
		}
		if(brouseop==2)strwordlist=bs.ReverseSequence(strwordlist);
		if(brouseop==3)strwordlist=bs.disorderSequence(strwordlist);
		if(brousemethod)currentID=-1;
		if(op==1&&brouseop>=2||op==2||op==1&&!(strwordlist.indexOf(srop.getWordIDListStudied())!=-1))
		{
			srop.editWordIDLisStudied("", StudyReviewID);
			checkState=3;}
		}
		void showopFirst(){
			Log.i("nnn5nnnnnn","nnn"+checkState+" "+currentID+" "+op);
			if(checkState>=1)
			{
				Log.i("nnn55","kk");
				iwop.setOpTable(sv.getTableName());
				Log.i("nnn","nnn0");
				Log.i("nnn55",sv.getTableName());
				iwop.setOpTable(sv.getTableName());
				Log.i("nnn56nnnn",strwordlist +"  "+currentID);
				Log.i("nnn567nnnn",strwordlist +" "+currentID+" "+StudyReviewID+" "+tttt);
				wordIDtemp=srop.getBrouseWordID(StudyReviewID, currentID, 1,circleState, strwordlist);
				srop.getStudyReviewInfo(tttt);
				Log.i("nnn56",wordIDtemp+" "+" "+currentID);
				int flagkingsoft=iwop.querrybyWordIDFirst(wordIDtemp);
				Log.i("nnn6nnnnn000000000000000n","nnn"+flagkingsoft);
				getKingSoftDis(wordIDtemp+"");
				Log.i("nnn6","nnn");
				srop.getStudyReviewInfo(tttt);
				if(iwop.getWordID()==-1)
				{if(circleState==true)
				{
					Log.i("nnn50nnnnnnn","nnn");
					srop.setWORDTableID(sv.getTableID());
					srop.setGroupingID(groupIDtemp);
					srop.setWordIDListStudiedop("","",StudyReviewID,brouseop,op,this);
					iwop.querrybyWordIDFirst(wordIDtemp);
					showwordall(1);
				}
				else {
					Toast.makeText(WordBrowse.this,"已经是最后一条记录！",0).show();
					timerflag=false;
					wbautoplayimg.setImageResource(R.drawable.playimg);
				}
				}
				else {
					srop.getStudyReviewInfo(tttt);
					showwordall(1);
				}
				Log.i("nnn9","nnn");
				setWelcomesText();
				Log.i("nnn101","nnn");
				if(checkState==3)
				{
					LinearLayout l1=null;
					l1=(LinearLayout)findViewById(R.id.l1);
					l1.setVisibility(View.VISIBLE);
					LinearLayout r2=null;
					LinearLayout r1=null;
					showorhide.setVisibility(View.VISIBLE);
					r2=(LinearLayout)findViewById(R.id.r2);
					r2.setVisibility(View.GONE);
				}
			}
			else {
				checkState=1;
			}
			checkState=1;
		}
		int checkstate(int groupIDtemp,String strtemp)
		{
			int flag=1;
			srop.setWORDTableID(sv.getTableID());
			Log.i("ooo","000000000000000000000000"+op);
			flag=srop.checkstudystate(uid, op, brouseop, groupIDtemp, strtemp);
			if(srop.getStudyReviewID()>0)
				StudyReviewID=srop.getStudyReviewID();
			return flag;
		}
		void setWelcomesText()
		{
			TextView WelcomeWords=(TextView)findViewById(R.id.wbWordPhonogram);
			WelcomeWords.setText(sv.getUName()+"用户，欢迎您！");
			wtop.setTableName(sv.getTableName());
			wtop.querrybyTablename();
			String groupingtemp="";
			if(content==1)
			{
				bs.Split_temp(SelectedGroupID,'#');
				String[] tempstr=bs.getStr_temp();
				if(tempstr.length>1)
				{
					gdop.setGroupingID(Integer.parseInt(tempstr[0]));
					srop.setGroupingID(Integer.parseInt(tempstr[0]));
				}
				else {
					gdop.setGroupingID(Integer.parseInt(SelectedGroupID));
					srop.setGroupingID(Integer.parseInt(SelectedGroupID));
				}
				gdop.setWORDTableID(sv.getTableID());
				gdop.setUID((int)uid);
				gdop.querryWordIDbygroupingID(sv.getFamiliarity(),this);
				srop.querryWordIDListGrouping(uid);
				groupingtemp=" 第  "+gdop.getGROUPID()+" 组   ,  "+(bs.getlenth(srop.getWordIDListStudied()))+"/"+bs.getlenth(strwordlist);
			}
			else {
				srop.querryWordIDListArray(uid);
				groupingtemp=bs.getlenth(srop.getWordIDListStudied())+"/"+bs.getlenth(SelectedArrayID);
			}
			groupingtemp=(wtop.getStudiedGroupCount())+"/"+wtop.getTotalWord()+"."+groupingtemp;
			wbClassgroup.setText(sv.getTableNameChina()+"词库."+groupingtemp);
		}
		void showwordall(int flagupdate){
			Log.i("hjffffffffffff",LeariningStudymode+"");
			if(LeariningStudymode.equals("2"))
			{
				wbNumbering.setText("编号："+iwop.getWordID()+"");
				wbThewords.setText("单词："+iwop.getWordName());
				wbPhoneticsymbol.setText("");
				wbInterpret.setText("");
				wbExample.setText("");
				flagupdatetemp=flagupdate;
			}
			else {
				showWord(flagupdate);}
		}
		void showWord(int flagupdate)
		{
			Log.i("oookjk1324","000000000000000000000000"+wordIDtemp+" "+flagupdate);
			currentID=iwop.getWordID();
			Log.i("nnn7","nnn");
			WordID=iwop.getWordID();
			wbNumbering.setText("编号111："+iwop.getWordID()+"");
			wbThewords.setText("单词："+iwop.getWordName());
			wbfamiliarity.setText(""+iwop.getFamiliarity());
			if(idls.checkup(iwop.getFamiliarity()))
			{
				wbup.setVisibility(View.INVISIBLE);
				Log.i("jjjjj","kk1");
			}
			else {
				wbup.setVisibility(View.VISIBLE);
				Log.i("jjjjj","kk2");
			}
			if(idls.checkdown(iwop.getFamiliarity()))
			{
				wbdown.setVisibility(View.INVISIBLE);
				Log.i("jjjjj","kk3");
			}
			else {
				wbdown.setVisibility(View.VISIBLE);
				Log.i("jjjjj","kk4");
			}
			idlsfamiliarity=iwop.getFamiliarity();
			if(idls.checkup(idlsfamiliarity))
			{
				wbup.setVisibility(View.GONE);
			}
			else {
				wbup.setVisibility(View.VISIBLE);
			}
			if(idls.checkdown(idlsfamiliarity))
			{
				wbup.setVisibility(View.GONE);
			}
			else {
				wbup.setVisibility(View.VISIBLE);
			}
			String Phonogramtemp="";
			Log.i("nnn8","nnn");
			int location1=0;
			int location2=0;
			if(iwop.getPhonogram()!=null&&iwop.getPhonogram().equals("")==false)Phonogramtemp="音标："+iwop.getPhonogram()+"";
			wbPhoneticsymbol.setText(Phonogramtemp);	
			}	
		void showformat()
		{
			String tempstr="";
			if(sv.getWordSize().trim().equals(""))tempstr="12";else tempstr=sv.getWordSize().trim();
			wbThewords.setTextSize(Integer.parseInt(tempstr));
			if(sv.getPhonogramSize().trim().equals(""))tempstr="12";
			else tempstr=sv.getPhonogramSize().trim();
			wbPhoneticsymbol.setTextSize(Integer.parseInt(tempstr));
			if(sv.getExplainSize().trim().equals(""))tempstr="12";
			else tempstr=sv.getExplainSize().trim();
			wbInterpret.setTextSize(Integer.parseInt(tempstr));
			if(sv.getExampleSize().trim().equals(""))tempstr="12";
			else tempstr=sv.getExampleSize().trim();
			wbExample.setTextSize(Integer.parseInt(tempstr));
			int color=0;
			if(sv.getWordColor().trim().equals(""))tempstr="#ffffff";
			else tempstr=sv.getWordColor().trim();
			color=Color.parseColor(tempstr);
			wbThewords.setTextColor(color);
			if(sv.getPhonogramColor().trim().equals(""))tempstr="#ffffff";
			else tempstr=sv.getExplainColor().trim();
			color=Color.parseColor(tempstr);
			wbPhoneticsymbol.setTextColor(color);
			if(sv.getExplainColor().trim().equals(""))tempstr="#ffffff";
			else tempstr=sv.getExplainColor().trim();
			color=Color.parseColor(tempstr);
			wbInterpret.setTextColor(color);
			if(sv.getExampleColor().trim().equals(""))tempstr="#ffffff";
			else tempstr=sv.getExampleColor().trim();
			color=Color.parseColor(tempstr);
			wbExample.setTextColor(color);
			if(sv.getWordShow().trim().equals("")||sv.getWordShow().trim().equals("0"))
				wbThewords.setVisibility(View.GONE);
			else wbThewords.setVisibility(View.VISIBLE);
			if(sv.getPhonogramShow().trim().equals("")||sv.getPhonogramShow().trim().equals("0"))
				wbPhoneticsymbol.setVisibility(View.GONE);
			else wbPhoneticsymbol.setVisibility(View.VISIBLE);
			if(sv.getExplainShow().trim().equals("")||sv.getExplainShow().trim().equals("0"))
				wbInterpret.setVisibility(View.GONE);
			else wbInterpret.setVisibility(View.VISIBLE);
			if(sv.getExampleShow().trim().equals("")||sv.getExampleShow().trim().equals("0"))
				wbExample.setVisibility(View.GONE);
			else wbExample.setVisibility(View.VISIBLE);
			if(sv.getWordShow()=="0"){
				wbNumbering.setVisibility(View.GONE);
				wbThewords.setVisibility(View.GONE);
			}
			if(sv.getPhonogramShow()=="0")
			{wbPhoneticsymbol.setVisibility(View.GONE);
			if(sv.getExplainShow()=="0")
			{wbInterpret.setVisibility(View.GONE);
			if(sv.getExampleShow()=="0")
			{wbExample.setVisibility(View.GONE);}
			}
			}
		}
			 private class MyButton implements OnClickListener {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch(v.getId()){
					case R.id.wbcheckconfirm:
						Log.i("ooo","000000000000000000000000");
						checkState=3;
						Log.i("ooo2","000000000000000000000000");
						continueflag=false;
						srop.setWORDTableID(sv.getTableID());
						srop.getStudyReviewInfo(StudyReviewID);
						int t=bs.getLocation(srop.getWordIDListTotal(),srop.getWordIDListStudied());
						Log.i("ooo2",t+" "+bs.getLast(srop.getWordIDListStudied()+" "+srop.getWordIDListStudied()));
						String stringt=bs.getLast(srop.getWordIDListStudied());
						if(stringt.equals("")){currentID=-1;srop.editWordIDLisStudied("",StudyReviewID);}
						else currentID=Integer.parseInt(stringt);
						if(t!=currentID){currentID=-1;srop.editWordIDLisStudied("",StudyReviewID);}
						Log.i("ooo3","000000000000000000000000"+currentID);
						showopFirst();
						break;
						case R.id.wbcheckcancle:
							checkState=3;
							continueflag=true;
							if(continueflag)
							{
								if(op==1)
								{wtop.addStudiedWordCount(-1*bs.getlenth(srop.getWordIDListStudied()),sv.getTableName(),(int)uid);
								}
								Log.i("nnn50nnnnnnn","nnn");
								srop.setWORDTableID(sv.getTableID());
								srop.setGroupingID(groupIDtemp);
								srop.editWordIDLisStudied("",StudyReviewID);
							}
							showopFirst();
							break;
						case R.id.showorhide:
							if(showorhide.getText().toString().trim().equals("显示临时设置和提醒"))
							{
								LinearLayouttext.setVisibility(View.VISIBLE);
								rr3.setVisibility(View.VISIBLE);
								rr4.setVisibility(View.VISIBLE);
								showorhide.setText("隐藏临时设置和提醒");
							}
							else {
								LinearLayouttext.setVisibility(View.GONE);
								rr3.setVisibility(View.GONE);
								rr4.setVisibility(View.GONE);
								showorhide.setText("显示临时设置和提醒");
							}
							break;
						case R.id.wbautoplayimg:
							break;
						case R.id.wbLoopplayimg:
							if(!circleState)
							{
								circleState=true;
								wbLoopplayimg.setImageResource(R.drawable.circleimg);
								}
								break;
								case R.id.wbadd:
									idlsfamiliarity=idls.getup(idlsfamiliarity);
									iwop.setOpTable(sv.getTableName());
									iwop.setWordID(currentID);
									iwop.setFamiliarity(idlsfamiliarity);
									iwop.editfamiliarity();
									if(idls.checkup(iwop.getFamiliarity()))
									{
										wbup.setVisibility(View.INVISIBLE);
										Log.i("jjjjj","kk1");
									}
									else {
										wbup.setVisibility(View.VISIBLE);
										Log.i("jjjjj","kk2");
									}
									if(idls.checkup(iwop.getFamiliarity()))
									{
										wbup.setVisibility(View.INVISIBLE);
										Log.i("jjjjj","kk3");
									}
									else {
										wbdown.setVisibility(View.VISIBLE);
										Log.i("jjjjj","kk4");
									}
									wbfamiliarity.setText(""+idlsfamiliarity);
									delayflag=1;
									break;
								case R.id.wbplus:
									idlsfamiliarity=idls.getdown(idlsfamiliarity);
									iwop.setOpTable(sv.getTableName());
									iwop.setWordID(currentID);
									iwop.setFamiliarity(idlsfamiliarity);
									iwop.editfamiliarity();
									if(idls.checkup(iwop.getFamiliarity()))
									{
										wbup.setVisibility(View.INVISIBLE);
										Log.i("jjjjj","kk1");
									}
									else {
										wbup.setVisibility(View.VISIBLE);
										Log.i("jjjjj","kk2");
									}
									if(idls.checkdown(iwop.getFamiliarity()))
									{
										wbdown.setVisibility(View.INVISIBLE);
										Log.i("jjjjj","kk3");
									}
									else {
										wbdown.setVisibility(View.VISIBLE);
										Log.i("jjjjj","kk4");
									}
									wbfamiliarity.setText(""+idlsfamiliarity);
									delayflag=1;
									break;
								case R.id.wbnewword:
									break;
								case R.id.wborderimg:
									if(brouseop<=2)brouseop++;
									else brouseop=1;
									if(brouseop==1)wborderimg.setImageResource(R.drawable.sequence);
									if(brouseop==2)wborderimg.setImageResource(R.drawable.reverse);
									if(brouseop==3)wborderimg.setImageResource(R.drawable.disordersequence);
									srop.editWordIDLisStudied("",StudyReviewID);
									initList(true);
									showopFirst();
									break;
								case R.id.wbpreimg:
									showpre();
									break;
								case R.id.wbnextimg:
									continueflag=false;
									showopFirst();
									break;
							}} 					
					void initSetting(){}
				}
			}

