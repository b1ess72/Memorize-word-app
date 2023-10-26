package Intnet.importword.kingsoft;

import general.base.op.BaseSplit;
import general.base.op.SettingVariable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import database.control.op.kingsofttableop;

import activity.control.kingsoft.KingSoftSelect;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


public class DownloadMp3File {
	int flag=0;
	String fileName="";
	String urlStr="";
	String path="";
	String pathName="";
	String storageState="";
	String SDCard="";
	BaseSplit bs=null;
	SettingVariable settingt=null;
	String errorString="";
	public DownloadMp3File(){}
	public void deleteAll(String[] path)
	{
		for(int i=0;i<path.length;i++)
		{
			String pathtString=path[i];
			String sdpath=getExternalStorage();
			if(sdpath.equals("")==false)
			{
				deleteFile(new File(sdpath+"/"+pathtString));
			}
			sdpath=getEXTERNAL_STORAGE();
			deleteFile(new File(sdpath+"/"+pathtString));
		}
	}
	String getExternalStorage(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return Environment.getExternalStorageDirectory()+"";}
		else {
			return "";
		}
	}
	String getEXTERNAL_STORAGE(){
		return System.getenv("EXTERNAL_STORAGE")+"";
	}
	void checkStorage()
	{
		SDCard="";
		if(storageState.equals("1")==true)
		{
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				SDCard=Environment.getExternalStorageDirectory()+"";
				Log.i("aa1",SDCard);
			}
			else {
				SDCard=System.getenv("EXTERNAL_STORAGE")+"";
				storageState="2";
				Log.i("aa2",SDCard);
			}}
		else {
			SDCard=System.getenv("EXTERNAL_STORAGE")+"";
		}
	}
	public void downloadmp3()
	{
		final Handler handler=new Handler();
		Thread t=new Thread(new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BaseSplit bSplit=new BaseSplit();
				fileName=bSplit.Split(urlStr,'/',0);
				OutputStream output=null;
				try{
					URL url=new URL(urlStr);
		Log.i("aa","bb");
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		checkStorage();
		pathName=SDCard+"/"+path+fileName;
		Log.i("aa",fileName);
		Log.i("aafffff",pathName);
		File file=new File(pathName);
		Log.i("aa","bbb2");
		InputStream input=conn.getInputStream();
		Log.i("aa","bbb22");
		if(file.exists()){
			Log.i("aa","bbb3");
			System.out.println("exits");
			flag=1;
			storageState="3";
			return;
		}else{
			flag=2;
			storageState="4";
			Log.i("aa","bbb33");
			String dir=SDCard+"/"+path;
			Log.i("aa",dir);
			new File(dir).mkdir();
			Log.i("aa",file.toString());
			file.createNewFile();
			Log.i("aa","bbb1");
			output=new FileOutputStream(file);
			byte[] buffer=new byte[4*1024];
			int len=-1;
			while((len=input.read(buffer))!=-1){
				output.write(buffer,0,len);
			}
		}
				}catch(MalformedURLException e){
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					try{
						Log.i("aa","bbb4");
						if(flag==2)output.close();
						Log.i("aa","bbb5");
						System.out.println("success");
					}catch(IOException e){
						System.out.println("fail");
						e.printStackTrace();
					}
				}
			}
			});
		t.start();
		try{
			t.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public void deleteFile(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}else if(file.isDirectory()){
				File files[]=file.listFiles();
				for(int i=0;i<files.length;i++){
					this.deleteFile(files[i]);
				}
			}
			file.delete();
		}else{
			Log.i("aaac","文件不存在！"+"\n");
		}
	}
	public boolean downloadPronunciation(Context context,kingsofttableop dcop,String wordName){
		{
			boolean flag=true;
			Log.i("aaaab","1111111111111111111");
			dcop.setWordName(wordName);
			dcop.setPronunciationPathE("");
			dcop.setPronunciationPathA("");
			bs=new BaseSplit();
			settingt=new SettingVariable();
			urlStr=dcop.getPronunciationE();
			String mp31="";
			if(urlStr!=null&&!urlStr.equals(""))
			{
				mp31=downloadop("",context);
				dcop.setPronunciationPathE(mp31);
				Log.i("hggggg",mp31+"   "+dcop.getPronunciationPathE());
				if(mp31.equals("")==true)
				{errorString="读音文件保存失败";
				flag=false;
				}
			}
			Log.i("cccz","ddd");
			urlStr=dcop.getPronunciationA();
			if(urlStr!=null&&!urlStr.equals(""))
			{
				String mp32=downloadop("2",context);
				dcop.setPronunciationPathA(mp32);
				Log.i("hggggg",mp32);
				if(mp32.equals("")==true){errorString="读音文件保存失败";
				flag=false;
				}
			}
			Log.i("hggggg",mp31+"       "+dcop.getPronunciationPathE()+""+dcop.getPronunciationPathA());
			dcop.editPronunciationPath();
			return flag;}
		}
		String downloadop(String path1,Context context)
		{
			path=bs.Split(settingt.getFilePath(),'#',1);
			if(urlStr!=null&&urlStr.equals("")==false)
			{
				setUrlStr(urlStr);
				setPath(path);
				setStorageState(storageState);
				downloadmp3();
				String PathName=getPathName();
				storageState=getStorageState();
				if(storageState.equals("2")==true)
				{
					Toast.makeText(context, "外置存储器存在问题，已经成功保存在内部存储器上",0).show();
					return "";
				}
				else if(storageState.equals("3")==true){
					if(path1.equals("")&&path1.equals(PathName)==false)
						Toast.makeText(context, "文件已经存在，未能再次保存！",0).show();
					return path1;
				}
				else{
					Log.i("aaaab","1111111111111111111"+getPathName());
					return getPathName();
				}
			}
			else {
				return "";
			}
		}
		public String getErrorString() {
			return errorString;
		}
		public String getStorageState() {
			return storageState;
		}
		public void setStorageState(String storageState) {
			this.storageState = storageState;
		}
		public String getPathName() {
			return pathName;
		}
		public void setUrlStr(String urlStr) {
			this.urlStr = urlStr;
		}
		public void setPath(String path) {
			if(path.equals("")==false)path=path+"/";
			this.path = path;
		}
	}