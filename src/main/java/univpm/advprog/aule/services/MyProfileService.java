package univpm.advprog.aule.services;
import univpm.advprog.aule.model.entities.Profile;
import univpm.advprog.aule.model.entities.User;

public interface MyProfileService {

	public Profile create(Profile profile);
	
	public User findByUsername(String username);
}
