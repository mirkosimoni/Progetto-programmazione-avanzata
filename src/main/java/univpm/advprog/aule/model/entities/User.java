package univpm.advprog.aule.model.entities;

import java.util.Set;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class User implements Serializable{

	@Id
	  @Column(name = "USERNAME")
	  private String username;

	  @Column(name = "PASSWORD", nullable = false)
	  private String password;

	  @Column(name = "ENABLED", nullable = false)
	  private boolean enabled;
	  
	  private Set<Role> roles;
}
