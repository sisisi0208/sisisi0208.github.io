package service;

import dao.UserDAO;
import domain.User;
import dto.UserDTO;

public class UserLoginService {
	
	public User loginCheck(String loginId, String password) {
		
		UserDAO userDAO = new UserDAO();
		UserDTO userDTO = userDAO.selectByLoginId(loginId);
		
		if(userDTO != null && userDTO.getPassword().equals(password)) {
			
			User user = new User(userDTO.getLoginId(),userDTO.getPassword(),userDTO.getName());
			user.setId(userDTO.getId());
			return user;
		}
		return null;
	}
}
