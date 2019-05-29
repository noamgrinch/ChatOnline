package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import CentralLogger.SendLogThread;
import GUI.ChatFrame.ChatFrame;
import Util.User;

public class initillizeUserThread extends Thread{
	
	
	@SuppressWarnings("unused")
	private String name,password;
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ChatFrame f;
	private User user;
	private final int GETUSERFLOW=8;
	
	public initillizeUserThread(ChatFrame f,User user,String name){ // TODO I need to change that the top panel will contain a USER object and not a name.
		this.name=name;
		this.f=f;
	}
	
	public void run(){
		@SuppressWarnings("unused")
		boolean approved = false;
			try{
				soc = new Socket("Localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());		
				out.writeObject((int)GETUSERFLOW);
				out.writeObject(name);
				in = new ObjectInputStream(soc.getInputStream());
				if((approved=(boolean)in.readObject())){
					user = (User)in.readObject();
					f.deliver(user); 
				}
				else{
					JOptionPane.showMessageDialog(null, "There was a problem opening the chat.");
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
