package provaservertoclient;

import java.util.*;
import java.rmi.RemoteException;
public class WrappedObserver implements Observer {
private RemoteObserver remoteClient = null;
public WrappedObserver(RemoteObserver ro) {
this.remoteClient = ro;
}
public void update(Observable o, Object arg) {
try {
remoteClient.remoteUpdate(o.toString(), arg);
} catch (RemoteException e) {
System.out.println(
"Remote exception removing observer:" + this);
o.deleteObserver(this);
}
}}