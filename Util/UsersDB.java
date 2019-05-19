package Util;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.regex.Pattern;

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
		a.addFriend(b);
	}
	
	public boolean exsits(String name) {
		return users.containsKey(name);
	}
	
	public User removeFriend(User user,String friendtoremove) {
		if(!users.containsKey(friendtoremove)) {
			return user;
		}
		User friend = users.get(friendtoremove);
		
		return user.removeFriend(friend);
	}
	
	public Object[][] getFriendsForTable(User user){ 
		ArrayList<User> friends = (ArrayList<User>) user.getFriendsList();
		Object [][] result = new Object[friends.size()][2];
		for(int i=0;i<friends.size();i++) {
			result[i][0] = friends.get(i).getName();
			result[i][1] = friends.get(i).getStatusString();
		}
		
		return result;
		
	}
	
	public int registerUserInDB(String name,String password,String email){ 
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
	
	
	public boolean LogIn(String name,String password){
		if(!users.containsKey(name)){
			return false;
		}
		User tmp = this.getUser(name);
		tmp.setStatus(true);
		return true;
	}
	
	public User addFriend(User user,String usertobeadded) {
		User friend = users.get(usertobeadded);
		if(friend==null) {
			return user;
		}
		user.addToFriendsList(friend);
		friend.addToFriendsList(user);
		return user;
		
	}
	
	public boolean userFormat(String name,String password,String email){
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

	public User getUser(String name) {		
		return users.get(name);	
	}
	

}
