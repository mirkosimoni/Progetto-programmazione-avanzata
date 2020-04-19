package univpm.advprog.aule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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

	@Test
	void testAddProfile() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDao userDao = ctx.getBean("userDao", UserDao.class);
			ProfileDao profileDao = ctx.getBean("profileDao", ProfileDao.class);
			Session s = sf.openSession();
			userDao.setSession(s);
			profileDao.setSession(s);
			
			s.beginTransaction();
			Profile profile1 = new Profile(); 
			profile1.setCognome("Simoni"); profile1.setNome("Mirko"); profile1.setDataNascita(LocalDate.of(1990, 1, 1));
			profile1 = profileDao.create(profile1);
			
			User user1 = userDao.create("Mirko", "12345", true, null);
			s.getTransaction().commit();
			
			s.beginTransaction();
			Profile profile = user1.getProfile();
			assertEquals(profile, null);
			user1.setProfile(profile1);
			userDao.update(user1);
			s.getTransaction().commit();
			
			s.beginTransaction();
			User user2 = userDao.findUserByUsername(user1.getUsername());
			Profile profile2 = user2.getProfile();
			assertEquals(profile1, profile2);
			s.getTransaction().commit();
		}
	}
	
	@Test
	void testAddRoles() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDao userDao = ctx.getBean("userDao", UserDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			Session s = sf.openSession();
			userDao.setSession(s);
			
			s.beginTransaction();
			User user1 = userDao.create("Mirko", "12345", true, null);
			s.getTransaction().commit();
			
			s.beginTransaction();
			User user2 = userDao.findUserByUsername(user1.getUsername());
			assertEquals(user2.getRoles(), null);
			Role admin = roleDao.create("Admin");
			Set<Role> setRole = new HashSet();
			setRole.add(admin);
			user2.setRoles(setRole);
			userDao.update(user2);
			s.getTransaction().commit();
			
			s.beginTransaction();
			User user3 = userDao.findUserByUsername(user1.getUsername());
			assertEquals(user3, user2);
			assertEquals(user3.getRoles().size(), 1);
			s.getTransaction().commit();
			
		}
	}

}
