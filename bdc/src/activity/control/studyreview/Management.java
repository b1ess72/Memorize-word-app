package activity.control.studyreview;

import wgy.recitewords.bdc.BDC_Main;
import wgy.recitewords.bdc.R;
import database.control.op.GroupingdetalsTableop;
import database.control.op.StudyReviewTableop;
import database.control.op.userInfoop;
import general.base.op.SettingVariable;
import activity.control.course.GroupWord;
import activity.control.course.SelectCourse;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Management extends Activity {
	Button mmLearningcontent=null;
	Button mmGrouplearn=null;
	Button mmReviewsales=null;
	Button mmSelectthereview=null;
	Button mmcancel=null;
	Button mmnewword=null;
	SettingVariable sv=null;
	StudyReviewTableop srtop=null;
	userInfoop uop=null;
	GroupingdetalsTableop gdop=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.studygov_management);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		srtop=new StudyReviewTableop(this);
		uop=new userInfoop(this);
		gdop=new GroupingdetalsTableop(this);
		setWelcomesText();
		mmLearningcontent=(Button)findViewById(R.id.mmLearningcontent);
		mmLearningcontent.setOnClickListener(new ButtonListener());
		mmGrouplearn=(Button)findViewById(R.id.mmGrouplearn);
		mmGrouplearn.setOnClickListener(new ButtonListener());
		mmReviewsales=(Button)findViewById(R.id.mmReviewsales);
		mmReviewsales.setOnClickListener(new ButtonListener());
		mmSelectthereview=(Button)findViewById(R.id.mmSelectthereview);
		mmSelectthereview.setOnClickListener(new ButtonListener());
		mmnewword=(Button)findViewById(R.id.mmnewword);
		mmnewword.setOnClickListener(new ButtonListener());
		mmcancel=(Button)findViewById(R.id.mmcancel);
		mmcancel.setOnClickListener(new ButtonListener());
	}
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			if(sv.getTableID()==-1)
			{
				intent.putExtra("op",7);
				intent.setClass(Management.this,SelectCourse.class);
			}else{
				uop.setUsername(sv.getUName());
				uop.getallbyusername();
				gdop.setUID((int)uop.getId1());
				gdop.setWORDTableID(sv.getTableID());
				gdop.setGROUPID(1);
				if(gdop.checkGroupInfo()<=0)
				{
					intent.putExtra("op",2);
					intent.setClass(Management.this,GroupWord.class);
				}
				else {
					switch(v.getId()){
					case R.id.mmLearningcontent:
						srtop.setWORDTableID(sv.getTableID());
						uop.setUsername(sv.getUName());
						uop.getallbyusername();
						long groupID=srtop.getTodayContentID(uop.getId1());
						intent.putExtra("op",1);
						intent.putExtra("content",1);
						intent.putExtra("SelectedGroupID",groupID+"");
						Log.i("aaaacccc",groupID+"");
						intent.putExtra("SelectedArrayID","");
						intent.putExtra("returnDialog","Management");
						intent.setClass(Management.this,StudyReviewList.class);
						break;
					case R.id.mmGrouplearn:
						intent.putExtra("op",1);
						intent.setClass(Management.this,SelectGroupNumber.class);
						break;
					case R.id.mmReviewsales:
						intent.setClass(Management.this,RecommendReview.class);
						break;
					case R.id.mmSelectthereview:
						intent.putExtra("op",2);
						intent.setClass(Management.this,SelectGroupNumber.class);
						break;
					case R.id.mmnewword:
					case R.id.mmcancel:
						intent.setClass(Management.this,BDC_Main.class);
						break;
					}
				}
			}
			startActivity(intent);
		}
	}
	void setWelcomesText(){
		sv=new SettingVariable();
		TextView WelcomesWords=(TextView)findViewById(R.id.mmWordPhonogram);
		WelcomesWords.setText(sv.getUName()+"ÓÃ»§£¬»¶Ó­Äú£¡");
	}
}
