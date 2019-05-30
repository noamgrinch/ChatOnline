package Client;

import java.util.logging.Level;

import javax.swing.UIManager;

import CentralLogger.SendLogThread;
import GUI.Friends.FriendsFrame;
import GUI.Login.LoginFrame;



public class Client {
	public static void main(String args[]) {
		try{
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			FriendsFrame p = new FriendsFrame();
			new LoginFrame(p.getPanel());		
		}
		catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }
	}
}
