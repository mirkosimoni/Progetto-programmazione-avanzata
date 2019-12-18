package univpm.advprog.aule.model.entities;

import javax.persistence.Column;

public class Profile {
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "COGNOME", nullable = false)
	private String cognome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	

}
