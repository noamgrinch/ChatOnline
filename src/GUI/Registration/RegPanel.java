package GUI.Registration;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Client.ClientRegThread;
import GUI.Login.LoginPanel;
import SpringUtilities.*;

public class RegPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JLabel lname,lpassword,lemail;
	private JTextField tname,tpassword,temail;
	private JButton bregister,bcancel;
	private RegistrationFrame f;
	private LoginPanel loginpanel;
	
	public RegPanel(RegistrationFrame f,LoginPanel loginpanel){
		this.f=f;
		this.loginpanel=loginpanel;
		lname = new JLabel("Name", JLabel.TRAILING);
		lpassword = new JLabel("Password", JLabel.TRAILING);
		lemail = new JLabel("Email", JLabel.TRAILING);
		tname = new JTextField();
		tname.setPreferredSize(new Dimension(70,20));
		tpassword = new JTextField();
		tpassword.setPreferredSize(new Dimension(70,20));
		temail = new JTextField();
		temail.setPreferredSize(new Dimension(70,20));
		bregister = new JButton("Register");
		bcancel = new JButton("Cancel");
		lname.setLabelFor(tname);
		lpassword.setLabelFor(tpassword);
		lemail.setLabelFor(temail);
	    bregister.addActionListener(this);
	    bcancel.addActionListener(this);
		this.setLayout(new SpringLayout());
		this.add(lname);
		this.add(tname);
		this.add(lpassword);
		this.add(tpassword);
		this.add(lemail);
		this.add(temail);
		this.add(bregister);
		this.add(bcancel);
		SpringUtilities.makeCompactGrid(this,4,2,5,5,5,5);
	}

	@Override
	public void actionPerformed(ActionEvent s) {
		if(s.getSource() == bregister){
			new ClientRegThread(tname.getText(),tpassword.getText(),temail.getText(),f,loginpanel).run();
			tname.setText("");
			tpassword.setText("");
			temail.setText("");
		}
		else if(s.getSource() == bcancel){
			f.close();
		}
	}

}
