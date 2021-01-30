package com.kg.dvlpr.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.print.DocFlavor.BYTE_ARRAY;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kg.dvlpr.board.dao.BoardDAO;
import com.kg.dvlpr.board.model.BoardVO;
import com.kg.dvlpr.member.dao.MemberDAO;
import com.kg.dvlpr.member.model.MemberVO;

@WebServlet("*.do")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public MemberController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String suserid = (String)session.getAttribute("userid");
		MemberDAO mdao = null;
		MemberVO mvo = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		Date date = new Date();

		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		int enabled = 1;
		int warningCnt = 0;
		//		byte[] profilePic = request.getParameter("profile_pic").getBytes();


		String c = request.getRequestURI().substring(request.getContextPath().length());
		String str = null;


		switch(c) {

		case "/insertUser.do":
			request.setAttribute("message", "회원가입");
			str = "/member_join.jsp";

			break;

			// <---------------------------------------------------------------------->

		case "/idCheck.do":

			try {
				mdao = new MemberDAO();
				String id = request.getParameter("id");
				System.out.println("userid : " + request.getParameter("id"));
				response.setContentType("application/x-json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				String result = mdao.confirmId(id);
				out.print(result);
				out.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}

			break;

			// <---------------------------------------------------------------------->

		case "/nickCheck.do":
			try {
				mdao = new MemberDAO();
				String id = request.getParameter("id");
				System.out.println("nickname : " + request.getParameter("id"));
				response.setContentType("application/x-json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				String result = mdao.confirmNick(id);
				out.print(result);
				out.close();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			break;

			// <---------------------------------------------------------------------->

		case "/loginCheck.do":
			try {
				mdao = new MemberDAO();
				int check = mdao.loginCheck(userid, password);

				System.out.println(check);

			}catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			break;

			// <---------------------------------------------------------------------->	

		case "/updateUser.do":
			try {
				mdao = new MemberDAO();
				mvo = new MemberVO();
				mvo = mdao.getMember(suserid);
				request.setAttribute("vo", mvo);
				request.setAttribute("message", "회원 정보 수정");
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			request.getRequestDispatcher("member_update.jsp").forward(request, response);
			break;

			// <---------------------------------------------------------------------->

		case "/updateUserEnabled.do":
			try {
				mdao = new MemberDAO();
				mvo = new MemberVO();

				mvo.setEnabled(0);
				mvo.setUserid("scall24");
				mdao.updateMemberEnabled(mvo);

				System.out.println("updateUserEnabled success");

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			break;

			// <---------------------------------------------------------------------->	

		case "/getPassword.do":
			try {
				mdao = new MemberDAO();
				String result = mdao.getPassword("scall24");
				System.out.println("비밀번호 : " + result);

				System.out.println("getPassword success");

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			break;

			// <---------------------------------------------------------------------->

			//관리자 전용 삭제
		case "/deleteUser.do":
			try {
				mdao = new MemberDAO();
				mdao.deleteMember(userid, password);
				System.out.println("deleteUser success");
			}catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			response.sendRedirect("getMemberList.do?userid=admin");
			break;

			// <---------------------------------------------------------------------->

		case "/getMember.do":
			try {
				mdao = new MemberDAO();
				mvo = new MemberVO();
				mvo = mdao.getMember(userid);
				System.out.println(mvo.getUserid());
				request.setAttribute("vo", mvo);
			}catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			request.getRequestDispatcher("member_mypage.jsp").forward(request, response);
			break;

			// <---------------------------------------------------------------------->

		case "/getMemberList.do":

			String pageStr = request.getParameter("page");
			int page = 1;
			if(pageStr != null) {
				page = Integer.parseInt(pageStr);
			}

			try {
				mdao = new MemberDAO();
				ArrayList<MemberVO> arr = mdao.getMemberList(page);
				for(int i = 0; i < arr.size(); i++) {
					System.out.println(arr.get(i).getName());
				}
				request.setAttribute("lists", arr);

				//페이징 처리를 위한 메서드 호출
				int memCount = mdao.getTotalMemCount();
				double totalPage = 0;
				if(memCount>0) {
					totalPage = memCount/10.0;
				}
				if((totalPage%1) != 0) {
					totalPage = totalPage+1;
				}
				request.setAttribute("totalPageCount", (int)totalPage);
				request.setAttribute("page", page);
				request.getRequestDispatcher("member_view.jsp").forward(request, response);

			}catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String suserid = (String)session.getAttribute("userid");
		String c = request.getRequestURI().substring(request.getContextPath().length());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		Date date = new Date();

		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		int enabled = 1;
		int warningCnt = 0;
		//		byte[] profilePic = request.getParameter("profile_pic").getBytes();

		MemberDAO mdao = null;
		MemberVO mvo = new MemberVO();
		mvo.setUserid(userid);
		mvo.setPassword(password);
		mvo.setName(name);
		mvo.setEmail(email);
		mvo.setNickname(nickname);
		mvo.setEnabled(enabled);
		mvo.setWarningCnt(warningCnt);
		mvo.setJoinDate(date);
		mvo.setProfilePic(null);

		String str = "";

		switch (c) {
		case "/insertUser.do":
			try {
				mdao = new MemberDAO();
				mdao.insertMember(mvo);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("message", "회원가입성공!");
			System.out.println("post insertUser success");
			request.getRequestDispatcher("member_login.jsp").forward(request, response);
			break;

		case "/updateUser.do":
			try {
				mdao = new MemberDAO();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			if(suserid.equals("admin")) {
				mdao.updateMember(mvo);
				request.setAttribute("message", "회원업데이트성공!");
				System.out.println("post updateUser success");
				response.sendRedirect("getMemberList.do?userid=admin");
			} else {
				mvo.setUserid(suserid);
				mdao.updateMember(mvo);
				request.setAttribute("message", "회원업데이트성공!");
				System.out.println("post updateUser success");
				response.sendRedirect("getMember.do?userid="+suserid);
			}
			break;

		case "/updatePassword.do":
			try {
				mdao = new MemberDAO();
				System.out.println(password);
				mdao.updatePassword(suserid, password);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				request.setAttribute("message", "비밀 번호 변경 실패");
			}
			response.sendRedirect("getMember.do?userid=" + suserid);
			break;


		case "/deleteUser.do":
			try {
				mdao = new MemberDAO();
				if(mdao.getPassword(suserid).equals(password)) {
					mdao.deleteMember(suserid, password);
					request.setAttribute("message", "탈퇴가 정상적으로 이루어졌습니다.");
					session.invalidate();
					request.getRequestDispatcher("member_login.jsp").forward(request, response);
				}else {
					System.out.println("비번틀림");
					request.getRequestDispatcher("member_delete.jsp").forward(request, response);
				}

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;

		}


	}

}
