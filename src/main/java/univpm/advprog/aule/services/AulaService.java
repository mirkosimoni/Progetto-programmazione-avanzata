package univpm.advprog.aule.services;

import java.util.List;

import org.joda.time.DateTime;

import univpm.advprog.aule.model.entities.Aula;

public interface AulaService {

	List<Aula> findAll();
	
	Aula findByNameQuota(String nome, int quota);
	
	Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese);
	
	Aula update(Aula aula);
	
	void delete(Aula aula);
	
	List<Aula> findLibere(DateTime oraInizio); //
	
	List<Aula> findLibere(DateTime oraInizio, DateTime oraFine);
	
	List<Aula> findLiberePosti(DateTime oraInizio, int minimoPosti);
	
	List<Aula> findLiberePosti(DateTime oraInizio, DateTime oraFine, int minimoPosti);
	
	List<Aula> findLiberePrese(DateTime oraInizio);
	
	List<Aula> findLiberePrese(DateTime oraInizio, DateTime oraFine);
	
	List<Aula> findLibereQuota(DateTime oraInizio, int quota);
	
	List<Aula> findLibereQuota(DateTime oraInizio, DateTime oraFine, int quota);
	
	
}
