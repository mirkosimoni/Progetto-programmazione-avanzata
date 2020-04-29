package univpm.advprog.aule.services;
import java.util.List;

import univpm.advprog.aule.model.entities.Profile;
import univpm.advprog.aule.model.entities.User;

public interface MyProfileService {

	public Profile create(Profile profile);
	
	public User update(User user);
	
	public User findByUsername(String username);
	
	public List<User> findAll();
}
