package GUI.Friends;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Util.User;

public class FriendsFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	private FriendsTablePanel friendstable;
	private User user;

	
		
	public FriendsFrame() {
		
		super("Chat");
		friendstable = new FriendsTablePanel();
		this.setLayout(new BorderLayout());
		this.add(friendstable,BorderLayout.CENTER);		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(270, 490);
		this.setResizable(false);
		this.setVisible(true);
		this.setEnabled(false);
		
	}
	
	public FriendsTablePanel getPanel() {
		return friendstable;
	}
	
	public void setUser(User user) {
		this.user=user;
	}
	
	public User getUser() {
		return this.user;
	}

}
