package Client;

import java.util.logging.Level;

import javax.swing.UIManager;

import CentralLogger.SendLogThread;
import GUI.Friends.FriendsFrame;
import GUI.Login.LoginFrame;



public class Client {
	public static void main(String args[]) {			
		FriendsFrame p = new FriendsFrame();
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new LoginFrame(p.getPanel());		
		}
		catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }
	}
}
