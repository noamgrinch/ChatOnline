package Util;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;


public class User implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private String email;
	private Color color;
	private TreeMap<String,User> friends;
	private boolean status;
	private Date RegistrationDate;
	private static int userID;
	private int ID;
	
	public User(String name,String password,String email){
		userID++;
		this.setID(userID);
		this.name=name;
		this.password=password;
		this.setEmail(email);
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		this.color = new Color(r,g,b);
		this.friends = new TreeMap<String,User>();
		this.status=false;
		this.RegistrationDate = new Date();
	}
	
	public synchronized void addToFriendsList(User friend) {
		this.friends.put(friend.getName(),friend);
	}
	
	public synchronized User removeFriend(User friend) {
		if(this.friends.size()==0 || !this.friends.containsKey(friend.getName())) {
			return this;
		}		
		this.friends.remove(friend.getName());
		return this;
	}
	
	public synchronized ArrayList<User> getFriendsList(){
		
		Collection<User> col = friends.values();
		List<User> result = new ArrayList<User>(col);
		
		return (ArrayList<User>) result;
	}
	
	public synchronized ArrayList<String> getFriendsListKeys(){
		
		Collection<String> col = friends.keySet();
		List<String> result = new ArrayList<String>(col);
		
		return (ArrayList<String>) result;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPassword(String password){
		this.password=password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void addFriend(User friend) {
		if(!friends.containsKey(friend.getName())) {
			friends.put(friend.getName(), friend);
		}
	}
	
	public void setStatus(boolean bool) {
		this.status=bool;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public String getStatusString() {
		if(this.getStatus()){
			return "Online";
		}
		return "Offline";
	}
	
	public Date getRegistrationDate() {
		return this.RegistrationDate;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
