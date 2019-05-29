package GUI.Friends;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import Client.ClientLogOffThread;
import Util.User;

public class FriendsFrame extends JFrame implements WindowListener{


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
		this.addWindowListener(this);
		
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

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		new ClientLogOffThread(user.getName()).run();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
