package com.kg.dvlpr.member.dao;

import java.util.ArrayList;

import com.kg.dvlpr.member.model.MemberVO;

public interface IMemberDAO {

	void insertMember(MemberVO mem);

	String confirmId(String userid);

	String confirmNick(String nickname);

	int loginCheck(String userid, String password);

	void updateMember(MemberVO mem);
	
	void updatePassword(String userid, String password);

	void updateMemberEnabled(MemberVO mem);

	String getPassword(String userid);
	
	void deleteMember(String userid, String password);

	MemberVO getMember(String userid);

	ArrayList<MemberVO> getMemberList(int page);
	
	int getTotalMemCount();
	
}
