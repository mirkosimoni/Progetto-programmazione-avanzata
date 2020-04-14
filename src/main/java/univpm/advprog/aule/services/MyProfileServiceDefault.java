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
	
	
	@Override
	public Profile create(String nome, String cognome) {
		
		return profilerepository.create(nome, cognome);
	}
	

}
