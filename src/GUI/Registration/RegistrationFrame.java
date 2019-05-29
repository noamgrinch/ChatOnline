package GUI.Registration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import CentralLogger.SendLogThread;
import GUI.Login.LoginPanel;
public class RegistrationFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private RegPanel p;
	private LoginPanel loginpanel;
	private BufferedImage img;
	
	public RegistrationFrame(LoginPanel loginpanel){
		super("Registration form");
		this.setLoginpanel(loginpanel);
		p = new RegPanel(this,loginpanel);
		img = getImage(); //sets up thumbnail.
		this.setIconImage(img);
		this.add(p);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File("registration-thumbnail.png"));
        } catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }

        return null;
    }

}
