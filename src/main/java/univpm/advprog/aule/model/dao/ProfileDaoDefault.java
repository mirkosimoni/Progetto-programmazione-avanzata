package univpm.advprog.aule.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Profile;


@Transactional
@Repository("profileDao")
public class ProfileDaoDefault extends DefaultDao implements ProfileDao  {

	
		//Restituisce profilo con nome richiesto
		@Override
		public Profile findByName(String name) {
			return this.getSession().createQuery("FROM Profile pr WHERE pr.name = :name", Profile.class).setParameter("name", name).getSingleResult();
		}
		
		//Restituisce tutti i profili
		@Override
		public List<Profile> findAll() {
			return this.getSession().createQuery("FROM Profile pr", Profile.class).getResultList();
		}
		
		//Restituisce profilo dell'id richiesto
		@Override
		public Profile findById(String id) {
			return this.getSession().find(Profile.class, id);
		}
		
		//Aggiorna il profilo
		@Override
		public Profile update(Profile profile) {
			return (Profile)this.getSession().merge(profile);
		}
		
		//Elimina il profilo
		@Override
		public void delete(Profile profile) {
			this.getSession().delete(profile);
		
		}
		
		//Crea il profilo
		@Override
		public Profile create(Profile profile) {
			this.getSession().save(profile);
			return profile;
		}

	
}
