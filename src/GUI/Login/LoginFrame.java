package GUI.Login;

import javax.swing.JFrame;
import GUI.Friends.FriendsTablePanel;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private LoginPanel pan;
	private FriendsTablePanel p;
	
	public LoginFrame(FriendsTablePanel p){
		super("Login");
		this.setP(p);
		pan = new LoginPanel(p,this);
		this.add(pan);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setAlwaysOnTop(true);

	}
	
	public void close(){
		this.dispose();
		this.setVisible(false);
	}

	public FriendsTablePanel getP() {
		return p;
	}

	public void setP(FriendsTablePanel p) {
		this.p = p;
	}

}
