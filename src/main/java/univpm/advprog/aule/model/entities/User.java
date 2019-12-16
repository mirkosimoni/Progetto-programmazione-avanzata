package univpm.advprog.aule.model.entities;

import javax.persistence.Column;
import javax.persistence.Id;

public class User {

	@Id
	  @Column(name = "USERNAME")
	  private String username;

	  @Column(name = "PASSWORD", nullable = false)
	  private String password;

	  @Column(name = "ENABLED", nullable = false)
	  private boolean enabled;
}
