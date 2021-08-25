package threads;

public class TransientSignal extends Signal {
protected int waiting = 0;
public synchronized void send() {
if(waiting>0)
super.send(); // Sveglia un solo Thread
}
public synchronized void waits() throws InterruptedException{
try {
waiting++;
while(!arrived)
wait();
waiting--;
arrived = false ;
} catch (InterruptedException ie) {
waiting--;
throw ie;
}
}
}
