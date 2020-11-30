package org.bean;

import java.util.Map;

public class DataJson {
	private int status;
	private Map<String,?> map;
	public DataJson() {
		super();
	}
	public DataJson(int status, Map<String, ?> map) {
		super();
		this.status = status;
		this.map = map;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Map<String, ?> getMap() {
		return map;
	}
	public void setMap(Map<String, ?> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "DataJson [status=" + status + ", map=" + map + "]";
	}
}
 