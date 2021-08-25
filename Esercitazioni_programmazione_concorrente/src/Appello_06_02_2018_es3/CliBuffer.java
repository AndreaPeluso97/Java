package Appello_06_02_2018_es3;


public class CliBuffer {
	private String theNews;
	private boolean isFresh;
	CliBuffer() {
		this.isFresh=false;
		this.theNews="abc00";
	}
	public synchronized String getNews() throws InterruptedException{
		isFresh=false;
		return theNews;
	}
	public synchronized void addNews(String v) throws InterruptedException{
		theNews=v;
		isFresh=true;
	}
 	public synchronized boolean newsIsFresh(){
		boolean res=isFresh;
		isFresh=false;
		return res;
	}
}
