package univpm.advprog.aule.services;

import java.util.List;

import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.User;
import univpm.advprog.aule.utils.PrenotationsOverlapFinder;
import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.dao.PrenotationDao;

@Transactional
@Service("prenotationService")
public class PrenotationServiceDefault implements PrenotationService {
	
	PrenotationDao prenotationRepository;
	AulaDao aulaRepository;
	PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();

	@Override
	public List<Prenotation> findAll() {
		return this.prenotationRepository.findAll();
	}

	@Override
	public Prenotation findById(Long id) {
		return this.prenotationRepository.findById(id);
	}

	@Override
	public List<Prenotation> findByAula(Aula aula) {
		return  this.prenotationRepository.findByAula(aula);
	}


	//Aggiungere controlli sull'orario, inoltre la data di inizio e fine deve essere la stessa
	//Devono essere rispettati gli orari di apertura dell'università (forse meglio nel controller?)
	@Override
	public Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note) {
		
		List<Prenotation> prenotazioniData = this.prenotationRepository.findByDate(oraInizio);
		boolean overlapped = false;
		
		Prenotation prenotation = new Prenotation();
		prenotation.setOraInizio(oraInizio);
		prenotation.setOraFine(oraFine);
		
		for(Prenotation p : prenotazioniData) {
			if(this.overlapFinder.areOverlapped(prenotation, p))
					overlapped = true;
		}
		
		if(overlapped) {
			return this.prenotationRepository.create(oraInizio, oraFine, user, aula, nomeEvento, note);
		}
		else return null;
	}

	//Aggiungere controlli sull'orario, inoltre la data di inizio e fine deve essere la stessa
	//Devono essere rispettati gli orari di apertura dell'università (forse meglio nel controller?)
	@Override
	public Prenotation update(Prenotation prenotation) {
		return this.prenotationRepository.update(prenotation);
	}

	@Override
	public void delete(Long id) {
		Prenotation p = this.prenotationRepository.findById(id);
		this.prenotationRepository.delete(p);
	}


	@Override
	public Prenotation findByAulaOra(Aula aula, DateTime oraInizio) {
		return this.prenotationRepository.findByAulaOra(aula, oraInizio);
	}

	@Override
	public void delete(Aula aula, DateTime oraInizio) {
		Prenotation p = this.prenotationRepository.findByAulaOra(aula, oraInizio);
		this.prenotationRepository.delete(p);
	}
	
	@Autowired
	public void setPrenotationRepository(PrenotationDao prenotationRepository) {
		this.prenotationRepository = prenotationRepository;
	}

	@Override
	public List<Prenotation> findByDate(DateTime data) {
		return this.prenotationRepository.findByDate(data);
	}

}
