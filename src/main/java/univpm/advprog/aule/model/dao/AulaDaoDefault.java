package univpm.advprog.aule.model.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;

public class AulaDaoDefault extends DefaultDao implements AulaDao {

	@Override
	public List<Aula> findAll() {
		return getSession().
				createQuery("from Aula a", Aula.class).
				getResultList();
	}

	@Override
	public Aula findById(long id) {
		return getSession().find(Aula.class, id);
	}

	@Override
	public Aula create(int numeroPosti, boolean presentiPrese) {
		Aula a = new Aula();
		a.setNumeroPosti(numeroPosti);
		a.setPresentiPrese(presentiPrese);
		
		this.getSession().save(a);
		
		return a;
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
		Query q = this.getSession().createQuery("From Prenotation p JOIN FETCH p.aula WHERE p.aula :aula", Prenotation.class);
		return new HashSet<Prenotation>(q.setParameter("aula", aula).getResultList());
	}

}
