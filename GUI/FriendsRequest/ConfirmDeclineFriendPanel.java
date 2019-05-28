package GUI.FriendsRequest;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Client.ClientConfirmFriendThread;
import Client.ClientDeclineFriendThread;
import Client.ClientGetInFriendRequestsThread;
import Util.User;

public class ConfirmDeclineFriendPanel extends JPanel implements ActionListener{


	private static final long serialVersionUID = 1L;
	private JLabel message;
	private JButton confirm,decline,cancel;
	private JPanel bottom;
	private User user;
	private String friend;

	public ConfirmDeclineFriendPanel(User user, String friend) {
		this.user=user;
		this.friend=friend;
		message = new JLabel("Please select either to confirm or decline your friend request from " + friend);
		message.setHorizontalAlignment(JLabel.CENTER);
		message.setVerticalAlignment(JLabel.CENTER);
		confirm = new JButton("Confirm");
		confirm.addActionListener(this);
		decline = new JButton("Decline");
		decline.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		bottom = new JPanel();
		bottom.add(confirm);
		bottom.add(decline);
		bottom.add(cancel);
		this.setLayout(new BorderLayout());
		this.add(message,BorderLayout.CENTER);
		this.add(bottom,BorderLayout.SOUTH);	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void actionPerformed(ActionEvent s) {
		if(s.getSource() == confirm) {
			new ClientConfirmFriendThread(user,friend).run();
			new ClientGetInFriendRequestsThread(((ConfirmDeclineFriendFrame) SwingUtilities.getWindowAncestor(this)).getParent(),user).run();
			((ConfirmDeclineFriendFrame) SwingUtilities.getWindowAncestor(this)).dispose();
		}
		if(s.getSource() == decline) {
			new ClientDeclineFriendThread(user,friend).run();
			new ClientGetInFriendRequestsThread(((ConfirmDeclineFriendFrame) SwingUtilities.getWindowAncestor(this)).getParent(),user).run();
			((ConfirmDeclineFriendFrame) SwingUtilities.getWindowAncestor(this)).dispose();
		}
		if(s.getSource() == cancel) {
			((ConfirmDeclineFriendFrame) SwingUtilities.getWindowAncestor(this)).dispose();
		}
		
	}
}
