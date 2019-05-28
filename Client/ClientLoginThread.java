package Client;
import java.net.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import CentralLogger.SendLogThread;
import GUI.Friends.FriendsTablePanel;
import GUI.Login.LoginFrame;
import Util.User;

import java.io.*;

public class ClientLoginThread extends Thread{
	
	private String name,password;
	private FriendsTablePanel p;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private LoginFrame f;
	private User user;
	private final int LOGINFLOW=3;
	
	public ClientLoginThread(String name,String password,FriendsTablePanel p,LoginFrame f){  
		this.name=name;
		this.password=password;
		this.p=p;
		this.f=f;
	}
	
	public void run(){
		@SuppressWarnings("unused")
		boolean approved = false;
			try{
				soc = new Socket("Localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());		
				out.writeObject((int)LOGINFLOW);
				out.writeObject(name);
				out.writeObject(password);
				in = new ObjectInputStream(soc.getInputStream());
				if((approved=(boolean)in.readObject())){
					user = (User)in.readObject();
					p.deliver(user); 
					p.Enable();
					f.close();
				}
				else{
					JOptionPane.showMessageDialog(null, "Invalid user name or password. Please try again.");
				}
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
					catch(Exception ex){
						new SendLogThread(Level.SEVERE,ex).run();
					}

			}
	}
	

}
