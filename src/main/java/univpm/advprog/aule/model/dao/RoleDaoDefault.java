package univpm.advprog.aule.model.dao;
import univpm.advprog.aule.model.entities.Role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository("roleDao")
public class RoleDaoDefault extends DefaultDao implements RoleDao {
	
	//Crea il ruolo
	@Override
	public Role create(String name) {
		Role r = new Role();
		r.setName(name);
		this.getSession().save(r);
		
		return r;
	}
	
	//Aggiorna il ruolo
	@Override
	public Role update(Role role) {
		return (Role)this.getSession().merge(role);
	}
	
	//Elimina il ruolo
	@Override
	public void delete(Role role) {
		this.getSession().delete(role);
		
	}	

}
