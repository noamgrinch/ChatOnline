package Server;

import CentralLogger.CentralLogger;

public class RunServers {
	
	public static void main(String[] args){
		Server s = new Server(8888);
		CentralLogger cl = new CentralLogger();
		Thread server = new Thread(s);
		Thread centrallogger = new Thread(cl);
		server.start();
		centrallogger.start();
	}

}
