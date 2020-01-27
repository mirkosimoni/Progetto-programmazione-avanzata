package univpm.advprog.aule.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Profile;


@Transactional
@Repository("profileDao")
public class ProfileDaoDefault extends DefaultDao implements ProfileDao  {

	
		@Override
		public Profile findByName(String name) {
			return this.getSession().createQuery("FROM Profile pr WHERE pr.name = :name", Profile.class).setParameter("name", name).getSingleResult();
		}

		@Override
		public List<Profile> findAll() {
			return this.getSession().createQuery("FROM Profile pr", Profile.class).getResultList();
		}

		@Override
		public Profile findById(String id) {
			return this.getSession().find(Profile.class, id);
		}

		@Override
		public Profile update(Profile profile) {
			return (Profile)this.getSession().merge(profile);
		}

		@Override
		public void delete(Profile profile) {
			this.getSession().delete(profile);
		
		}

		@Override
		public Profile create(String id, String name, String family) {
			Profile pr = new Profile();
			pr.setProfileId(id);
			pr.setName(name);
			pr.setFamily(family);
			this.getSession().save(pr);
			return pr;
		}

	
}
