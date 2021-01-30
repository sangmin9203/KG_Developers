package com.kg.dvlpr.board.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.kg.dvlpr.board.model.BoardVO;
import com.kg.dvlpr.db.DevelopersDBConn;

public class BoardDAO implements IBoardDAO {
	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public BoardDAO() throws ClassNotFoundException, SQLException {
		con = new DevelopersDBConn().getConnection();
	}

	public void pstmtClose() throws SQLException {
		if (pstmt != null) {
			pstmt.close();
		}
	}

	public void getAllInfoClose() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (con != null) {
			con.close();
		}
	}

	@Override
	public void insertBoard(BoardVO board) {
		String sql = "INSERT INTO board (board_num, board_class, board_title, board_content, file1, file2, file3, write_date, userid)"
				+ " VALUES (board_num_seq.NEXTVAL,?,?,?,?,?,?,SYSDATE,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board.getBoardClass());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setCharacterStream(3, new StringReader(board.getBoardContent()));
			pstmt.setString(4, board.getFile1());
			pstmt.setString(5, board.getFile2());
			pstmt.setString(6, board.getFile3());
			pstmt.setString(7, board.getUserid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insertBoard Exception");
			e.printStackTrace();
		}
	}

	// 게시판 전체글목록 보기
	@Override
	public ArrayList<BoardVO> getBoardList(int boardClass, int page) {
		ArrayList<BoardVO> bArray = new ArrayList<>();
		BoardVO board = null;
		String sql = "SELECT board_num, board_class, board_title, board_content, file1, file2, file3, read_cnt, like_cnt, warning_cnt, write_date, userid, rnum " + 
				"FROM (SELECT board_num, board_class, board_title, board_content, file1, file2, file3, read_cnt, like_cnt, warning_cnt, write_date, userid, rownum as rnum " + 
				"FROM (SELECT board_num, board_class, board_title, board_content, file1, file2, file3, read_cnt, like_cnt, warning_cnt, write_date, userid " + 
				"FROM board " + 
				"ORDER BY board_num DESC)) " + 
				"WHERE board_class=? AND (rnum BETWEEN ? AND ?)";
		//1페이지일때 start=1, end=10. 2페이지일때 start=11, end=20. 
		int start = (page-1) * 10 + 1;
		int end = start + 9;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardClass);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int boardNum = rs.getInt(1);
				String boardTitle = rs.getString(3);
				String boardContent = rs.getString(4);
				String file1 = rs.getString(5);
				String file2 = rs.getString(6);
				String file3 = rs.getString(7);
				int readCnt = rs.getInt(8);
				int likeCnt = rs.getInt(9);
				int warningCnt = rs.getInt(10);
				Timestamp writeDate = rs.getTimestamp(11);
				String userid = rs.getString(12);
				board = new BoardVO(boardNum, boardClass, boardTitle, boardContent, file1,file2, file3, readCnt, likeCnt,
						warningCnt, writeDate, userid);
				bArray.add(board);
			}
		} catch (SQLException e) {
			System.out.println("getBoardList Exception");
			e.printStackTrace();
		}
		return bArray;
	}

	// 게시글 상세보기
	@Override
	public BoardVO getBoard(int boardNum) {
		BoardVO board = null;
		String sql = "SELECT * FROM board WHERE board_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int boardClass = rs.getInt(2);
				String boardTitle = rs.getString(3);
				String boardContent = rs.getString(4);
				String file1 = rs.getString(5);
				String file2 = rs.getString(6);
				String file3 = rs.getString(7);
				int readCnt = rs.getInt(8);
				int likeCnt = rs.getInt(9);
				int warningCnt = rs.getInt(10);
				Timestamp writeDate = rs.getTimestamp(11);
				String userid = rs.getString(12);
				board = new BoardVO(boardNum, boardClass, boardTitle, boardContent, file1,file2, file3, readCnt, likeCnt,
						warningCnt, writeDate, userid);
			}
		} catch (SQLException e) {
			System.out.println("getBoard Exception");
			e.printStackTrace();
		}
		return board;
	}

	// 게시글수정
	@Override
	public void updateBoard(BoardVO board) {
		String sql = "UPDATE board SET board_class=?, board_title=?, board_content=?, file1=?, file2=?, file3=?, write_date=SYSDATE"
				+ " WHERE board_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board.getBoardClass());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setCharacterStream(3, new StringReader(board.getBoardContent()));
			pstmt.setString(4,board.getFile1());
			pstmt.setString(5,board.getFile2());
			pstmt.setString(6,board.getFile3());
			pstmt.setInt(7, board.getBoardNum());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateBoard Exception");
			e.printStackTrace();
		}

	}

	// 게시글삭제
	@Override
	public void deleteBoard(int boardNum) {
		String sql = "DELETE FROM board WHERE board_num=?";
		try {
			con.setAutoCommit(false); 	//게시글 삭제시 댓글도 같이 삭제되어야 하므로 여러 쿼리가 동시에 실행되어야 해서
			//AutoCommit해제한 상태여야함
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
			sql = "DELETE FROM board_comment WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.out.println("deleteBoard Exception");
			e.printStackTrace();
		}
	}

	// 게시글검색
	@Override
	public ArrayList<BoardVO> searchBoard(String category, String keyword) {
		ArrayList<BoardVO> bArray = new ArrayList<>();
		String sql = null;
		if (category.equals("글제목")) {
			sql = "SELECT * FROM board WHERE board_title LIKE ?";
		} else if (category.equals("글내용")) {
			sql = "SELECT * FROM board WHERE board_content LIKE ?";
		} else if (category.equals("작성자")) {
			sql = "SELECT * FROM board WHERE userid LIKE ?";
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int boardNum = rs.getInt(1);
				int boardClass = rs.getInt(2);
				String boardTitle = rs.getString(3);
				String boardContent = rs.getString(4);
				String file1 = rs.getString(5);
				String file2 = rs.getString(6);
				String file3 = rs.getString(7);
				int readCnt = rs.getInt(8);
				int likeCnt = rs.getInt(9);
				int warningCnt = rs.getInt(10);
				Timestamp writeDate = rs.getTimestamp(11);
				String userid = rs.getString(12);
				BoardVO vo = new BoardVO(boardNum, boardClass, boardTitle, boardContent, file1,file2, file3, readCnt, likeCnt,
						warningCnt, writeDate, userid);
				bArray.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("searchBoard Exception");
			e.printStackTrace();
		}

		return bArray;
	}

	// 조회수
	@Override
	public void upCnt(int boardNum) {
		String sql = "UPDATE board SET read_cnt=read_cnt+1 WHERE board_num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("upCnt Exception");
			e.printStackTrace();
		}

	}

	/*
	 * @Override public void insertFile(byte[] fileData) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

	// 좋아요
	@Override
	public boolean likeCnt(int boardNum, int select) {
		String sql = null;
		boolean like = false;
		if (select == 2) { // 좋아요
			sql = "UPDATE board SET like_cnt=like_cnt+1 WHERE board_num=?";
			like = true;
		} else if (select == 3) { // 좋아요취소
			sql = "UPDATE board SET like_cnt=like_cnt-1 WHERE board_num=?";
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("likeCnt Exception");
			e.printStackTrace();
		}
		return like;
	}

	// 싫어요
	@Override
	public boolean dislikeCnt(int boardNum, int select) {
		String sql = null;
		boolean dislike = false;
		if (select == 2) { // 싫어요
			sql = "UPDATE board SET warning_cnt=warning_cnt+1 WHERE board_num=?";
			dislike = true;
		} else if (select == 3) { // 싫어요취소
			sql = "UPDATE board SET warning_cnt=warning_cnt-1 WHERE board_num=?";
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("dislikeCnt Exception");
			e.printStackTrace();
		}
		return dislike;
	}

	//게시판 구분하지 않고 전부다 가져오기
	@Override
	public ArrayList<BoardVO> getBoardListAll() {
		ArrayList<BoardVO> bArray = new ArrayList<>();
		BoardVO board = null;
		String sql = "SELECT * FROM board ORDER BY board_num DESC";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int boardNum = rs.getInt(1);
				int boardClass = rs.getInt(2);
				String boardTitle = rs.getString(3);
				String boardContent = rs.getString(4);
				String file1 = rs.getString(5);
				String file2 = rs.getString(6);
				String file3 = rs.getString(7);
				int readCnt = rs.getInt(8);
				int likeCnt = rs.getInt(9);
				int warningCnt = rs.getInt(10);
				Timestamp writeDate = rs.getTimestamp(11);
				String userid = rs.getString(12);
				board = new BoardVO(boardNum, boardClass, boardTitle, boardContent, file1,file2, file3, readCnt, likeCnt,
						warningCnt, writeDate, userid);
				bArray.add(board);
			}
		} catch (SQLException e) {
			System.out.println("getBoardListAll Exception");
			e.printStackTrace();
		}
		return bArray;
	}

	//게시글의 총 갯수 (게시판 별로)
	public int selectTotalBbsCount(int boardClass) {
		String sql = "select count(board_num) from board where board_class=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardClass);
			rs = pstmt.executeQuery();
			rs.next();
			int bbsCount = rs.getInt(1);
			return bbsCount;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("selectTotalBbsCount Exception");
		}
	}

	//게시글의 총 갯수 (전체 db)
	@Override
	public int selectTotalBbsCount() {
		String sql = "select count(board_num) from board";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			int bbsCount = rs.getInt(1);
			return bbsCount;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("selectTotalBbsCount Exception");
		}
	}
}
