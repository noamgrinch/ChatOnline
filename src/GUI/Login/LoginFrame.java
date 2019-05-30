package GUI.Login;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import GUI.Friends.FriendsTablePanel;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private LoginPanel pan;
	private FriendsTablePanel p;
	private BufferedImage img;
	private String thumbnail = "login-thumbnail.jpg";
	
	
	public LoginFrame(FriendsTablePanel p) throws Exception{
		super("Login");
		img = GUI.Util.getImage(thumbnail); //sets up thumbnail.
		this.setIconImage(img);
		this.setP(p);
		pan = new LoginPanel(p,this);
		this.add(pan);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(440,65));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
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
