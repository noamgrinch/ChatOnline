package GUI.ChatFrame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import CentralLogger.SendLogThread;
import Client.TopPanelUpdaterThread;
import Client.initillizeUserThread;
import Util.Chat;
import Util.User;

public class ChatFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private User self;
	private User otherguy;
	private Chat chat;
	private final int GETSETCHAT = 9;
	private Socket soc;
	private ObjectOutputStream outob;
	private ObjectInputStream in;
	private BufferedImage img;
	
	
	public ChatFrame(User self,String friend) {
		super(friend);
		try{
			new initillizeUserThread(this,otherguy,friend).run(); 
			this.self=self;
			this.setChat();
			ClientPanel p = new ClientPanel(self,otherguy,chat);
			this.add(p);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			img = getImage(); //sets up thumbnail.
			this.setIconImage(img);
			this.setSize(400, 400);
			this.setVisible(true);
			TopPanelUpdaterThread up = new TopPanelUpdaterThread(p); 
			up.start();
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void deliver(User otherguy){
		this.otherguy=otherguy;
	}
	
	public void setChat(){
		try{
			soc = new Socket("Localhost",8888);
			outob = new ObjectOutputStream(soc.getOutputStream());		
			outob.writeObject((int)GETSETCHAT);
			outob.writeObject(self);
			outob.writeObject(otherguy);
			in = new ObjectInputStream(soc.getInputStream());
			this.chat = (Chat)in.readObject();
		}
		catch(Exception ex) {
			new SendLogThread(Level.SEVERE,ex).run();
		}
		finally {
			try {
				outob.close();
				soc.close();
				in.close();
			} catch (Exception e1) {
				new SendLogThread(Level.SEVERE,e1).run();
			}
		}
	}
	
	public Chat getChat(){
		return this.chat;
	}
	
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File("chat-thumbnail.png"));
        } catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }

        return null;
    }
	
}

