package com.kg.dvlpr.comment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kg.dvlpr.board.dao.BoardDAO;
import com.kg.dvlpr.board.model.BoardVO;
import com.kg.dvlpr.comment.dao.CommentDAO;
import com.kg.dvlpr.comment.model.CommentVO;

@WebServlet("*.co")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String c = request.getRequestURI().substring(request.getContextPath().length());

		switch (c) {
		case "/insertComment.co":
			CommentDAO cdao1 = null;
			CommentVO cvo1 = null;

			int boardNum = Integer.parseInt(request.getParameter("boardNum"));
			String userid = request.getParameter("userid");
			String commentContent = request.getParameter("commentContent");
			
			cvo1 = new CommentVO();
			cvo1.setBoardNum(boardNum);
			cvo1.setUserid(userid);
			cvo1.setCommentContent(commentContent);
			
			try {
				cdao1 = new CommentDAO();
				cdao1.insertComment(cvo1);
				response.sendRedirect("getCommentList.co?boardNum="+boardNum);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			break;
			
		case "/insertCommentRe.co":
			CommentDAO cdao10 = null;
			CommentVO cvo10 = null;

			int boardNum10 = Integer.parseInt(request.getParameter("boardNum"));
			String userid10 = request.getParameter("userid");
			String commentContent10 = request.getParameter("commentContent");
			int parentCommentNum = Integer.parseInt(request.getParameter("parentCommentNum"));
			
			cvo10 = new CommentVO();
			cvo10.setBoardNum(boardNum10);
			cvo10.setUserid(userid10);
			cvo10.setCommentContent(commentContent10);
			
			try {
				cdao10 = new CommentDAO();
				cdao10.insertCommentRe(cvo10, parentCommentNum);
				response.sendRedirect("getCommentList.co?boardNum="+boardNum10+"&parentCommentNum="+parentCommentNum);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			break;

		case "/updateComment.co":
			CommentDAO cdao2 = null;

			int boardNum2 = Integer.parseInt(request.getParameter("boardNum"));
			int cmtNum = Integer.parseInt(request.getParameter("commentNum"));
			String cmtContent = request.getParameter("commentContent");

			try {
				cdao2 = new CommentDAO();
				cdao2.updateComment(cmtNum, cmtContent);
				response.sendRedirect("getCommentList.co?boardNum="+boardNum2);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			break;

		case "/deleteComment.co":
			CommentDAO cdao3 = null;

			int boardNum3 = Integer.parseInt(request.getParameter("boardNum"));
			int cmtNum2 = Integer.parseInt(request.getParameter("commentNum"));

			try {
				cdao3 = new CommentDAO();
				cdao3.deleteComment(cmtNum2);
				response.sendRedirect("getCommentList.co?boardNum="+boardNum3);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			break;
			
		case "/getComment.co":
			CommentDAO cdao7 = null;
			
			int parentNum = Integer.parseInt(request.getParameter("parentNum"));
			
			try {
				cdao7 = new CommentDAO();
				CommentVO comment = cdao7.getComment(parentNum);
				request.setAttribute("comment", comment);
				request.getRequestDispatcher("board_view.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e2) {
				e2.printStackTrace();
			}
			break;

		case "/getCommentList.co":
			CommentDAO cdao4 = null;
			BoardDAO bdao = null;////
			
			int boardNum4 = Integer.parseInt(request.getParameter("boardNum"));
			
			try {
				cdao4 = new CommentDAO();
				bdao = new BoardDAO();////
				ArrayList<CommentVO> clist = cdao4.getCommentList(boardNum4); //댓글
				BoardVO bvo = bdao.getBoard(boardNum4);////
				request.setAttribute("clist", clist); //댓글
				request.setAttribute("board", bvo);////
				request.getRequestDispatcher("board_view.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			//response.getWriter().write(getJSON(boardNum2));
			break;
			
		case "/likeCnt.co":
			CommentDAO cdao5 = null;

			int cmtNum3 = Integer.parseInt(request.getParameter("commentNum"));
			int select1 = Integer.parseInt(request.getParameter("select"));
			int boardNum11 = Integer.parseInt(request.getParameter("boardNum"));

			try {
				cdao5 = new CommentDAO();
				cdao5.likeCnt(cmtNum3, select1);
				response.sendRedirect("getCommentList.co?boardNum="+boardNum11);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/dislikeCnt.co":
			CommentDAO cdao6 = null;

			int cmtNum4 = Integer.parseInt(request.getParameter("commentNum"));
			int select2 = Integer.parseInt(request.getParameter("select"));
			int boardNum12 = Integer.parseInt(request.getParameter("boardNum"));

			try {
				cdao6 = new CommentDAO();
				cdao6.dislikeCnt(cmtNum4, select2);
				response.sendRedirect("getCommentList.co?boardNum="+boardNum12);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

	public String getJSON(int boardNum) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\": [");
		CommentDAO cdao = null;
		try {
			cdao = new CommentDAO();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ArrayList<CommentVO> clist = cdao.getCommentList(boardNum);
		for(int i=0; i<clist.size(); i++) {
			result.append("{\"boardNum\": \"" + clist.get(i).getBoardNum() + "\", ");
			result.append("\"userid\": \"" + clist.get(i).getUserid() + "\", ");
			result.append("\"commentContent\": \"" + clist.get(i).getCommentContent() + "\"}");
		}
		result.append("]}");
		return result.toString();
	}

}
