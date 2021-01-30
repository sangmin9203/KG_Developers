package com.kg.dvlpr.login.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kg.dvlpr.member.dao.MemberDAO;
import com.kg.dvlpr.member.model.MemberVO;


@WebServlet("/Login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	MemberDAO mdao;
	
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("logout")) {
			System.out.println("logout success");
			session.invalidate();
			response.sendRedirect("getBoardListAll.bo");////
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			mdao = new MemberDAO();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			System.out.println("LoginController에러 발생!");
		}
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String str = "";
		try {
			String dbpw = mdao.getPassword(userid);
			if(dbpw.equals(password)) {
				MemberVO mvo = mdao.getMember(userid);
				session.setAttribute("name", mvo.getName());
				session.setAttribute("userid", userid);
				request.setAttribute("userid", userid);
				str = "getBoardListAll.bo";////
				response.sendRedirect(str);
				return;
			} else {
				session.invalidate();
				str = "member_login.jsp";
				if(dbpw.equals("")) {
					request.setAttribute("message", "없는 아이디입니다.");
				} else {
					request.setAttribute("message", "비밀번호가 틀렸습니다.");
				}
			}
		} catch (RuntimeException e) {
			session.invalidate();
			request.setAttribute("message", e.getMessage());
			str = "member_login.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(str);
		disp.forward(request, response);
	}
}
