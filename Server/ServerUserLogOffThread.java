package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.User;

public class ServerUserLogOffThread extends Thread{
	
	private Socket soc;
	private UsersDB DB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String username;
	
	public ServerUserLogOffThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		try{
			username = (String)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			if(DB.LogOff(username)) {
				out.writeObject(true);
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
