package com.kg.dvlpr.comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.kg.dvlpr.board.model.BoardVO;
import com.kg.dvlpr.comment.model.CommentVO;
import com.kg.dvlpr.db.DevelopersDBConn;

public class CommentDAO implements ICommentDAO {
	
	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public CommentDAO() throws ClassNotFoundException, SQLException {
		con = new DevelopersDBConn().getConnection();
	}
	
	public void pstmtClose() throws SQLException {
		if(pstmt != null) {
			pstmt.close();
		}
	}
	
	public void getAllInfoClose() throws SQLException {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}

	@Override
	public void insertComment(CommentVO comment) {
		String sql = "INSERT INTO board_comment "
				+ "(board_num, comment_num, comment_content, userid, "
				+ "write_date) "
				+ "VALUES(?, comment_num_seq.NEXTVAL, ?, ?, "
				+ "SYSDATE)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment.getBoardNum());
			pstmt.setString(2, comment.getCommentContent());
			pstmt.setString(3, comment.getUserid());
			pstmt.executeUpdate();		
		} catch (Exception e) {
			System.out.println("insertComment Exception");
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertCommentRe(CommentVO comment, int parentCommentNum) { //자식객체, 부모번호
		CommentVO parentComment = getComment(parentCommentNum);
		
		String sql = "INSERT INTO board_comment "
				+ "(board_num, comment_num, comment_content, userid, "
				+ "write_date, parent_num, step_num) "
				+ "VALUES(?, comment_num_seq.NEXTVAL, ?, ?, "
				+ "SYSDATE, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment.getBoardNum());
			pstmt.setString(2, comment.getCommentContent());
			pstmt.setString(3, comment.getUserid());
			pstmt.setInt(4, parentComment.getCommentNum());
			pstmt.setInt(5, parentComment.getStepNum()+1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertCommentRe Exception");
			e.printStackTrace();
		}
	}

	@Override
	public void updateComment(int cmtNum, String cmtContent) {
		String sql = "UPDATE board_comment "
				+ "SET comment_content=? "
				+ "WHERE comment_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cmtContent);
			pstmt.setInt(2, cmtNum);
			pstmt.executeUpdate();		
		} catch (Exception e) {
			System.out.println("updateComment Exception");
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteComment(int cmtNum) {
		String sql = "DELETE FROM board_comment "
				+ "WHERE comment_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cmtNum);
			pstmt.executeUpdate();		
		} catch (Exception e) {
			System.out.println("deleteComment Exception");
			e.printStackTrace();
		}	
	}
	
	@Override
	public CommentVO getComment(int commentNum) {
		CommentVO comment = null;
		String sql = "SELECT * FROM board_comment WHERE comment_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, commentNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				comment = new CommentVO(
						rs.getInt("board_num"),
						rs.getInt("comment_num"),
						rs.getString("comment_content"),
						rs.getString("userid"),
						rs.getInt("like_cnt"),
						rs.getInt("dislike_cnt"),
						rs.getTimestamp("write_date"),
						rs.getInt("parent_num"),
						rs.getInt("step_num")
						);
			}
		} catch (SQLException e) {
			System.out.println("getComment Exception");
			e.printStackTrace();
		}
		return comment;
	}

	@Override
	public ArrayList<CommentVO> getCommentList(int boardNum) {
		ArrayList<CommentVO> commentList = new ArrayList<>();
		String sql = "SELECT board_num, "
				+ "comment_num, "
				+ "comment_content, "
				+ "userid, "
				+ "like_cnt, "
				+ "dislike_cnt, "
				+ "write_date, "
				+ "parent_num, "
				+ "LEVEL step_num "
				+ "FROM (SELECT * "
				+ "FROM board_comment "
				+ "WHERE board_num = ?"
				+ ")"
				+ "START WITH parent_num = 0 "
				+ "CONNECT BY PRIOR comment_num = parent_num "
				+ "ORDER SIBLINGS BY comment_num";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommentVO comment = new CommentVO(
						rs.getInt("board_num"),
						rs.getInt("comment_num"),
						rs.getString("comment_content"),
						rs.getString("userid"),
						rs.getInt("like_cnt"),
						rs.getInt("dislike_cnt"),
						rs.getTimestamp("write_date"),
						rs.getInt("parent_num"),
						rs.getInt("step_num")
						);
				commentList.add(comment);
			}
		} catch (Exception e) {
			System.out.println("getCommentList Exception");
			e.printStackTrace();
		}
		return commentList;
	}

	@Override
	public boolean likeCnt(int cmtNum, int select) {
		boolean flag = false;
		String sql = "";
		if(select == 2) {
			sql = "UPDATE board_comment "
					+ "SET like_cnt=like_cnt+1 "
					+ "WHERE comment_num=?";
			flag = true;
		} else if(select == 3) {
			sql = "UPDATE board_comment "
					+ "SET like_cnt=like_cnt-1 "
					+ "WHERE comment_num=?";
			flag = false;
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cmtNum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("likeCnt Exception");
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean dislikeCnt(int cmtNum, int select) {
		boolean flag = false;
		String sql = "";
		if(select == 2) {
			sql = "UPDATE board_comment "
					+ "SET dislike_cnt=dislike_cnt+1 "
					+ "WHERE comment_num=?";
			flag = true;
		} else if(select == 3) {
			sql = "UPDATE board_comment "
					+ "SET dislike_cnt=dislike_cnt-1 "
					+ "WHERE comment_num=?";
			flag = false;
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cmtNum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("dislikeCnt Exception");
			e.printStackTrace();
		}
		return flag;
	}

	

}
