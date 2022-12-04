package rpc.chat.interfaces;

public interface IServer {
	void broadcast(String msg, IClient client, String receiver);
	void anmelden(IClient client);
	void abmelden(IClient client);
}
