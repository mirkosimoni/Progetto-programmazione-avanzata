package univpm.advprog.aule.model.dao;

import javax.management.relation.Role;

import org.hibernate.Session;

public interface RoleDao {
	
	Session getSession();
	public void setSession(Session session);

	Role create(String name);
	
	Role update(Role role);
	
	void delete(Role role);	
	
}
