package Intnet.importword.kingsoft;

import general.base.op.BaseSplit;
import general.base.op.SettingVariable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.xml.sax.InputSource;

import database.control.op.kingsofttableop;

import activity.control.kingsoft.KingSoftSelect;
import android.R.integer;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DownloadKingsoft {
	MediaPlayer mediaPlayer;
	WordValue w=null;
	int intnetState=1;
	String WordnameString=null;
	String ErrorString="";
	DownloadMp3File dw=null;
	SettingVariable sv=null;
	kingsofttableop dcop=null;
	public DownloadKingsoft(){
	}
	public int getdownloadword(Context context,String wordNameselect)
	{
		Log.i("sssssssssssssss","dddddddddddd");
		int flagint=0;
		dcop=new kingsofttableop(context);
		Log.i("sssssssssssssss","dddddddddddd");
		dw=new DownloadMp3File();
		Log.i("sssssssssssssss","dddddddddddd");
		sv=new SettingVariable();
		Log.i("aaaaaaaaa1","kkkkkk");
		dcop.setWordName(wordNameselect+"");
		dcop.setWordID(-1);
		dcop.querrybyname();
		Log.i("aaaaaaaaa2",dcop.getWordID()+"kkkkkk");
		if(dcop.getWordID()!=-1)
		{Log.i("aaaab","1");
		if(!dcop.getPhonogramE().equals("")&&(dcop.getPronunciationPathE()==null||
				dcop.getPronunciationPathE().equals(""))||!dcop.getPhonogramA().equals("")&&(dcop.getPronunciationPathA()==null||
				dcop.getPronunciationPathA().equals("")))
			if(!dw.downloadPronunciation(context, dcop, wordNameselect))
			{
				ErrorString=dw.getErrorString();
			}
		dcop.querrybyname();
		flagint=1;
		}
		else {
			IntnetCheck iCheck=new IntnetCheck(context);
			boolean flag=iCheck.getkingsoftinternetright(context);
			Log.i("ccd","bbb4");
			if(flag)
			{
				ErrorString="没有查到该单词！正在从网络下载！";
				setWordnameString(wordNameselect);
				getintnetword();
				w=GetWord();
				boolean checkintnetwordState=checkintnetword();
				if(checkintnetwordState)
				{
				long WordID=-1;
				dcop.setWordID(WordID);
				dcop.setWordName(w.word);
				Log.i("wwwwwwwwwwwwww",dcop.getWordName());
				dcop.setPhonogramE(w.psE);
				dcop.setPhonogramA(w.psA);
				dcop.setWordMeaning(w.interpret);
				dcop.setPronunciationE(w.pronE);
				dcop.setPronunciationA(w.pronA);
				dcop.setExampleSentence(w.sentOrig);
				dcop.setSentenceMeaning(w.sentTrans);
				dcop.setPronunciationPathE("");
				dcop.setPronunciationPathA("");
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date curDate=new Date(System.currentTimeMillis());
				String DownLoadTime1=formatter.format(curDate);
				dcop.setDownLoadTime(DownLoadTime1);
				Log.i("ccf","bbb4");
				dcop.Add();
				Log.i("ccg","bbb4");
				dcop.querrybyname();
				Log.i("wwwwwwwwwwwwww",dcop.getWordName());
				if(dcop.getWordID()!=-1)
				{
					flagint=3;
					flag=iCheck.getkingsoftinternetsave(context, wordNameselect);
					if(flag==true&&checkintnetwordState)
					{
						if(!dw.downloadPronunciation(context, dcop, wordNameselect))
						{
							ErrorString=dw.getErrorString();
						}
						dcop.querrybyname();
					}
					else {
						ErrorString=iCheck.getErrorString();
					}
				}
				}
				else {
					Toast.makeText(context,"没有查到该单词！",0).show();
					flagint=2;
				}
			}
			else {
				ErrorString=iCheck.getErrorString();
				Log.i("aaaas","dddddd");
				flagint=0;
			}
			Log.i("wwwwwwwwwwwwww",ErrorString);
		}
		return flagint;
	}
	public WordValue GetWord(){
		return w;
	}
	public void setWordnameString(String wordstring){
		WordnameString=wordstring;
	}
	public boolean checkintnetword(){
		return (w.psA.equals("")==false)||(w.psE.equals("")==false)||(w.pronA.equals("")==false)
				||(w.pronE.equals("")==false)||(w.sentOrig.equals("")==false)||(w.sentTrans.equals("")==false)
				||(w.interpret.equals("")==false);
	}
	public void getintnetword(){
		Thread t=new Thread(new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("asd1","aaa");
				w=getWordFromInternet(WordnameString);
				if(w==null){
					intnetState=0;
					Log.i("读取网络数据失败","aaa");
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
	public WordValue getWordFromInternet(String searchedWord){
		WordValue wordValue=null;
		String tempWord=searchedWord;
		Log.i("asd21","aaa");
		if(!(tempWord==null)&&tempWord.equals(""))
			return null;
		char[] array=tempWord.toCharArray();
		if(array[0]>256)
			tempWord="_"+URLEncoder.encode(tempWord);
		InputStream in=null;
		String str=null;
		try{
			NetOperator.SetKINGSOFT("C66152887D76046E43B6D0A905F7C0EB");
			String tempUrl=NetOperator.iCiBaURL1+tempWord+NetOperator.iCiBaURL2+NetOperator.KINGSOFT;
			in=NetOperator.getInputStreamByUrl(tempUrl);
			Log.i("cccc",in.toString());
			if(in!=null){
				XMLParser xmlParser=new XMLParser();
				InputStreamReader reader=new InputStreamReader(in,"utf-8");
				JinShanContentHandler contentHandler=new JinShanContentHandler();
				contentHandler.setFlag(1);
				xmlParser.parseJinShanXml(contentHandler,new InputSource(reader));
				wordValue=contentHandler.getWordValue();
				wordValue.setWord(searchedWord);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return wordValue;
	}
	public String getErrorString() {
		return ErrorString;
	}
	public void setErrorString(String errorString) {
		ErrorString = errorString;
	}
	public kingsofttableop getDcop() {
		return dcop;
	}
}
