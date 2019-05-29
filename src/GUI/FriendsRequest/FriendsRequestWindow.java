package GUI.FriendsRequest;

import javax.swing.JFrame;

import Util.User;

public class FriendsRequestWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private User user;
	private FriendsRequestPanel p;
	
	public FriendsRequestWindow(User user) {
		super("Friends Requests");
		this.setUser(user);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(350, 500);
		this.setResizable(false);
		p = new FriendsRequestPanel(user);
		this.add(p);
		this.setVisible(true);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FriendsRequestPanel getP() {
		return p;
	}

	public void setP(FriendsRequestPanel p) {
		this.p = p;
	}

}
