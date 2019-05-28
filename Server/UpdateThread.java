package Server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import Util.Storage;
import Util.User;

public class UpdateThread extends Thread{
	
	private Socket soc;
	private ObjectOutputStream outob;
	private ObjectInputStream in;
	private Storage storage;
	private int storagelastline;
	private ChatsDB chatsDB;
	
	public UpdateThread(Socket soc,ChatsDB chatsDB,ObjectInputStream in){
		this.soc=soc;
		this.chatsDB=chatsDB;
		this.in=in;

	}
	
	public void run(){
		
		try{
			int chatID = (int)in.readObject();
			storage = chatsDB.getStorageByID(chatID);
			if(storage==null){
				System.out.println("WTFF");
			}
			outob = new ObjectOutputStream(soc.getOutputStream());
			storagelastline = storage.getLastLine();
			outob.writeObject(storagelastline);
			if(storage != null){
				storagelastline = storage.getLastLine();
				int updaterline = (int)in.readObject();
				User user;
				for(;updaterline<storagelastline;updaterline++){
					user = storage.getLineUser(updaterline);
					if(user!=null){
						outob.writeObject(storage.getLineUser(updaterline));
						outob.writeObject(storage.getLineSentence(updaterline));
					}
				}
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
		finally{
				try{
					outob.close();
				}
				catch(Exception e){
					new SendLogThread(Level.SEVERE,e).run();
				}
		}
		
	}

}
