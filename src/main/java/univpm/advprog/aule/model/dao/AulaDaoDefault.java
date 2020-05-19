package univpm.advprog.aule.model.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.utils.PrenotationsOverlapFinder;

@Transactional
@Repository("aulaDao")
public class AulaDaoDefault extends DefaultDao implements AulaDao {  
	
	PrenotationDao prenotationDao;
	
	@Autowired
	public void setPrenotationDao (PrenotationDao prenotationDao) {
		this.prenotationDao = prenotationDao;
	}
	
	@Override
	public List<Aula> findAll() {		//Ritorna lista con tutte le aule esistenti ordinate per quota
		return getSession().
				createQuery("from Aula a order by a.quota", Aula.class).
				getResultList();
	}

	@Override
	public Aula findById(long id) {				//Ritorna aula il cui "id" combaci con quello richiesto
		return this.getSession().find(Aula.class, id);
	}


	@Override
	public Aula update(Aula aula) {				//Ritorna la pagina delle aule aggiornata
		Aula merged = (Aula)this.getSession().merge(aula);
		return merged;
	}

	@Override
	public void delete(Aula aula) {				//Elimini l'aula passata come parametro al metodo
		this.getSession().delete(aula);
	}
	
	@Override
	public void delete(Long id) { 				//Uguale ma passando l'id dell'aula
		Aula aula = this.findById(id);
		this.getSession().delete(aula);
	}

	@SuppressWarnings("unchecked")
	@Override 
	public Set<Prenotation> getPrenotazioni(Aula aula) {			//Ritorna prenotazioni effettuate sull'aula passata per parametro 
		Query q = this.getSession().createQuery("From Prenotation p JOIN FETCH p.aula WHERE p.aula= :aula", Prenotation.class);
		return new HashSet<Prenotation>(q.setParameter("aula", aula).getResultList());
	}

	@Override
	public Aula findByNomeQuota(String nome, int quota) {			//Ritorna aula dal nome e quota corrispondente nel database se esiste, altrimenti ritorna NULL
		try {
		return this.getSession().createQuery("FROM Aula a  WHERE a.nome= :nome AND a.quota = :quota", Aula.class).setParameter("nome",nome).
				setParameter("quota", quota).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public List <String> findNome() {			//Ritorna i nomi (saltando i doppioni) delle aule ordinati alfabeticamente
		return this.getSession().createQuery("SELECT DISTINCT (a.nome) FROM Aula a ORDER BY a.nome", String.class).getResultList();
	}
	
	@Override
	public List<String> findQuota() {			//Ritorna le quote (saltando i doppioni) delle aule ordinate in verso crescente
		try {
			List<String> quote= new ArrayList<String>();
			List<Integer> quoteAule = this.getSession().createQuery("SELECT DISTINCT (a.quota) FROM Aula a ORDER BY a.quota", Integer.class).getResultList();
		
			for(int a: quoteAule)
			
				quote.add(String.valueOf(a)); //conversione quota Nesima da INT a STRING
				
			return quote;
		
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	
	
	
	@Override
	public Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese) {		//Ritorna aula creata coi parametri passati dal richiamante nel caso non sia gi� presente nel DB
		
		List<Aula> aule = this.findAll();
		
		for(Aula a : aule) {
			if(a.getQuota() == quota && nome.toLowerCase().equals(a.getNome().toLowerCase()))
				return null;
		}
		
		Aula a = new Aula();
		a.setNome(nome);
		a.setQuota(quota);
		a.setNumeroPosti(numeroPosti);
		a.setPresentiPrese(presentiPrese);
		this.getSession().save(a);
		return a;
	}

	@Override
	public List<Aula> findAulePosti(int minimoPosti) {			//Ritorna lista di aule con numero di posti maggiore o uguale al minimo passato per parametro
		return this.getSession().createQuery("FROM Aula a WHERE a.numeroPosti >= :minimoPosti", Aula.class).
				setParameter("minimoPosti", minimoPosti).getResultList();
	}

	@Override
	public List<Aula> findAuleQuota(int quota) {			//Ritorna lista di aule presenti in una data quota 
		return this.getSession().createQuery("FROM Aula a WHERE a.quota = :quota", Aula.class).
				setParameter("quota", quota).getResultList();
	}

	@Override
	public List<Aula> findAulePrese() {				//Ritorna lista con tutte le aule dotate di prese
		return this.getSession().createQuery("FROM Aula a WHERE a.presentiPrese = TRUE", Aula.class).getResultList();
	}

	@Override
	public List<Aula> findAule(int quota, String nome, int minimoPosti, Boolean presentiPrese) {		//Fa query con criteri dinamica per usare valori dei campi inseriti nella corrispondente vista 
																										//Ritorna lista di aule che rispettano i criteri di ricerca 
		CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Aula> cr = cb.createQuery(Aula.class);
		Root<Aula> root = cr.from(Aula.class);
		cr.select(root);
		
		List<Predicate> predicates = new ArrayList<Predicate>();

		if(quota > 0)
			predicates.add(cb.equal(root.get("quota"), quota));
		
		if(nome != null)
			predicates.add(cb.equal(root.get("nome"), nome));
		
		if(minimoPosti > 0)
			predicates.add(cb.ge(root.get("numeroPosti"), minimoPosti));
		
		if(presentiPrese != null) {
			if(presentiPrese == true)
				predicates.add(cb.isTrue(root.get("presentiPrese")));
			else predicates.add(cb.isFalse(root.get("presentiPrese")));
		}
		
		cr.where(predicates.toArray(new Predicate[]{}));
		return this.getSession().createQuery(cr).getResultList();
	}

	@Override
	public List<Aula> findAuleLibere(DateTime oraInizio, DateTime oraFine, int quota, String nome, int minimoPosti, Boolean presentiPrese) {		//Ritorna la lista di aule libere, tutte quelle senza prenotazioni presenti nell'ultima mezz'ora.
		
		List<Aula> aule = this.findAule(quota, nome, minimoPosti, presentiPrese);
		List<Aula> auleLibere = new ArrayList<Aula>();
		PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();
		
		Prenotation dummyPrenotation = new Prenotation();
		
		
		if(oraInizio != null && oraFine != null) {			//Implementazione del controllo accessibilit� aula: 	
			dummyPrenotation.setOraInizio(oraInizio);		//Si usa una "prenotazione artificiale" per verificare che non si sovrapponi con una gi� esistente
			dummyPrenotation.setOraFine(oraFine);			//Se si sovrappone vuol dire che l'aula non � libera, altrimenti l'aggiungo assieme alle altre nella lista da ritornare	
		}
		else if(oraInizio != null && oraFine == null) {
			dummyPrenotation.setOraInizio(oraInizio);
			dummyPrenotation.setOraFine(oraInizio.plusMinutes(30));
		}
		else {
			dummyPrenotation.setOraInizio(DateTime.now());
			dummyPrenotation.setOraFine(DateTime.now().plusMinutes(30));
		}
		
		for(Aula a : aule){
			boolean libera = true;

			List<Prenotation> prenotazioniAula = this.prenotationDao.findPrenotationsData(null, null, String.valueOf(a.getQuota()) , a.getNome(), dummyPrenotation.getOraInizio());			
			for(Prenotation p : prenotazioniAula) {
				if(overlapFinder.areOverlapped(p, dummyPrenotation))
					libera = false;
			}
			
			if(libera)
				auleLibere.add(a);
		}
		
		return auleLibere;
		
		
	}

	
}
