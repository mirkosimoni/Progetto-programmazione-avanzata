package univpm.advprog.aule.model.dao;

import javax.management.relation.Role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class RoleDaoDefault extends DefaultDao implements RoleDao {
	

	@Override
	public Role create(String name) {
		Role r = new Role(name, null);
		r.setRoleName(name);
		this.getSession().save(r);
		
		return r;
	}

	@Override
	public Role update(Role role) {
		return (Role)this.getSession().merge(role);
	}

	@Override
	public void delete(Role role) {
		this.getSession().delete(role);
		
	}	

}
