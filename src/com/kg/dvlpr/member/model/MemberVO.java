package com.kg.dvlpr.member.model;

import java.util.Date;

public class MemberVO {

	private String userid;
	private String password;
	private String name;
	private String email;
	private String nickname;
	private int enabled;
	private int warningCnt;
	private byte[] profilePic;
	private Date joinDate;
	
	
	public MemberVO(String userId, String password, String name, String email, String nickname, int enabled, int warningCnt,
			byte[] profilePic, Date joinDate) {
		this.userid = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.enabled = enabled;
		this.warningCnt = warningCnt;
		this.profilePic = profilePic;
		this.joinDate = joinDate;
		
		// TODO Auto-generated constructor stub
	}
	public MemberVO() {
		// TODO Auto-generated constructor stub
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getEnabled() {
		return enabled;
	}
	public int getWarningCnt() {
		return warningCnt;
	}
	public void setWarningCnt(int warningCnt) {
		this.warningCnt = warningCnt;
	}
	public byte[] getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
}
