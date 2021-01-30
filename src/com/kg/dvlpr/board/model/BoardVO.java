package com.kg.dvlpr.board.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class BoardVO {
	private int boardNum;
	private int boardClass;
	private String boardTitle;
	private String boardContent;
	private String file1;
	private String file2;
	private String file3;
	private int readCnt;
	private int likeCnt;
	private int warningCnt;
	private Timestamp writeDate;
	private String userid;

	//db에 등록할 때 사용 (게시글번호,조회수,좋아요수,싫어요수, 작성시간은 자동으로 설정)
	public BoardVO(int boardClass, String boardTitle, String boardContent, String file1, String file2, String file3,
			String userid) {
		this.boardClass = boardClass;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.file1 = file1;
		this.file2 = file2;
		this.file3 = file3;
		this.userid = userid;
	}

	//db로부터 받아올때 사용
	public BoardVO(int boardNum, int boardClass, String boardTitle, String boardContent, String file1, String file2,
			String file3, int readCnt, int likeCnt, int warningCnt, Timestamp writeDate, String userid) {
		this.boardNum = boardNum;
		this.boardClass = boardClass;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.file1 = file1;
		this.file2 = file2;
		this.file3 = file3;
		this.readCnt = readCnt;
		this.likeCnt = likeCnt;
		this.warningCnt = warningCnt;
		this.writeDate = writeDate;
		this.userid = userid;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public int getBoardClass() {
		return boardClass;
	}

	public void setBoardClass(int boardClass) {
		this.boardClass = boardClass;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public String getFile3() {
		return file3;
	}

	public void setFile3(String file3) {
		this.file3 = file3;
	}

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public int getWarningCnt() {
		return warningCnt;
	}

	public void setWarningCnt(int warningCnt) {
		this.warningCnt = warningCnt;
	}

	public Timestamp getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}