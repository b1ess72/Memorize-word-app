package general.base.op;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.bool;
import android.R.integer;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class EditTextCheck {
	String errorString="";
	int pwdcheck=-1;
	int ID_LENGTH=18;
	public EditTextCheck(){}
	public boolean notEmpty(String str)
	{
		if(str==null) return true;
		return str.trim().equals("");
	}
	public void setEditTextInhibitInputSpace(EditText editText){
		InputFilter filter=new InputFilter() {	
			@Override
			public CharSequence filter(CharSequence source, int start, int end, 
					Spanned dest, int dstart, int dend) {
				// TODO Auto-generated method stub
				if (source.equals("")) {
					return "";
				}
				else{return null;}
			}
		};
		editText.setFilters(new InputFilter[]{filter});
	}
	public int checkString(EditText ettemp,Boolean flag){
		Boolean checktemp=notEmpty(ettemp.getText().toString());
		if(pwdcheck==-1){
			if(!checktemp){
				errorString="";
				return 2;}
			else {
				errorString=errorString+"不能为空";
				return 0;}
			}
			else {
				if(!checktemp){
					int checktempint=0;
					if(ettemp.getText().toString().length()<6)
					{
						errorString=errorString+"太短了，不能小于6位";
						checktempint=0;
					}
					else if(ettemp.getText().toString().length()>16)
					{
						errorString=errorString+"过长了，不能大于16位";
						checktempint=0;
					}
					else checktempint=1;
					if(checktempint>=1){
						if(pwdcheck>=1){
							if(flag)
							{
								pwdcheck=1;errorString="两次密码输入不一致";return 1;
							}
							else{pwdcheck=2;errorString="";return 2;
							}
						}
						else {
							errorString="";return 1;}
					}
					else if(checktempint==0)
					{
						return 0;
					}}
				else{
					errorString=errorString+"不能为空";
					return 0;
				}
			}
		errorString="";
		return 0;
	}
		public  boolean isE_mail(String string) {
			String regEx1="^\\w+((-\\w+)|((\\.\\w)+))*\\@[A-Za-z0-9]+" +
		"((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
			Pattern p; Matcher m;
			p=Pattern.compile(regEx1);
			m=p.matcher(string);
			return m.matches();
		}
		public boolean isPhone(String str)
		{ return isChinasfPhone(str)||isChinaPhone(str);}
		boolean isChinasfPhone(String str)
		{String regExp="^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-9])|(147))\\d{8}$";
		Pattern p=Pattern.compile(regExp);
		Matcher m=p.matcher(str);
		return m.matches();
		}
		boolean isChinaPhone(String str){
			String regExp="^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})+(-[0-9]{1,4})?$";
			Pattern p=Pattern.compile(regExp);
			Matcher m=p.matcher(str);
			return m.matches();
		}
		boolean vIDNumByRegex(String idNum){
			String curYear=""+Calendar.getInstance().get(Calendar.YEAR);
			int y3=Integer.valueOf(curYear.substring(2,3));
			int y4=Integer.valueOf(curYear.substring(3,4));
			return idNum.matches("^(1[1-5]|2[1-3]|3[1-7]|4[1-6]"+
			"|5[0-4]|6[1-5]|71|8[1-2])\\d{4}(19\\d{2}|20([0-"+(y3-1) + "][0-9]|" + y3 + "[0-" + y4
			+ "]))(((0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])))\\d{3}([0-9]|x|X)$");
		}
		boolean vIDNumByCode(String idNum){
			int[] ratioArr={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
			char[] checkCodeList={'1','0','X','9','8','7','6','5','4','3','2'};
			char[] cIds=idNum.toCharArray();
			if(cIds.length!=ID_LENGTH) return false;
			char oCode=cIds[ID_LENGTH-1];
			int[] iIds=new int[ID_LENGTH-1];
			int idSum=0;
			int residue=0;
			for(int i=0;i < ID_LENGTH-1;i++){
				iIds[i]=cIds[i]-'0';
				idSum +=iIds[i] * ratioArr[i];
			}
			residue=idSum%11;
			return Character.toUpperCase(oCode)==checkCodeList[residue];
		}
		public boolean vId(String idNum){
			return vIDNumByCode(idNum)&&vIDNumByRegex(idNum);
		}
		public boolean isNunmber(String str){
			String regExp="^[0-9]*[1-9][0-9]*$";
			Pattern p=Pattern.compile(regExp);
			Matcher m=p.matcher(str);
			return m.matches();
		}
		public boolean ischinaname(String str){
			String regExp="([\u4e00-\u9fa5]+)";
			Pattern p=Pattern.compile(regExp);
			Matcher m=p.matcher(str);
			return m.matches();
		}
		public boolean isColor(String str){
			String regExp="^([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
			Pattern p=Pattern.compile(regExp);
			Matcher m=p.matcher(str);
			return m.matches();
		}
		public String getErrorString() {
			return errorString;
		}
		public int getPwdcheck() {
			return pwdcheck;
		}
		public void setErrorString(String errorString) {
			this.errorString = errorString;
		}
		public void setPwdcheck(int pwdcheck) {
			this.pwdcheck = pwdcheck;
		}
}

	/*public void setEditTextInhibitInputSpeChat(EditText editText){
		InputFilter filter=new InputFilter(){
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				// TODO Auto-generated method stub
				String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~"+
				"！@#￥%……&*（）————+|{}【】‘;:” “’。，、？]";
				Pattern pattern=Pattern.compile(speChat);
				Matcher matcher=pattern.matcher(source.toString());
				if(matcher.find())return "";
				else return null;
			}
		};
		editText.setFilters(new InputFilter[]{filter});
	}	*/
		
	/*boolean isHKPhone(String str){
		String regExp="^(5|6|7|8|9)\\d{7}$";
		Pattern p=Pattern.compile(regExp);
		Matcher m=p.matcher(str);
		return m.matches();
	}*/	

