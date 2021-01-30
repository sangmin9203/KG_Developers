package com.kg.dvlpr.comment.model;

import java.sql.Timestamp;

public class CommentVO {

	private int boardNum;
	private int commentNum;
	private String commentContent;
	private String userid;
	private int likeCnt;
	private int dislikeCnt;
	private Timestamp writeDate;
	private int parentNum;
	private int stepNum;
	
	public CommentVO() {}

	public CommentVO(int boardNum, int commentNum, String commentContent, String userid, int likeCnt, int dislikeCnt,
			Timestamp writeDate, int parentNum, int stepNum) {
		this.boardNum = boardNum;
		this.commentNum = commentNum;
		this.commentContent = commentContent;
		this.userid = userid;
		this.likeCnt = likeCnt;
		this.dislikeCnt = dislikeCnt;
		this.writeDate = writeDate;
		this.parentNum = parentNum;
		this.stepNum = stepNum;
	}

	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public int getDislikeCnt() {
		return dislikeCnt;
	}
	public void setDislikeCnt(int dislikeCnt) {
		this.dislikeCnt = dislikeCnt;
	}
	public Timestamp getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}
	public int getParentNum() {
		return parentNum;
	}
	public void setParentNum(int parentNum) {
		this.parentNum = parentNum;
	}
	public int getStepNum() {
		return stepNum;
	}
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

}
