package GUI.FriendsRequest;

import java.awt.Dimension;

import javax.swing.JFrame;
import Util.User;

public class ConfirmDeclineFriendFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	private User user;
	private String friend;
	private ConfirmDeclineFriendPanel p;
	private InRequestspanel parent;

	
	
	public ConfirmDeclineFriendFrame(InRequestspanel parent,User user,String friend) {
		super(friend);	
		this.parent=parent;
		this.setUser(user);
		this.setFriend(friend);
		setP(new ConfirmDeclineFriendPanel(user,friend));
		this.add(this.p);
		this.setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension(500,150));
		//this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setAlwaysOnTop(true);
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getFriend() {
		return friend;
	}


	public void setFriend(String friend) {
		this.friend = friend;
	}


	public ConfirmDeclineFriendPanel getP() {
		return p;
	}


	public void setP(ConfirmDeclineFriendPanel p) {
		this.p = p;
	}
	
	public InRequestspanel getParent() {
		return this.parent;
	}
	
	

}
