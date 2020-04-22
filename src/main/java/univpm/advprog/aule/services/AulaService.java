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
	
	void delete(Long id);
	
	List<Aula> findAule(int quota, String nome, int minimoPosti, Boolean presentiPrese);
	
	List<Aula> findAuleLibere(DateTime oraInizio, DateTime oraFine, int quota, String nome, int minimoPosti, Boolean presentiPrese);
	
	List<String> findName();
	
	List<String> findQuota();
}
