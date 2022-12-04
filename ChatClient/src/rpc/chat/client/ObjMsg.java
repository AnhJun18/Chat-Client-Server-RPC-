package rpc.chat.client;

import rpc.chat.interfaces.IObjMsg;

public class ObjMsg implements IObjMsg{
 public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
 private String from;
 private String to;
 private String msg;
 
}
