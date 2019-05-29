package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.User;

public class ServerGetOutFriendRequestsThread extends Thread{
	
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private UsersDB DB;
	private User user;
	
	public ServerGetOutFriendRequestsThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		
		try{
			user = (User)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			out.writeObject(DB.getOutFriendsRequestsForTable(user));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
		finally{
				try{
					out.close();
					in.close();
				}
				catch(Exception e){
					new SendLogThread(Level.SEVERE,e).run();
				}
		}
		
	}

}
