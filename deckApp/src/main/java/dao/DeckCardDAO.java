package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DeckCardDTO;

public class DeckCardDAO extends BaseDAO{
	
	public List<DeckCardDTO> selectByDeckId(int deckId){
		
		List<DeckCardDTO> cardList = new ArrayList<>();
		
		Connection conn = getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM deck_cards WHERE deck_id = ?");
			ps.setInt(1, deckId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				DeckCardDTO dto = new DeckCardDTO();
				dto.setId(rs.getInt("id"));
				dto.setDeckId(rs.getInt("deck_id"));
				dto.setName(rs.getString("name"));
				dto.setColor(rs.getString("color"));
				dto.setCost(rs.getString("cost"));
				dto.setInc(rs.getString("inc"));
				dto.setType(rs.getString("type"));
				dto.setQuantity(rs.getInt("quantity"));
				
				cardList.add(dto);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cardList;
	}
	
	//デッキにカードを追加
	public int insert(DeckCardDTO dto) {
		int result = 0;
		
		Connection conn = getConnection();
		
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO deck_cards(deck_id, name, color, cost, inc, type, quantity) VALUES(?,?,?,?,?,?,?)");
			ps.setInt(1,dto.getDeckId());
			ps.setString(2,dto.getName());
			ps.setString(3,dto.getColor());
			ps.setString(4,dto.getCost());
			ps.setString(5,dto.getInc());
			ps.setString(6,dto.getType());
			ps.setInt(7,dto.getQuantity());
			
			result = ps.executeUpdate();
			
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			
			e.printStackTrace();
		}
		tm.close();
		return result;
	}
	
	//デッキのカードを削除
	public int delete(int cardId, int deckId) {
		int result = 0;
		
		Connection conn = getConnection();
		
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM deck_cards WHERE id =? AND deck_id = ?");
			ps.setInt(1, cardId);
			ps.setInt(2, deckId);
			
			result = ps.executeUpdate();
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
	}

}