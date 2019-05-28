package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import Util.Storage;
import Util.User;

public class ServerPutThread extends Thread{
	
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private User user;
	private String inputsentence;
	private Storage storage;
	private ChatsDB chatsDB;
	
	public ServerPutThread(Socket soc,ChatsDB chatsDB,ObjectInputStream in){
		this.soc=soc;
		this.chatsDB=chatsDB;
		this.in=in;
	}
	
	public void run(){
		
		try{
			out = new ObjectOutputStream(soc.getOutputStream());
			int chatID = (int)in.readObject();
			storage = chatsDB.getStorageByID(chatID);
			user=(User)in.readObject();
			inputsentence=(String)in.readObject();
			storage.addString(user, inputsentence);
			out.writeObject(true);
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
		finally{
				try{
					in.close();
					out.close();
				}
				catch(Exception e){
					new SendLogThread(Level.SEVERE,e).run();
				}
		}
		
	}

}
