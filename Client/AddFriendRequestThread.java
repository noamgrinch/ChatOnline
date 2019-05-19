package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import CentralLogger.SendLogThread;
import GUI.Friends.FriendsTablePanel;
import Util.User;

public class AddFriendRequestThread extends Thread{
	
	private User user;
	private String friendtoadd;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final int ADDFRIENDFLOW = 5;
	private FriendsTablePanel pan;
	
	public AddFriendRequestThread(FriendsTablePanel pan,User user,String friendtoadd) {
		this.user=user;
		this.pan=pan;
		this.friendtoadd=friendtoadd;		
	}
	
	public void run() {
		if(user.getName().equals(friendtoadd)) {
			JOptionPane.showMessageDialog(null, "You cannot add yourself.");
		}
		else {
			try {
				soc = new Socket("Localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());		
				out.writeObject((int)ADDFRIENDFLOW);
				out.writeObject((String)friendtoadd);
				out.writeObject((User)user);
				in = new ObjectInputStream(soc.getInputStream());
				if((boolean)in.readObject()) {
					pan.deliver((User)in.readObject());
					pan.update();
				}
				else {
					JOptionPane.showMessageDialog(null, "User: "+ friendtoadd + " does not exsits.");
				}
			}
			catch(Exception ex) {
				new SendLogThread(Level.SEVERE,ex);
			}
			finally{
					try{	
						out.close();
						in.close();
						soc.close();
					}
					catch(Exception ex){
						new SendLogThread(Level.SEVERE,ex);
					}

			}
		
		}
	}
	

}
