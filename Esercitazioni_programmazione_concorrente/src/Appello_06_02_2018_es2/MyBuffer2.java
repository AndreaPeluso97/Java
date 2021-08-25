package Appello_06_02_2018_es2;


public class MyBuffer2 {
	private String theNews;
	private boolean isFresh;
	MyBuffer2() {
		this.isFresh=false;
		this.theNews="fuffa iniziale";
	}
	public synchronized String getNews() throws InterruptedException{
		isFresh=false;
		return theNews;
	}
	public synchronized void setNews(String s) throws InterruptedException{
		isFresh=true;
		theNews=s;	
	}
	public synchronized void setFresh() throws InterruptedException{
		System.out.println("fresh news!");
		isFresh=true;
	}
	public synchronized boolean newsIsFresh(){
		return isFresh;
	}
}
