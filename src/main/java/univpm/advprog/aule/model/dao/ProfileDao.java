package univpm.advprog.aule.model.dao;

import java.util.List;

import org.hibernate.Session;

import univpm.advprog.aule.model.entities.Profile;



public interface ProfileDao {
	Session getSession();
	public void setSession(Session session);
	
    Profile findByName(String name);
	
	List<Profile> findAll();

	Profile findById(String id);

	Profile update(Profile profile);

	void delete(Profile contact);
	
	Profile create(Profile profile);
	
}
