package univpm.advprog.aule.model.dao;

import java.util.List;
import org.hibernate.Session;

import org.joda.time.DateTime;

import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.User;
import univpm.advprog.aule.model.entities.Aula;

public interface PrenotationDao {
	Session getSession();
	public void setSession(Session session);
	
	//Tutte le prenotazioni
	List<Prenotation> findAll();
	
	Prenotation findById(Long id);
	
	//Tutte le prenotazioni per quell'aula ordinate cronologicamente
	List<Prenotation> findByAula(Aula aula);
	
	//Tutte le prenotazioni in quella data
	List<Prenotation> findByDate(DateTime data);
	
	//Tutte le prenotazioni in un'aula in una data, ordinate cronologicamente
	List<Prenotation> findByAulaDate(Aula aula, DateTime data);
	
	//Lista prenotazioni con date caratteristiche, a partire dall'ora in cui viene fatta la richiesta
	List<Prenotation> findPrenotations(String cognome, String nome, Aula aula);
	
	//Lista delle prenotazioni con date caratteristiche e in una specifica data
	List<Prenotation> findPrenotationsData(String cognome, String nome, Aula aula, DateTime data);
	
	//Lista delle prenotazioni con date caratteristiche in un determinato rage 
	List<Prenotation> findPrenotationsRange(String cognome, String nome, Aula aula, DateTime oraInizio, DateTime oraFine);
	
	//Prenotazioni in una data aula dell'ora specificata
	List<Prenotation> findByAulaOra(Aula aula, DateTime oraInizio);
	
	//Crea prenotazione
	Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note);
	
	//Aggiorna prenotazione
	Prenotation update(Prenotation prenotation);
	
	//Elimina prenotazione
	void delete(Prenotation prenotation);
	
}
