package univpm.advprog.aule.model.dao;

import java.util.Set;

import org.hibernate.Session;

import univpm.advprog.aule.model.entities.Role;
import univpm.advprog.aule.model.entities.User;

public interface UserDao {
	
	Session getSession();
	public void setSession(Session session);

	
	User findUserByUsername(String username);
	
	User create(String username, String password, boolean isEnabled, Set<Role> roles);
	
	User update(User user);
	
	void delete(User user);

	public String encryptPassword(String password);
	
}
