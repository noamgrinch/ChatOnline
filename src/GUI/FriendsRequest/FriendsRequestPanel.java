package GUI.FriendsRequest;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Util.User;

public class FriendsRequestPanel extends JTabbedPane{


	private static final long serialVersionUID = 1L;
	private InRequestspanel  inpanel;
	private OutRequestspanel  outpanel;
	private User user;
	
	public FriendsRequestPanel(User user) {
		this.user=user;
		inpanel = new InRequestspanel(user);
		outpanel = new OutRequestspanel(user);
		this.addTab("Incoming Requests", inpanel);
		this.addTab("Outgoing Requests", outpanel);
		
	}
	
	public User getUser() {
		return this.user;
	}

}
