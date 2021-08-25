package Eserc_4;

public class Pool {

	private int NumTask;
	private int[] WaitingList;
	private int maxwait=10;
	private int Resources;

	public Pool(int NumTask,int Resources){

		this.NumTask=NumTask;
		this.WaitingList=new int[NumTask];
		this.Resources=Resources;
		for(int i=0;i<NumTask;i++){
			WaitingList[i]=0;
		}

	}
	
	public boolean someelsestarvating(int userid){
		
		for(int i=0;i<NumTask;i++){
			if(WaitingList[i]>maxwait && i!=userid){
				return true;
			}
		}
		return false;
	}

	public synchronized void require(int need,int i) throws InterruptedException{
		
		while(someelsestarvating(i) && need>Resources){
			if(!someelsestarvating(i)){
				WaitingList[i]++;
			}
			wait();
		}
		Resources=Resources-need;
	}

	public synchronized void release(int need,int i){
		Resources=Resources+need;
		WaitingList[i]=0;
		notifyAll();
	}
}
