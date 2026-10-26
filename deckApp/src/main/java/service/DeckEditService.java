package service;

import java.util.List;

import dao.DeckCardDAO;
import domain.DeckCard;
import dto.DeckCardDTO;

public class DeckEditService {
	
	public boolean cardEntryConfirm(DeckCard card) {
		
		if(card == null) {
			return false;
		}
		
		if(card.getDeckId() <= 0) {
			return false;
		}
		
		if(card.getName() == null || card.getName().isEmpty()) {
			return false;
		}
		if(card.getQuantity() < 1 || card.getQuantity() >4) {
			return false;
		}
		return true;
	}
	
	public boolean cardEntryDo(DeckCard card) {
		
		if(!cardEntryConfirm(card)) {
		
			return false;
		}
		
		DeckCardDAO deckCardDAO = new DeckCardDAO();
		DeckCardDTO dto = new DeckCardDTO(card.getDeckId(),card.getName(),card.getColor(),card.getCost(),card.getInc(),card.getType(),card.getQuantity());
		
		int result = deckCardDAO.insert(dto);
		
		return result == 1;
	}
	
	public boolean cardDeleteDo(int cardId, int deckId) {
		
		if(cardId <= 0 || deckId <= 0) {
			return false;
		}
		
		DeckCardDAO deckCardDAO = new DeckCardDAO();
		
		int result = deckCardDAO.delete(cardId,deckId);
		
		return result == 1;
	}
	
	public int getTotalQuantity(List<DeckCardDTO> cardList) {
		int total = 0;
		
		for(DeckCardDTO card:cardList) {
			total += card.getQuantity();
		}
		return total;
	}

}
