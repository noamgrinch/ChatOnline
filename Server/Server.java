package Server;
import java.net.*;
import CentralLogger.*;

import java.util.logging.Level;

public class Server implements Runnable{
	
	private ServerSocket ss;
	private UsersDB userDB;
	private ChatsDB chatsDB;
	
	public Server(int port) {
		userDB = new UsersDB();
		chatsDB = new ChatsDB();
		chatsDB = new ChatsDB();
		try {
			ss = new ServerSocket(port);
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e);
		}
	}

	@Override
	public void run() {	
			try {
				while(true) {	
					new ClientThread(ss.accept(),userDB,chatsDB).start();
				}
			} 
			catch (Exception e) {
					new SendLogThread(Level.SEVERE,e).start();
			}	
	}
	
	
}
	



	


