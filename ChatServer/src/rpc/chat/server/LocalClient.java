package rpc.chat.server;

import java.util.ArrayList;

import rpc.chat.interfaces.IClient;

public class LocalClient implements IClient {
	String name;

	public LocalClient(String name) {
		this.name = name;
	}
	
	@Override
	public void empfangen(String msg) {
		System.out.println(name + " h�rt : " + msg);

	}

	@Override
	public String gibName() {
		return name;
	}

	@Override
	public void updateMember(String clients) {
		// TODO Auto-generated method stub
		
	}

}
