package general.base.op;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.text.StaticLayout;
import android.util.Log;
import database.control.op.Settingop;

public class SettingVariable {
	static String UName="";
	static String UID="";
	
	static int init=-1;
	Settingop stop=null;
	static int TableID=-1;
	static String TableName="";
	static String TableNameChina="";
	static String PopWin="";
	static String Familiarity="";
	static String queryWordG="1";
	static String saveWord="1";
	static String queryWordWiFi="1";
	static String saveFileG="0";
	static String playWord="0";
	static String saveFileWiFi="0";
	static String playFileWiFi="0";
	static String computerSynzationWiFi="0";
	static String computerSynzationG="0";
	static String outsideinsideStorape;
	static String filePath="pp";
	static String soundRight;
	static String showWord;
	static String learningStudy;
	static String momeryModel;
	static String phoneicInterpretation;
	static String loopPlayback;
	static String autoPlay;
	static String intervaPlay;
	static String wordSize;
	static String phonogramSize;
	static String explainSize;
	static String exampleSize;
	static String wordColor;
	static String phonogramColor;
	static String explainColor;
	static String exampleColor;
	static String overallProgress;
	static String group;
	static String order;
	static String wordShow;
	static String phonogramShow;
	static String explainShow;
	static String exampleShow;
	static String[] errorarraystring=new String[100];
	static int counterror=0;
	DateTimeOp dtop=null;
	
	public SettingVariable(){}
	public SettingVariable(String errorstring,String wordnameString){
		dtop=new DateTimeOp();
		String errorStringall="";
		errorarraystring[counterror]=dtop.getDateTimeNowLong()+";"+wordnameString
				+"\n"+errorstring;
		for(int i=counterror;i>=0;i--)
		{
			if(errorarraystring[i]!=null&&!errorarraystring[i].equals(""))
			{if(errorStringall.equals(""))errorStringall=errorStringall+errorarraystring[i];
			else{
				errorStringall=errorStringall+"\n"+errorarraystring[i];
			}		
			}
		}
			Log.i("ssss1",errorStringall);
			for(int i=99;i>counterror;i--)
			{
				if(errorarraystring[i]!=null&&errorarraystring[i].equals(""))
				{if(errorStringall.equals(""))errorStringall=errorStringall+errorarraystring[i];
				else{
					errorStringall=errorStringall+"\n"+errorarraystring[i];
				}		
				}
			}
			counterror++;
			if(counterror>=100)counterror=0;
			//return errorStringall;
			return;
	}
	
