package GUI.ChatFrame;
import java.awt.BorderLayout;
import javax.swing.*;
import Util.Chat;
import Util.User;


public class ClientPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private TopPanel top;
	private BottomPanel bottom;
	private ToolBar toolbar;
	private Chat chat;
	private User self,otherguy;


	public ClientPanel(User self, User otherguy,Chat chat) { 
			this.setSelf(self);
			this.setOtherguy(otherguy);
			this.chat=chat;
			top = new TopPanel(otherguy.getName());
			this.setLayout(new BorderLayout());
			this.add(top,BorderLayout.CENTER);
			bottom = new BottomPanel(self,chat);
			this.add(bottom,BorderLayout.SOUTH);
			toolbar = new ToolBar();
			this.add(toolbar,BorderLayout.NORTH);
			

	}
	
	public void deliver(User user) {
		bottom.intilizaizeUser(user);
	}

	public TopPanel getTop() {
		return top;
	}
	public BottomPanel getBottomPanel() {
		return bottom;
	}
	
	public Chat getChat(){
		return chat;
	}

	public User getOtherguy() {
		return otherguy;
	}

	public void setOtherguy(User otherguy) {
		this.otherguy = otherguy;
	}

	public User getSelf() {
		return self;
	}

	public void setSelf(User self) {
		this.self = self;
	}

}
