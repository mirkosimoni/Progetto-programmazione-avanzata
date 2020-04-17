package univpm.advprog.aule.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.User;
import univpm.advprog.aule.utils.PrenotationsOverlapFinder;
import univpm.advprog.aule.model.entities.Aula;

@Transactional
@Repository("prenotationDao")
public class PrenotationDaoDefault extends DefaultDao implements PrenotationDao {

	@Override
	public List<Prenotation> findAll() {
		return getSession().
				createQuery("from Prenotation p", Prenotation.class).
				getResultList();
	}
	
	//Tutte le prenotazioni
	@Override
	public Prenotation findById(Long id) {
		return getSession().find(Prenotation.class, id);
	}
	
	//Elimina la prenotazione
	@Override
	public void delete(Prenotation prenotation) {
		this.getSession().delete(prenotation);
	}
	
	//Crea prenotazione
	@Override
	public Prenotation create(DateTime oraInizio, DateTime oraFine, User user, Aula aula, String nomeEvento, String note) {
		
		Prenotation prenotation = new Prenotation();
		prenotation.setOraInizio(oraInizio);
		prenotation.setOraFine(oraFine);
		prenotation.setUser(user);
		prenotation.setAula(aula);
		prenotation.setNomeEvento(nomeEvento);
		prenotation.setNote(note);
		this.getSession().save(prenotation);
		return prenotation;
	}
	
	//Aggiorna prenotazione
	@Override 
	public Prenotation update(Prenotation prenotation) {
		return(Prenotation)this.getSession().merge(prenotation);
	}

	//Tutte le prenotazioni per quell'aula, ordinate cronologicamente
	@Override
	public List<Prenotation> findByAula(Aula aula) {
		return this.getSession().createQuery("FROM Prenotation p JOIN FETCH p.aula WHERE p.aula= :aula ORDER BY p.oraInizio", Prenotation.class).
				setParameter("aula", aula).getResultList();
				
	}
	
	//Tutte le prenotazioni in una data, ordinate cronologicamente
	@Override
	public List<Prenotation> findByDate(DateTime data) {
		
		DateTime inizio = new DateTime(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth(), 0, 0, 0);
		DateTime fine = new DateTime(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth(), 23, 59, 59);
		
		return this.getSession().createQuery("FROM Prenotation p WHERE p.oraInizio between :inizio AND :fine ORDER BY p.oraInizio", Prenotation.class).
				setParameter("inizio", inizio).setParameter("fine", fine).getResultList();
	}

	//Lista prenotazioni con date caratteristiche, a partire dall'ora in cui viene fatta la richiesta
	@Override
	public List<Prenotation> findPrenotations(String cognome, String nome, Aula aula) {
		
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Prenotation> cr = cb.createQuery(Prenotation.class);
		Root<Prenotation> root = cr.from(Prenotation.class);
		cr.select(root);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(cognome != null) {
			predicates.add(cb.equal(root.get("user").get("profile").get("cognome"), cognome));
			System.out.println("Cognome != null");
		}
		if(nome != null) {
			predicates.add(cb.equal(root.get("user").get("profile").get("nome"), nome));
			System.out.println("Nome != null");
		}
		if(aula != null) {
			predicates.add(cb.equal(root.get("aula"), aula));
			System.out.println("Aula != null");
		}
		
		DateTime now = DateTime.now();
		predicates.add(cb.greaterThanOrEqualTo(root.get("oraInizio"), now));
		
		cr.where(predicates.toArray(new Predicate[]{}));
		return this.getSession().createQuery(cr).getResultList();
		
	}

	//Lista delle prenotazioni con date caratteristiche e in una specifica data
	@Override
	public List<Prenotation> findPrenotationsData(String cognome, String nome, Aula aula, DateTime data) {
		
		if(data == null)
			return this.findPrenotations(cognome, cognome, aula);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		DateTime inizio = new DateTime(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth(), 0, 0);
		DateTime fine = new DateTime(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth(), 23, 59);
		
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Prenotation> cr = cb.createQuery(Prenotation.class);
		Root<Prenotation> root = cr.from(Prenotation.class);
		cr.select(root);
	
		if(cognome != null)
			predicates.add(cb.equal(root.get("user").get("profile").get("cognome"), cognome));
		if(nome != null)
			predicates.add(cb.equal(root.get("user").get("profile").get("nome"), nome));
		if(aula != null)
			predicates.add(cb.equal(root.get("aula"), aula));
		
		predicates.add(cb.between(root.get("oraInizio"), inizio, fine));
		
		cr.where(predicates.toArray(new Predicate[]{}));
		return this.getSession().createQuery(cr).getResultList();
	}

	//Lista delle prenotazioni con date caratteristiche in un determinato rage 
	@Override
	public List<Prenotation> findPrenotationsRange(String cognome, String nome, Aula aula, DateTime oraInizio,
			DateTime oraFine) {
		
		if(oraFine == null)
			return this.findPrenotationsData(cognome, nome, aula, oraInizio);
		
		if(oraInizio.getYear() != oraFine.getYear() || oraInizio.getDayOfMonth() != oraFine.getDayOfMonth() || oraInizio.getMonthOfYear() != oraFine.getMonthOfYear()) {
			this.findPrenotationsData(cognome, nome, aula, oraInizio);
		}
		
		List<Prenotation> prenotazioniData = this.findPrenotationsData(nome, cognome, aula, oraInizio);
		List<Prenotation> prenotazioniResult = new ArrayList<Prenotation>();
		PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();
		
		Prenotation dummyPrenotation = new Prenotation();
		dummyPrenotation.setOraInizio(oraInizio);
		dummyPrenotation.setOraFine(oraFine);
		
		for(Prenotation p : prenotazioniData) {
			if(overlapFinder.areOverlapped(dummyPrenotation, p))
				prenotazioniResult.add(p);
		}
		
		return prenotazioniResult;
	}

	@Override
	public List<Prenotation> findPrenotationsDataOra(String cognome, String nome, Aula aula, DateTime dataOra) {
		
		if(dataOra == null)
			return this.findPrenotations(cognome, cognome, aula);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		DateTime fine = new DateTime(dataOra.getYear(), dataOra.getMonthOfYear(), dataOra.getDayOfMonth(), 23, 59);
		
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Prenotation> cr = cb.createQuery(Prenotation.class);
		Root<Prenotation> root = cr.from(Prenotation.class);
		cr.select(root);
	
		if(cognome != null)
			predicates.add(cb.equal(root.get("user").get("profile").get("cognome"), cognome));
		if(nome != null)
			predicates.add(cb.equal(root.get("user").get("profile").get("nome"), nome));
		if(aula != null)
			predicates.add(cb.equal(root.get("aula"), aula));
		
		predicates.add(cb.between(root.get("oraInizio"), dataOra, fine));
		
		cr.where(predicates.toArray(new Predicate[]{}));
		return this.getSession().createQuery(cr).getResultList();
	}
	
}
















