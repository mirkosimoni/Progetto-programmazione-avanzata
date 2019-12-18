package univpm.advprog.aule.model.entities;

import java.io.Serializable;

import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@Entity
public class Prenotation implements Serializable{
	
	private Long id;
	private DateTime oraInizio;
	private DateTime oraFine;
	private User user;
	private Aula aula;
	private String nomeEvento;
	private String note;
	private int version;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	
	
	//TODO controllare valore di TIMESTAMP sul db
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORA_INIZIO")
	public DateTime getOraInizio() {
		return this.oraInizio;
	}
	
	public void setOraInizio(DateTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORA_FINE")
	public DateTime getOraFine() {
		return this.oraFine;
	}
	
	public void setOraFine(DateTime oraFine) {
		this.oraFine = oraFine;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Aula getAula() {
		return aula;
	}
	
	public void setAula(Aula aula) {
		this.aula = aula;
	}
	
	public String getNomeEvento() {
		return nomeEvento;
	}
	
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
}
