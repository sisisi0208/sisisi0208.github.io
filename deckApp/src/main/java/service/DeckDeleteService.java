package service;

import dao.DeckDAO;
import dto.DeckDTO;

public class DeckDeleteService {
	public boolean deckDeleteDo(int deckId, int userId) {
		
		if(deckId <= 0 ||userId <= 0) {
			return false;
		}
		DeckDAO deckDAO = new DeckDAO();
		DeckDTO dto = new DeckDTO();
		dto.setId(deckId);
		dto.setUserId(userId);
		int result = deckDAO.delete(dto);
		
		return result == 1;
	}

}
