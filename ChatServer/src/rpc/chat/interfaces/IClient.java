package rpc.chat.interfaces;

public interface IClient {
	void empfangen(String msg);
	String gibName();
	void updateMember(String members);
}
