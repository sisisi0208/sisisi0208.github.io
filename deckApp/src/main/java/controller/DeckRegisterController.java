package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Deck;
import service.DeckRegisterService;
import validation.DeckValidation;

@WebServlet("/deckRegister")
public class DeckRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckRegister.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		DeckValidation validation = new DeckValidation();
		
		validation.isBlank("デッキ名", name);
		validation.length("デッキ名",name,255);
		validation.length("デッキ説明",description,255);
		
		if(validation.hasErrorMsg()) {
			request.setAttribute("errorMsgList", validation.getErrorMsgList());
			
			request.setAttribute("name", name);
			request.setAttribute("description", description);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckRegister.jsp");
			rd.forward(request, response);
			return;
		}
		
		//ログインセッションを取得
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("userId") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		//ユーザーID取得
		int userId = (Integer) session.getAttribute("userId");
		//登録デッキ作成
		Deck deck = new Deck(userId, name, description);
		//サービスの呼び出し
		DeckRegisterService service = new DeckRegisterService();
		//deckId取得
		int newDeckId = service.deckEntryDo(deck);
		//登録成功判定後、デッキ編集画面へ
		if(newDeckId > 0) {
			response.sendRedirect(request.getContextPath()+"/deckEdit?deckId="+newDeckId);
			return;
		}
		
	}

}
