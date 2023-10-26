package activity.control.studyreview;

import java.util.ArrayList;
import java.util.List;

import wgy.recitewords.bdc.R;

import general.base.op.BaseSplit;
import general.base.op.SettingVariable;
import database.control.op.GroupingdetalsTableop;
import database.control.op.ImportWordTableop;
import database.control.op.WordtotalTableop;
import database.control.op.userInfoop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StudyReviewList extends Activity {
	int op;
	int content;
	String SelectedGroupID;
	String SelectedArrayID;
	String returnDialog="";
	Button srlcancle=null;
	Button srlWordstobrowse=null;
	ListView listView1=null;
	MyAdapter adapter=null;
	GroupingdetalsTableop gdop=null;
	userInfoop uifop=null;
	SettingVariable sv=null;
	TextView srlrevieworstudy=null;
	TextView srlCourseandgroup=null;
	ImportWordTableop iwop=null;
	WordtotalTableop wtop=null;
	BaseSplit bs=null;
	private List<ImportWordTableop> list=new ArrayList<ImportWordTableop>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.studygov_reviewlist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		gdop=new GroupingdetalsTableop(this);
		iwop=new ImportWordTableop(this);
		wtop=new WordtotalTableop(this);
		uifop=new userInfoop(this);
		bs=new BaseSplit();
		srlrevieworstudy=(TextView)findViewById(R.id.srlrevieworstudy);
		srlCourseandgroup=(TextView)findViewById(R.id.srlCourseandgroup);
		srlcancle=(Button)findViewById(R.id.srlcancle);
		srlcancle.setOnClickListener(new ButtonListener());
		srlWordstobrowse=(Button)findViewById(R.id.srlWordstobrowse);
		srlWordstobrowse.setOnClickListener(new ButtonListener());
		String SelectedGroupIDtemp="";
		Intent intent=getIntent();
		op=intent.getIntExtra("op",0);
		content=intent.getIntExtra("content",0);
		if(intent.getStringExtra("SelectedGroupID")==null)
			SelectedGroupIDtemp="";
		else 
			SelectedGroupIDtemp=intent.getStringExtra("SelectedGroupID");
		SelectedGroupID=SelectedGroupIDtemp;
		if(intent.getStringExtra("SelectedArrayID")==null)
			SelectedArrayID="";
		else
			SelectedArrayID=intent.getStringExtra("SelectedArrayID");
		returnDialog=intent.getStringExtra("returnDialog");
		setWelcomesText();
		String strwordlist="";
		Log.i("aaafaaaaaaaaaaaaaa",SelectedGroupIDtemp+" "+op+" "+content);
		if(content==1)
		{
			int groupIDtemp=0;
			bs.Split_temp(SelectedGroupIDtemp,'#');
			String[] tempstr=bs.getStr_temp();
			if(tempstr.length>1)
			{
				gdop.setGroupingID(Integer.parseInt(tempstr[0]));
				groupIDtemp=Integer.parseInt(tempstr[0]);
			}
			else {
				gdop.setGroupingID(Integer.parseInt(SelectedGroupIDtemp));
				groupIDtemp=Integer.parseInt(SelectedGroupIDtemp);
			}
			uifop.setUsername(sv.getUName());
			uifop.getallbyusername();
			gdop.setWORDTableID(sv.getTableID());
			uifop.setUsername(sv.getUName());
			uifop.getallbyusername();
			gdop.setUID((int)uifop.getId1());
			Log.i("aaasaaaaaaaaaaaaaa",SelectedGroupIDtemp+" "+uifop.getId1()+"  "+content);
			gdop.querryWordIDbygroupingID(sv.getFamiliarity(),this);
			Log.i("aaadaaaaaaaaaaaaaa",SelectedGroupIDtemp+"   "+op+"    "+content);
			strwordlist=gdop.getWordIDList();
			Log.i("aaaddaaaaaaaaaaaaa",SelectedGroupIDtemp+"  "+content);
			wtop.setTableName(sv.getTableName());
			wtop.querrybyTablename();
			if(bs.getlenth(strwordlist)==0)
			{
				srlWordstobrowse.setVisibility(View.GONE);
				srlCourseandgroup.setText(sv.getTableNameChina()+"课程，共计"+wtop.getGroupCount()+" 组，当前第  "+gdop.getGROUPID()+" 组，选中"+bs.getlenth(strwordlist)+"个单词，请重新选择难易程度。");
			}
			else 
				srlCourseandgroup.setText(sv.getTableNameChina()+" 课程，共计"+wtop.getGroupCount()+" 组，当前第 "+gdop.getGROUPID()+" 组，选中 "+bs.getlenth(strwordlist)+"个单词");
		}
		else {
			strwordlist=SelectedArrayID;
		}
		Log.i("aaaab",strwordlist);
		iwop.setOpTable(sv.getTableName());
		list=new ArrayList<ImportWordTableop>();
		list=iwop.querrybyWordList(strwordlist);
		Log.i("aaaac",strwordlist);
		adapter=new MyAdapter();
		listView1=(ListView)findViewById(R.id.srlitem2);
		Log.i("ddddddd","ss");
		listView1.setAdapter(adapter);
		Log.i("aaaac",strwordlist);
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.srlcancle:
				intent.putExtra("op",op);
				if(returnDialog.equals("Management"))intent.setClass(StudyReviewList.this,Management.class);
				if(returnDialog.equals("SelectGroupNumber"))intent.setClass(StudyReviewList.this,SelectGroupNumber.class);
				
				startActivity(intent);
				break;
			case R.id.srlWordstobrowse:
				intent.putExtra("op",op);
				intent.putExtra("content",content);
				intent.putExtra("SelectedGroupID",SelectedGroupID);
				intent.putExtra("SelectedArrayId",SelectedArrayID);
				intent.putExtra("returnDialog",returnDialog);
				intent.setClass(StudyReviewList.this,WordBrowse.class);
				break;
			}
			startActivity(intent);
		}
	}
	void setWelcomesText()
	{
		sv=new SettingVariable();
		if(op==1)
			srlrevieworstudy.setText("单词学习列表");
		else 
			srlrevieworstudy.setText("单词复习列表");
		TextView WelcomeWords=(TextView)findViewById(R.id.srlWordPhonogram);
		WelcomeWords.setText(sv.getUName()+"用户，欢迎您!");
	}
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View item=convertView!=null?convertView:View.inflate(getApplicationContext(),R.layout.studygov_reviewlist_item,null);
			TextView numbering=(TextView)item.findViewById(R.id.numbering);
			TextView words=(TextView)item.findViewById(R.id.words);
			TextView Phoneticsymbol=(TextView)item.findViewById(R.id.Phoneticsymbol);
			TextView interpret=(TextView)item.findViewById(R.id.interpret);
			final ImportWordTableop a=list.get(position);
			numbering.setText(a.getWordIDString()+"");
			words.setText(a.getWordName()+"");
			Phoneticsymbol.setText(a.getPhonogram()+"");
			interpret.setText(a.getWordMeaning()+"");
			return item;
		}
	}
}
