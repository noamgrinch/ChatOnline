package GUI.Friends;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FriendsTablePanel extends JPanel implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"User" , "Status"};
	private JTable table;
	private JButton add,remove,chat;
	private FriendsTableUpdaterThread updater;
	private DefaultTableCellRenderer renderer;
	
	
	public FriendsTablePanel() {
		
		//----------------friends table----------------//
		DefaultTableModel model = new DefaultTableModel() ;
		model.setColumnIdentifiers(columnNames);
		table = new JTable(model);
		table.setDefaultEditor(Object.class, null); //users can't double click and edit the tables.
		table.getTableHeader().setReorderingAllowed(false); //users cannot drag headers.
		table.setCellSelectionEnabled(true);
		TitledBorder tableborder = BorderFactory.createTitledBorder("Friends list");
		JScrollPane scroll =new JScrollPane(table);
		scroll.setBorder(tableborder);
		table.setBorder(BorderFactory.createEmptyBorder());
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
		

		

	}
	
	public synchronized void updateFriendsList(Object[][] friendslist) {
		if(friendslist==null) {
			//Object[][] empty = new Object[0][0];
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);			
			model.fireTableDataChanged();
			table.revalidate();
		}
		else {
			DefaultTableModel model = new DefaultTableModel(friendslist,columnNames); // data should be a matrix with pairs of (name,status). should get it from DB.
			table.setModel(model);
			renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(JLabel.CENTER);
			for(int i = 0; i < table.getColumnCount(); i++){
			    table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			model.fireTableDataChanged();
		}

	}
	
	public synchronized void update() {
		new FriendsTableUpdaterThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser()).start();
	}
	
	public void deliver(User user) {
		((FriendsFrame) SwingUtilities.getWindowAncestor(this)).setUser(user);
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
			table.clearSelection();
			new RemoveFriendRequestThread(this,((FriendsFrame) SwingUtilities.getWindowAncestor(this)).getUser(),JOptionPane.showInputDialog(((FriendsFrame) SwingUtilities.getWindowAncestor(this)), "Please enter username")).run();
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
}
