package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.User;

public class ServerConfirmFriendRequestsThread extends Thread{
	
	
	private Socket soc;
	private UsersDB DB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private User user;
	
	public ServerConfirmFriendRequestsThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			boolean ok = DB.exsits(name);
			out.writeObject(ok);
			if(ok) {
				out.writeObject(DB.addFriend(user, name));
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}

}
