package threads;

public class Negozio extends Thread {
private Pulse portaNegozio;
public Negozio(Pulse pn) {
portaNegozio=pn;
setName("Negozio" + getName());
}
public void run() {
while(true) {
try {
Thread.sleep(1000);
System.out.println(getName() + ": apro le porte");
portaNegozio.send();
System.out.println(getName() +  " : chiudo le porte");
} catch(InterruptedException e) { }
}
}
}
