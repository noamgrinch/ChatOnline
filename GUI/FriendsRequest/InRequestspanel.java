package GUI.FriendsRequest;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Client.ClientGetInFriendRequestsThread;
import Util.User;

public class InRequestspanel extends JPanel implements MouseListener{
	

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"User" , "Confirm/Decilne"};
	private User user;
	private JTable table;
	private DefaultTableModel model;
	private TitledBorder tableborder;
	private DefaultTableCellRenderer renderer;

	public InRequestspanel(User user) {
		this.setUser(user);
		model = new DefaultTableModel() ;
		model.setColumnIdentifiers(columnNames);
		table = new JTable(model);
		new ClientGetInFriendRequestsThread(this,user).run();
		table.setDefaultEditor(Object.class, null); //users can't double click and edit the tables.
		table.getTableHeader().setReorderingAllowed(false); //users cannot drag headers.
		tableborder = BorderFactory.createTitledBorder("Incoming requests");
		JScrollPane scroll =new JScrollPane(table);
		scroll.setBorder(tableborder);
		table.setBorder(BorderFactory.createEmptyBorder());
		table.addMouseListener(this);
		this.setLayout(new BorderLayout());
		this.add(scroll,BorderLayout.CENTER);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public synchronized void updateFriendsInRequests(Object[][] friendslist) {
		if(friendslist==null) {
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
        JTable table =(JTable) s.getSource();
        Point point = s.getPoint();
        int row = table.rowAtPoint(point);
        if (s.getClickCount() == 2 && table.getSelectedRow() != -1 && row!=-1) {
            new ConfirmDeclineFriendFrame(this,this.user,(String)table.getModel().getValueAt(row, 0)); // table.getModel().getValueAt(row, 0) = user value as string.
        }
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
