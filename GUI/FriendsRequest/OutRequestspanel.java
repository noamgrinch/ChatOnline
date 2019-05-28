package GUI.FriendsRequest;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Util.User;

public class OutRequestspanel extends JPanel{


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"User" , "Confirm/Decilne"};
	private User user;
	private JTable table;
	private DefaultTableModel model;
	private TitledBorder tableborder;
	private DefaultTableCellRenderer renderer;

	public OutRequestspanel(User user) {
		this.setUser(user);
		setTable(new JTable());
		model = new DefaultTableModel() ;
		model.setColumnIdentifiers(columnNames);
		table = new JTable(model);
		table.setDefaultEditor(Object.class, null); //users can't double click and edit the tables.
		table.getTableHeader().setReorderingAllowed(false); //users cannot drag headers.
		tableborder = BorderFactory.createTitledBorder("Outgoing requests");
		JScrollPane scroll =new JScrollPane(table);
		scroll.setBorder(tableborder);
		table.setBorder(BorderFactory.createEmptyBorder());
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

}
