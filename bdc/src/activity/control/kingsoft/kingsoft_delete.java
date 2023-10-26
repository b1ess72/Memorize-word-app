package activity.control.kingsoft;

import wgy.recitewords.bdc.R;
import database.control.op.kingsofttableop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class kingsoft_delete extends Activity {
	EditText editdelwordnameselect=null;
	Button kingsoftDeleteall=null;
	Button returntousermain=null;
	Button kingsoftconfirmDelete=null;
	Button kingsoftconfirmcancel=null;
	LinearLayout RelativeLayout1=null;
	LinearLayout RelativeLayout2=null;
	TextView textView2=null;
	kingsofttableop dcop=null;
	int deletemethod=2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.kingsoft_delete);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		dcop=new kingsofttableop(this);
		editdelwordnameselect=(EditText)findViewById(R.id.editdelwordnameselect);
		Button kingsoftDeleteall=(Button)findViewById(R.id.kingsoftDeleteall);
		Button returntousermain=(Button)findViewById(R.id.returntousermain);
		Button kingsoftDelete=(Button)findViewById(R.id.kingsoftDelete);
		Button kingsoftconfirmDelete=(Button)findViewById(R.id.kingsoftconfirmDelete);
		Button kingsoftconfirmcancel=(Button)findViewById(R.id.kingsoftconfirmcancel);
		RelativeLayout1=(LinearLayout)findViewById(R.id.RelaticeLayout1);
		RelativeLayout2=(LinearLayout)findViewById(R.id.RelativeLayout2);
		textView2=(TextView)findViewById(R.id.textView2);
		kingsoftDeleteall.setOnClickListener(new ButtonListener());
		returntousermain.setOnClickListener(new ButtonListener());
		kingsoftDelete.setOnClickListener(new ButtonListener());
		kingsoftconfirmDelete.setOnClickListener(new ButtonListener());
		kingsoftconfirmcancel.setOnClickListener(new ButtonListener());
	}
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int dbcount=0;
			switch(v.getId()){
			case R.id.kingsoftDeleteall:
				Log.i("aa",editdelwordnameselect.getText()+"");
				dcop.setWordName("");
				dbcount=(int)dcop.querrybyname();
				if(dbcount>0)
				{deletemethod=1;
				textView2.setVisibility(View.VISIBLE);
				textView2.setText("确认删除"+dbcount+"个单词吗？");
				RelativeLayout1.setVisibility(View.GONE);
				RelativeLayout2.setVisibility(View.VISIBLE);
				}
				else {
					Toast.makeText(kingsoft_delete.this,"没有查到单词！",0).show();
				}
				break;
			case R.id.returntousermain:
				Intent intent=new Intent();
				intent.setClass(kingsoft_delete.this,KingSoft_gov.class);
				startActivity(intent);
				break;
			case R.id.kingsoftDelete:
				Log.i("aa",editdelwordnameselect.getText()+"");
				dcop.setWordName(editdelwordnameselect.getText().toString()+"");
				dbcount=dcop.querrybyname();
				if(dbcount>0)
				{
					deletemethod=2;
					textView2.setVisibility(View.VISIBLE);
					textView2.setText("确认删除"+editdelwordnameselect.getText().toString()+""+"单词吗？");
					RelativeLayout1.setVisibility(View.GONE);
					RelativeLayout2.setVisibility(View.VISIBLE);
				}
				else {
					Toast.makeText(kingsoft_delete.this,"没有查到该单词，请重新检索！",0).show();
				}
				break;
			case R.id.kingsoftconfirmDelete:
				if(deletemethod==1)
				{
				}
				else {
					dcop.setWordName(editdelwordnameselect.getText()+"");
					dcop.delete();
				}
				Toast.makeText(kingsoft_delete.this,"删除操作成功",0).show();
				editdelwordnameselect.setText("");
				textView2.setVisibility(View.VISIBLE);
				RelativeLayout1.setVisibility(View.GONE);
				break;
			case R.id.kingsoftconfirmcancel:
				RelativeLayout1.setVisibility(View.VISIBLE);
				RelativeLayout2.setVisibility(View.GONE);
				editdelwordnameselect.setText("");
				textView2.setVisibility(View.GONE);
				break;
			}
		}
	}
}

