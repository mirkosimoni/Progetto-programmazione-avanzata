package univpm.advprog.aule.utils;

import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import univpm.advprog.aule.model.entities.Prenotation;

@Component
public class PrenotationsOverlapFinder {

	public boolean areOverlapped(Prenotation p1, Prenotation p2) {
		
		
		Interval interval1 = new Interval(p1.getOraInizio(), p1.getOraFine());
		Interval interval2 = new Interval(p2.getOraInizio(), p2.getOraFine());
		
		return interval1.overlaps(interval2);		
	}
}
