package gioco;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
public class  WrappedObserver implements Observer{
private RemoteObserver remoteClient = null;
public WrappedObserver(RemoteObserver ro) {
this.remoteClient = ro;
}
public void update(Observable o,Object msg) {
try {
remoteClient.remoteUpdate(msg);
} catch (RemoteException e) {
System.out.println(
"Remote exception removing observer:" + this);
e.printStackTrace();
o.deleteObserver(this);
}
}

public RemoteObserver getObs(){
	return remoteClient;
}
}