	public void querryallv()
	{
		List<Settingop> list1=null;
		 int count1=0;
		 list1=stop.querrySetting_network_right();
		 for(Settingop tt:list1){
			 count1++;
			 switch(count1){
			 case 1:queryWordG=tt.getSettingContent().toString().trim();break;
			 case 2:saveWord=tt.getSettingContent().toString().trim();break;
			 case 3:queryWordWiFi=tt.getSettingContent().toString().trim();break;
			 case 4:saveFileG=tt.getSettingContent().toString().trim();break;
			 case 5:playWord=tt.getSettingContent().toString().trim();break;
			 case 6:saveFileWiFi=tt.getSettingContent().toString().trim();break;
			 case 7:playFileWiFi=tt.getSettingContent().toString().trim();break;
			 case 8:computerSynzationWiFi=tt.getSettingContent().toString().trim();break;
			 case 9:computerSynzationG=tt.getSettingContent().toString().trim();break;
			 case 10:outsideinsideStorape=tt.getSettingContent().toString().trim();break;
			 case 11:filePath=tt.getSettingContent().toString().trim();break;
			 }
		 }
		 count1=0;
		 list1=stop.querrysetting_word_pronunciation();
		 for(Settingop tt:list1){
			 count1++;
			 switch(count1){
			 case 1:soundRight=tt.getSettingContent().toString().trim();break;
			 case 2:showWord=tt.getSettingContent().toString().trim();break;
			 }
		 }
		 count1=0;
		 list1=stop.querrysetting_learning_mode();
		 for(Settingop tt:list1){
			 count1++;
			 switch(count1){
			 case 1:learningStudy=tt.getSettingContent().toString().trim();break;
			 case 2:momeryModel=tt.getSettingContent().toString().trim();break;
			 case 3:phoneicInterpretation=tt.getSettingContent().toString().trim();break;
			 case 4:loopPlayback=tt.getSettingContent().toString().trim();break;
			 case 5:autoPlay=tt.getSettingContent().toString().trim();break;
			 case 6:intervaPlay=tt.getSettingContent().toString().trim();break;
			 }
		 }
		 count1=0;
		list1=stop.querrysetting_display_interface();
		for(Settingop tt:list1){
			count1++;
			switch(count1){
			case 1:wordSize=tt.getSettingContent().toString().trim();break;
			case 2:phonogramSize=tt.getSettingContent().toString().trim();break;
			case 3:explainSize=tt.getSettingContent().toString().trim();break;
			case 4:exampleSize=tt.getSettingContent().toString().trim();break;
			case 5:wordColor=tt.getSettingContent().toString().trim();break;
			case 6:phonogramColor=tt.getSettingContent().toString().trim();break;
			case 7:explainColor=tt.getSettingContent().toString().trim();break;
			case 8:exampleColor=tt.getSettingContent().toString().trim();break;
			case 9:overallProgress=tt.getSettingContent().toString().trim();break;
			case 10:group=tt.getSettingContent().toString().trim();break;
			case 11:order=tt.getSettingContent().toString().trim();break;
			case 12:wordShow=tt.getSettingContent().toString().trim();break;
			case 13:phonogramShow=tt.getSettingContent().toString().trim();break;
			case 14:explainShow=tt.getSettingContent().toString().trim();break;
			case 15:exampleShow=tt.getSettingContent().toString().trim();break;
			}}
		count1=0;
		list1=stop.querrySettingClass4();
		for(Settingop tt:list1){
			count1++;
			switch(count1){
			case 1:TableName=tt.getSettingContent().toString().trim();break;
			}
		}
	}
	public SettingVariable(Context context){
		super();
		stop=new Settingop(context);
	}
	public static String getUName(){return UName;}
	public static void setUName(String uName){UName=uName;}
	public static int getInit() {
		return init;
	}
	public static void setInit(int init) {
		SettingVariable.init = init;
	}
	public static String getTableName() {
		return TableName;
	}
	public static void setTableName(String tableName) {
		TableName = tableName;
	}
	public static String getTableNameChina() {
		return TableNameChina;
	}
	public static void setTableNameChina(String tableNameChina) {
		TableNameChina = tableNameChina;
	}
	public static int getTableID() {
		return TableID;
	}
	public static void setTableID(int tableID) {
		TableID = tableID;
	}
	public static String getFamiliarity() {
		return Familiarity;
	}
	public static void setFamiliarity(String familiarity) {
		Familiarity = familiarity;
	}
	public static String getQueryWordG() {
		return queryWordG;
	}
	public static void setQueryWordG(String queryWordG) {
		SettingVariable.queryWordG = queryWordG;
	}
	public static String getSaveWord() {
		return saveWord;
	}
	public static void setSaveWord(String saveWord) {
		SettingVariable.saveWord = saveWord;
	}
	public static String getQueryWordWiFi() {
		return queryWordWiFi;
	}
	public static void setQueryWordWiFi(String queryWordWiFi) {
		SettingVariable.queryWordWiFi = queryWordWiFi;
	}
	public static String getSaveFileG() {
		return saveFileG;
	}
	public static void setSaveFileG(String saveFileG) {
		SettingVariable.saveFileG = saveFileG;
	}
	public static String getPlayWord() {
		return playWord;
	}
	public static void setPlayWord(String playWord) {
		SettingVariable.playWord = playWord;
	}
	public static String getSaveFileWiFi() {
		return saveFileWiFi;
	}
	public static void setSaveFileWiFi(String saveFileWiFi) {
		SettingVariable.saveFileWiFi = saveFileWiFi;
	}
	public static String getPlayFileWiFi() {
		return playFileWiFi;
	}
	public static void setPlayFileWiFi(String playFileWiFi) {
		SettingVariable.playFileWiFi = playFileWiFi;
	}
	public static String getComputerSynzationWiFi() {
		return computerSynzationWiFi;
	}
	public static void setComputerSynzationWiFi(String computerSynzationWiFi) {
		SettingVariable.computerSynzationWiFi = computerSynzationWiFi;
	}
	public static String getComputerSynzationG() {
		return computerSynzationG;
	}
	public static void setComputerSynzationG(String computerSynzationG) {
		SettingVariable.computerSynzationG = computerSynzationG;
	}
	public static String getOutsideinsideStorape() {
		return outsideinsideStorape;
	}
	public static void setOutsideinsideStorape(String outsideinsideStorape) {
		SettingVariable.outsideinsideStorape = outsideinsideStorape;
	}
	public static String getFilePath() {
		return filePath;
	}
	public static void setFilePath(String filePath) {
		SettingVariable.filePath = filePath;
	}
	public static String getSoundRight() {
		return soundRight;
	}
	public static void setSoundRight(String soundRight) {
		SettingVariable.soundRight = soundRight;
	}
	public static String getShowWord() {
		return showWord;
	}
	public static void setShowWord(String showWord) {
		SettingVariable.showWord = showWord;
	}
	public static String getLearningStudy() {
		return learningStudy;
	}
	public static void setLearningStudy(String learningStudy) {
		SettingVariable.learningStudy = learningStudy;
	}
	public static String getMomeryModel() {
		return momeryModel;
	}
	public static void setMomeryModel(String momeryModel) {
		SettingVariable.momeryModel = momeryModel;
	}
	public static String getPhoneicInterpretation() {
		return phoneicInterpretation;
	}
	public static void setPhoneicInterpretation(String phoneicInterpretation) {
		SettingVariable.phoneicInterpretation = phoneicInterpretation;
	}
	public static String getLoopPlayback() {
		return loopPlayback;
	}
	public static void setLoopPlayback(String loopPlayback) {
		SettingVariable.loopPlayback = loopPlayback;
	}
	public static String getAutoPlay() {
		return autoPlay;
	}
	public static void setAutoPlay(String autoPlay) {
		SettingVariable.autoPlay = autoPlay;
	}
	public static String getIntervaPlay() {
		return intervaPlay;
	}
	public static void setIntervaPlay(String intervaPlay) {
		SettingVariable.intervaPlay = intervaPlay;
	}
	public static String getWordSize() {
		return wordSize;
	}
	public static void setWordSize(String wordSize) {
		SettingVariable.wordSize = wordSize;
	}
	public static String getPhonogramSize() {
		return phonogramSize;
	}
	public static void setPhonogramSize(String phonogramSize) {
		SettingVariable.phonogramSize = phonogramSize;
	}
	public static String getExplainSize() {
		return explainSize;
	}
	public static void setExplainSize(String explainSize) {
		SettingVariable.explainSize = explainSize;
	}
	public static String getExampleSize() {
		return exampleSize;
	}
	public static void setExampleSize(String exampleSize) {
		SettingVariable.exampleSize = exampleSize;
	}
	public static String getWordColor() {
		return wordColor;
	}
	public static void setWordColor(String wordColor) {
		SettingVariable.wordColor = wordColor;
	}
	public static String getPhonogramColor() {
		return phonogramColor;
	}
	public static void setPhonogramColor(String phonogramColor) {
		SettingVariable.phonogramColor = phonogramColor;
	}
	public static String getExplainColor() {
		return explainColor;
	}
	public static void setExplainColor(String explainColor) {
		SettingVariable.explainColor = explainColor;
	}
	public static String getExampleColor() {
		return exampleColor;
	}
	public static void setExampleColor(String exampleColor) {
		SettingVariable.exampleColor = exampleColor;
	}
	public static String getOverallProgress() {
		return overallProgress;
	}
	public static void setOverallProgress(String overallProgress) {
		SettingVariable.overallProgress = overallProgress;
	}
	public static String getGroup() {
		return group;
	}
	public static void setGroup(String group) {
		SettingVariable.group = group;
	}
	public static String getOrder() {
		return order;
	}
	public static void setOrder(String order) {
		SettingVariable.order = order;
	}
	public static String getWordShow() {
		return wordShow;
	}
	public static void setWordShow(String wordShow) {
		SettingVariable.wordShow = wordShow;
	}
	public static String getPhonogramShow() {
		return phonogramShow;
	}
	public static void setPhonogramShow(String phonogramShow) {
		SettingVariable.phonogramShow = phonogramShow;
	}
	public static String getExplainShow() {
		return explainShow;
	}
	public static void setExplainShow(String explainShow) {
		SettingVariable.explainShow = explainShow;
	}
	public static String getExampleShow() {
		return exampleShow;
	}
	public static void setExampleShow(String exampleShow) {
		SettingVariable.exampleShow = exampleShow;
	}
	public static String[] getErrorarraystring() {
		return errorarraystring;
	}
	public static void setErrorarraystring(String[] errorarraystring) {
		SettingVariable.errorarraystring = errorarraystring;
	}
	public static int getCounterror() {
		return counterror;
	}
	public static void setCounterror(int counterror) {
		SettingVariable.counterror = counterror;
	}
	public static String getPopWin() {
		return PopWin;
	}
	public static void setPopWin(String popWin) {
		PopWin = popWin;
	}
	public static String getUID() {
		return UID;
	}
	public static void setUID(String uID) {
		UID = uID;
	}
	public CharSequence errorStringall(String errorString, String wordnameString) {
		// TODO Auto-generated method stub
		return null;
	}
}
