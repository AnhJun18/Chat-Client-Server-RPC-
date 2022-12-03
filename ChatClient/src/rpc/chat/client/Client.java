package rpc.chat.client;

import rpc.chat.interfaces.IClient;

public class Client implements IClient {
	
	String name;
	boolean newMsg;
	String msg;
	
	public Client(String name) {
		this.name = name;
		this.msg=null;
		this.newMsg=false;
	}

	@Override
	public void empfangen(String msg) {
		System.out.println(msg);
		this.msg=msg;
		setStatus(true);
	}

	@Override
	public String gibName() {
		return name;
	}
	
	public boolean gibStatus() {
		return newMsg;
	}
	
	public void setStatus(boolean st) {
		this.newMsg=st;
	}
	
	public String gibMsg() {
		return msg;
	}
	


}
