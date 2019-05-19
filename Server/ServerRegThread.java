package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.UsersDB;

public class ServerRegThread extends Thread{
	
	private Socket soc;
	private UsersDB DB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerRegThread(Socket soc,UsersDB DB,ObjectInputStream in){
		this.soc=soc;
		this.DB=DB;
		this.in=in;
	}
	
	public void run(){
		try{
			String name = (String)in.readObject();
			String password = (String)in.readObject();
			String email = (String)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			out.writeObject(DB.registerUserInDB(name, password,email));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e);
		}
	}
}
