package GUI.Friends;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import CentralLogger.SendLogThread;
import Client.AddFriendRequestThread;
import Client.FriendsTableUpdaterThread;
import Client.RemoveFriendRequestThread;
import GUI.ChatFrame.ChatFrame;
import Util.User;

public class FriendsTablePanel extends JPanel implements ActionListener,MouseListener{
	

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"User" , "Status"};
	private JTable table;
	private JButton add,remove,chat;
	private FriendsTableUpdaterThread updater;
	private DefaultTableCellRenderer renderer;
	private FriendsToolBar toolbar;
	private DefaultTableModel model;
	private TitledBorder tableborder;
	private String pressed;

	
	
	public FriendsTablePanel() {
		try {
		
		//----------------friends table----------------//
		model = new DefaultTableModel() ;
		model.setColumnIdentifiers(columnNames);
		table = new JTable(model);
		table.setDefaultEditor(Object.class, null); //users can't double click and edit the tables.
		table.getTableHeader().setReorderingAllowed(false); //users cannot drag headers.
		table.addMouseListener(this);
		tableborder = BorderFactory.createTitledBorder("Friends list");
		JScrollPane scroll =new JScrollPane(table);
		scroll.setBorder(tableborder);
		table.setBorder(BorderFactory.createEmptyBorder());
		table.setAutoCreateRowSorter(true);
		this.setLayout(new BorderLayout());
		this.add(scroll,BorderLayout.CENTER);
		//----------------friends table----------------//
		 
		//----------------bottom buttons----------------//
		add = new JButton("Add");
		add.setPreferredSize(new Dimension(70,30));
		add.addActionListener(this);
		remove = new JButton("Remove");
		remove.setPreferredSize(new Dimension(90,30));
		remove.addActionListener(this);
		chat = new JButton("Chat");
		chat.setPreferredSize(new Dimension(70,30));
		chat.addActionListener(this);
		JPanel bottom = new JPanel();
		bottom.add(add);
		bottom.add(remove);
		bottom.add(chat);
		this.add(bottom,BorderLayout.SOUTH);
		//----------------bottom buttons----------------//
		toolbar = new FriendsToolBar();
		this.add(toolbar,BorderLayout.NORTH);
		}
		
		  catch(Exception e){
			  new SendLogThread(Level.SEVERE,e).run();
		  }

		

	}
	
	public synchronized void updateFriendsList(Object[][] friendslist) {	
		SwingUtilities.invokeLater(new Runnable(){public void run(){ //All of the table updating threads should happen on the EDT!
			try{
				if(friendslist==null) {
					model = (DefaultTableModel) table.getModel();
					model.setRowCount(0);			
					model.fireTableDataChanged();
					table.revalidate();
				}
				else {
					model = new DefaultTableModel(friendslist,columnNames); 
					table.setModel(model);
					renderer = new DefaultTableCellRenderer();
					renderer.setHorizontalAlignment(JLabel.CENTER);
					for(int i = 0; i < table.getColumnCount(); i++){
						table.getColumnModel().getColumn(i).setCellRenderer(renderer);
					}
					model.fireTableDataChanged();
					for(int j=0;j<table.getRowCount();j++) { // returns to the selected row.
						if(((String)table.getModel().getValueAt(j, 0)).equals(pressed)) {
							table.setRowSelectionInterval(j, j);
						}
					}
				}
			}
			catch(Exception e){
				new SendLogThread(Level.SEVERE,e).start();
			}
		}});

	}
	
	/*public synchronized void update() {
		new FriendsTableUpdaterThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser()).start();
	}*/
	
	public void deliver(User user) {
		((FriendsFrame) SwingUtilities.getWindowAncestor(this)).setUser(user);
		toolbar.setUser(((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser());
		new FriendsTableUpdaterThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser()).start();

	}
	
	
	public void Enable() {
		((FriendsFrame) SwingUtilities.getWindowAncestor(this)).setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent s) {
	  try{
		if(s.getSource() == add) {
			table.clearSelection();
			new AddFriendRequestThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser(),JOptionPane.showInputDialog(((FriendsFrame) SwingUtilities.getWindowAncestor(this)), "Please enter username")).run();
		}
		else if(s.getSource() == chat) {
			int row = table.getSelectedRow();
			int column = table.getSelectedColumn();
			String o = (String)table.getValueAt(row, column);
			new ChatFrame(((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser(),o); 
			table.clearSelection();
		}
		else if(s.getSource() == remove) {
			int row = table.getSelectedRow();
			if (row != -1) {
				pressed = (String)table.getModel().getValueAt(row, 0); // table.getModel().getValueAt(row, 0) = user value as string.
				new RemoveFriendRequestThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser(),pressed).run();
			}
			else {
				String toremove = JOptionPane.showInputDialog(((FriendsFrame) SwingUtilities.getWindowAncestor(this)), "Please enter username");
				if(toremove!=null && !(toremove.trim().length()==0)) {
					new RemoveFriendRequestThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser(),toremove).run();
				}
			}
			table.clearSelection();
		}
	  }
	  catch(ArrayIndexOutOfBoundsException e){
		  //no cell is selected. doesn't effect the program.
		  table.clearSelection();
	  }
	  catch(Exception e){
		  new SendLogThread(Level.SEVERE,e).run();
	  }
	}

	public FriendsTableUpdaterThread getUpdater() {
		return updater;
	}

	public void setUpdater(FriendsTableUpdaterThread updater) {
		this.updater = updater;
	}

	@Override
	public void mouseClicked(MouseEvent s) {
		try{
			JTable table =(JTable) s.getSource();
			Point point = s.getPoint();
			int row = table.rowAtPoint(point);
			if (s.getClickCount() == 1 && table.getSelectedRow() != -1 && row!=-1) {
				pressed = (String)table.getModel().getValueAt(row, 0); // table.getModel().getValueAt(row, 0) = user value as string.
			}
        
			if (s.getClickCount() == 2 && table.getSelectedRow() != -1 && row!=-1) {
				pressed = (String)table.getModel().getValueAt(row, 0); // table.getModel().getValueAt(row, 0) = user value as string.
				new ChatFrame(((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser(),pressed);
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).start();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setUser(User user) {
		((FriendsFrame) SwingUtilities.getWindowAncestor(this)).setUser(user);
		
	}
}
