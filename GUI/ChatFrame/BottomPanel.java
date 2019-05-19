package GUI.ChatFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import CentralLogger.SendLogThread;
import Client.ClientPutThread;
import Util.Chat;
import Util.User;

public class BottomPanel extends JPanel implements MouseListener,KeyListener{
	

	private static final long serialVersionUID = 1L;
	private JButton send;
	private JTextField textfield;
	private String reset;
	private User user;
	private Chat chat;

	
	
	public BottomPanel(User self,Chat chat) {
		GridBagConstraints c = new GridBagConstraints();
		this.chat=chat;
		user = self;
		reset = "";
		send = new JButton("Send");
		send.addMouseListener(this);
		textfield = new JTextField(reset);
		this.setLayout(new GridBagLayout());
		
		//setting up the textfield.
		c.fill = GridBagConstraints.HORIZONTAL; //sticks it to the sides
		c.weightx = 0.5;
		c.gridwidth=2;		
		this.add(textfield,c);
		
		//setting up send button.
		c.gridwidth=1;
		c.weighty = 1;
		this.add(send);
		textfield.addKeyListener(this);
		textfield.requestFocus();
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
			if(this.legalString(textfield.getText())){
				try {
					new ClientPutThread(textfield.getText(),user,chat.getID()).run();
					textfield.setText("");
					textfield.requestFocus();
				}
				catch(Exception ex) {
					new SendLogThread(Level.SEVERE,ex).run();
				}
			}
	}


	@Override
	public void keyPressed(KeyEvent s) {
		if(s.getKeyCode() == KeyEvent.VK_ENTER){
			if(this.legalString(textfield.getText())){
				new ClientPutThread(textfield.getText(),user,chat.getID()).run();
				textfield.setText("");
			}
		}
	}
	
	public void focus(){
		textfield.requestFocus();
	}

	
	public boolean legalString(String str){
		String tmp = new String(str);
		if(tmp.trim().length() == 0 || tmp.isEmpty()){
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	
	}
	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {	
	}
	@Override
	public void mousePressed(MouseEvent e) {	
	}
	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	public void intilizaizeUser(User user) {
		this.user=user;
		
	}

	public User getUser() {
		return user;
	}



}
