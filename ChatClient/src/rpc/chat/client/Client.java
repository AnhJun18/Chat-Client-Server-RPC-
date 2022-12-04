package rpc.chat.client;
import javax.swing.text.Utilities;

import rpc.chat.interfaces.IClient;

public class Client implements IClient {
	
	String name;
	boolean newMember=false;
	String[] listMember=null;
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
	
	
	@Override
	public void updateMember(String clients) {
		System.out.println(clients);
		listMember=clients.split("\\+");
		newMember=true;
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
	
	public String[] getMember() {
		return listMember;
	}
	
	public void setStatusMember(boolean st) {
		this.newMember=st;
	}
	
	public boolean getStatusMember() {
		return newMember;
	}

}
