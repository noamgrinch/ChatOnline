package Util;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import CentralLogger.SendLogThread;


public class UsersDB {
	
	private TreeMap<String,User> users;
	private final int MAXNAMELENGTH = 10;
	private final int SUCCESSREG = 0;
	private final int EXSITSINDB = 1;
	private final int INVALIDINPUT = 2;
	
	
	public UsersDB(){
		users = new TreeMap<String,User>();
		User a = new User("a","a","a@a.com");
		User b = new User("b","b","b@b.com");
		users.put("a",a);
		users.put("b",b);
	}
	
	public synchronized boolean exsits(String name) {
		return users.containsKey(name);
	}
	
	public synchronized boolean LogOff(String  username) {
		if(!users.containsKey(username)) {
			return false;
		}
		User user = users.get(username);
		user.setStatus(false);
		return true;
		
	}
	
	public synchronized boolean declineFriendRequest(User user,String friend) {
		User fr = users.get(friend);
		user = this.users.get(user.getName());
		if(fr==null) {
			return false;
		}
		user.removeInFriendRequest(friend);
		user.removeOutFriendRequest(friend);
		fr.removeOutFriendRequest(user.getName());
		fr.removeInFriendRequest(user.getName());	
		return true;
	}
	
	public synchronized User removeFriend(User user,String friendtoremove) {
		user = this.users.get(user.getName());
		if(!users.containsKey(friendtoremove)) {
			return user;
		}
		User friend = users.get(friendtoremove);
		
		return user.removeFriend(friend);
	}
	
	public synchronized Object[][] getFriendsForTable(User user){ 
		user = this.users.get(user.getName());
		ArrayList<User> friends = (ArrayList<User>) user.getFriendsList();
		Object [][] result = new Object[friends.size()][2];
		for(int i=0;i<friends.size();i++) {
			result[i][0] = friends.get(i).getName();
			result[i][1] = friends.get(i).getStatusString();
		}
		
		return result;
		
	}
	
	public synchronized Object[][] getInFriendsRequestsForTable(User user){ 
		user = this.users.get(user.getName());
		TreeSet<String> friends = (TreeSet<String>) user.getInReqeustsFriendsList();
		Object [][] result = new Object[friends.size()][2];
		int i=0;
		for(String fr: friends) {
			result[i][0] = fr;
			result[i][1] = ("Confirm/Decline");
			i++;
		}
		
		return result;
		
	}
	
	public synchronized int registerUserInDB(String name,String password,String email){ 
		if(users.containsKey(name)){
			return EXSITSINDB;
		}
		if(!userFormat(name,password,email)){
			return INVALIDINPUT;
		}
		users.put(name,new User(name,password,email));
		new SendLogThread(Level.INFO,new Exception("User: " + name + " has registered successfuly.")).run();
		return SUCCESSREG;
	}
	
	
	public synchronized boolean LogIn(String name,String password){
		if(!users.containsKey(name)){
			return false;
		}
		User tmp = this.getUser(name);
		tmp.setStatus(true);
		return true;
	}
	
	public synchronized boolean addFriend(User user,String usertobeadded) {
		User friend = users.get(usertobeadded);
		user = this.users.get(user.getName());
		if(friend==null) {
			return false;
		}
		user.addToFriendsList(friend);
		user.removeInFriendRequest(usertobeadded);
		user.removeOutFriendRequest(usertobeadded);
		friend.addToFriendsList(user);
		friend.removeOutFriendRequest(user.getName());
		friend.removeInFriendRequest(user.getName());	
		return true;		
	}
	
	public synchronized User addFriendRequest(User user,String usertobeadded) {
		User friend = users.get(usertobeadded);
		if(friend==null) {
			return user;
		}
		user.addOutFriendRequest(friend.getName());
		friend.addInFriendRequest(user.getName());
		return user;		
	}
	
	public synchronized boolean userFormat(String name,String password,String email){
		String tmpname = new String(name);
		String tmppassword = new String(password);
		
		if(tmpname.contains(" ") || tmpname.contains("\t") || tmpname.isEmpty() || tmppassword.isEmpty() || tmppassword.contains(" ") || tmppassword.contains("\t") || tmpname.length()>MAXNAMELENGTH){
			return false;
		}
		
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 
                  
        Pattern pat = Pattern.compile(emailRegex); 
        
        return pat.matcher(email).matches(); 
		
	}

	public synchronized User getUser(String name) {		
		return users.get(name);	
	}
	

}
