package rpc.chat.server;
import java.util.ArrayList;
import java.util.List;

import rpc.chat.interfaces.IClient;
import rpc.chat.interfaces.IServer;

public class Server implements IServer {
	
	List<IClient> clients = new ArrayList<>();

	@Override
	//phát tin
	public void broadcast(String msg, IClient client) {
		
		for (IClient iClient : clients) {
			// �ber Leiche gehen und die dann "beerdigen"
			try {
				iClient.empfangen(client.gibName() + " viết: " + msg);
			} catch (Exception e) {
				this.abmelden(iClient);
			}
		}
	}


	//dăng nhập
	@Override
	public void anmelden(IClient client) {
		clients.add(client);

	}

	//đăng xuất
	@Override
	public void abmelden(IClient client) {
		clients.remove(client);

	}

}
