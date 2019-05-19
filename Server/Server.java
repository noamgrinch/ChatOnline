package Server;
import java.net.*;
import CentralLogger.*;
import Util.ChatsDB;
import Util.UsersDB;

import java.util.logging.Level;

public class Server /*implements Runnable*/{ //Fix it at the very end.
	
	/*private ServerSocket ss;
	private UsersDB userDB;
	private ChatsDB chatsDB;
	
	public Server(int port) {
		userDB = new UsersDB();
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
					new ClientThread(ss.accept(),userDB,chatsDB).run();
				}
			} 
			catch (Exception e) {
					new SendLogThread(Level.SEVERE,e).run();
			}	
	}*/
	
	public static void main(String args[]) { 
		ServerSocket ss;
		UsersDB userDB = new UsersDB();
		ChatsDB chatsDB = new ChatsDB();
		
		try {
			ss = new ServerSocket(8888);
			while(true) {	
				new ClientThread(ss.accept(),userDB,chatsDB).run();
			}
		} 
		catch (Exception e) {
				new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
}
	


