package com.divatt.designer.entity;

import java.util.List;

public class UserList {

	private List<UserProfile> userList;

	public UserList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserList(List<UserProfile> userList) {
		super();
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "UserList [userList=" + userList + "]";
	}

	public List<UserProfile> getUserList() {
		return userList;
	}

	public void setUserList(List<UserProfile> userList) {
		this.userList = userList;
	}
	
}
