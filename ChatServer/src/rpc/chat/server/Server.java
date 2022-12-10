package rpc.chat.server;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameAlreadyBoundException;

import rpc.chat.interfaces.IClient;
import rpc.chat.interfaces.IServer;

public class Server implements IServer {

	List<IClient> clients = new ArrayList<>();

	/* Phát tin */
	@Override
	public void broadcast(String msg, IClient client, String receiver) {
		System.out.println(client.getName()+"to "+receiver+": "+msg);
		if (receiver.equals("ALL")) {
			for (IClient iClient : clients) {
				try {
					if (!iClient.getName().equals(client.getName())) {
						String a= URLEncoder.encode(msg,"UTF-8");
						iClient.receive("ALL" + ":"+ client.getName()+":"+ a);
				}} catch (Exception e) {
					this.logout(iClient);
				}
			}
		} else {
			for (IClient iClient : clients) {
				try {
					if (iClient.getName().equals(receiver))
						iClient.receive(client.getName() + ": " + msg);
				} catch (Exception e) {
					this.logout(iClient);
				}
			}
		}
	}

	/* Đăng nhập */
	@Override
	public void login(IClient client) throws NameAlreadyBoundException {
		for (IClient iClient : clients)
			if(iClient.getName().equals(client.getName())) {
				throw new NameAlreadyBoundException();
			}
				
		clients.add(client);
	}

	/* Cập nhật thành viên */
	public void updateMember() {
		String listMember = "ALL";
		for (IClient iClient : clients) {
			listMember += "+" + iClient.getName();
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
