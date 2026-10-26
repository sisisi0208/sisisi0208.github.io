package service;

import dao.DeckDAO;
import domain.Deck;
import dto.DeckDTO;

public class DeckRegisterService {
	//デッキの登録が可能か確認する
	public boolean deckEntryConfirm(Deck deck) {
		return deck != null;
	}
	//デッキの新規登録
	public int deckEntryDo(Deck deck) {
		
		if(!deckEntryConfirm(deck)) {
			return 0;
		}
		
		DeckDAO deckDAO = new DeckDAO();
		DeckDTO dto = new DeckDTO(deck.getUserId(), deck.getName(), deck.getDescription());
		
		return deckDAO.insert(dto);
	}	
}
