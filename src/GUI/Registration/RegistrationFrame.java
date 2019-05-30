package GUI.Registration;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import GUI.Login.LoginPanel;
public class RegistrationFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private RegPanel p;
	private LoginPanel loginpanel;
	private BufferedImage img;
	private String thumbnail = "registration-thumbnail.png";
	
	
	public RegistrationFrame(LoginPanel loginpanel){
		super("Registration");
		this.setLoginpanel(loginpanel);
		p = new RegPanel(this,loginpanel);
		img = GUI.Util.getImage(thumbnail); //sets up thumbnail.
		this.setIconImage(img);
		this.add(p);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(250,150));
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

	public LoginPanel getLoginpanel() {
		return loginpanel;
	}

	public void setLoginpanel(LoginPanel loginpanel) {
		this.loginpanel = loginpanel;
	}
	


}
