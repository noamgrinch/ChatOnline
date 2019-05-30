package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.User;

public class ServerAddFriendThread extends Thread{
	
	private Socket soc;
	private UsersDB DB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private User user;
	
	public ServerAddFriendThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			if(name==null || user == null) {
				throw new IOException("Flow was aborted by user.");
			}
			out = new ObjectOutputStream(soc.getOutputStream());
			boolean ok = DB.exsits(name);
			out.writeObject(ok);
			if(ok) {
				out.writeObject(DB.addFriendRequest(user, name));
			}
		}
		catch(IOException ex) {
			//User canceled before adding. nothing to do here.
			new SendLogThread(Level.WARNING,ex).run();
		}
		
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
}


