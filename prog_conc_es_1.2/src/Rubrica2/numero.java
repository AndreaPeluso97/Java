package Rubrica2;

import java.io.Serializable;

public class numero implements Serializable{
	
	
	
	private static final long serialVersionUID = 1;

	String chi;
	String num;
	
	public numero(String chi, String n) {
		this.chi = chi;
		this.num = n;
	}

	public String getChi() {
		return chi;
	}

	public String getNum() {
		return num;
	}

}
