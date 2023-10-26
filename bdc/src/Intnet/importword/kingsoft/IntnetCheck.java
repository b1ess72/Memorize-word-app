package Intnet.importword.kingsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import database.control.op.kingsofttableop;
import general.base.op.SettingVariable;
import activity.control.kingsoft.KingSoftSelect;
//import activity.control.studyreview.WordBrowse;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class IntnetCheck {
	SettingVariable settingt=null;
	String errorString="";
	kingsofttableop dcop=null;
	private static long mCurrentClickTime_old=0;
	private static long mCurrentClickTime_new=0;
	static String strNetworkType="";
	static Context context=null;
	Thread t=new Thread(new Runnable() {		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			getNetworkType(context);
			Log.i(mCurrentClickTime_old+" "+mCurrentClickTime_new,"vvvvvvvvvvvvvdddddd");
			mCurrentClickTime_old=mCurrentClickTime_new;
		}
	});
	private String _strSubTypeName;
	public String checkplaypession(Context context)
	{
		this.context=context;
		SettingVariable sv=new SettingVariable();
		Log.i("oooooooooooooooo","p1");
		String nettypeString=strNetworkType;
		if((nettypeString.equals("3G")||nettypeString.equals("4G")||nettypeString.equals("2G")))
		if(sv.getPlayWord().equals("1"))
			return "1";else return nettypeString+"网络下不允许播放声音，如果需要播放声音，请重新设置权限！";
		if(nettypeString.equals("WIFI"))
			if(sv.getPlayFileWiFi().equals("1"))
				return "1";
			else 
				return nettypeString+"网络下不允许播放声音，如果需要播放声音，请重新设置权限！";
		return "";
	}
	public String getNetworkType(Context context){
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null&&networkInfo.isConnected())
			if(true)
			{
				if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
					strNetworkType="WIFI";
				}else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE)
				{
					String strSubTypeName=networkInfo.getSubtypeName();
					int networkType=networkInfo.getSubtype();
					switch(networkType){
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						strNetworkType="2G";
						Log.e("logger","2g");
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						strNetworkType="3G";
						break;
					case TelephonyManager.NETWORK_TYPE_LTE:
						strNetworkType="4G";
						break;
						default:
							if(_strSubTypeName.equalsIgnoreCase("TD-SCDMA")||
									_strSubTypeName.equalsIgnoreCase("WCDMA")||
									_strSubTypeName.equalsIgnoreCase("CDMA2000")){
								strNetworkType="3G";
							}else{
								strNetworkType=_strSubTypeName;
							}
							break;
					}
				}
			}
		return strNetworkType;
	}
	boolean ping(){
		String result=null;
		try{
			String ip="www.baidu.com";
			Log.d("------ping------","result content 123:1");
			Process p=Runtime.getRuntime().exec("ping -c 1 -w 10 " + ip);
			Log.d("------ping------","result content 123:2");
			int status=p.waitFor();
			if(status==0){
				Log.d("------ping------","result content 123:3");
				result="success";
				return true;
			}else{
				Log.d("------ping------","result content 123:3");
				result="failed";
			}
		}catch(IOException e){
			result="IOException";
		}catch(InterruptedException e){
			result="InterruptedException";
		}finally{
			Log.d("----result----1","result= " +result);
		}
		return false;
	}
	public boolean getkingsoftinternetright(Context context){
		boolean flag=true;
		String intnetState=getStrNetworkType().trim();
		Log.i("aaaab",intnetState);
		if(intnetState.equals("")==true)
		{
			errorString="未开启网络，请开启网络后联网查找！";
			flag=false;
		}
		else {
			if(intnetState.equals("WIFI")==true)
			{
				if(settingt.getQueryWordWiFi().equals("0")){
				flag=false;
				errorString=intnetState+"网络已经开启，但本软件的网络设置不允许在线查询单词，请在设置里面设置网络权限！";
			}
			else{
				flag=true;
			}
		}
		else{
			if(settingt.getQueryWordG().equals("0"))
			{
				flag=false;
				errorString=intnetState+"网络已经开启，但本软件的网络设置不允许在线查询单词，请在设置里面设置网络权限！";
			}
			else {
				flag=true;
			}
		}
	}return flag;}
public boolean getkingsoftinternetsave(Context context,String wordNameselect){
	dcop=new kingsofttableop(context);
	boolean flag=true;
	String intnetState=getStrNetworkType().trim();
	if(settingt.getSaveWord().equals("0"))
	{Log.i("cccccc","bbb"+wordNameselect);
	dcop.setWordName(wordNameselect+"");
	dcop.delete();
	errorString="当前设置不允许保存网络单词到本地";
	flag=false;
	}
	else{
		{if(intnetState.equals("WIFI")==true)
		{
			if(settingt.getSaveFileWiFi().equals("0"))
			{flag=false;
			Toast.makeText(context,intnetState+"网络已经开启，但本软件的网络设置"+intnetState+"环境下不允许保存读音文件，请在设置里面设置网络权限！",0).show();
			errorString=intnetState+"网络已经开启，但本软件的网络设置"+intnetState+"环境下不允许保存读音文件，请在设置里面网络权限！";
			}
			else{
				flag=true;
			}
		}
		else {
			if(settingt.getSaveFileG().equals("0"))
			{
				flag=false;
				Toast.makeText(context,intnetState+"网络已经开启，但本软件的网络设置"+intnetState+"环境下不允许保存读音文件，请在设置里面设置网络权限！",0).show();
				errorString=intnetState+"网络已经开启，但本软件的网络设置"+intnetState+"环境下不允许保存读音文件，请在设置里面设置网络权限！";
			}
			else {
				flag=true;
			}
		}
	}
}
return flag;
}
public String getErrorString() {
	return errorString;
}
public void setErrorString(String errorString) {
	this.errorString = errorString;
}
public String getStrNetworkType() {
	mCurrentClickTime_new=Calendar.getInstance().getTimeInMillis();
	Log.i(mCurrentClickTime_old+"     "+mCurrentClickTime_new+"   "+(mCurrentClickTime_new-mCurrentClickTime_old)+"    "+(long)20*1000,"vvvvvvvvvvvvvdddddd");
	if(mCurrentClickTime_old==0||mCurrentClickTime_new-mCurrentClickTime_old>(long)20*1000)
	{
		t.start();
	}
	return strNetworkType;
}
public static Context getContext() {
	return context;
}
public static void setContext(Context context) {
	IntnetCheck.context = context;
}
public IntnetCheck(Context context){
	super();
	this.context=context;
}
}


