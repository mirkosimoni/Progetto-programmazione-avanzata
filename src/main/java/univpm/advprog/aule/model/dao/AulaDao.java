package univpm.advprog.aule.model.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.joda.time.DateTime;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;

public interface AulaDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Aula> findAll();
	
	Aula findById(long id);
	
	Aula findByNomeQuota(String nome, int quota);
	
	Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese);
	
	Aula update(Aula aula);
	
	void delete(Aula aula);
	
	Set<Prenotation> getPrenotazioni(Aula aula);
	
	List<Aula> findAulePosti(int minimoPosti);
	
	List<Aula> findAuleQuota(int quota);
	
	List<Aula> findAulePrese();
	
	List<Aula> findAule(int quota, int minimoPosti, Boolean presentiPrese);
	
}
