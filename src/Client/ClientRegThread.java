package Client;
import java.net.*;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import CentralLogger.SendLogThread;
import GUI.Login.LoginPanel;
import GUI.Registration.RegistrationFrame;

import java.io.*;

public class ClientRegThread extends Thread{
	
	private String name,password,email;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private RegistrationFrame f;
	private final int SUCCESSREG = 0;
	private final int EXSITSINDB = 1;
	private final int INVALIDINPUT = 2;
	private LoginPanel loginpanel;
	
	public ClientRegThread(String name,String password,String email,RegistrationFrame f,LoginPanel loginpanel){
		this.name=name;
		this.password=password;
		this.email=email;
		this.f=f;
		this.loginpanel=loginpanel;
	}
	
	public void run(){
		int approved;
			try{
				soc = new Socket("Localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());		
				out.writeObject((int)4);
				out.writeObject(name);
				out.writeObject(password);
				out.writeObject(email);
				in = new ObjectInputStream(soc.getInputStream());
				approved=(int)in.readObject();
				if(approved==SUCCESSREG){
					JOptionPane.showMessageDialog(f, "Great Success!");
					f.close();
					loginpanel.focus();
				}
				if(approved==EXSITSINDB){
					JOptionPane.showMessageDialog(f, "User already exsits. Please try a different name.");
				}
				if(approved==INVALIDINPUT){
					JOptionPane.showMessageDialog(f, "Invalid input. Please try again.");
				}
			}
			catch(ConnectException e) {
				JOptionPane.showMessageDialog(f,"Connection to the server have been lost.");
			}
			catch(Exception ex) {
				new SendLogThread(Level.SEVERE,ex).run();
			}
			finally{
					try{	
						out.close();
						in.close();
						soc.close();
					}
					catch(ConnectException e) {
						//no connected  to the server.
					}
					catch(NullPointerException e) {
						//no connected  to the server.
					}
					catch(Exception ex){
						new SendLogThread(Level.SEVERE,ex).run();
					}

			}
	}
	

}
