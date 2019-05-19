package GUI.Login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.*;
import CentralLogger.SendLogThread;
import Client.ClientLoginThread;
import GUI.Friends.FriendsTablePanel;
import GUI.Registration.RegistrationFrame;

public class LoginPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JButton login,register;
	private JTextField name,password;
	private JLabel labelname,labelpassword;
	private FriendsTablePanel p;
	private LoginFrame f;
	
	public LoginPanel(FriendsTablePanel p,LoginFrame frame){
		this.f=frame;
		this.p=p;
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		login = new JButton("Login");
		login.addActionListener(this);
		name = new JTextField("");
		name.setPreferredSize(new Dimension(50,20));
		password= new JTextField("");
		password.setPreferredSize(new Dimension(50,20));
		labelname = new JLabel("Name:");
		labelpassword = new JLabel("Password:");
		register = new JButton("Register");
		register.addActionListener(this);
		this.add(labelname);
		this.add(name);
		this.add(labelpassword);
		this.add(password);
		this.add(login);
		this.add(register);
		
	}
	
	public void focus(){
		name.requestFocus();
	}

	

	@Override
	public void actionPerformed(ActionEvent s) {
		if(s.getSource() == login){
				try{
					new ClientLoginThread(name.getText(),password.getText(),p,f).run();
					name.setText("");
					password.setText("");
				}
				catch(Exception ex) {
					new SendLogThread(Level.SEVERE,ex);
				}
			
		}
		else if(s.getSource()==register){
				new RegistrationFrame(this);
			}
		
	}
	

}
