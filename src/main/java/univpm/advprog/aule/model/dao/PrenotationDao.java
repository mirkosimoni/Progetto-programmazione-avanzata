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
	
	List<Prenotation> findByAula(Aula aula);
	
	List<Prenotation> findByDate(DateTime data);
	
	List<Prenotation> findByUser(User user);
	
	List<Prenotation> findPrenotations(String cognome, String nome, String quota, String nomeAula);
	
	List<Prenotation> findPrenotationsData(String cognome, String nome, String quota, String nomeAula, DateTime data);
	
	List<Prenotation> findPrenotationsDataOra(String cognome, String nome, String quota, String nomeAula, DateTime dataOra);
	
	List<Prenotation> findPrenotationsRange(String cognome, String nome, String quota, String nomeAula, DateTime oraInizio, DateTime oraFine);
	
	
	Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note);
	
	Prenotation update(Prenotation prenotation);
	
	void delete(Prenotation prenotation);
	
}
