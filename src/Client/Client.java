package Client;

import GUI.Friends.FriendsFrame;
import GUI.Login.LoginFrame;



public class Client {
	public static void main(String args[]) {			
		FriendsFrame p = new FriendsFrame();
		new LoginFrame(p.getPanel());		
	}
}
