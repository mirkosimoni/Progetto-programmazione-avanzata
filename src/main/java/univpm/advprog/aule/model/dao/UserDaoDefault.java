package univpm.advprog.aule.model.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Role;
import univpm.advprog.aule.model.entities.User;

@Transactional
@Repository("userDao")
public class UserDaoDefault extends DefaultDao implements UserDao {
	
	//Usato per codificare password
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Restituisce l'utente a seconda dell'username
	@Override
	public User findUserByUsername(String username) {
		return this.getSession().get(User.class, username);
	}
	
	//Restituisce tutti gli utenti
	@Override
	public List<User> findAll(){
		return getSession().
				createQuery("from User u", User.class).
				getResultList();
	}
	
	//Crea un utente
	@Override
	public User create(String username, String password, boolean isEnabled, Set<Role> roles) {
		User u = new User();
		u.setUsername(username);
		u.setPassword(this.encryptPassword(password));
		u.setEnabled(isEnabled);
		u.setRoles(roles);
		this.getSession().save(u);
		
		return u;
	}
	
	//Aggiorna un utente
	@Override
	public User update(User user) {
		return (User)this.getSession().merge(user);
	}

	//Elimina un utente
	@Override
	public void delete(User user) {
		this.getSession().delete(user);
		
	}
	
	//Cifratore della password
	@Override
	public String encryptPassword(String password) {
		return this.passwordEncoder.encode(password);
	}

}
