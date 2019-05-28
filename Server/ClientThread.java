package Server;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import CentralLogger.SendLogThread;


public class ClientThread extends Thread{
	
	private Socket s;
	private ObjectInputStream in;
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

			try {
				in = new ObjectInputStream(s.getInputStream());
				int code = (int)in.readObject();
				
				if(code==UPDATEFLOW){
					new UpdateThread(s,chatsDB,in).run();
				}
				if(code==PUTFLOW){
					new ServerPutThread(s,chatsDB,in).run();
				}
				if(code==LOGINFLOW){
					new ServerLoginThread(s,userDB,in).run();
				}
				if(code==REGFLOW){
					new ServerRegThread(s,userDB,in).run();
				}
				if(code==ADDFRIENDREQUESTFLOW){
					new ServerAddFriendThread(s,userDB,in).run();
				}
				if(code==FRIENDUPDATEFLOW){
					new ServerFriendUpdaterThread(s,userDB,in).run();
				}
				if(code==REMOVEFRIENDFLOW){
					new ServerRemoveFriendThread(s,userDB,in).run();
				}
				if(code==GETUSERFLOW){
					new ServerInitillizeUserThread(s,userDB,in).run();
				}
				if(code==GETSETCHAT){
					new ServerInitillizeChatThread(s,chatsDB,in).run();
				}
				if(code==LOGOFFFLOW){
					new ServerUserLogOffThread(s,userDB,in).run();
				}
				if(code==GETINREQUESTSFLOW){
					new ServerGetInFriendRequestsThread(s,userDB,in).run();
				}				
				if(code==ADDFRIENDFLOW){
					new ServerConfirmFriendRequestsThread(s,userDB,in).run();
				}
				
				if(code==DECLINEFRIENDREQUESTFLOW){
					new ServerDeclineFriendRequestsThread(s,userDB,in).run();
				}
				
				if(code==GETOUTREQUESTSFLOW){
					new ServerGetOutFriendRequestsThread(s,userDB,in).run();
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

}
