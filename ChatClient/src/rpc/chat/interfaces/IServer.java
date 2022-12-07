package rpc.chat.interfaces;

public interface IServer {
	void broadcast(String msg, IClient client, String receiver);
	void login(IClient client) ;
	void logout(IClient client);
}
