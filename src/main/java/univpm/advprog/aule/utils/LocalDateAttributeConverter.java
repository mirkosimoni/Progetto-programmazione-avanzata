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
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate locDate) {
		
		return locDate == null ? null : Date.valueOf(locDate);
	}
	
	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		return sqlDate == null ? null : sqlDate.toLocalDate();
	}
}
