package univpm.advprog.aule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import univpm.advprog.aule.model.dao.*;
import univpm.advprog.aule.model.entities.*;
import univpm.advprog.aule.test.DataServiceConfigTest;


public class TestUserDao {
	
	private static Validator validator;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	void testPassword() {
		User u1= new User();
		u1.setUsername("Username");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(u1);
		assertEquals(1, constraintViolations.size());
		assertEquals("La password non pu� essere nulla!", constraintViolations.iterator().next().getMessage());
	}

}
