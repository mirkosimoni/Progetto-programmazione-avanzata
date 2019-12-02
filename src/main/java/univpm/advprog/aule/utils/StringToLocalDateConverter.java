package univpm.advprog.aule.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(String source) {
		LocalDate res = null;
		
		try {
			res = LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			try {
				res = LocalDate.parse(source, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			} catch (DateTimeParseException e1) {
				// nothing to do
			}
		}
		
		return res;
	}
		
}
