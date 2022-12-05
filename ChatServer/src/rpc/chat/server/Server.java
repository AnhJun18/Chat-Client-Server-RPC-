package rpc.chat.server;
import java.util.ArrayList;
import java.util.List;

import rpc.chat.interfaces.IClient;
import rpc.chat.interfaces.IServer;

public class Server implements IServer {
	
	List<IClient> clients = new ArrayList<>();
	
	/* Phát tin */
	@Override
	public void broadcast(String msg, IClient client,String receiver) {
		if(receiver.equals("ALL")) {
			for (IClient iClient : clients) {
				try {
					if(!iClient.getName().equals(client.getName()))
						iClient.receive("ALL" + ": " + msg);
				} catch (Exception e) {
					this.logout(iClient);
				}
			}
		 }
		else {
			 for (IClient iClient : clients) {
					try {
						if(iClient.getName().equals(receiver))
							iClient.receive(client.getName() + ": " + msg);
					}catch (Exception e) {
						this.logout(iClient);
					}
				}
		 }		
	}
	
	
	/* Đăng nhập */
	@Override
	public void login(IClient client) {
		clients.add(client);
	}
	
	/* Cập nhật thành viên */
	public void updateMember() {
		String listMember = "ALL";
		for (IClient iClient : clients) {
			listMember+="+"+iClient.getName();
		}
		for (IClient iClient : clients) {
			iClient.updateMember(listMember);
		}
	}

	/* Đăng xuất */
	@Override
	public void logout(IClient client) {
		clients.remove(client);

	}

}
