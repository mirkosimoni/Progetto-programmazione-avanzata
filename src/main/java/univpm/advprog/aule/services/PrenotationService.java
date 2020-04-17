package univpm.advprog.aule.services;

import java.util.List;
import org.joda.time.DateTime;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.User;;

public interface PrenotationService {
	
	List<Prenotation> findAll();
	
	Prenotation findById(Long id);
	
	List<Prenotation> findByAula(Aula aula);
	
	List<Prenotation> findByDate(DateTime data);
	
	List<Prenotation> findPrenotations(String cognome, String nome, Aula aula);
	
	List<Prenotation> findPrenotationsData(String cognome, String nome, Aula aula, DateTime data);
	
	List<Prenotation> findPrenotationsDataOra(String cognome, String nome, Aula aula, DateTime dataOra);
	
	List<Prenotation> findPrenotationsRange(String cognome, String nome, Aula aula, DateTime oraInizio, DateTime oraFine);
	
	Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note);
	
	Prenotation update(Prenotation prenotation);
	
	void delete(Long id);
	
	//void delete(Aula aula, DateTime oraInizio);
}
