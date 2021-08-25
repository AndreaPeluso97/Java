package serverprova;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.Observer;

public class RmiSubjectImpl extends Observable implements RmiSubjectService {

    private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;
       
        private RemoteObserver ro = null;

        public WrappedObserver(RemoteObserver ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.update(o.toString(), arg);
            } catch (RemoteException e) {
                System.out
                        .println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
            }
        }

    }

    @Override
    public void addObserver(RemoteObserver o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        System.out.println("Added observer:" + mo);
    }

    Thread thread = new Thread() {
      private int i=0;
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    // ignore
                }
                setChanged();
                notifyObservers(new Model("model", i++,System.currentTimeMillis()));
                //notifyObservers(new Date());
            }
        };
    };

    public RmiSubjectImpl() {
        thread.start();
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
       
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(1099);
            RmiSubjectService rmiService = (RmiSubjectService) UnicastRemoteObject
                    .exportObject(new RmiSubjectImpl(), 1099);
            rmiRegistry.bind("RmiService", rmiService);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}