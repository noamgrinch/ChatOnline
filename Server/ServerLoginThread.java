package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import Util.User;

public class ServerLoginThread extends Thread{
	
	private Socket soc;
	private UsersDB DB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private User user;
	
	public ServerLoginThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		try{
			String name = (String)in.readObject();
			String password = (String)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			if(DB.LogIn(name, password)){
				out.writeObject(true);
				user = DB.getUser(name);
				out.writeObject(user); 
			}
			else{
				out.writeObject(false);
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e);
		}
	}
}
