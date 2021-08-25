package Appello_19_12_2018_es2;

import java.io.IOException;


import java.net.*;
import java.io.*;

public class MonoServer extends Thread{
  static final int PORT = 8080; 
  Socket socket;
  
  public MonoServer(Socket s) {
	  
	  
	  socket=s;
	  
	  start();
	  
	  
}
public void run() {
    System.out.println("Bank Server Started");
    ObjectInputStream obj_in_s;
    ObjectOutputStream obj_out_s;
    try {
     
        try {  
          obj_out_s = new ObjectOutputStream(socket.getOutputStream());
          obj_in_s = new ObjectInputStream(socket.getInputStream());
          while (true) {
            String str = (String) obj_in_s.readObject();
            System.out.println("Received: " + str);
            if (str.equals("END"))
              break;
            if(str.equals("bankOp")){
              Operation op = (Operation)obj_in_s.readObject();
              System.out.println("bankOp executed for " + op.getType() + " "+ op.getAmount());
              obj_out_s.writeObject("OK");
              System.out.println("server: bankOp executed");      
            }
          }
          System.out.println("closing...");
        } catch (IOException | ClassNotFoundException e) {
          System.err.println("IO Exception");
        } finally {
          try {
            socket.close();
          } catch (IOException e) {
            System.err.println("Socket not closed");
          }
        }
      
    } finally {
      try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
  }
}

