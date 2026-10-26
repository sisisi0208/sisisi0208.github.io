package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDTO;

public class UserDAO extends BaseDAO {
	public UserDTO selectByLoginId(String loginId) {
		UserDTO dto = null;
		
		Connection conn = getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE login_id = ?");
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getInt(1));
				dto.setLoginId(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setName(rs.getString(4));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public int insert(UserDTO dto) {
		int result = 0;
		Connection conn = getConnection();
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO users(login_id, password, name) VALUES(?, ?, ?)");
			ps.setString(1,  dto.getLoginId());
			ps.setString(2,  dto.getPassword());
			ps.setString(3,  dto.getName());
			
			result = ps.executeUpdate();
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
	}
	
	public int edit(UserDTO dto) {
		int result = 0;
		
		Connection conn = getConnection();
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ?,name = ? WHERE login_id = ?");
			ps.setString(1, dto.getPassword());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getLoginId());
			
			result = ps.executeUpdate();
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
	}
	
	public int delete(UserDTO dto) {
		int result = 0;
		
		Connection conn = getConnection();
		TransactionManager tm = new TransactionManager(conn);
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE login_id = ?");
			
			System.out.println("退会対象loginId=[" + dto.getLoginId() + "]");
			
			ps.setString(1, dto.getLoginId());
			
			result = ps.executeUpdate();
			
			System.out.println("退会DELETE件数=[" + result + "]");
			
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
	}

}