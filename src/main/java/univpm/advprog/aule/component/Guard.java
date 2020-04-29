package univpm.advprog.aule.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import univpm.advprog.aule.model.dao.PrenotationDao;
import univpm.advprog.aule.model.dao.UserDao;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.User;

@Component
public class Guard {
	
	private UserDao userRepository;
	private PrenotationDao prenotationRepository;
	    
	@Autowired
	public void setPrenotationRepository(PrenotationDao prenotationRepository) {
		this.prenotationRepository = prenotationRepository;
	}
	
	@Autowired
	public void setUserRepository(UserDao userRepository) {
		this.userRepository = userRepository;
	}
	 
	
	public boolean checkPrenotationId(Authentication authentication, Long id) {
        
		Prenotation prenotation = this.prenotationRepository.findById(id);
		User user = this.userRepository.findUserByUsername(authentication.getName());
		
		if (!user.getUsername().equals(prenotation.getUser().getUsername())) return false;
		
		return true;
		
    }

}
