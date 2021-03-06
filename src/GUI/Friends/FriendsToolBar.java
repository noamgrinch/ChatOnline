package GUI.Friends;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.MenuSelectionManager;

import CentralLogger.SendLogThread;
import GUI.FriendsRequest.FriendsRequestWindow;
import Util.User;

public class FriendsToolBar extends JPanel implements MouseListener,MouseMotionListener,ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JMenu edit,background,about;
	private JMenuBar bar;
	private JMenuItem friendsrequests,bgcolor,bgpicture,seeabout;
	private ArrayList<JMenu> menuscontainer;
	private ArrayList<JMenuItem> menuitemsscontainer;
	private User user;
	
	
	
	public FriendsToolBar(){
		menuscontainer = new ArrayList<JMenu>();
		menuitemsscontainer = new ArrayList<JMenuItem>();
		bar = new JMenuBar();
		//edit menu.
		edit = new JMenu("Edit");
		menuscontainer.add(edit);
		edit.addMouseListener(this);
		friendsrequests = new JMenuItem("Friends requests");
		friendsrequests.addActionListener(this);
		menuitemsscontainer.add(friendsrequests);
		edit.add(friendsrequests);
		edit.addSeparator();
		background = new JMenu("Set background");
		menuscontainer.add(background);
		bgcolor = new JMenuItem("Background color");
		menuitemsscontainer.add(bgcolor);
		bgpicture = new JMenuItem("Background picture");
		menuitemsscontainer.add(bgpicture);
		background.add(bgcolor);
		background.add(bgpicture);
		edit.add(background);		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		bar.add(edit);
		this.add(bar);
		//about menu.
		about = new JMenu("About");
		about.addMouseListener(this);
		seeabout = new JMenuItem("About this");
		seeabout.addActionListener(this);
		about.add(seeabout);
		menuitemsscontainer.add(seeabout);
		bar.add(about);
	}


	@Override
	public void mouseClicked(MouseEvent s) {
		if(s.getSource() == edit){
			edit.doClick();
		}
		else if(s.getSource() == about){
			about.doClick();
		}
		
	}


	@Override
	public void mouseEntered(MouseEvent s) {
		if(s.getSource() == edit){
			edit.setArmed(true);
		}
		else if(s.getSource() == about){
			about.setArmed(true);
		}

	}


	@Override
	public void mouseExited(MouseEvent s) {
		if(s.getSource() == edit ){
			edit.setArmed(false);
			if(isExitedComponent(s)){			
				MenuSelectionManager.defaultManager().clearSelectedPath(); //unclicks the jmenu.
			}
		}
		else {
			if(s.getSource() == about ){
				about.setArmed(false);
				if(isExitedComponent(s))
					MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent s) {

		
	}
	
	public boolean isExitedComponent(MouseEvent s){


		for(JMenuItem m: menuitemsscontainer){
			if(s.getSource()==m){
				return true;
			}
		}
		
		return false;
	}


	@Override
	public void actionPerformed(ActionEvent s) {
		if(s.getSource() == friendsrequests){
			new FriendsRequestWindow(user);
		}
		if(s.getSource() == seeabout){
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    try {
					Desktop.getDesktop().browse(new URI("https://github.com/noamgrinch/ChatOnline"));
				} catch (IOException | URISyntaxException e) {
					new SendLogThread(Level.SEVERE,e).run();
				}
			}
		}
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}
