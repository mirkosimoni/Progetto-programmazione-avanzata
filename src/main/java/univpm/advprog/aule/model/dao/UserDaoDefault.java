package univpm.advprog.aule.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import univpm.advprog.aule.model.entities.User;

public class UserDaoDefault extends DefaultDao implements UserDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findUserByUsername(String username) {
		return this.getSession().get(User.class, username);
	}

	@Override
	public User create(String username, String password, boolean isEnabled) {
		User u = new User();
		u.setUsername(username);
		u.setPassword(this.encryptPassword(password));
		u.setEnabled(isEnabled);
		this.getSession().save(u);
		
		return u;
	}

	@Override
	public User update(User user) {
		return (User)this.getSession().merge(user);
	}

	@Override
	public void delete(User user) {
		this.getSession().delete(user);
		
	}

	@Override
	public String encryptPassword(String password) {
		return this.passwordEncoder.encode(password);
	}

}
