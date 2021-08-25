package Appello_21_06_es2;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PORT = 2345;

	public static void main(String[] args) {
		Gioco mioGioco = new Gioco(0);
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(PORT);
			System.out.println("Server ready");
			while(true) {
				Socket cliSocket = ss.accept();
				new Thread(new ServerThread(cliSocket, mioGioco)).start();
				//cliSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
