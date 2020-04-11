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
	
	List<Prenotation> findAll();
	
	Prenotation findById(Long id);
	
	List<Prenotation> findByAula(Aula aula);
	
	List<Prenotation> findByDate(DateTime data);
	
	Prenotation findByAulaOra(Aula aula, DateTime oraInizio);
	
	Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note);
	
	Prenotation update(Prenotation prenotation);
	
	void delete(Prenotation prenotation);
	
}
