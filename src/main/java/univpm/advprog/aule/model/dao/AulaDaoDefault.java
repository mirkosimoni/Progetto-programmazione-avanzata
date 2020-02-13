package univpm.advprog.aule.model.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;

@Transactional
@Repository("aulaDao")
public class AulaDaoDefault extends DefaultDao implements AulaDao {
	
	@Autowired
	PrenotationDao prenotationDao;
	
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
		Aula merged = (Aula)this.getSession().merge(aula);
		return merged;
	}

	@Override
	public void delete(Aula aula) {
		this.getSession().delete(aula);
		
	}

	@Override
	public Set<Prenotation> getPrenotazioni(Aula aula) {
		Query q = this.getSession().createQuery("From Prenotation p JOIN FETCH p.aula WHERE p.aula= :aula", Prenotation.class);
		return new HashSet<Prenotation>(q.setParameter("aula", aula).getResultList());
	}

	@Override
	public Aula findByNomeQuota(String nome, int quota) {
		return this.getSession().createQuery("FROM Aula a  WHERE a.nome= :nome AND a.quota = :quota", Aula.class).setParameter("nome",nome).
				setParameter("quota", quota).getSingleResult();
	}
	
	
	@Override
	public Aula create(String nome, int quota, int numeroPosti, boolean presentiPrese) {
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

}
