package com.kg.dvlpr.comment.dao;

import java.util.ArrayList;

import com.kg.dvlpr.comment.model.CommentVO;

public interface ICommentDAO {
	
	void insertComment(CommentVO comment);
	
	void insertCommentRe(CommentVO comment, int commentNum);////

	void updateComment(int cmtNum, String cmtContent);

	void deleteComment(int cmtNum);
	
	CommentVO getComment(int parentNum);

	ArrayList<CommentVO> getCommentList(int boardNum);

	boolean likeCnt(int cmtNum, int select);

	boolean dislikeCnt(int cmtNum, int select);

}
