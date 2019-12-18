package univpm.advprog.aule.model.entities;

import java.io.Serializable;
import java.util.Set;

public class Aula implements Serializable {
	
	private long id;
	private int numeroPosti;
	private boolean presentiPrese;
	private Set<Prenotation> prenotazioni;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getNumeroPosti() {
		return numeroPosti;
	}
	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}
	public boolean isPresentiPrese() {
		return presentiPrese;
	}
	public void setPresentiPrese(boolean presentiPrese) {
		this.presentiPrese = presentiPrese;
	}
	public Set<Prenotation> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(Set<Prenotation> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
}
