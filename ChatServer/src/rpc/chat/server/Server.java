package rpc.chat.server;
import java.util.ArrayList;
import java.util.List;

import rpc.chat.interfaces.IClient;
import rpc.chat.interfaces.IServer;

public class Server implements IServer {
	
	List<IClient> clients = new ArrayList<>();

	@Override
	//phát tin
	public void broadcast(String msg, IClient client,String receiver) {
		if(receiver.equals("ALL")) {
			for (IClient iClient : clients) {
				try {
					if(!iClient.gibName().equals(client.gibName()))
						iClient.empfangen(client.gibName() + ": " + msg);
				} catch (Exception e) {
					this.abmelden(iClient);
				}
			}
		 }
		else {
			 for (IClient iClient : clients) {
					try {
						if(iClient.gibName().equals(receiver))
							iClient.empfangen(client.gibName() + ": " + msg);
					}catch (Exception e) {
						this.abmelden(iClient);
					}
				}
		 }
		
	}
	public String getAllMember() {
		String listMember = "";
		for (IClient iClient : clients) 
			listMember.concat("+"+iClient.gibName());
		return listMember;
	}


	//dăng nhập
	@Override
	public void anmelden(IClient client) {
		clients.add(client);
	}
	
	public void updateMember() {
		String listMember = "ALL";
		for (IClient iClient : clients) {
			listMember+="+"+iClient.gibName();
		}
		for (IClient iClient : clients) {
			iClient.updateMember(listMember);
		}
	}

	//đăng xuất
	@Override
	public void abmelden(IClient client) {
		clients.remove(client);

	}

}
