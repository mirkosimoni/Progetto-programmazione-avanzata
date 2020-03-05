package univpm.advprog.aule.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.dao.PrenotationDao;
import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;

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
		
		List<Aula> aule = aulaRepository.findAll();
		List<Aula> libere = new ArrayList<Aula>();
		
		for(Aula a : aule) {
			Set<Prenotation> prenotazioni = a.getPrenotazioni();
			boolean occupato = false;
			
			for(Prenotation p : prenotazioni) {
				
				//Caso in cui l'orario sia in mezzo ad una prenotazione effettuata
				if(p.getOraInizio().isBefore(oraInizio) && p.getOraFine().isAfter(oraInizio))
					occupato = true;
				
				//Caso in cui un'altra prenotazione inizia in questo  momento
				if(p.getOraInizio().equals(oraInizio))
					occupato = true;
				
				//Caso in cui è libera per meno di 30 minuti
				Duration intervallo = new Duration(oraInizio, p.getOraInizio());
				long minuti = intervallo.getStandardMinutes();
				if(minuti < 30)
					occupato = true;
			}
			
			if(!occupato)
				libere.add(a);
		}
		
		return libere;
	}

	@Override
	public List<Aula> findLibere(DateTime oraInizio, DateTime oraFine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLiberePosti(DateTime oraInizio, int minimoPosti) {
		List<Aula> aule = aulaRepository.findAulePosti(minimoPosti);
		List<Aula> libere = new ArrayList<Aula>();
		
		for(Aula a : aule) {
			Set<Prenotation> prenotazioni = a.getPrenotazioni();
			boolean occupato = false;
			
			for(Prenotation p : prenotazioni) {
				
				//Caso in cui l'orario sia in mezzo ad una prenotazione effettuata
				if(p.getOraInizio().isBefore(oraInizio) && p.getOraFine().isAfter(oraInizio))
					occupato = true;
				
				//Caso in cui un'altra prenotazione inizia in questo  momento
				if(p.getOraInizio().equals(oraInizio))
					occupato = true;
				
				//Caso in cui è libera per meno di 30 minuti
				Duration intervallo = new Duration(oraInizio, p.getOraInizio());
				long minuti = intervallo.getStandardMinutes();
				if(minuti < 30)
					occupato = true;
			}
			
			if(!occupato)
				libere.add(a);
		}
		
		return libere;
	}

	@Override
	public List<Aula> findLiberePosti(DateTime oraInizio, DateTime oraFine, int minimoPosti) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLiberePrese(DateTime oraInizio) {
		
		List<Aula> aule = aulaRepository.findAulePrese();
		List<Aula> libere = new ArrayList<Aula>();
		
		for(Aula a : aule) {
			Set<Prenotation> prenotazioni = a.getPrenotazioni();
			boolean occupato = false;
			
			for(Prenotation p : prenotazioni) {
				
				//Caso in cui l'orario sia in mezzo ad una prenotazione effettuata
				if(p.getOraInizio().isBefore(oraInizio) && p.getOraFine().isAfter(oraInizio))
					occupato = true;
				
				//Caso in cui un'altra prenotazione inizia in questo  momento
				if(p.getOraInizio().equals(oraInizio))
					occupato = true;
				
				//Caso in cui è libera per meno di 30 minuti
				Duration intervallo = new Duration(oraInizio, p.getOraInizio());
				long minuti = intervallo.getStandardMinutes();
				if(minuti < 30)
					occupato = true;
			}
			
			if(!occupato)
				libere.add(a);
		}
		return libere;
	}

	@Override
	public List<Aula> findLiberePrese(DateTime oraInizio, DateTime oraFine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aula> findLibereQuota(DateTime oraInizio, int quota) {
		List<Aula> aule = aulaRepository.findAuleQuota(quota);
		List<Aula> libere = new ArrayList<Aula>();
		
		for(Aula a : aule) {
			Set<Prenotation> prenotazioni = a.getPrenotazioni();
			boolean occupato = false;
			
			for(Prenotation p : prenotazioni) {
				
				//Caso in cui l'orario sia in mezzo ad una prenotazione effettuata
				if(p.getOraInizio().isBefore(oraInizio) && p.getOraFine().isAfter(oraInizio))
					occupato = true;
				
				//Caso in cui un'altra prenotazione inizia in questo  momento
				if(p.getOraInizio().equals(oraInizio))
					occupato = true;
				
				//Caso in cui è libera per meno di 30 minuti
				Duration intervallo = new Duration(oraInizio, p.getOraInizio());
				long minuti = intervallo.getStandardMinutes();
				if(minuti < 30)
					occupato = true;
			}
			
			if(!occupato)
				libere.add(a);
		}
		
		return libere;
	}

	@Override
	public List<Aula> findLibereQuota(DateTime oraInizio, DateTime oraFine, int quota) {
		// TODO Auto-generated method stub
		return null;
	}


}
