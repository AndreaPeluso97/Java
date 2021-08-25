package Appello_03_06_2019_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

	Socket socket;
	Rubrica miaRubrica;

	ObjectOutputStream oos;
	ObjectInputStream ois;

	String nome=null;
	String numero=null;

	public ServerThread(Socket s,Rubrica r) throws IOException{
		socket=s;
		miaRubrica = r;
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		start();

	}

	public void run(){	
		while(true){
			try{
				if(ois.readObject().equals("aggiungi")){
					nome=(String) ois.readObject();
					numero=(String)ois.readObject();
					miaRubrica.aggiungiNumero(nome, numero);
				}else if(ois.readObject().equals("In Rubrica")){
					nome=(String) ois.readObject();
					miaRubrica.inRubrica(nome);
				}else if(ois.readObject().equals("Elimina")){
					nome=(String) ois.readObject();
					miaRubrica.eliminaNumero(nome);
				}else if(ois.readObject().equals("Trova")){
					nome=(String) ois.readObject();
					numero=miaRubrica.trova(nome);
					oos.writeObject(numero);
				}
			}catch(Exception e){break;}}
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			
		}
	}

