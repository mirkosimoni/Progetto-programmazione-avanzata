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
	
	public List<Prenotation> findByUser(User user){
		return this.prenotationRepository.findByUser(user);
	}

	@Override
	public Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note) {
		
		//oraInizio e oraFine devono essere nello stesso giorno
		if(oraInizio.getYear() != oraFine.getYear() || oraInizio.getMonthOfYear() != oraFine.getMonthOfYear() ||
				oraInizio.getDayOfMonth() != oraFine.getDayOfMonth())
			return null;
		
		//oraInizio deve precedere oraFine
		if(oraInizio.isAfter(oraFine))
			return null;
		
		List<Prenotation> prenotazioniData = this.prenotationRepository.findPrenotationsData(null, null, String.valueOf(aula.getQuota()), aula.getNome(), oraInizio);
		boolean overlapped = false;
		
		Prenotation prenotation = new Prenotation();
		prenotation.setOraInizio(oraInizio);
		prenotation.setOraFine(oraFine);
		
		for(Prenotation p : prenotazioniData) {
			if(this.overlapFinder.areOverlapped(prenotation, p))
					overlapped = true;
		}
		
		if(!overlapped) {
			return this.prenotationRepository.create(oraInizio, oraFine, user, aula, nomeEvento, note);
		}
		else return null;
	}

	//Aggiungere controlli sull'orario, inoltre la data di inizio e fine deve essere la stessa
	//Devono essere rispettati gli orari di apertura dell'università (forse meglio nel controller?)
	@Override
	public Prenotation update(Prenotation prenotation) {
		
		//oraInizio e oraFine devono essere nello stesso giorno
		if(prenotation.getOraInizio().getYear() != prenotation.getOraFine().getYear() ||
				prenotation.getOraInizio().getMonthOfYear() != prenotation.getOraFine().getMonthOfYear() ||
				prenotation.getOraInizio().getDayOfMonth() != prenotation.getOraFine().getDayOfMonth())
			return null;
		
		//oraInizio deve precedere oraFine
		if(prenotation.getOraInizio().isAfter(prenotation.getOraFine()))
			return null;
		
		List<Prenotation> prenotazioniData = this.prenotationRepository.findPrenotationsData(null, null, String.valueOf(prenotation.getAula().getQuota()), prenotation.getAula().getNome(), prenotation.getOraInizio());
		boolean overlapped = false;
		
		for(Prenotation p : prenotazioniData) {
			if(this.overlapFinder.areOverlapped(prenotation, p) && prenotation.getId() != p.getId())
					overlapped = true;
		}
		
		if(!overlapped) {
			return this.prenotationRepository.update(prenotation);
		}
		else return null;
	}

	@Override
	public void delete(Long id) {
		Prenotation p = this.prenotationRepository.findById(id);
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

	@Override
	public List<Prenotation> findPrenotations(String cognome, String nome, String quota, String nomeAula) {
		
		return this.prenotationRepository.findPrenotations(cognome, nome, quota, nomeAula);
	}

	@Override
	public List<Prenotation> findPrenotationsData(String cognome, String nome, String quota, String nomeAula, DateTime data) {
		
		return this.prenotationRepository.findPrenotationsData(cognome, nome, quota, nomeAula, data);
	}

	@Override
	public List<Prenotation> findPrenotationsRange(String cognome, String nome, String quota, String nomeAula, DateTime oraInizio,
			DateTime oraFine) {
		
		return this.prenotationRepository.findPrenotationsRange(cognome, nome, quota, nomeAula, oraInizio, oraFine);
	}

	@Override
	public List<Prenotation> findPrenotationsDataOra(String cognome, String nome, String quota, String nomeAula, DateTime dataOra) {
		
		return this.prenotationRepository.findPrenotationsDataOra(cognome, nome, quota, nomeAula, dataOra);
	}

	@Override
	public List<Prenotation> findAllFromToday() {
		return this.prenotationRepository.findAllFromToday();
		
	}

}
