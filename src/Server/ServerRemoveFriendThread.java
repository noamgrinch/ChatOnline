package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.User;

public class ServerRemoveFriendThread extends Thread{
	
	private Socket soc;
	private UsersDB DB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private User user;
	
	public ServerRemoveFriendThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			out.writeObject(DB.removeFriend(user, name));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e);
		}
	}

}
