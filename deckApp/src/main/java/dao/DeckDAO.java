package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.DeckDTO;

public class DeckDAO extends BaseDAO{
	
	public List<DeckDTO> selectByUserId(int userId) {
		//空のリスト作成
		List<DeckDTO> deckList = new ArrayList<>();
		//データベースへ接続
		Connection conn = getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM decks WHERE user_id = ? ORDER BY id ASC");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			//繰り返し検索
			while(rs.next()) {
				DeckDTO dto = new DeckDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				
				//リストにDTO追加
				deckList.add(dto);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return deckList;
	}
	
	public int insert(DeckDTO dto) {
		int deckId = 0;
		// データベースへ接続
		Connection conn =getConnection();
		// トランザクション処理を開始
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO decks(user_id,name,description) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, dto.getUserId());
			ps.setString(2,  dto.getName());
			ps.setString(3, dto.getDescription());
			
			//DBへのinsertが成功した件数がint型で返却される
			int result = ps.executeUpdate();
			
			if(result == 1) {
				ResultSet rs = ps.getGeneratedKeys();
				
				if(rs.next()) {
					deckId = rs.getInt(1);
				}
				rs.close();
			}
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return deckId;
	}
	
	public int edit(DeckDTO dto) {
		int result = 0;
		
		Connection conn = getConnection();
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE decks SET name = ?, description = ? WHERE id =? AND user_id = ?");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, dto.getId());
			ps.setInt(4, dto.getUserId());
			
			result = ps.executeUpdate();
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
	}
	
	public int delete(DeckDTO dto) {
		int result = 0;
		
		Connection conn = getConnection();
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM decks WHERE id = ? AND user_id = ?");
			ps.setInt(1, dto.getId());
			ps.setInt(2, dto.getUserId());
			
			result = ps.executeUpdate();
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
	}
	
	public DeckDTO selectById(int deckId, int userId) {
		DeckDTO dto = null;
		Connection conn = getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT *  FROM decks WHERE id = ? AND user_id = ?");
			ps.setInt(1, deckId);
			ps.setInt(2, userId);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new DeckDTO();
				
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setName(rs.getString("name"));
				
				dto.setDescription(rs.getString("description"));
			}
			rs.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
}
		