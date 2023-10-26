package Intnet.importword.kingsoft;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import android.util.Log;

public class WordValue {
	public String word = null, psE = null, pronE = null, psA = null,
			pronA = null, interpret = null, sentOrig = null, sentTrans = null;

	public WordValue(String word, String psE, String pronE, String psA,
			String pronA, String interpret, String sentOrig, String sentTrans) {
		super();
		this.word = "" + word;
		this.psE = "" + psE;
		this.pronE = "" + pronE;
		this.psA = "" + psA;
		this.pronA = "" + pronA;
		this.interpret = "" + interpret;
		this.sentOrig = "" + sentOrig;
		this.sentTrans = "" + sentTrans;
	}

	public WordValue() {
		super();
		this.word = ""; // ∑¿÷πø’÷∏’Î“Ï≥£
		this.psE = "";
		this.pronE = "";
		this.psA = "";
		this.pronA = "";
		this.interpret = "";
		this.sentOrig = "";
		this.sentTrans = "";

	}

	public ArrayList<String> getOrigList() {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new StringReader(this.sentOrig));
		String str = null;
		try {
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getTransList() {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new StringReader(this.sentTrans));
		String str = null;
		try {
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPsE() {
		return psE;
	}

	public void setPsE(String psE) {
		this.psE = psE;
	}

	public String getPronE() {
		return pronE;
	}

	public void setPronE(String pronE) {
		this.pronE = pronE;
	}

	public String getPsA() {
		return psA;
	}

	public void setPsA(String psA) {
		this.psA = psA;
	}

	public String getPronA() {
		return pronA;
	}

	public void setPronA(String pronA) {
		this.pronA = pronA;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public String getSentOrig() {
		return sentOrig;
	}

	public void setSentOrig(String sentOrig) {
		this.sentOrig = sentOrig;
	}

	public String getSentTrans() {
		return sentTrans;
	}

	public void setSentTrans(String sentTrans) {
		this.sentTrans = sentTrans;
	}

	public void printInfo() {
		Log.i("aaa1", this.word);
		Log.i("aaa2", this.psE);
		Log.i("aaa3", this.pronE);
		Log.i("aaa4", this.psA);
		Log.i("aaa5", this.pronA);
		Log.i("aaa6", this.interpret);
		Log.i("aaa7", this.sentOrig);
		Log.i("aaa8", this.sentTrans);

	}

}