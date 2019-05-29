package GUI.Login;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import CentralLogger.SendLogThread;
import GUI.Friends.FriendsTablePanel;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private LoginPanel pan;
	private FriendsTablePanel p;
	private BufferedImage img;
	
	
	public LoginFrame(FriendsTablePanel p) throws Exception{
		super("Login");
		img = getImage(); //sets up thumbnail.
		this.setIconImage(img);
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
	
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File("login-thumbnail.jpg"));
        } catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }

        return null;
    }

}
