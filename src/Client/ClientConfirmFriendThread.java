package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import Util.User;

public class ClientConfirmFriendThread extends Thread{
	
	private User user;
	private String friend;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final int ADDFRIENDFLOW = 12;
	
	public ClientConfirmFriendThread(User user,String friend) {
		this.user=user;
		this.friend=friend;
	}
	
	public void run() {

			try {
				soc = new Socket("Localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());		
				out.writeObject((int)ADDFRIENDFLOW);
				out.writeObject((String)friend);
				out.writeObject((User)user);
				in = new ObjectInputStream(soc.getInputStream());
				@SuppressWarnings("unused")
				boolean ok = (boolean)in.readObject();
			}
			catch(Exception ex) {
				new SendLogThread(Level.SEVERE,ex).run();
			}
			finally{
					try{	
						out.close();
						in.close();
						soc.close();
					}
					catch(Exception ex){
						new SendLogThread(Level.SEVERE,ex).run();
					}

			}
		
		}
	

}
