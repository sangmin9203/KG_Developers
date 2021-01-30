package com.kg.dvlpr.board.dao;

import java.util.ArrayList;

import com.kg.dvlpr.board.model.BoardVO;

public interface IBoardDAO {
	void insertBoard(BoardVO board);

	ArrayList<BoardVO> getBoardList(int boardClass, int page);

	BoardVO getBoard(int boardNum);

	void updateBoard(BoardVO board);

	void deleteBoard(int boardNum);

	ArrayList<BoardVO> searchBoard(String category, String keyword);

	void upCnt(int boardNum);

	boolean likeCnt(int boardNum, int select);

	boolean dislikeCnt(int boardNum, int select);
	
	ArrayList<BoardVO> getBoardListAll();////
	
	int selectTotalBbsCount(int boardClass);
	
	int selectTotalBbsCount();

	//void insertFile(byte[] fileData);
}
