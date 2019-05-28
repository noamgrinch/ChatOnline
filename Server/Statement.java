package Server;
import java.io.Serializable;

import Util.User;

public class Statement implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int line;
	private String sentence;
	private User user;

	
	public Statement(int line,User user,String sentence) {
		this.setLine(0) ;
		this.user=user;
		this.sentence=sentence; 
	}
	
	public User getUser() {
		return user;
	}
	
	public String getSentence() {
		return sentence;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

}
