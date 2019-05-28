package Util;
import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import Server.Statement;


public class Storage implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private static int line;
	private Vector<Statement> history;
	
	public Storage() {
		line = 0;
		history = new Vector<Statement>();
	}
	
	public synchronized void addString(User user,String sentence) { 
		Statement state = new Statement(line,user,sentence);
		this.history.add(state);
		line++;
	}
	
	public synchronized Vector<Statement> getStorage(){
		return history;
	}
	
	public synchronized int getLastLine(){
		return line;
	}
	
	public synchronized User getLineUser(int line){  
		try{
			if(history.get(line) != null){
				return history.get(line).getUser();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			new SendLogThread(Level.SEVERE,e).run();
		}
		
		return null;
	}
	
	public synchronized String getLineSentence(int line){
		try{
			if(history.get(line) != null){
				return  history.get(line).getSentence();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			new SendLogThread(Level.SEVERE,e).run();
		}
		
		return null;
	}

}
