package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserRegisterService;


@WebServlet("/registerConfirm")
public class RegisterConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		
		User user = new User(loginId,password,name);
		
		UserRegisterService userRegister = new UserRegisterService();
		boolean result = userRegister.userEntryDo(user);
		
		if(result == true) {
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/registerDone.jsp");
			rd.forward(request, response);
		}else {
			request.setAttribute("registerError", "新規会員登録に失敗しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
			rd.forward(request, response);
		}
	}
}
