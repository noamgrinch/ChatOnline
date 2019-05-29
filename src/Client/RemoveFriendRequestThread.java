package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import CentralLogger.SendLogThread;
import GUI.Friends.FriendsTablePanel;
import Util.User;

public class RemoveFriendRequestThread extends Thread{

	private User user;
	private String friendtoremove;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final int REMOVEFRIENDFLOW = 7;
	private FriendsTablePanel pan;
	
	public RemoveFriendRequestThread(FriendsTablePanel pan,User user,String friendtoremove){
		this.user=user;
		this.pan=pan;
		this.friendtoremove=friendtoremove;
	}
	
	public void run() {
		if(!user.getFriendsListKeys().contains(friendtoremove)|| user.getName().equals(friendtoremove)) {
			JOptionPane.showMessageDialog(null, "Friend: " + friendtoremove + " is not your friend.");
		}
		else {
			try {
				soc = new Socket("Localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());		
				out.writeObject((int)REMOVEFRIENDFLOW);
				out.writeObject((String)this.friendtoremove);
				out.writeObject((User)this.user);
				in = new ObjectInputStream(soc.getInputStream());
				pan.deliver((User)in.readObject());
				pan.update();
				
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
	
}
