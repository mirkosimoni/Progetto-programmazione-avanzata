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
		
		if(nome == null || quota < 0 || numeroPosti < 0)
			return null;
		
		return aulaRepository.create(nome, quota, numeroPosti, presentiPrese);
	}

	@Override
	public Aula update(Aula aula) {
		if(aula.getNome() == null || aula.getQuota() < 0 || aula.getNumeroPosti() < 0)
			return null;
		
		return aulaRepository.update(aula);
	}

	@Override
	public void delete(Aula aula) {
		aulaRepository.delete(aula);
	}

	@Override
	public List<Aula> findAule(int quota, String nome, int minimoPosti, Boolean presentiPrese) {
		return this.aulaRepository.findAule(quota, nome, minimoPosti, presentiPrese);
	}

	@Override
	public List<Aula> findAuleLibere(DateTime oraInizio, DateTime oraFine, int quota, String nome, int minimoPosti,
			Boolean presentiPrese) {
		
		return this.aulaRepository.findAuleLibere(oraInizio, oraFine, quota, nome, minimoPosti, presentiPrese);
	}


}
