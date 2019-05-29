package Client;

import java.util.logging.Level;
import CentralLogger.SendLogThread;
import GUI.Friends.FriendsFrame;
import GUI.Login.LoginFrame;



public class Client {
	public static void main(String args[]) {			
		FriendsFrame p = new FriendsFrame();
		try{
		new LoginFrame(p.getPanel());		
		}
		catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }
	}
}
