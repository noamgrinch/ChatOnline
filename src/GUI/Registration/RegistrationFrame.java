package GUI.Registration;
import javax.swing.JFrame;
import GUI.Login.LoginPanel;
public class RegistrationFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private RegPanel p;
	private LoginPanel loginpanel;
	
	public RegistrationFrame(LoginPanel loginpanel){
		super("Registration form");
		this.setLoginpanel(loginpanel);
		p = new RegPanel(this,loginpanel);
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

}
