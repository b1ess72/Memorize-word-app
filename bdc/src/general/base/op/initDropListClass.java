package general.base.op;

import java.util.ArrayList;
import java.util.List;

public class initDropListClass {
	List<String> list=new ArrayList<String>();
	public List<String> getList(){
		return list;
	}
	public void setList(List<String> list){
		this.list=list;
	}
	public initDropListClass()
	{
		list.add("");
		list.add("很熟悉");
		list.add("熟悉");
		list.add("比较熟悉");
		list.add("比较陌生");
		list.add("陌生");
		list.add("很陌生");
		list.add("其他");	
	}
	public initDropListClass(String import1)
	{
		list.add("很熟悉");
		list.add("熟悉");
		list.add("比较熟悉");
		list.add("比较陌生");
		list.add("陌生");
		list.add("很陌生");
		list.add("其他");		
	}
	public initDropListClass(int begin,int end)
	{
		for(int i=begin;i<=end;i++)
			list.add(i+"");
	}
	public String getdown(String familiarity)
	{
		String temp="";
		int flag=0;
		for(String str:list){
			if(flag==1){
				temp=str;
				break;
			}
			else {
				if(str.equals(list.get(list.size()-1)))
				{
					temp=str;
					break;
				}
				else
					if(str.equals(familiarity))
					{flag=1;}
			}
		}
		return temp;
	}
	public boolean checkup(String familiarity){
		if(list.get(1).equals(familiarity))
			return true;
		return false;
	}
	public boolean checkdown(String familiarity){
		if(list.get(list.size()-1).equals(familiarity))
			return true;
		return false;
	}
	public String getup(String familiarity){
		String temp="";
		for(String str:list){
			if(temp.equals(""))
			{
				temp=str;
				if(str.equals(familiarity))
					break;
			}
			else {
				temp=str;
			}
		}
	return temp;
}
}
