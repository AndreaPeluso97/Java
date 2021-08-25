package Appello_06_02_2018_es1;


public class MyBuffer {
	private String theNews;
	private int[] thr_read;
	private void set(){
		thr_read[0]=thr_read[1]=thr_read[2]=1;
	}
	private void reset(){
		thr_read[0]=thr_read[1]=thr_read[2]=0;
	}

	MyBuffer() {
		theNews="abc00";
		thr_read= new int[3];
		set();
	}
	public synchronized String getNews(int thr_id) throws InterruptedException{
		while(thr_read[thr_id]==1){
			wait();
		}
		thr_read[thr_id]=1;
		if(thr_read[0]+thr_read[1]+thr_read[2]==3){
			notifyAll();
		}
		return theNews;
	}
	public synchronized void addNews(String v) throws InterruptedException{
		while(thr_read[0]+thr_read[1]+thr_read[2]<3){
			wait();
		}
		theNews=v;
		reset();
		notifyAll();
	}
}
