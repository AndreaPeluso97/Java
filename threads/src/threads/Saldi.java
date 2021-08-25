package threads;

public class Saldi {
public static void main(String[] args) {
Pulse portaNegozio=new Pulse();
new Negozio(portaNegozio).start();
while(true) {
int numClienti=(int) (5* Math.random());
for(int i=0; i<numClienti; i++) {
new Cliente(portaNegozio).start();
}
try {
Thread.sleep(300);
} catch (InterruptedException e) {}
}
}
}
