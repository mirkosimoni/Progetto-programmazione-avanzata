package univpm.advprog.aule.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "profilo")
public class Profile {
	
	private String nome;
	
	private String cognome;

	@Column(name = "NOME", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "COGNOME", nullable = false)
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	

}
