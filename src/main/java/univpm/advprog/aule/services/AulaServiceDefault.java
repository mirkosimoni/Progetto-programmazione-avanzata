package univpm.advprog.aule.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.dao.PrenotationDao;
import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;

@Transactional
@Service("aulaService")
public class AulaServiceDefault implements AulaService {
	
	PrenotationDao prenotationRepository;
	AulaDao aulaRepository;
	
	@Autowired
	public void setPrenotationRepository(PrenotationDao prenotationRepository) {
		this.prenotationRepository = prenotationRepository;
	}
	
	@Autowired
	public void setAuleRepository(AulaDao auleRepository) {
		this.aulaRepository = auleRepository;
	}

	// Restituisce una lista con tutte le aule
	@Override
	public List<Aula> findAll() {
		return aulaRepository.findAll();
	}

	// Restituisce se presente l'aula con i parametri corrispondenti a quelli passati
	@Override
	public Aula findByNameQuota(String nome, int quota) {
		return aulaRepository.findByNomeQuota(nome, quota);
	}
	
	// Restituisce una lista con tutti i nomi delle aule
	@Override
	public List<String> findName() {
		return aulaRepository.findNome();
	}
	
	// Restituisce una lista con tutte le quote
	@Override
	public List<String> findQuota() {
		return aulaRepository.findQuota();
	}
	
	// Restituisce l'aula con l'ID corrispondente
	@Override
	public Aula findById(Long id) {
		return this.aulaRepository.findById(id);
	}
	
	

	// Creazione di un'aula
	@Override
	public Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese) {
		
		if(nome == null || quota < 0 || numeroPosti < 0)
			return null;
		
		return aulaRepository.create(nome, quota, numeroPosti, presentiPrese);
	}

	// Update di un'aula
	@Override
	public Aula update(Aula aula) {
		if(aula.getNome() == null || aula.getQuota() < 0 || aula.getNumeroPosti() < 0)
			return null;
		
		return aulaRepository.update(aula);
	}

	// Eliminazione di un'aula
	@Override
	public void delete(Aula aula) {
		aulaRepository.delete(aula);
	}

	// Restituisce le aule che matchano con i parametri passati
	// Passando null al posto di un oggetto o -1 al posto di un intero il parametro non verrà considerato nella ricerca
	@Override
	public List<Aula> findAule(int quota, String nome, int minimoPosti, Boolean presentiPrese) {
		return this.aulaRepository.findAule(quota, nome, minimoPosti, presentiPrese);
	}

	// Restituisce le aule libere in un range temporale che matchano con i parametri passati
	// Passando null al posto di un oggetto o -1 al posto di un intero il parametro non verrà considerato nella ricerca
	@Override
	public List<Aula> findAuleLibere(DateTime oraInizio, DateTime oraFine, int quota, String nome, int minimoPosti,
			Boolean presentiPrese) {
		
		return this.aulaRepository.findAuleLibere(oraInizio, oraFine, quota, nome, minimoPosti, presentiPrese);
	}

	// Eliminazione di un'aula
	public void delete(Long id) {
		this.aulaRepository.delete(id);
	}

}
