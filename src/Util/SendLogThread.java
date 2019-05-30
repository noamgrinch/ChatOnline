package Util;
/**@author Noam Greenshtain
 * Thread which is fired to initiate a log to be written to the file.
 */

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;

import javax.swing.JOptionPane;

public class SendLogThread extends Thread{
	

	private Socket Logsoc;
	private ObjectOutputStream outob;
	private Level level;
	private Exception e;
	
	public SendLogThread(Level level,Exception e){
		this.level=level;
		this.e=e;
	}
	
	public void run(){
		try {
			Logsoc = new Socket("Localhost",7777);
			outob = new ObjectOutputStream(Logsoc.getOutputStream());
			outob.writeObject(level);
			outob.writeObject(e);

		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		finally{
			try {
				outob.close();
				
			}
			catch (ConnectException ex) {
				System.exit(0);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
