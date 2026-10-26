package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DeckDAO;
import dto.DeckDTO;

@WebServlet("/deckList")
public class DeckListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインセッションの取得
		HttpSession session = request.getSession(false);
		
		//未ログインの場合
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		//ログイン中のユーザーIDを取得
		int userId = (int)session.getAttribute("userId");
		
		//ログインユーザーの所有するデッキを取得
		DeckDAO deckDAO = new DeckDAO();
		List<DeckDTO> deckList = deckDAO.selectByUserId(userId);
		
		request.setAttribute("deckList", deckList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckList.jsp");
		rd.forward(request, response);
	}

}
