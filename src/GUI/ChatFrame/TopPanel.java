package GUI.ChatFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import CentralLogger.SendLogThread;
import Util.User;


public class TopPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JTextPane textpane;
	private TitledBorder border;
	private JScrollPane scroll;

	
	public TopPanel(String otherguy) {
		
		border = BorderFactory.createTitledBorder(otherguy);
		textpane = new JTextPane();
		DefaultCaret caret = (DefaultCaret)textpane.getCaret(); //sticks the scroll pane all the way down.
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);//sticks the scroll pane all the way down.
		textpane.setEditable(false);	
		scroll = new JScrollPane(textpane);
		scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//border = BorderFactory.createTitledBorder(otherguy);
		//scroll.setBorder(border);
		scroll.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.add(scroll,BorderLayout.CENTER);
		validate();	

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	
	public synchronized void setHistory(String his) {

	}
	
	public synchronized void addLine(User user,String sentence){
		try {
			SimpleAttributeSet bold = new SimpleAttributeSet();
			StyleConstants.setBold(bold, true);
			StyleConstants.setForeground(bold,user.getColor());
			SimpleAttributeSet normal = new SimpleAttributeSet();
			textpane.getDocument().insertString(textpane.getDocument().getLength(), user.getName() + ":\n", bold);
			textpane.getDocument().insertString(textpane.getDocument().getLength() - 1, sentence, normal);
			this.revalidate();
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e);
		}
	}


	

}
