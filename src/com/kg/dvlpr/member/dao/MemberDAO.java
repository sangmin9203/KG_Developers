package com.kg.dvlpr.member.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.kg.dvlpr.db.DevelopersDBConn;
import com.kg.dvlpr.member.model.MemberVO;

import sun.security.jca.GetInstance;

public class MemberDAO implements IMemberDAO{
	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public MemberDAO()  //시작하자마자 생성자에서 접속객체 get
			throws ClassNotFoundException, SQLException{
		con= new DevelopersDBConn().getConnection();
	}
	
	public void pstmtClose(){
		if(pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			}
	}
	public void getAllInfoClose(){
		if(rs != null){ 
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if(pstmt != null){ 
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if(con != null){ 
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void insertMember(MemberVO mem) {
		String sql = 
				"INSERT INTO member values(?,?,?,?,?,?,?,?,SYSDATE)";
		try {		
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem.getUserid());
			pstmt.setString(2, mem.getPassword());
			pstmt.setString(3, mem.getName());
			pstmt.setString(4, mem.getEmail());
			pstmt.setString(5, mem.getNickname());
			pstmt.setInt(6, mem.getEnabled());
			pstmt.setInt(7, mem.getWarningCnt());
			pstmt.setBytes(8, mem.getProfilePic());
			//Date d = new Date(mem.getJoinDate().getYear(), mem.getJoinDate().getMonth(), mem.getJoinDate().getDate());
			//pstmt.setDate(9,  d);
			pstmt.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("MemberDAO insert메소드 예외 발생!");
		}
	}
	@Override
	public String confirmId(String userid) {
		String result = null;
		int num = 0;
		
		String sql = "SELECT count(*) FROM member WHERE userid=?";
		try {
			
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userid);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			num = rs.getInt(1);
			if(num == 1) {
				result = "NO";
			} else {
				result = "OK";
			}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO confirmId메소드 예외 발생!");
		}
		return result;
	}
	@Override
	public String confirmNick(String nickname) {
		String result = null;
		int num = 0;
		String sql = "SELECT count(*) FROM member WHERE nickname=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
				if(num == 1) {
					result = "NO";
				} else {
					result = "OK";
				}
				}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO confirmNick메소드 예외 발생!");
		}
		return result;
	}
	@Override
	public int loginCheck(String userid, String password) {
		int check = -1;
		String dbPW = "";
		String sql = 
				"SELECT password FROM member WHERE userid=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				dbPW = rs.getString("password");
				if(dbPW.equals(password))
					check = 1;
				else
					check = 0;
			} else {
				check = -1;
			}
			return check;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO loginCheck메소드 ID 예외 발생!");
		}
		
	}
	
	@Override
	public void updateMember(MemberVO mem) {
		String sql = "UPDATE member SET name=?, nickname=?, email=? WHERE userid=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem.getName());
			pstmt.setString(2, mem.getNickname());
			pstmt.setString(3, mem.getEmail());
			// pstmt.setString(4, mem.getPassword());
			pstmt.setString(4, mem.getUserid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO updateMember메소드 예외 발생!");
		}
	}
	
	@Override
	public void updateMemberEnabled(MemberVO mem) {
		String sql = 
				"UPDATE member SET enabled=? WHERE userid=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mem.getEnabled());
			pstmt.setString(2, mem.getUserid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Enabled Exception");
		}
		
	}
	@Override
	public String getPassword(String userid) {
		String pw = "";
		String sql = 
				"SELECT password FROM member WHERE userid=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO getPassword메소드 예외 발생!");
		}
		return pw;
	}
	
	@Override
	public void deleteMember(String userid, String password) {
		try {
			String sql2 = "DELETE FROM member WHERE userid=?";
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, userid);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			System.out.println("MemberDAO delete메소드 예외 발생!");
		}

	}
	
	
	@Override
	public MemberVO getMember(String userid){
		String sql = 
				"SELECT * FROM member WHERE userid=?";
		MemberVO mem = new MemberVO();
		try {
			
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userid);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			mem.setUserid(userid);
			mem.setPassword(rs.getString("password"));
			mem.setName(rs.getString("name"));
			mem.setEmail(rs.getString("email"));
			mem.setNickname(rs.getString("nickname"));
			mem.setEnabled(rs.getInt("enabled"));
			mem.setWarningCnt(rs.getInt("warning_cnt"));
			mem.setProfilePic(rs.getBytes("profile_pic"));
			mem.setJoinDate(rs.getDate("join_date"));
			} 
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO getMember메소드 예외 발생!");
		}
		return mem;
	}
	@Override
	public ArrayList<MemberVO> getMemberList(int page) {
		String sql = "SELECT userid, password, name, email, nickname, enabled, warning_cnt, profile_pic, join_date, rnum " + 
				"FROM (SELECT userid, password, name, email, nickname, enabled, warning_cnt, profile_pic, join_date, rownum as rnum " + 
				"FROM (SELECT userid, password, name, email, nickname, enabled, warning_cnt, profile_pic, join_date " + 
				"FROM member " + 
				"ORDER BY userid DESC)) " + 
				"WHERE rnum BETWEEN ? AND ?";
		//1페이지일때 start=1, end=10. 2페이지일때 start=11, end=20. 
		int start = (page-1) * 10 + 1;
		int end = start + 9;
		ArrayList<MemberVO> memarray = new ArrayList<MemberVO>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String userId = rs.getString("userid");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String nickname = rs.getString("nickname");
				int enabled = rs.getInt("enabled");
				int warningCnt = rs.getInt("warning_cnt");
				byte[] profilePic = rs.getBytes("profile_pic");
				Date joinDate = rs.getDate("join_date");
				
				MemberVO mem = new MemberVO(userId, password, name, email, nickname, enabled, warningCnt, profilePic, joinDate);
				memarray.add(mem);
			}
		} catch (SQLException e) {
			System.out.println("MemberDAO memberList메소드 예외 발생!");
		}
		return memarray;
	}
	
	//페이징 처리 위해 전체 회원수 가져오기
	@Override
	public int getTotalMemCount() {
		String sql = "select count(userid) from member";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			int bbsCount = rs.getInt(1);
			return bbsCount;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("selectTotalMemCount Exception");
		}
	}

	@Override
	public void updatePassword(String userid, String password) {
		String sql = "UPDATE member SET password=? WHERE userid=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, userid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO updatePassword메소드 예외 발생!");
		}

	}
	
}
