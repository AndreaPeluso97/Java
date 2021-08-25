package clientprova;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import clientprova.Model;
import clientprova.RemoteObserver;
import clientprova.RmiSubjectService;


public class RmiObserverClient extends UnicastRemoteObject implements RemoteObserver {
    
 /**
  * If the client hasn't received any data for the span of connectionCheckInterval, it will try to reconnect the server.
  * It will keep trying until the server is not back up.
  * @param connectionCheckInterval
  * @throws RemoteException
  */
 public RmiObserverClient(long connectionCheckInterval) throws RemoteException{
        super();
        this.connectionCheckInterval = connectionCheckInterval;
    }
    private static long lastTimeStamp;
    private static final long serialVersionUID = 1L;
    private static long connectionCheckInterval = 10*1000;
    private static RmiSubjectService remoteService;
    Thread checkConnectionAlive = new Thread() {
       @Override
       public void run() {
           while (true) {
               try {
                   Thread.sleep(connectionCheckInterval);
               } catch (InterruptedException e) {
                   // ignore
               }
               long currentTimeStamp = System.currentTimeMillis();
               
               System.out.println("Inside client checkConnectionAlive : " + currentTimeStamp);
               
               if (currentTimeStamp - lastTimeStamp>connectionCheckInterval )subscribe();
           }
       };
   };
   
    public static void main(String[] args) {
     try {
      RmiObserverClient rc = new RmiObserverClient(10 * 1000);
   rc.subscribe();
   rc.checkConnectionAlive.start();
  } catch (RemoteException e) {
   e.printStackTrace();
  }
     
    }
    public void subscribe(){
   
        try {
            remoteService = (RmiSubjectService) Naming.lookup("//localhost:9999/RmiService");
            remoteService.addObserver(this);
        } catch (ConnectException ex) {
            ex.printStackTrace();
        }catch (RemoteException e) {
          e.printStackTrace();
  }catch (MalformedURLException e) {
   e.printStackTrace();
  }catch (NotBoundException e) {
   e.printStackTrace();
  }
    }
    @Override
    public void update(Object observable, Object updateMsg)
            throws RemoteException {
        System.out.println("===============start message=============");
        Model model = (Model)updateMsg;
        System.out.println(model.getName());
        System.out.println(model.getId());
        lastTimeStamp = model.getLastTimsStamp();
        System.out.println(lastTimeStamp);
        System.out.println("===============end message=============");
    }
}