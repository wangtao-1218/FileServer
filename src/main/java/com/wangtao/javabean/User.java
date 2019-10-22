package com.wangtao.javabean;

import java.sql.Timestamp;

public class User {
	 private int id;
	 private String type;	
	 private String realname;
	 private String uuidname; 
	 private Timestamp time;
	 private String savepath;
	public int getId() {
			return id;
		}
	public void setId(int id) {
			this.id = id;
		}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getUuidname() {
		return uuidname;
	}
	public void setUuidname(String uuidname) {
		this.uuidname = uuidname;
	}
	public Timestamp gettime() {
		return time;
	}
	public void settime(Timestamp uploadtime) {
		this.time = uploadtime;
	}
	public String getSavepath() {
		return savepath;
	}
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	
	
}
