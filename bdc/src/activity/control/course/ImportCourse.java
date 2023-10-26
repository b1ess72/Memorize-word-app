package activity.control.course;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import wgy.recitewords.bdc.R;

import general.base.op.DateTimeOp;
import general.base.op.SettingVariable;
import general.base.op.initDropListClass;
import database.control.op.ImportWordTableop;
import database.control.op.WordtotalTableop;
import database.control.op.userInfoop;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ImportCourse extends Activity {
	TextView WelcomeWords=null;
	Button BrowseImportButton=null;
	Button returnbt=null;
	EditText importCourseName=null;
	CheckBox duplicationname=null;
	CheckBox duplicationcourse=null;
	TextView progressBarstate=null;
	private String familiarityString="";
	private Spinner spinnerfamiliarity=null;
	LinearLayout lnormal=null;
	LinearLayout limportpass=null;
	userInfoop uifop=null;
	ImportWordTableop iwop=null;
	SettingVariable sv=null;
	private ArrayAdapter<String> adapterfamiliarity;
	WordtotalTableop wtt=null;
	
	int duplicationnameflag=0;
	int duplicationcourseflag=0;
	String importCourseNamevalue="";
	
	List<String> listfamiliarity=new ArrayList<String>();
	File file=null;
	int count=0,countt=0;
	BufferedReader bReader=null;
	String tableNameString="";
	
	int countgg=0,maxzunei=0,countp=0;
	private ProgressBar mProgress;
	private double mProgressStatus=0;
	private Handler mHandler=new Handler();
	long counttemp=0;
	String WordName="";
	int afterop=1;
	int wordcountmin=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_importcourse);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		init();
	}
	void init()
	{
		duplicationname=(CheckBox)findViewById(R.id.duplicationName);
		duplicationcourse=(CheckBox)findViewById(R.id.duplicationCourse);
		lnormal=(LinearLayout)findViewById(R.id.init);
		limportpass=(LinearLayout)findViewById(R.id.importpass);
		progressBarstate=(TextView)findViewById(R.id.progressBarstate);
		progressBarstate.setText("正在准备导入，请稍后！");
		importCourseName=(EditText)findViewById(R.id.importCourseName);
		wtt=new WordtotalTableop(ImportCourse.this);
		iwop=new ImportWordTableop(ImportCourse.this);
		BrowseImportButton=(Button)findViewById(R.id.browseImportButton);
		BrowseImportButton.setOnClickListener(new ButtonListener());
		returnbt=(Button)findViewById(R.id.returnbt);
		returnbt.setOnClickListener(new ButtonListener());
		uifop=new userInfoop(this);
		duplicationname.setChecked(true);
		duplicationcourse.setChecked(true);
		
		setWelcomesText();
		initDropList();
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.returnbt:
				Intent intent=new Intent();
				intent.setClass(ImportCourse.this,Course_gov.class);
				startActivity(intent);
				break;
			case R.id.browseImportButton:
				if(duplicationname.isChecked()){duplicationnameflag=1;}
				if(duplicationcourse.isChecked()){duplicationcourseflag=1;}
				importCourseNamevalue=importCourseName.getText().toString();
				wtt.setTableNameChina(importCourseNamevalue);
				if(wtt.checkTableNameChina()==1)
				{ if(duplicationcourseflag==1)
				{
					wtt.deleteTableInfo();
					Intent intent1=new Intent(Intent.ACTION_GET_CONTENT);
					intent1.setType("*/*");
					intent1.addCategory(Intent.CATEGORY_OPENABLE);
					startActivityForResult(intent1, 1);
				}
				else{
					Toast.makeText(ImportCourse.this,"存在同名词库，请更改后重新导入！",
							0).show();
				}}
				else{
					Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);
					intent2.setType("*/*");
					intent2.addCategory(Intent.CATEGORY_OPENABLE);
					startActivityForResult(intent2, 1);
				}
				break;
			}}
	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(resultCode==Activity.RESULT_OK){
			Uri uri=data.getData();
			String[] proj={MediaStore.Images.Media.DATA};
			Cursor actualimagecursor=managedQuery(uri,proj,null,null,null);
			int actual_image_column_index=
					actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String File_path=
					actualimagecursor.getString(actual_image_column_index);
			file=new File(File_path);
			Toast.makeText(ImportCourse.this,file.toString()
					,Toast.LENGTH_SHORT).show();
			ReadFile();
		}
	}
	public void ReadFile()
	{
		DateTimeOp dtOp=new DateTimeOp();
		SettingVariable sv=new SettingVariable();
		FileInputStream inStream;
		try{
			inStream=new FileInputStream(file);
			bReader=new BufferedReader(new InputStreamReader(inStream));
			long id=-1;
			String t;
			t=bReader.readLine().toString().trim();
			
			byte[] bt=t.getBytes();
			String st="";
			for(int i=0;i<bt.length;i++)
			{
				int tostring=bt[i]-48;
				if(bt[i]<=48+9&&bt[i]>=48)
					st+=tostring+"";
			}
			count=Integer.valueOf(st).intValue();
			
			wtt.setTableNameChina(importCourseNamevalue);
			tableNameString=wtt.createTable();
			iwop.setOpTable(tableNameString);
			wtt.setTableID(-1);
			wtt.setTableName(tableNameString);
			wtt.setTotalWord(count);
			wtt.setLeadInTime(dtOp.getDateTimeNowShort());
			wtt.setGroupCount(0);
			wtt.setStudiedGroupCount(0);
			wtt.setStudiedWordCount(0);
			wtt.setUID(Integer.parseInt(sv.getUID()));
			wtt.setTableNameChina(importCourseNamevalue);
			wtt.Add();
			
			if(tableNameString.equals("")==true)
			{
				Toast.makeText(ImportCourse.this,"存储词库z信息失败！",
						Toast.LENGTH_SHORT).show();
			}
			else{
				lnormal.setVisibility(View.GONE);
				limportpass.setVisibility(View.VISIBLE);
				maxzunei=count/1000+1;
				mProgress=(ProgressBar)findViewById(R.id.progressBar);
				new Thread(new Runnable() {			
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(mProgressStatus<=100){
							mProgressStatus=writedata();
							mHandler.post(new Runnable() {	
								@Override
								public void run() {
									// TODO Auto-generated method stub
									mProgress.setProgress((int)mProgressStatus);
									BigDecimal db=new BigDecimal(mProgressStatus);
									db=db.setScale(1,BigDecimal.ROUND_HALF_UP);
									progressBarstate.setText(db+"%           "+countt+"/"+count);
									if(mProgressStatus>=100||countt==count)
									{ progressBarstate.setText("导入完成，即将返回管理主界面");
									mProgressStatus++;
									if(wordcountmin>0||countt!=count)
									{ 
									if(afterop==1)
									{ 
									wtt.setTableName(tableNameString);
									//wtt.setTotalWord(countt-wordcountmin);
									wtt.editWordCount(-1*wordcountmin);
									}
									afterop=0;
									}
									
									Intent intent1=new Intent(ImportCourse.this,Course_gov.class);
									intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent1);
									}}   });    } }
					}).start();;
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		public double writedata()
		{
			long WordID;
			String Phonogram;
			String WordMeaning;
			String Familiarity;
			SettingVariable sv=new SettingVariable();
			Familiarity=familiarityString;
			try{
				if(WordName.equals("")==true) WordName=bReader.readLine();
				while(!WordName.equals("#")&&countgg<=maxzunei)
				{ countgg++;countt++;
				Phonogram=bReader.readLine();
				WordMeaning=bReader.readLine();
				WordID=-1;
				iwop.setWordID(WordID);
				iwop.setWordName(WordName);
				iwop.setPhonogram(Phonogram);
				iwop.setWordMeaning(WordMeaning);
				iwop.setFamiliarity(Familiarity);
				
				int checkWordflag=iwop.checkWordName();
				if(duplicationnameflag==1&&checkWordflag>=1)
				{ iwop.setWordID(checkWordflag);
				iwop.edit();
				wordcountmin++;
				}
				else{
					counttemp=iwop.Add();
				}
				WordName=bReader.readLine();
				}}catch(IOException e){
					e.printStackTrace();
				}
			if(countgg>maxzunei||WordName.equals("#")){countgg=0;countp++;}
			if(WordName.equals("#")) return 101;
			double tt=((int)(countt*1000/count))/10.0-1;
			if(tt<0)
				tt=0;
			return (countt==count)?101:tt;
		}
		void setWelcomesText()
		{
			sv=new SettingVariable();
			WelcomeWords=(TextView)findViewById(R.id.importWeleComeWords);
			WelcomeWords.setText(sv.getUName()+"用户，欢迎您！");
		}
		void initDropList()
		{
			spinnerfamiliarity=(Spinner)findViewById(R.id.spinnerfamiliarity);
			initDropListClass idls=new initDropListClass("");
			listfamiliarity=idls.getList();
			familiarityString=listfamiliarity.get(1);
			
			adapterfamiliarity=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listfamiliarity);
			adapterfamiliarity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerfamiliarity.setAdapter(adapterfamiliarity);
			spinnerfamiliarity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> arg0,View arg1,
						int arg2,long arg3){
					familiarityString=listfamiliarity.get(arg2);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(ImportCourse.this,"Nothing",0).show();
				}
			});
		}
	}
