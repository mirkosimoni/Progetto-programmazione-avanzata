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
@Table(name="prenotation")
public class Prenotation implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORA_INIZIO", nullable = false)
	private DateTime oraInizio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORA_FINE", nullable = false)
	private DateTime oraFine;
	
	@ManyToOne
	@JoinColumn(name = "USERNAME", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "ID", nullable = false)
	private Aula aula;
	
	@Column(name = "NOME_EVENTO", nullable = false)
	private String nomeEvento;
	
	@Column(name = "NOTE")
	private String note;
	
	@Version
	@Column(name = "VERSION")
	private int version;
	
	

	public Long getId() {
		return this.id;
	}

	
	public int getVersion() {
		return version;
	}
	
	
	//TODO controllare valore di TIMESTAMP sul db
	public DateTime getOraInizio() {
		return this.oraInizio;
	}
	
	public void setOraInizio(DateTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	
	
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
