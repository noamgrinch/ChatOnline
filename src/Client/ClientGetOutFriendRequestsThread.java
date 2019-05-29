package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import GUI.FriendsRequest.OutRequestspanel;
import Util.User;

public class ClientGetOutFriendRequestsThread extends Thread{

	private OutRequestspanel p;
	private User user;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final int GETOUTREQUESTSFLOW=14;
	private Object[][] table;

	
	
	
	public ClientGetOutFriendRequestsThread(OutRequestspanel p , User user) {
		this.p=p;
		this.user=user;
	}
	
	public void run() {
		try {
			soc = new Socket("localhost",8888);
			out = new ObjectOutputStream(soc.getOutputStream());
			out.writeObject(GETOUTREQUESTSFLOW);
			out.writeObject(user);
			in = new ObjectInputStream(soc.getInputStream());
			table = (Object[][])in.readObject();
			p.updateFriendsOutRequests(table);
		}
		catch(Exception ex) {
			new SendLogThread(Level.SEVERE,ex).run();
		}
		finally {
			try {
				soc.close();
				out.close();
				in.close();
			}
			catch(Exception ex) {
				new SendLogThread(Level.SEVERE,ex).run();
			}
		}
	}
}
