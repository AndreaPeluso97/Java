package Appello_19_12_2018_es2;

import java.io.Serializable;

public class Operation implements Serializable {
	private static final long serialVersionUID = 1;
	String type;
	double amount;
	public Operation(String t, double a){
		type=t; amount=a;
	}
	public String getType() {
		return this.type;
	}
	public double getAmount() {
		return this.amount;
	}
		
}
