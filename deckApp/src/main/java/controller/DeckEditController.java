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

import dao.DeckCardDAO;
import dao.DeckDAO;
import domain.DeckCard;
import dto.DeckCardDTO;
import dto.DeckDTO;
import service.DeckEditService;
import validation.DeckValidation;

@WebServlet("/deckEdit")
public class DeckEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//deckIdを取得
		String deckIdText = request.getParameter("deckId");
		int deckId = 0;
		
		//int型に変換
		try {
			deckId = Integer.parseInt(deckIdText);
			if(deckId <= 0) {
				response.sendRedirect(request.getContextPath()+"/deckList");
				return;
			}
		}catch(NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/deckList");
			return;
		}
		
		//ログインセッションの取得
		HttpSession session = request.getSession(false);
				
		//未ログインの場合
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
				
		//ログイン中のユーザーIDを取得
		int userId = (int)session.getAttribute("userId");
		
		DeckDAO deckDAO = new DeckDAO();
		DeckDTO deck = deckDAO.selectById(deckId, userId);
		
		if(deck == null) {
			response.sendRedirect(request.getContextPath()+"/deckList");
			return;
		}		
		
		//デッキ内のカードを取得
		DeckCardDAO deckCardDAO = new DeckCardDAO();
		List<DeckCardDTO> cardList = deckCardDAO.selectByDeckId(deckId);
		
		//合計枚数の取得
		DeckEditService deckEditService = new DeckEditService();
		int totalQuantity = deckEditService.getTotalQuantity(cardList);
		
		//jspへ渡す
		request.setAttribute("deckId", deckId);
		request.setAttribute("deck", deck);
		request.setAttribute("cardList",cardList);
		request.setAttribute("totalQuantity", totalQuantity);
		
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckEdit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//登録処理と削除処理の区別
		String operation = request.getParameter("operation");
		
		//削除の場合
		if("delete".equals(operation)) {
			int cardId = Integer.parseInt(request.getParameter("cardId"));
			int deckId = Integer.parseInt(request.getParameter("deckId"));
			
			DeckEditService editService = new DeckEditService();
			editService.cardDeleteDo(cardId,deckId);
			
			response.sendRedirect(request.getContextPath()+"/deckEdit?deckId="+deckId);
			return;
			
		}
		
		String deckIdText = request.getParameter("deckId");
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String cost = request.getParameter("cost");
		String inc = request.getParameter("inc");
		String type = request.getParameter("type");
		String quantityText = request.getParameter("quantity");
		
		DeckValidation validation = new DeckValidation();
		
		//必須入力確認
		validation.isBlank("カード名", name);
		validation.isBlank("枚数", quantityText);
		//文字数確認
		validation.length("カード名",name,255);
		validation.length("カード色",color,255);
		validation.length("カードコスト",cost,255);
		validation.length("カードインク",inc,255);
		validation.length("カードタイプ",type,255);
		
		int deckId = 0;
		int quantity = 0;
		
		try {
			deckId = Integer.parseInt(deckIdText);
			if(deckId <= 0) {
				validation.addErrorMsg("デッキIDが正しくありません。");
			}
		}catch(NumberFormatException e) {
			validation.addErrorMsg("デッキIDが正しくありません。");
		}
		
		if(quantityText != null && !quantityText.isBlank()) {
			try {
				quantity = Integer.parseInt(quantityText);
				if(quantity <1 || quantity >4) {
					validation.addErrorMsg("枚数は1枚以上４枚以下で入力してください。");
				}
			}catch(NumberFormatException e) {
				validation.addErrorMsg("枚数は数値で入力してください。");
			}
		}
		
		if(validation.hasErrorMsg()) {
			request.setAttribute("errorMsg", validation.getErrorMsgList());
			
			setInputValues(request,deckIdText,name,color,cost,inc,type,quantityText);
			
			if(deckId > 0) {
				//デッキ情報取得
				HttpSession session = request.getSession(false);
				int userId = (Integer)session.getAttribute("userId");
				
				DeckDAO deckDAO = new DeckDAO();
				DeckDTO deck =deckDAO.selectById(deckId, userId);
				request.setAttribute("deck", deck);
				
				//カード一覧取得
				DeckCardDAO deckCardDAO = new DeckCardDAO();
				List<DeckCardDTO> cardList = deckCardDAO.selectByDeckId(deckId);
				request.setAttribute("cardList", cardList);
				
				//合計枚数取得
				DeckEditService deckEditService = new DeckEditService();
				int totalQuantity = deckEditService.getTotalQuantity(cardList);
				request.setAttribute("totalQuantity", totalQuantity);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckEdit.jsp");
			rd.forward(request, response);
			return;
		}
		
		DeckCard card = new DeckCard(deckId,name,color,cost,inc,type,quantity);
		
		DeckEditService editService = new DeckEditService();
		boolean result = editService.cardEntryDo(card);
		
		if(result) {
			response.sendRedirect(request.getContextPath()+"/deckEdit?deckId="+deckId);
			return;
		}
		
		validation.addErrorMsg("カードを登録できませんでした。");
		
		request.setAttribute("errorMsg", validation.getErrorMsgList());
		
		setInputValues(request,deckIdText,name,color,cost,inc,type,quantityText);
		
		//デッキ情報取得
		HttpSession session = request.getSession(false);
		int userId = (Integer)session.getAttribute("userId");
		
		DeckDAO deckDAO = new DeckDAO();
		DeckDTO deck =deckDAO.selectById(deckId, userId);
		request.setAttribute("deck", deck);
		//カード一覧取得
		DeckCardDAO deckCardDAO = new DeckCardDAO();
		List<DeckCardDTO> cardList = deckCardDAO.selectByDeckId(deckId);
		request.setAttribute("cardList", cardList);
		//合計枚数取得
		DeckEditService deckEditService = new DeckEditService();
		int totalQuantity = deckEditService.getTotalQuantity(cardList);
		request.setAttribute("totalQuantity", totalQuantity);
		
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/deckEdit.jsp");
		rd.forward(request, response);
	}
	
	private void setInputValues(
			HttpServletRequest request,String deckId,String name,String color,String cost,String inc,String type,String quantity){
		
		request.setAttribute("deckId", deckId);
		request.setAttribute("name", name);
		request.setAttribute("color", color);
		request.setAttribute("cost", cost);
		request.setAttribute("inc", inc);
		request.setAttribute("type", type);
		request.setAttribute("quantity", quantity);
	}
}
