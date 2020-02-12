package univpm.advprog.aule.services;

import java.util.List;
import org.joda.time.DateTime;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.User;;

public interface PrenotationService {
	List<Prenotation> findAll();
	Prenotation findById(Long id);
	List<Prenotation> findByAula(String nome, int quota);
	List<Prenotation> findByAulaOra(String nome, int quota, DateTime oraInizio);
	Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note);
	Prenotation update(Prenotation prenotation);
	void delete(Long id);
	void delete(Aula aula, DateTime oraInizio);
	void delete();
	
}
