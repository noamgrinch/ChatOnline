package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import CentralLogger.SendLogThread;

public class ClientLogOffThread extends Thread{
	
	private String username;
	private Socket soc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean ok;
	private final int LOGOFFFLOW = 10;
	
	public ClientLogOffThread(String username) {
		this.username=username;
	}
	
	public void run() {
		try{
			soc = new Socket("Localhost",8888);
			out = new ObjectOutputStream(soc.getOutputStream());		
			out.writeObject((int)LOGOFFFLOW);
			out.writeObject(username);
			in = new ObjectInputStream(soc.getInputStream());
			if(!(ok = (boolean)in.readObject())){
				new SendLogThread(Level.SEVERE,new Exception("There was a problem logging user: " + username + " Off.")).run();
			}
			
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
