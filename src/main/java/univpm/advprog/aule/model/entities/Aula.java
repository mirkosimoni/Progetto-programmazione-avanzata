package univpm.advprog.aule.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "aula")
public class Aula implements Serializable {
	
	private long id;
	private String nome;
	private int quota;
	private int numeroPosti;
	private boolean presentiPrese;
	private Set<Prenotation> prenotazioni = new HashSet<Prenotation>();
	private int version;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "NUMERO_POSTI")
	public int getNumeroPosti() {
		return numeroPosti;
	}
	
	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}
	
	@Column(name = "NOME")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "QUOTA")
	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}
	
	@Column(name = "PRESE")
	public boolean isPresentiPrese() {
		return presentiPrese;
	}
	
	public void setPresentiPrese(boolean presentiPrese) {
		this.presentiPrese = presentiPrese;
	}
	
	//Se l'admin elimina un'aula, venogno eliminate anche tutte le prenotazioni relative a quell'aula
	@OneToMany(mappedBy="aula", orphanRemoval=true)
	public Set<Prenotation> getPrenotazioni() {
		return prenotazioni;
	}
	
	public void setPrenotazioni(Set<Prenotation> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public void addPrenotation(Prenotation prenotation) {
		this.prenotazioni.add(prenotation);
		prenotation.setAula(this);
	}
	
	public void removePrenotation(Prenotation prenotation) {
		this.prenotazioni.remove(prenotation);
	}
}
