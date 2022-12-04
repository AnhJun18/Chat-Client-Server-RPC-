package rpc.chat.interfaces;

import rpc.chat.client.ObjMsg;

public interface IClient {
	void empfangen(String msg);
	void sendMsg(ObjMsg obj);
	String gibName();
	void updateMember(String clients);
}
