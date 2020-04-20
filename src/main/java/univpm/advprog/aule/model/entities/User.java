package univpm.advprog.aule.model.entities;

import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.persistence.JoinColumn;

@Entity
@Table(name="user")
public class User implements Serializable{
	
	private String username;
	private String password;
	private boolean enabled;
	private Profile profile;
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToOne
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	  
	@Id
	@NotBlank
	@Column(name = "USERNAME")
	  public String getUsername() {
		  return this.username;
	  }
	  
	  public void setUsername(String username) {
		  this.username = username;
	  }
	  
	  @NotBlank
	  @Column(name = "PASSWORD", nullable = false)
	  public String getPassword() {
		  return this.password;
	  }
	  
	  
	  public void setPassword(String password) {
		  this.password = password;
	  }
	  
	  @Column(name = "ENABLED", nullable = false)
	  public boolean isEnabled() {
		  return this.enabled;
	  }
	  
	  public void setEnabled(boolean enabled) {
		  this.enabled = enabled;
	  } 
	  

	  public void addRole(Role role) {
		  if (this.roles == null) {
			  this.roles = new HashSet<Role>();
		  }
		  
		  this.roles.add(role);
	  }
	  
	  public void removeRole(Role role) {
		
		  if(this.roles != null && this.roles.contains(role)) {
			  this.roles.remove(role);
		  }
	  }
	  
	  public void setRoles(Set<Role> roles) {
		  this.roles = roles;
	  }
	  
	  @ManyToMany
	  @JoinTable( 
	      name = "users_roles", 
	       joinColumns = @JoinColumn(
	        name = "username", referencedColumnName = "username"), 
	      inverseJoinColumns = @JoinColumn(
	        name = "role_id", referencedColumnName = "id"))
	  public Set<Role> getRoles() {
		  return this.roles;
	  }
	  
	  
}
