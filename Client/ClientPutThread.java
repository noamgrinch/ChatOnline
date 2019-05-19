package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Util.User;

public class ClientPutThread extends Thread{
	
	private String text;
	private ObjectOutputStream out;
	private Socket soc;
	private ObjectInputStream in;
	private final int PUTFLOW = 2;
	@SuppressWarnings("unused")
	private boolean ok;
	private User user;
	private int chatID;
	
	public ClientPutThread(String text,User user,int chatID){
		this.user=user;
		this.text=text;
		this.chatID=chatID;
	}
	
	public void run(){
		try{
			soc = new Socket("Localhost",8888);
			out = new ObjectOutputStream(soc.getOutputStream());		
			out.writeObject((int)PUTFLOW);
			out.writeObject(chatID);
			out.writeObject(user);
			out.writeObject(text);
			in = new ObjectInputStream(soc.getInputStream());
			ok = (boolean)in.readObject();
			
		}
		catch(Exception ex) {
			new SendLogThread(Level.SEVERE,ex).run();
		}
		finally {
			try {
				out.close();
				soc.close();
			} catch (Exception e1) {
				new SendLogThread(Level.SEVERE,e1).run();
			}
		}
	}

}
