package univpm.advprog.aule.utils;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This class shows an example of how to map a "custom" type (java.time.LocalDate) onto some existing sql date (java.sql.Date). This mechanism
 * can be used also in order to map some user defined data type. 
 * 
 * @author spegni
 *
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter2 implements AttributeConverter<LocalDate, String> {

	@Override
	public String convertToDatabaseColumn(LocalDate locDate) {
		
		return locDate == null ? null : locDate.getYear() + "-" + locDate.getMonthValue() + "-" + locDate.getDayOfMonth();
	}
	
	@Override
	public LocalDate convertToEntityAttribute(String sqlDate) {
		if (sqlDate == null) { return null; }
		
		String[] parts = sqlDate.split("-");
		
		if (parts.length != 3) { throw new RuntimeException("stringa malforamta:" + sqlDate); }
		
		return LocalDate.of(new Integer(parts[0]), new Integer(parts[1]), new Integer(parts[2]));
	}
}
