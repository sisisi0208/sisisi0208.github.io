package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DeckDAO;
import dto.DeckDTO;
import service.DeckDeleteService;

@WebServlet("/deckDelete")
public class DeckDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		try {
			int deckId = Integer.parseInt(request.getParameter("deckId"));
			int userId = (int)session.getAttribute("userId");
			
			DeckDAO deckDAO = new DeckDAO();
			DeckDTO deck = deckDAO.selectById(deckId, userId);
			
			if(deck == null) {
				response.sendRedirect(request.getContextPath()+"/deckList");
				return;
			}
			
			request.setAttribute("deck",deck);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckDeleteConfirm.jsp");
			rd.forward(request, response);
		}catch(NumberFormatException e){
			response.sendRedirect(request.getContextPath()+"/deckList");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインセッションの取得
		HttpSession session = request.getSession(false);
				
		//未ログインの場合
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		try {
			int deckId = Integer.parseInt(request.getParameter("deckId"));
			int userId = (int)session.getAttribute("userId");
			
			DeckDeleteService deleteService = new DeckDeleteService();
			boolean result = deleteService.deckDeleteDo(deckId, userId);
			
			if(result) {
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckDeleteDone.jsp");
				rd.forward(request, response);
				return;
			}
			request.setAttribute("deleteError", "デッキの削除に失敗しました。");
			
			request.setAttribute("deckId",deckId);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckDeleteConfirm.jsp");
			rd.forward(request, response);
		}catch(NumberFormatException e){
			response.sendRedirect(request.getContextPath()+"/deckList");
		}
	}

}
