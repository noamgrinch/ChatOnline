package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import Util.Storage;
import Util.User;


public class ClientThread extends Thread{
	
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream outob;
	private UsersDB userDB;
	private ChatsDB chatsDB;
	private final int UPDATEFLOW=1;
	private final int PUTFLOW=2;
	private final int LOGINFLOW=3;
	private final int REGFLOW=4;
	private final int ADDFRIENDREQUESTFLOW = 5;
	private final int FRIENDUPDATEFLOW = 6 ;
	private final int REMOVEFRIENDFLOW = 7;
	private final int GETUSERFLOW=8;
	private final int GETSETCHAT = 9;
	private final int LOGOFFFLOW = 10;
	private final int GETINREQUESTSFLOW=11;
	private final int ADDFRIENDFLOW = 12;
	private final int DECLINEFRIENDREQUESTFLOW = 13;
	private final int GETOUTREQUESTSFLOW = 14 ;
	
	
	public ClientThread(Socket s,UsersDB userDB,ChatsDB chatsDB) {
		this.s=s;
		this.userDB=userDB;
		this.chatsDB=chatsDB;
	}
	
	public void run(){
		
		int code;
		try {
			in = new ObjectInputStream(s.getInputStream());
			outob = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			new SendLogThread(Level.SEVERE,e).run();
		}

			try {
			  while(true) {	

				code = (int)in.readObject();
				
				if(code==UPDATEFLOW){
					updater();
				}
				if(code==PUTFLOW){
					put();
				}
				if(code==LOGINFLOW){
					login();
				}
				if(code==REGFLOW){
					registration();
				}
				if(code==ADDFRIENDREQUESTFLOW){
					addfriendreqesut();
				}
				if(code==FRIENDUPDATEFLOW){
					friendupdater();
				}
				if(code==REMOVEFRIENDFLOW){
					removefriend();
				}
				if(code==GETUSERFLOW){
					initillizeuser();
				}
				if(code==GETSETCHAT){
					initillizechat();
				}
				if(code==LOGOFFFLOW){
					logoff();
				}
				if(code==GETINREQUESTSFLOW){
					getinfriendrequests();
				}				
				if(code==ADDFRIENDFLOW){
					confirmfriendrequest();
				}
				
				if(code==DECLINEFRIENDREQUESTFLOW){
					declinefriendrequest();
				}
				
				if(code==GETOUTREQUESTSFLOW){
					getoutfriendrequests();
				}
			  }
				
				
			}
			catch(Exception e) {
				new SendLogThread(Level.SEVERE,e).run();
			}
			finally {
				try {
					in.close();
				}
				catch(Exception e) {
					new SendLogThread(Level.SEVERE,e).run();
				}
			}
		}
	
	private void updater() {
		Storage storage;
		int storagelastline;
		try{
			int chatID = (int)in.readObject();
			storage = chatsDB.getStorageByID(chatID);
			storagelastline = storage.getLastLine();
			outob.writeObject(storagelastline);
			if(storage != null){
				storagelastline = storage.getLastLine();
				int updaterline = (int)in.readObject();
				User user;
				for(;updaterline<storagelastline;updaterline++){
					user = storage.getLineUser(updaterline);
					if(user!=null){
						outob.writeObject(storage.getLineUser(updaterline));
						outob.writeObject(storage.getLineSentence(updaterline));
					}
				}
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	private void put() {
		
		Storage storage;
		String inputsentence;
		User user;
		try{
			int chatID = (int)in.readObject();
			storage = chatsDB.getStorageByID(chatID);
			user=(User)in.readObject();
			inputsentence=(String)in.readObject();
			storage.addString(user, inputsentence);
			outob.writeObject(true);
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
		
		
	}
	
	public void login() {
		User user;
		try{
			String name = (String)in.readObject();
			String password = (String)in.readObject();
			if(userDB.LogIn(name, password)){
				outob.writeObject(true);
				user = userDB.getUser(name);
				outob.writeObject(user); 
			}
			else{
				outob.writeObject(false);
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e);
		}
	}
	
	public void registration() {
		try{
			String name = (String)in.readObject();
			String password = (String)in.readObject();
			String email = (String)in.readObject();
			outob = new ObjectOutputStream(s.getOutputStream());
			outob.writeObject(userDB.registerUserInDB(name, password,email));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e);
		}
	}
	
	public void addfriendreqesut() {
		User user;
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			if(name==null || user == null) {
				throw new IOException("Flow was aborted by user.");
			}
			boolean ok = userDB.exsits(name);
			outob.writeObject(ok);
			if(ok) {
				outob.writeObject(userDB.addFriendRequest(user, name));
			}
		}
		catch(IOException ex) {
			//User canceled before adding. nothing to do here.
			new SendLogThread(Level.WARNING,ex).run();
		}
		
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void friendupdater() {
		User user;
		try{
			user = (User)in.readObject();
			outob = new ObjectOutputStream(s.getOutputStream());
			outob.writeObject(userDB.getFriendsForTable(user));
			outob.writeObject(userDB.getUser(user.getName()));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void removefriend() {
		User user;
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			outob.writeObject(userDB.removeFriend(user, name));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e);
		}
	}
	
	public void initillizeuser() {
		User user;
		try{	
			String name = (String)in.readObject();
			if(userDB.exsits(name)){
				outob.writeObject(true);
				user = userDB.getUser(name);
				outob.writeObject(user); 
			}
			else{
				outob.writeObject(false);
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void initillizechat() {
		try{	
			User user1 = (User)in.readObject();
			User user2 = (User)in.readObject();
			outob.writeObject(chatsDB.getChat(user1, user2));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void logoff() {
		String username;
		try{
			username = (String)in.readObject();
			if(userDB.LogOff(username)) {
				outob.writeObject(true);
			}
			else{
				outob.writeObject(false);
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void getinfriendrequests() {
		User user;
		try{
			user = (User)in.readObject();
			outob.writeObject(userDB.getInFriendsRequestsForTable(user));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void confirmfriendrequest() {
		User user;
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			boolean ok = userDB.exsits(name);
			outob.writeObject(ok);
			if(ok) {
				outob.writeObject(userDB.addFriend(user, name));
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void declinefriendrequest() {
		User user;
		try{
			String name = (String)in.readObject();
			user = (User)in.readObject();
			boolean ok = userDB.exsits(name);
			outob.writeObject(ok);
			if(ok) {
				outob.writeObject(userDB.declineFriendRequest(user, name));
			}
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}
	}
	
	public void getoutfriendrequests() {
		User user;
		try{
			user = (User)in.readObject();
			outob.writeObject(userDB.getOutFriendsRequestsForTable(user));
		}
		catch(Exception e){
			new SendLogThread(Level.SEVERE,e).run();
		}

	}
	

}
