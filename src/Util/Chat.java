package Util;

import java.io.Serializable;
import java.util.Vector;

public class Chat implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private Storage storage;
	private Vector<User> participents;
	private int ID;
	private static int chatID;
	
	public Chat(User user1, User user2){
		chatID++;
		this.setID(chatID);
		participents = new Vector<User>();
		participents.add(user1);
		participents.add(user2);
		storage = new Storage();	
	}



	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public User getUser1(){
		return participents.get(0);
	}
	
	public User getUser2(){
		return participents.get(1);
	}



	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}

}
