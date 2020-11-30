package org.bean;

public class Error {
	private int status;
	private String msg;
	public Error() {
		super();
	}
	public Error(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Error [status=" + status + ", msg=" + msg + "]";
	}
}
