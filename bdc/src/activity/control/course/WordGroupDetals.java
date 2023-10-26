package activity.control.course;

import java.util.ArrayList;
import java.util.List;

import wgy.recitewords.bdc.R;

import database.control.op.GroupingdetalsTableop;
import database.control.op.WordtotalTableop;
import database.control.op.userInfoop;
import general.base.op.SettingVariable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WordGroupDetals extends Activity {
	SettingVariable sv=null;
	WordtotalTableop wtop=null;
	GroupingdetalsTableop gdop=null;
	userInfoop uifop=null;
	
	TextView textView1=null;
	TextView courseSelectInfo=null;
	Button Alladjustments=null;
	Button btreturn=null;
	ListView listView1=null;
	MyAdapter adapter=null;
	int op=0;
	List<WordGroupDetals_items> list=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.coursegov_wordgroupdetals);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		gdop=new GroupingdetalsTableop(this);
		wtop=new WordtotalTableop(this);
		uifop=new userInfoop(this);
		courseSelectInfo=(TextView)findViewById(R.id.courseSelectInfo);
		setWelcomesText();
		list=new ArrayList<WordGroupDetals_items>();
		uifop.setUsername(sv.getUName());
		uifop.getallbyusername();
		
		list=gdop.querryGroupwordCount(sv.getTableID(),(int)uifop.getId1());
		adapter=new MyAdapter();
		listView1=(ListView)findViewById(R.id.listView1);
		listView1.setAdapter(adapter);
		textView1=(TextView)findViewById(R.id.textView1);
		
		intijiemian();
		Alladjustments=(Button)findViewById(R.id.Alladjustments);
		btreturn=(Button)findViewById(R.id.btreturn);
		Alladjustments.setOnClickListener(new ButtonListener());
		btreturn.setOnClickListener(new ButtonListener());
		
		Intent intent=getIntent();
		op=intent.getIntExtra("op",0);
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch(v.getId()){
			case R.id.Alladjustments:
				intent.setClass(WordGroupDetals.this,StudyProgressadjustment.class);
				startActivity(intent);
				break;
			case R.id.btreturn:
				intent.setClass(WordGroupDetals.this,Course_gov.class);
				startActivity(intent);
				break;
			}
		}
	}
	private void setWelcomesText()
	{
		sv=new SettingVariable();
		TextView WelcomeWords=(TextView)findViewById(R.id.wgtWordPhonogram);
		WelcomeWords.setText(sv.getUName()+"用户，欢迎您!");
	}
	void intijiemian()
	{
		wtop.setTableName(sv.getTableName());
		wtop.querrybyTablename();
		TextView wgtcoursetitle=(TextView)findViewById(R.id.wgtcoursetitle);
		TextView wgtNumberofclusters=(TextView)findViewById(R.id.wgtNumberofclusters);
		TextView wgttotalnumberofwords=(TextView)findViewById(R.id.wgttotalnumberofwords);
		TextView wgtaveragenumberofwords=(TextView)findViewById(R.id.wgtaveragenumberofwords);
		
		wgtcoursetitle.setText("课程名称："+sv.getTableNameChina());
		wgtNumberofclusters.setText("分组数："+wtop.getGroupCount());
		wgttotalnumberofwords.setText("总单词数："+wtop.getTotalWord());
		int av=wtop.getTotalWord()/wtop.getGroupCount();
		wgtaveragenumberofwords.setText("平均每组单词数："+av+"");
	}
	class MyAdapter extends BaseAdapter
	{
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
			View item=convertView!=null?convertView:View.inflate(
					getApplicationContext(),
					R.layout.coursegov_groupdetals_item,null);
			TextView gnumber1=(TextView)item.findViewById(R.id.gnumber1);
			TextView words1=(TextView)item.findViewById(R.id.words1);
			TextView studies1=(TextView)item.findViewById(R.id.studies1);
			TextView gnumber2=(TextView)item.findViewById(R.id.gnumber2);
			TextView words2=(TextView)item.findViewById(R.id.words2);
			TextView studies2=(TextView)item.findViewById(R.id.studies2);
			
			final WordGroupDetals_items a=list.get(position);
			gnumber1.setText(a.getGnumber1()+"");
			words1.setText(a.getWords1()+"");
			studies1.setText(a.getStudies1()+"");
			gnumber2.setText(a.getGnumber2()+"");
			words2.setText(a.getWords2()+"");
			studies2.setText(a.getStudies2()+"");
			return item;
		}
	}
}

