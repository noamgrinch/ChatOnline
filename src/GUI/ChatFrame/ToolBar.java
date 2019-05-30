package GUI.ChatFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;



public class ToolBar extends JPanel implements MouseListener,MouseMotionListener,ActionListener{ //currently not functional.


	private static final long serialVersionUID = 1L;
	private JMenu edit,background,about;
	private JMenuBar bar;
	private JMenuItem text,bgcolor,bgpicture,seeabout;
	private ArrayList<JMenu> menuscontainer;
	private ArrayList<JMenuItem> menuitemsscontainer;
	
	
	
	public ToolBar(){
		menuscontainer = new ArrayList<JMenu>();
		menuitemsscontainer = new ArrayList<JMenuItem>();
		bar = new JMenuBar();
		//edit menu.
		edit = new JMenu("Edit");
		menuscontainer.add(edit);
		edit.addMouseListener(this);
		text = new JMenuItem("Text");
		text.addActionListener(this);
		menuitemsscontainer.add(text);
		edit.add(text);
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
		/*if((s.getSource() != edit && edit.isSelected()) || (s.getSource() !=about && about.isSelected())){
			MenuSelectionManager.defaultManager().clearSelectedPath();

		}*/
		
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
		if(s.getSource() == text){
			//new TextWindow();
		}
	}

}
