package rpc.chat.interfaces;

public interface IServer {
	void broadcast(String msg, IClient client, String receiver);
	void login(IClient client) throws CannotProceedException;
	void logout(IClient client);
}
