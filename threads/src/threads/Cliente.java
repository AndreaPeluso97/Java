package threads;

public class Cliente extends Thread {
private Pulse portaNegozio;
public Cliente(Pulse pn) {
portaNegozio=pn;
setName("Cliente"+getName()); 
}
public void run() {
System.out.println(getName()+": mi metto in coda");
try {
portaNegozio.waits();
System.out.println(getName()+": entrato nel negozio");
} catch (InterruptedException e) {}
}
}
