package Util;

import java.util.Map;
import java.util.TreeMap;

public class ChatsDB {
	
	private TreeMap<Integer,Chat> chats;
	
	public ChatsDB(){
		chats = new TreeMap<Integer,Chat>();
	}
	
	public synchronized void addChatUsers(User user1,User user2){
		Chat tmp = new Chat(user1,user2);
		chats.put(tmp.getID(),tmp);
	}
	
	public synchronized void addChat(Chat chat){
		chats.put(chat.getID(),chat);
	}
	
	public synchronized Chat getChat(User user1,User user2){ 
		
		for(Map.Entry<Integer,Chat> entry : chats.entrySet()){
			Chat tm = entry.getValue();
			if((tm.getUser1().getID()==user1.getID()&&tm.getUser2().getID()==user2.getID()) || (tm.getUser2().getID()==user1.getID()&&tm.getUser1().getID()==user2.getID())){
				return tm;
			}
		}
		Chat tmp = new Chat(user1,user2);
		this.addChat(tmp);
		return tmp;
	}
	
	public synchronized Chat getChatByID(User user1,User user2,int chatID){ 	
		if(chats.containsKey(chatID)){
			return chats.get(chatID);
		}
		Chat tmp = new Chat(user1,user2);
		this.addChat(tmp);
		return tmp;
	}
	
	public synchronized Storage getStorageByID(int chatID){
		if(chats.containsKey(chatID)){
			return chats.get(chatID).getStorage();
		}
		
		return null;
	}
	
	

}
