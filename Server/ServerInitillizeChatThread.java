package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import Util.User;


public class ServerInitillizeChatThread extends Thread{
	
	private Socket soc;
	private ChatsDB chatDB;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	@SuppressWarnings("unused")
	private User user1,user2;
	
	public ServerInitillizeChatThread(Socket soc,ChatsDB chatDB,ObjectInputStream in){
		this.soc=soc;
		this.chatDB=chatDB;
		this.in=in;
	}
	
	public void run(){
		try{	
			User user1 = (User)in.readObject();
			User user2 = (User)in.readObject();
			out = new ObjectOutputStream(soc.getOutputStream());
			out.writeObject(chatDB.getChat(user1, user2));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}

}
