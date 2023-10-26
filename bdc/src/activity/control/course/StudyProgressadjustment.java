package activity.control.course;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import wgy.recitewords.bdc.R;

import database.control.op.GroupingdetalsTableop;
import database.control.op.ImportWordTableop;
import database.control.op.StudyReviewTableop;
import database.control.op.WordtotalTableop;
import database.control.op.userInfoop;
import general.base.op.BaseSplit;
import general.base.op.SettingVariable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StudyProgressadjustment extends Activity {
	Button spatPreview=null;
	Button spatPreservation=null;
	Button spatcancle=null;
	
	SettingVariable sv=null;
	GroupingdetalsTableop gdop=null;
	WordtotalTableop wtop=null;
	ImportWordTableop iwmt=null;
	StudyReviewTableop srtop=null;
	BaseSplit bs=null;
	userInfoop uifop=null;
	
	EditText allediText=null;
	EditText resteditText=null;
	int unstudyword=0;
	String studycontent="";
	int studyCount=0;
	int groupcountbefore=1;
	int studiedWordCount=0;
	private List<String> list1=new ArrayList<String>();
	TextView Statisticalgrouping=null;
	TextView unregroup=null;
	TextView Courseandgroup=null;
	TextView Eachcourseandgroup=null;
	TextView TextView06=null;
	TextView Pregroup=null;
	TextView Postgroup=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_progressadjustment);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		
		gdop=new GroupingdetalsTableop(this);
		wtop=new WordtotalTableop(this);
		srtop=new StudyReviewTableop(this);
		iwmt=new ImportWordTableop(this);
		uifop=new userInfoop(this);
		bs=new BaseSplit();
		
		Statisticalgrouping=(TextView)findViewById(R.id.Statisticalgrouping);
		Courseandgroup=(TextView)findViewById(R.id.Courseandgroup);
		Eachcourseandgroup=(TextView)findViewById(R.id.Eachcourseandgroup);
		Pregroup=(TextView)findViewById(R.id.Pregroup);
		Postgroup=(TextView)findViewById(R.id.Postgroup);
		unregroup=(TextView)findViewById(R.id.unregroup);
		TextView06=(TextView)findViewById(R.id.TextView06);
		allediText=(EditText)findViewById(R.id.alleditText);
		resteditText=(EditText)findViewById(R.id.resteditText);
		
		setWelcomesText();
		intijiemian();
		showNon();
		
		spatPreview=(Button)findViewById(R.id.spatPreview);
		spatPreservation=(Button)findViewById(R.id.spatPreservation);
		spatcancle=(Button)findViewById(R.id.spatcancle);
		spatPreview.setOnClickListener(new ButtonListener());
		spatPreservation.setOnClickListener(new ButtonListener());
		spatcancle.setOnClickListener(new ButtonListener());
	}
		
		void showNon()
		{
			Statisticalgrouping.setVisibility(View.GONE);
			Courseandgroup.setVisibility(View.GONE);
			Eachcourseandgroup.setVisibility(View.GONE);
			Pregroup.setVisibility(View.GONE);
			Postgroup.setVisibility(View.GONE);
			unregroup.setVisibility(View.GONE);
			TextView06.setVisibility(View.GONE);
		}
		private class ButtonListener implements OnClickListener{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int countgroup=0;
				int counttotal=0;
				showNon();
				
				String alltext=allediText.getText().toString().trim();
				String restText=resteditText.getText().toString().trim();
				if(alltext.equals("")==false&&restText.equals("")==false)
				{Toast.makeText(StudyProgressadjustment.this,"不能同时对课程中的所有单词进行全部重新分组和部分分组操作，请重新给定分组要求！",0).show();
				allediText.setText("");
				resteditText.setText("");
				}
				else {
					if(alltext.equals("")==false)
					{
						countgroup=Integer.parseInt(allediText.getText().toString());
						counttotal=wtop.getTotalWord();
						TextView06.setVisibility(View.VISIBLE);
					}
					else{
						if(restText.equals("")==false)
						{
							unregroup.setVisibility(View.VISIBLE);
							Courseandgroup.setVisibility(View.VISIBLE);
							Eachcourseandgroup.setVisibility(View.VISIBLE);
							TextView06.setVisibility(View.VISIBLE);
							countgroup=Integer.parseInt(resteditText.getText().toString());
							Courseandgroup.setText("共计分为"+(studyCount)+"组");
							Eachcourseandgroup.setText(studycontent);
							counttotal=unstudyword;
						}
						else{
							Toast.makeText(StudyProgressadjustment.this,"请输入相关的分组数，再点击相应的操作！",0).show();
						}
					}
					switch(v.getId()){
					case R.id.spatPreview:
						if(counttotal%countgroup==0)
						{
							int groupSize=counttotal/countgroup;
							Pregroup.setText("分为"+countgroup+"组，每组"+groupSize+"个单词");
							Pregroup.setVisibility(View.VISIBLE);
						}
						else {
							int groupSize=counttotal/countgroup+1;
							int temp=countgroup-1;
							int least=counttotal-(countgroup-1)*groupSize;
							while(least<0)
							{
								temp--;
								countgroup--;
								least+=groupSize;
							}
							Statisticalgrouping.setVisibility(View.VISIBLE);
							Pregroup.setVisibility(View.VISIBLE);
							Postgroup.setVisibility(View.VISIBLE);
							Courseandgroup.setText("共计分为"+(studyCount)+"组");
							if(alltext.equals(""))
								Statisticalgrouping.setText("共计分为"+(studyCount+countgroup)+"组");
							else 
								Statisticalgrouping.setText("共计分为"+(countgroup)+"组");
							Pregroup.setText("前"+temp+"组。每组"+groupSize+"个单词");
							Postgroup.setText("最后一组单词数为"+least+"个。");
						}
						break;
					case R.id.spatPreservation:
						showNon();
						int groupSize=0;
						int beginWordID=0;
						int begingroupID=0;
						int groupCountt=0;
						int totalgroup=0;
						if(alltext.equals("")==false)
						{
							beginWordID=1;
							begingroupID=1;
							totalgroup=0;
						}
						else{
							beginWordID=groupcountbefore;
							begingroupID=studyCount+1;
							totalgroup=studyCount;
						}
						if(counttotal%countgroup==0)
						{
							Log.i("vvvva","6");
							groupSize=counttotal/countgroup;
							Log.i(counttotal+"",groupSize+"  "+countgroup);
							totalgroup+=countgroup;
						}
						else {
							Log.i("vvvva","5");
							groupSize=counttotal/countgroup+1;
							Log.i(counttotal+"",groupSize+"  "+countgroup);
							int temp=countgroup-1;
							int least=counttotal-(countgroup-1)*groupSize;
							while(least<0)
							{
								temp--;
								countgroup--;
								least+=groupSize;
							}
							totalgroup+=countgroup;
						}
						gdop.setWORDTableID(sv.getTableID());
						iwmt.setOpTable(sv.getTableName());
						uifop.setUsername(sv.getUName());
						uifop.getallbyusername();
						gdop.setUID((int)uifop.getId1());
						gdop.AddList(beginWordID,-1, begingroupID, groupSize,iwmt);
						Log.i(counttotal+"",groupSize+"    "+countgroup+"   "
								+totalgroup+"    "+studiedWordCount+"    "+studyCount+"    ");
						wtop.setGroupCount(totalgroup);
						wtop.setStudiedWordCount(studiedWordCount);
						wtop.setStudiedGroupCount(studyCount);
						wtop.setTableName(sv.getTableName());
						wtop.editGroup();
						Toast.makeText(StudyProgressadjustment.this,"分组信息保存成功！",0).show();
						break;
					case R.id.spatcancle:
						showNon();
						Intent intent=new Intent();
						intent.setClass(StudyProgressadjustment.this,Course_gov.class);
						startActivity(intent);
						break;
					}
				}
			}
		}
		void setWelcomesText()
		{
			sv=new SettingVariable();
			TextView welcomeWords=(TextView)findViewById(R.id.spatWordPhonogram);
			welcomeWords.setText(sv.getUName()+"用户，欢迎您！");
		}
		void studiedcomputer(int totalWord)
		{
			int[] groupdetails=new int[10];
			int[] groupcount=new int[10];
			int k=0;
			int flag=1;
			int tempcount=0;int j=0;
			for(int i=0;i<0;i++) groupcount[i]=0;
			uifop.setUsername(sv.getUName());
			uifop.getallbyusername();
			List<StudyReviewTableop> gdopList=srtop.querrystudiedGroupdetailsbytableID(sv.getTableID(),(int)uifop.getId1());
			for(StudyReviewTableop wij:gdopList){
				Log.i("number："+bs.getlenth(wij.getWordIDListTotal()),"ii");
				studyCount=wij.getGroupID();
				if(wij.getState().equals("0")==false)
				{Log.i("number:1 "+wij.getWordIDListTotal(),"iii");
				studiedWordCount+=bs.getlenth(wij.getWordIDListTotal());
				groupcountbefore=Integer.parseInt(bs.getLast(wij.getWordIDListTotal()))+1;
				for(j=0;j<=k;j++)
				{tempcount=bs.getlenth(wij.getWordIDListTotal());
				if(tempcount==groupcount[j])
				{groupdetails[j]++;break;}
				}
				if(j==k+1)
				{groupcount[k]=tempcount;groupdetails[k++]=1;}
				}
				else{
					Log.i("number: "+wij.getStudiedCount(),"iiii");
					if(wij.getState().equals("0")&&wij.getStudiedCount()<bs.getlenth(wij.getWordIDListTotal())
							&&wij.getWordIDListTotal().contains(wij.getWordIDListStudied())&&
							wij.getWordIDListTotal().startsWith(wij.getWordIDListStudied()))
					{studiedWordCount+=bs.getlenth(wij.getWordIDListStudied());
					groupcountbefore=Integer.parseInt(bs.getLast(wij.getWordIDListStudied()))+1;
					tempcount=bs.getlenth(wij.getWordIDListStudied());
					for(j=0;j<=k;j++)
					{
						if(tempcount==groupcount[j])
						{groupdetails[j]++;break;}
					}
					if(j==k+1)
					{groupcount[k]=tempcount;groupdetails[k++]=1;}
					}
					break;
				}
			}
			for(j=0;j<k;j++)
			{if(groupdetails[1]==0){studycontent="共"+groupdetails[0]+"组，每组"+groupcount[0]+"个单词";}
			else{
				if(studycontent.equals("")==false)studycontent+="\n";
				studycontent+="其中"+groupdetails[j]+"组,每组"+groupcount[j]+"个单词";
			}
			}
			unstudyword=totalWord-studiedWordCount;
		}
		void intijiemian()
		{
			wtop.setTableName(sv.getTableName());
			wtop.querrybyTablename();
			TextView spatCoursetitle=(TextView)findViewById(R.id.spatCoursetitle);
			TextView spattotalnumberofwords=(TextView)findViewById(R.id.spattotalnumberofwords);
			TextView spatunlearned=(TextView)findViewById(R.id.spatunlearned);
			studiedcomputer(wtop.getTotalWord());
			spatCoursetitle.setText("课程名："+sv.getTableNameChina());
			spattotalnumberofwords.setText("总单词数："+wtop.getTotalWord()+"，分为");
			spatunlearned.setText("未学习单词"+unstudyword+"，分为");
		}
}

