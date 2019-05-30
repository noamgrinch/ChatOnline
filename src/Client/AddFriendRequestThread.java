package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import CentralLogger.SendLogThread;
import GUI.Friends.FriendsFrame;
import GUI.Friends.FriendsTablePanel;
import Util.User;

public class AddFriendRequestThread extends Thread{
	
	private User user;
	private String friendtoadd;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final int ADDFRIENDREQUESTFLOW = 5;
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
				out.writeObject((int)ADDFRIENDREQUESTFLOW);
				out.writeObject((String)friendtoadd);
				out.writeObject((User)user);
				in = new ObjectInputStream(soc.getInputStream());
				if((boolean)in.readObject()) {
					pan.deliver((User)in.readObject());
					JOptionPane.showMessageDialog(((FriendsFrame) SwingUtilities.getWindowAncestor(pan)), "Friend request was sent.");
				}
				else {
					JOptionPane.showMessageDialog(((FriendsFrame) SwingUtilities.getWindowAncestor(pan)), "User: "+ friendtoadd + " does not exsits.");
				}
			}
			catch(IOException ex) {
				//User canceled before adding. nothing to do here.
			}
			catch(Exception ex) {
				new SendLogThread(Level.SEVERE,ex).run();
			}
			finally{
					try{
						if(out!=null) {
							out.close();
						}
						if(in!=null) {
							in.close();
						}
						if(soc!=null) {
							soc.close();
						}
					}
					catch(Exception ex){
						new SendLogThread(Level.SEVERE,ex).run();
					}

			}
		
		}
	}
	

}
