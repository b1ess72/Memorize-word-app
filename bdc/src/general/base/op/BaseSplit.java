package general.base.op;

import java.util.Random;

import android.util.Log;

public class BaseSplit {
	String [] str_temp=null;
	public BaseSplit(){	}
	
	public void Split_temp(String str,char sp)
	{ str_temp=str.split(sp+"");
	}
	public String getLast(String strTemp)
	{ if(strTemp.equals(""))return "";
		Split_temp(strTemp, '#');
	return str_temp[str_temp.length-1];
	}
	public int getlenth(String strTemp)
	{ if(strTemp.equals(""))
		return 0;
	else{
		Split_temp(strTemp, '#');
		return str_temp.length;
	}}
	public String[] getStr_temp(){
		return str_temp;
	}
	public void setStr_temp(String[] str_temp){
		this.str_temp=str_temp;
	}
	public int bsfind(String str1,String str2)
	{ int temp=-1;
	Split_temp(str1, '#');
	String[] a=getStr_temp();
	int i=0;
	for(i=0;i<a.length;i++)
	{ if(a[i].equals(str2))
	{temp=Integer.parseInt(a[i]);
	break;
	}
	}
	if(i==a.length)return -1;
	else 
		return i+1;
	}
	public String trimletf(String str1,int n)
	{String temp="";
	Split_temp(str1, '#');
	String[] a=getStr_temp();
	int i=0;
	for(i=0;i<n;i++)
	{if(temp.equals(""))
		temp=a[i];
	else{temp+="#"+a[i];
	}}
	return temp;
	}
	public String Split(String str, char sp, int n) {
		// TODO Auto-generated method stub
		int l;
		Split_temp(str, sp);
		l=str_temp.length;
		if(n==0)
			return str_temp[l-1];
		else {
			return str_temp[n-1];
		}
	}
	public String replaceCode(String strtotal, char sp1, char sp2) {
		// TODO Auto-generated method stub
		String tempstrString="";
		Split_temp(strtotal, sp1);
		int lentemp=str_temp.length;
		int k=0;
		while(k<lentemp)
		{
			if(tempstrString.equals(""))tempstrString=str_temp[k];
			else tempstrString+=sp2+str_temp[k];
			k++;
		}
		return tempstrString;
	}
public String ReverseSequence(String str)
{
	String strtemp="";
	Split_temp(str, '#');
	for(int i=str_temp.length-1;i>=0;i--)
		if(strtemp.equals(""))strtemp=str_temp[i];
		else strtemp+="#"+str_temp[i];
	return strtemp;
}
public String disorderSequence(String str)
{
	String strtemp="";
	Split_temp(str, '#');
	int lentemp=str_temp.length;
	int[] a=new int[lentemp];
	int k=0;
	while(k<lentemp)
	{
		Random random=new Random();
		int s=random.nextInt(2*lentemp)%(lentemp);
		int j=0;
		for(;j<k;j++)
			if(s==a[j])break;
		if(j==k){a[k++]=s;System.out.println(s+" "+k);}
	}
	for(int i=0;i<a.length;i++)
		if(strtemp.equals(""))strtemp=str_temp[a[i]];
		else strtemp+="#"+str_temp[a[i]];
	return strtemp;
}
public int getLocation(String str1,String str2)
{int temp=-1;
Split_temp(str1,'#');
String[] a=getStr_temp();
Split_temp(str2,'#');
String[] b=getStr_temp();
int i=0;
for(i=0;i<b.length;i++)
{if(a[i].equals(b[i]))
{Log.i("aaaaaaaaaaa88",a[i]+" "+b[i]);
temp=Integer.parseInt(a[i]);
continue;
}
else 
	break;
}
Log.i("aaaaaaaaaaa88",a[i]+" kk "+i+" "+a[0]+" "+b.length);
return temp;
}
}
