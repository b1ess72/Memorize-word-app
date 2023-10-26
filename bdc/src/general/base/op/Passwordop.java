package general.base.op;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Passwordop {
	public Passwordop(){}
	public static String md5Password(String password){
		try{
			MessageDigest digest=MessageDigest.getInstance("md5");
			byte[] result=digest.digest(password.getBytes());
			StringBuffer buffer=new StringBuffer();
			for(byte b:result){
				int number=b&0xff;
				String str=Integer.toHexString(number);
				if(str.length()==1){
					buffer.append("0");
				}
				buffer.append(str);
				}
			return buffer.toString();
			}catch(NoSuchAlgorithmException e){
				e.printStackTrace();
				return "";
		}
	}
	public String getBase64(String str){
		byte[] b=null;
		String s=null;
		try{
			b=str.getBytes("utf-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		if(b!=null){
			s=new BASE64Encoder().encode(b);
		}
		return s;
	}
	public String getFromBase64(String s){
		byte[] b=null;
		String result=null;
		if(s!=null){
			BASE64Decoder decoder=new BASE64Decoder();
			try{
				b=decoder.decodeBuffer(s);
				result=new String(b,"utf-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

}
