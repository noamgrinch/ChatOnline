package Client;


import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import GUI.ChatFrame.ClientPanel;
import Util.User;

public class TopPanelUpdaterThread extends Thread{
	
	private final int UPDATEFLOW=1;
	private Socket soc;
	private ClientPanel pan;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int line;
	private int linetoread;
	private User user;
	
	public TopPanelUpdaterThread(ClientPanel pan) { 
		this.pan=pan;	
		this.line = 0;
		linetoread=0;
	}
	
	public void run() {
		while(true) {
			try {
				sleep(100);
				soc = new Socket("localhost",8888);
				out = new ObjectOutputStream(soc.getOutputStream());
				out.writeObject(UPDATEFLOW);
				out.writeObject(pan.getChat().getID());
				in = new ObjectInputStream(soc.getInputStream());
				linetoread=(int)in.readObject();
				out.writeObject(line);
				for(;line<linetoread;line++){
					try{
					user = (User)in.readObject();
					pan.getTop().addLine(user,(String)in.readObject());
					}
					catch(EOFException e){
						break;
					}
				}
				soc.close();
				out.close();
				in.close();
			}
			catch(Exception ex) {
				new SendLogThread(Level.SEVERE,ex).run();
			}
		}

	}

}
