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
	public List<Aula> findAll() {
		return getSession().
				createQuery("from Aula a", Aula.class).
				getResultList();
	}

	@Override
	public Aula findById(long id) {
		return this.getSession().find(Aula.class, id);
	}


	@Override
	public Aula update(Aula aula) {
		
		List<Aula> aule = this.findAll();
		
		for(Aula a : aule) {
			if(a.getId() != aula.getId())
				if(a.getQuota() == aula.getQuota() && a.getNome() == a.getNome())
					return null;
		}
		
		Aula merged = (Aula)this.getSession().merge(aula);
		return merged;
	}

	@Override
	public void delete(Aula aula) {
		Set<Prenotation> prenotazioni = aula.getPrenotazioni();
		
		/*
		for(Iterator<Prenotation> it = prenotazioni.iterator(); it.hasNext();) {
			Prenotation toRemove = it.next();
			aula.removePrenotation(toRemove);
			prenotationDao.delete(toRemove);
		}
		*/
		this.getSession().delete(aula);
	}
	
	public void delete(Long id) {
		Aula aula = this.findById(id);
		this.getSession().delete(aula);
	}

	@Override
	public Set<Prenotation> getPrenotazioni(Aula aula) {
		Query q = this.getSession().createQuery("From Prenotation p JOIN FETCH p.aula WHERE p.aula= :aula", Prenotation.class);
		return new HashSet<Prenotation>(q.setParameter("aula", aula).getResultList());
	}

	@Override
	public Aula findByNomeQuota(String nome, int quota) {
		try {
		return this.getSession().createQuery("FROM Aula a  WHERE a.nome= :nome AND a.quota = :quota", Aula.class).setParameter("nome",nome).
				setParameter("quota", quota).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public List <String> findNome() {
		try {
		List<String> nomi= new ArrayList<String>();
		List<Aula> nomiAule = this.getSession().createQuery("SELECT DISTINCT (a.nome) FROM Aula a", Aula.class).getResultList();
		
		for(Aula a: nomiAule)
			nomi.add(a.getNome());
		
		return nomi;
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public List<String> findQuota() {
		try {
			List<String> quote= new ArrayList<String>();
			List<Integer> quoteAule = this.getSession().createQuery("SELECT DISTINCT (a.quota) FROM Aula a", Integer.class).getResultList();
		
			for(int a: quoteAule)
			
				quote.add(String.valueOf(a));
				
			return quote;
		
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	
	
	
	@Override
	public Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese) {
		
		List<Aula> aule = this.findAll();
		
		for(Aula a : aule) {
			if(a.getQuota() == quota && a.getNome() == nome)
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
	public List<Aula> findAulePosti(int minimoPosti) {
		return this.getSession().createQuery("FROM Aula a WHERE a.numeroPosti >= :minimoPosti", Aula.class).
				setParameter("minimoPosti", minimoPosti).getResultList();
	}

	@Override
	public List<Aula> findAuleQuota(int quota) {
		return this.getSession().createQuery("FROM Aula a WHERE a.quota = :quota", Aula.class).
				setParameter("quota", quota).getResultList();
	}

	@Override
	public List<Aula> findAulePrese() {
		return this.getSession().createQuery("FROM Aula a WHERE a.presentiPrese = TRUE", Aula.class).getResultList();
	}

	@Override
	public List<Aula> findAule(int quota, String nome, int minimoPosti, Boolean presentiPrese) {
		
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
	public List<Aula> findAuleLibere(DateTime oraInizio, DateTime oraFine, int quota, String nome, int minimoPosti, Boolean presentiPrese) {
		
		List<Aula> aule = this.findAule(quota, nome, minimoPosti, presentiPrese);
		List<Aula> auleLibere = new ArrayList<Aula>();
		PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();
		
		Prenotation dummyPrenotation = new Prenotation();
		
		//if(oraInizio == null && oraFine == null) {
		//	return aule;
		//}
		
		if(oraInizio != null && oraFine != null) {
			dummyPrenotation.setOraInizio(oraInizio);
			dummyPrenotation.setOraFine(oraFine);
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
			//Prenotazioni AulaData
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
