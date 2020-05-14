package univpm.advprog.aule.services;

import java.util.List;

import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.Profile;
import univpm.advprog.aule.model.entities.User;
import univpm.advprog.aule.utils.PrenotationsOverlapFinder;
import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.dao.PrenotationDao;
import univpm.advprog.aule.model.dao.ProfileDao;
import univpm.advprog.aule.model.dao.RoleDao;
import univpm.advprog.aule.model.dao.UserDao;

@Transactional
@Service("myprofileService")
public class MyProfileServiceDefault implements MyProfileService {
	
	ProfileDao profilerepository;
	UserDao userRepository;
	
	@Autowired
	public void setUserRepository(UserDao userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setProfileRepository(ProfileDao profileRepository) {
		this.profilerepository = profileRepository;
	}
	
	// Restituisce la lista di tutti gli utenti
	@Override
	public List<User> findAll(){
		
		return userRepository.findAll();
	}
	
	// Creazione di un profilo
	@Override
	public Profile create(Profile profilo) {
		
		return profilerepository.create(profilo);
	}
	
	// Trova un utente dato il suo username
	@Override
	public User findByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	// Modifica di un utente
	@Override
	public User update(User user) {
		this.profilerepository.update(user.getProfile());
		return userRepository.update(user);
	}

}
