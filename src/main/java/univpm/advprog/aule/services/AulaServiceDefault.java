package univpm.advprog.aule.services;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.dao.PrenotationDao;
import univpm.advprog.aule.model.entities.Aula;

@Transactional
@Service("aulaService")
public class AulaServiceDefault implements AulaService {
	
	AulaDao aulaRepository;
	PrenotationDao prenotationRepository;

	@Override
	public List<Aula> findAll() {
		return aulaRepository.findAll();
	}

	@Override
	public Aula findByNameQuota(String nome, int quota) {
		return aulaRepository.findByNomeQuota(nome, quota);
	}

	@Override
	public Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese) {
		return aulaRepository.create(nome, quota, numeroPosti, presentiPrese);
	}

	@Override
	public Aula update(Aula aula) {
		return aulaRepository.update(aula);
	}

	@Override
	public void delete(Aula aula) {
		aulaRepository.delete(aula);
	}


	@Override
	public List<Aula> findLibere(DateTime oraInizio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLibere(DateTime oraInizio, DateTime oraFine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLiberePosti(DateTime oraInizio, int minimoPosti) {
		List<Aula> temp = aulaRepository.findAulePosti(minimoPosti);
		
		//Filtraggio per giorno 
		
		return null;
	}

	@Override
	public List<Aula> findLiberePosti(DateTime oraInizio, DateTime oraFine, int minimoPosti) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLiberePrese(DateTime oraInizio, boolean presentiPrese) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLiberePrese(DateTime oraInizio, DateTime oraFine, boolean presentiPrese) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLibereQuota(DateTime oraInizio, int quota) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLibereQuota(DateTime oraInizio, DateTime oraFine, int quota) {
		// TODO Auto-generated method stub
		return null;
	}

}
