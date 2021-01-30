package com.kg.dvlpr.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kg.dvlpr.board.dao.BoardDAO;
import com.kg.dvlpr.board.model.BoardVO;
import com.kg.dvlpr.comment.dao.CommentDAO;
import com.kg.dvlpr.comment.model.CommentVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public BoardController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ServletContext context = getServletContext();
		String saveDir = context.getRealPath("upload");	//첨부파일 저장되는 경로
		String str = null;
		String c = request.getRequestURI().substring(request.getContextPath().length());
		switch (c) {
		
		case "/getBoardListAll.bo":
			BoardDAO bdao10 = null;
			try {
				bdao10 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			ArrayList<BoardVO> blist10 = null;
			blist10 = bdao10.getBoardListAll();
			request.setAttribute("blist", blist10);
			str = "index.jsp";
			request.getRequestDispatcher(str).forward(request, response);
			break;
		
		case "/getBoardList.bo":
			int boardClass = Integer.parseInt(request.getParameter("boardClass"));////
			
			String pageStr = request.getParameter("page");
			int page = 1;
			if(pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
			BoardDAO bdao = null;
			try {
				bdao = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			ArrayList<BoardVO> blist1 = bdao.getBoardList(boardClass, page);////
			request.setAttribute("blist", blist1);////
			request.setAttribute("boardClass", boardClass);////
			
			//페이징 처리를 위한 메서드 호출
			int bbsCount = bdao.selectTotalBbsCount(boardClass);
			double totalPage = 0;
			if(bbsCount>0) {
				totalPage = bbsCount/10.0;
			}
			if((totalPage%1) != 0) {
				totalPage = totalPage+1;
			}
			request.setAttribute("totalPageCount", (int)totalPage);
			request.setAttribute("page", page);
			
			request.getRequestDispatcher("board_list.jsp").forward(request, response);////
			break;
		case "/insertBoard.bo":
			MultipartRequest mr1 = new MultipartRequest(request, saveDir, 10 * 1024 * 1024, "UTF-8",
					new DefaultFileRenamePolicy());
			int boardClass2 = Integer.parseInt(mr1.getParameter("boardClass"));
			String boardTitle2 = mr1.getParameter("boardTitle");
			String boardContent2 = mr1.getParameter("boardContent");
			String file1 = mr1.getFilesystemName("file1");
			String file2 = mr1.getFilesystemName("file2");
			String file3 = mr1.getFilesystemName("file3");
			String userid2 = mr1.getParameter("userid");
			BoardDAO bdao2 = null;
			BoardVO bvo2 = null;
			bvo2 = new BoardVO(boardClass2, boardTitle2, boardContent2, file1, file2, file3, userid2);

			try {
				bdao2 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			bdao2.insertBoard(bvo2);
			str = "getBoardList.bo?boardClass=" + boardClass2;
			request.getRequestDispatcher(str).forward(request, response);
			break;
		case "/getBoard.bo":
			BoardDAO bdao3 = null;
			BoardVO bvo3 = null;
			CommentDAO cdao = null;////
			int boardNum3 = Integer.parseInt(request.getParameter("boardNum"));
			String update = request.getParameter("update");
			try {
				bdao3 = new BoardDAO();
				cdao = new CommentDAO();////
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			bvo3 = bdao3.getBoard(boardNum3);
			ArrayList<CommentVO> clist = cdao.getCommentList(boardNum3);////
			request.setAttribute("boardNum", boardNum3);
			request.setAttribute("board", bvo3);
			request.setAttribute("clist", clist);////
			if (update.equals("no"))
				str = "board_view.jsp";
			else if (update.equals("yes"))
				str = "board_update.jsp?boardNum=" + boardNum3;
			request.getRequestDispatcher(str).forward(request, response);
			break;
		case "/fileDownload.bo":
			String fileName = request.getParameter("fileName");
			ServletContext context2 = getServletContext();
			String saveDir2 = context2.getRealPath("upload")+"/"+fileName;
			try {
				File file = new File(saveDir2);
				byte b[] = new byte[(int) file.length()];
				response.reset();
				response.setContentType("application/octet-stream");
				String encoding = new String(fileName.getBytes("EUC-KR"), "8859_1");
				response.setHeader("Content-Disposition", "attachment;filename=" + encoding);
				response.setHeader("Content-Length", String.valueOf(file.length()));

				if (file.isFile()) // 파일이 있을경우
				{
					FileInputStream fileInputStream = new FileInputStream(file);
					ServletOutputStream servletOutputStream = response.getOutputStream();

					// 파일을 읽어서 클라이언트쪽으로 저장한다.
					int readNum = 0;
					while ((readNum = fileInputStream.read(b)) != -1) {
						servletOutputStream.write(b, 0, readNum);
					}

					servletOutputStream.close();
					fileInputStream.close();
				}

			} catch (Exception e) {
				System.out.println("Download Exception : " + e.getMessage());
			}
			break;
		case "/updateBoard.bo":
			MultipartRequest mr4 = new MultipartRequest(request, saveDir, 10 * 1024 * 1024, "UTF-8",
					new DefaultFileRenamePolicy());
			BoardDAO bdao4 = null;
			BoardVO bvo4 = null;
			CommentDAO cdao2 = null;////
			try {
				bdao4 = new BoardDAO();
				cdao2 = new CommentDAO();////
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			int boardNum4 = Integer.parseInt(mr4.getParameter("boardNum"));
			bvo4 = bdao4.getBoard(boardNum4);
			ArrayList<CommentVO> clist2 = cdao2.getCommentList(boardNum4);////
			request.setAttribute("board", bvo4);
			request.setAttribute("clist", clist2);////
			// 수정내역받아오기
			// int boardClass4 = Integer.parseInt(request.getParameter("boardClass"));
			String boardTitle4 = mr4.getParameter("boardTitle");
			String boardContent4 = mr4.getParameter("boardContent");
			
			String upfile1 = mr4.getFilesystemName("file1");
			String upfile2 = mr4.getFilesystemName("file2");
			String upfile3 = mr4.getFilesystemName("file3");
			// bvo4.setBoardClass(boardClass4);
			bvo4.setBoardTitle(boardTitle4);
			bvo4.setBoardContent(boardContent4);
			if(upfile1!=null) {
				deleteFile(bvo4.getFile1());
				bvo4.setFile1(upfile1);
			}
			if(upfile2!=null) {
				deleteFile(bvo4.getFile2());
				bvo4.setFile2(upfile2);
			}
			if(upfile3!=null) {
				deleteFile(bvo4.getFile3());
				bvo4.setFile3(upfile3);
			}
			// bvo4.setFileData(fileData4);

			bdao4.updateBoard(bvo4);
			str = "board_view.jsp?boardNum=" + boardNum4;
			request.getRequestDispatcher(str).forward(request, response);
			break;
		case "/deleteBoard.bo":
			BoardDAO bdao5 = null;
			int boardNum5 = Integer.parseInt(request.getParameter("boardNum"));
			try {
				bdao5 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			BoardVO bvo5 = bdao5.getBoard(boardNum5);
			//저장되어있는 첨부파일 삭제
			deleteFile(bvo5.getFile1());
			deleteFile(bvo5.getFile2());
			deleteFile(bvo5.getFile3());
			bdao5.deleteBoard(boardNum5);
			str = "getBoardList.bo?boardClass="+bvo5.getBoardClass();
			request.getRequestDispatcher(str).forward(request, response);
			break;
		case "/searchBoard.bo":
			BoardDAO bdao6 = null;
			String category = request.getParameter("category");
			String keyword = request.getParameter("keyword");
			try {
				bdao6 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			ArrayList<BoardVO> blist6 = bdao6.searchBoard(category, keyword);
			request.setAttribute("blist", blist6);
			str = "board_list.jsp";
			request.getRequestDispatcher(str).forward(request, response);
			break;
		case "/upCnt.bo":
			BoardDAO bdao7 = null;
			int boardNum7 = Integer.parseInt(request.getParameter("boardNum"));
			try {
				bdao7 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			bdao7.upCnt(boardNum7);
			break;
		case "/likeCnt.bo":
			BoardDAO bdao8 = null;
			int boardNum8 = Integer.parseInt(request.getParameter("boardNum"));
			int select8 = Integer.parseInt(request.getParameter("select"));
			try {
				bdao8 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			boolean result8 = bdao8.likeCnt(boardNum8, select8);
			request.setAttribute("result", result8);
			response.sendRedirect("getCommentList.co?boardNum="+boardNum8);////
			break;
		case "/dislikeCnt.bo":
			BoardDAO bdao9 = null;
			int boardNum9 = Integer.parseInt(request.getParameter("boardNum"));
			int select9 = Integer.parseInt(request.getParameter("select"));
			try {
				bdao9 = new BoardDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			boolean result9 = bdao9.dislikeCnt(boardNum9, select9);
			request.setAttribute("result", result9);
			response.sendRedirect("getCommentList.co?boardNum="+boardNum9);////
			break;
		}
		/*RequestDispatcher rd1 = request.getRequestDispatcher(str);
		rd1.forward(request, response);*/
	}
	
	public void deleteFile(String fileName) {
		ServletContext context5 = getServletContext();
		String dfile = context5.getRealPath("upload")+"/"+fileName;
		File f1 = new File(dfile);
		if(f1.exists()) 	f1.delete();
	}
	

}
