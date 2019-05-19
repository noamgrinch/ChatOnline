package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import GUI.Friends.FriendsTablePanel;
import Util.User;


public class FriendsTableUpdaterThread extends Thread{
	
	private FriendsTablePanel pan;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final int FRIENDUPDATEFLOW=6;
	private Object[][] table;
	private User user;
	
	public FriendsTableUpdaterThread(FriendsTablePanel pan,User user) {
		this.pan=pan;
		this.user=user;
	}
	
	public void run() {
			try {
				soc = new Socket("localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());
				out.writeObject(FRIENDUPDATEFLOW);
				out.writeObject(user);
				in = new ObjectInputStream(soc.getInputStream());
				table = (Object[][])in.readObject();
				pan.updateFriendsList(table);
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
