package univpm.advprog.aule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import univpm.advprog.aule.model.dao.*;
import univpm.advprog.aule.model.entities.*;
import univpm.advprog.aule.test.DataServiceConfigTest;

public class TestPrenotationDao {
	
	@Test
	void testBeginCommitTransaction(){
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PrenotationDao prenotationDao = ctx.getBean("prenotationDao", PrenotationDao.class);
			
			Session s = sf.openSession();
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			prenotationDao.setSession(s);
			assertEquals(s, prenotationDao.getSession());
			s.getTransaction().commit();
			
			assertFalse(s.getTransaction().isActive());
		}
	}
	
	// Test metodi ricerca
	@Test
	void searchPrenotazione() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PrenotationDao prenotationDao = ctx.getBean("prenotationDao", PrenotationDao.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			UserDao userDao = ctx.getBean("userDao", UserDao.class);
			ProfileDao profileDao = ctx.getBean("profileDao", ProfileDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s = sf.openSession();
			prenotationDao.setSession(s);
			aulaDao.setSession(s);
			profileDao.setSession(s);
			roleDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			//Creazione utente
			Role admin = roleDao.create("Admin");
			Role utente = roleDao.create("User");
			Set<Role> setRole = new HashSet();
			setRole.add(admin);
			setRole.add(utente);
			
			Profile profile1 = new Profile(); 
			profile1.setCognome("Cognome1"); profile1.setNome("Nome1"); profile1.setDataNascita(LocalDate.of(1990, 1, 1));
			profile1 = profileDao.create(profile1);
			User user1 = userDao.create("username1", "12345", true, setRole);
			user1.setProfile(profile1);
			user1.setRoles(setRole);
			userDao.update(user1);
			
			Profile profile2 = new Profile(); 
			profile2.setCognome("Cognome2"); profile2.setNome("Nome2"); profile2.setDataNascita(LocalDate.of(1990, 1, 1));
			profile2 = profileDao.create(profile2);
			User user2 = userDao.create("username2", "12345", true, setRole);
			user2.setProfile(profile2);
			user2.setRoles(setRole);
			userDao.update(user2);
				
			//Creazione aula
			Aula aula1 = aulaDao.create("D1", 155, 20, true);
			Aula aula2 = aulaDao.create("D2", 155, 20, true);
			
			//Creazione prenotazioni
			DateTime inizio1 = new DateTime(2020, 7, 1, 10, 0, 0);
			DateTime fine1 = new DateTime(2020, 7, 1, 13, 0, 0);
			Prenotation prenotation1 = prenotationDao.create(inizio1, fine1, user1, aula1, "evento1", "note1");
			
			DateTime inizio2 = new DateTime(2020, 7, 1, 14, 0, 0);
			DateTime fine2 = new DateTime(2020, 7, 1, 17, 0, 0);
			Prenotation prenotation2 = prenotationDao.create(inizio2, fine2, user1, aula1, "evento2", "note2");
			
			DateTime inizio3 = new DateTime(2020, 7, 2, 14, 0, 0);
			DateTime fine3 = new DateTime(2020, 7, 2, 17, 0, 0);
			Prenotation prenotation3 = prenotationDao.create(inizio3, fine3, user2, aula2, "evento3", "note3");
			
			DateTime inizio4 = new DateTime(2020, 7, 1, 10, 0, 0);
			DateTime fine4 = new DateTime(2020, 7, 1, 14, 0, 0);
			Prenotation prenotation4 = prenotationDao.create(inizio4, fine4, user1, aula2, "evento4", "note4");
			s.getTransaction().commit();
			
			
			s.beginTransaction();
			List<Prenotation> prenotazioni;
			
			prenotazioni = prenotationDao.findByAula(aula1);
			assertEquals(prenotazioni.size(), 2);
			
			prenotazioni = prenotationDao.findByDate(inizio1);
			assertEquals(prenotazioni.size(), 3);
			
			prenotazioni = prenotationDao.findByUser(user1);
			assertEquals(prenotazioni.size(), 3);
			
			for(Prenotation p: prenotazioni)
				System.out.println(p.getOraInizio());
			
			prenotazioni = prenotationDao.findPrenotations(null, null, null, aula1.getNome());
			assertEquals(prenotazioni.size(), 2);
			
			prenotazioni = prenotationDao.findPrenotations(null, null, String.valueOf(aula1.getQuota()), null);
			assertEquals(prenotazioni.size(), 4);
			
			prenotazioni = prenotationDao.findPrenotations("Cognome1", null, null, null);
			assertEquals(prenotazioni.size(), 3);
			
			prenotazioni = prenotationDao.findPrenotations(null, "Nome1", null, null);
			assertEquals(prenotazioni.size(), 3);
			
			prenotazioni = prenotationDao.findPrenotations("Cognome2", null, null, null);
			assertEquals(prenotazioni.size(), 1);
			
			prenotazioni = prenotationDao.findPrenotations(null, "Nome2", null, null);
			assertEquals(prenotazioni.size(), 1);
			
			prenotazioni = prenotationDao.findPrenotationsData(null, null, null, null, inizio3);
			assertEquals(prenotazioni.size(), 1);
			
			prenotazioni = prenotationDao.findPrenotationsDataOra(null, null, null, null, inizio1);
			assertEquals(prenotazioni.size(), 3);
			
			DateTime range1 = new DateTime(2020, 7, 1, 11, 0, 0);
			DateTime range2 = new DateTime(2020, 7, 1, 13, 0, 0);
			prenotazioni = prenotationDao.findPrenotationsRange(null, null, null, null, range1, range2);
			assertEquals(prenotazioni.size(), 2);
			
			s.getTransaction().commit();
		}
		
	}
	
	// Test creazione e modifica di una prenotazione
	@Test
	void createAndUpdatePrenotazione() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PrenotationDao prenotationDao = ctx.getBean("prenotationDao", PrenotationDao.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			UserDao userDao = ctx.getBean("userDao", UserDao.class);
			ProfileDao profileDao = ctx.getBean("profileDao", ProfileDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s = sf.openSession();
			prenotationDao.setSession(s);
			aulaDao.setSession(s);
			profileDao.setSession(s);
			roleDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			//Creazione utente
			Role admin = roleDao.create("Admin");
			Role utente = roleDao.create("User");
			Set<Role> setRole = new HashSet();
			setRole.add(admin);
			setRole.add(utente);
			Profile profile1 = new Profile(); 
			profile1.setCognome("Simoni"); profile1.setNome("Mirko"); profile1.setDataNascita(LocalDate.of(1990, 1, 1));
			profile1 = profileDao.create(profile1);
			User user1 = userDao.create("username1", "12345", true, setRole);
			user1.setProfile(profile1);
			user1.setRoles(setRole);
				
			//Creazione aula
			Aula aula = aulaDao.create("D1", 155, 20, true);
			
			//Creazione prenotazione
			DateTime inizio = new DateTime(2020, 7, 1, 10, 0, 0);
			DateTime fine = new DateTime(2020, 7, 1, 13, 0, 0);
			Prenotation prenotation = prenotationDao.create(inizio, fine, user1, aula, "evento1", "note1");
			s.getTransaction().commit();
			
			
			s.beginTransaction();
			assertEquals(prenotationDao.findAll().size(), 1);
			
			inizio = new DateTime(2020, 7, 1, 14, 0, 0);
			fine = new DateTime(2020, 7, 1, 17, 0, 0);
			Prenotation prenotation2 = new Prenotation();
			prenotation2.setId(prenotation.getId());
			prenotation2.setOraInizio(inizio);
			prenotation2.setOraFine(fine);
			prenotation2.setAula(aula);
			prenotation2.setUser(user1);
			prenotation2.setNomeEvento("evento2");
			prenotation2.setNote("note2");
			
			assertEquals(prenotation.getNomeEvento(), "evento1");
			assertNotEquals(prenotation.getNomeEvento(), "evento2");
			
			Prenotation prenotation3 = prenotationDao.update(prenotation2);
			s.getTransaction().commit();
			
			
			s.beginTransaction();
			assertNotEquals(prenotation2, prenotation3);
			assertEquals(prenotation3, prenotation);
			assertNotEquals(prenotation.getNomeEvento(), "evento1");
			assertEquals(prenotation.getNomeEvento(), "evento2");
			assertEquals(aulaDao.findAll().size(), 1);
			
			s.getTransaction().commit();
			
		}
		
	}
	
	// Test eliminazione prenotazione
	@Test
	void deletePrenotazione() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PrenotationDao prenotationDao = ctx.getBean("prenotationDao", PrenotationDao.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			UserDao userDao = ctx.getBean("userDao", UserDao.class);
			ProfileDao profileDao = ctx.getBean("profileDao", ProfileDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s = sf.openSession();
			prenotationDao.setSession(s);
			aulaDao.setSession(s);
			profileDao.setSession(s);
			roleDao.setSession(s);
			userDao.setSession(s);
			
			s.beginTransaction();
			//Creazione utente
			Role admin = roleDao.create("Admin");
			Role utente = roleDao.create("User");
			Set<Role> setRole = new HashSet();
			setRole.add(admin);
			setRole.add(utente);
			Profile profile1 = new Profile(); 
			profile1.setCognome("Simoni"); profile1.setNome("Mirko"); profile1.setDataNascita(LocalDate.of(1990, 1, 1));
			profile1 = profileDao.create(profile1);
			User user1 = userDao.create("username1", "12345", true, setRole);
			user1.setProfile(profile1);
			user1.setRoles(setRole);
				
			//Creazione aula
			Aula aula = aulaDao.create("D1", 155, 20, true);
			
			//Creazione prenotazione
			DateTime inizio = new DateTime(2020, 7, 1, 10, 0, 0);
			DateTime fine = new DateTime(2020, 7, 1, 13, 0, 0);
			Prenotation prenotation = prenotationDao.create(inizio, fine, user1, aula, "evento1", "note1");
			s.getTransaction().commit();
			
			s.beginTransaction();
			Long id = prenotation.getId();
			Prenotation prenotation2 = prenotationDao.findById(id);
			assertNotEquals(prenotation2, null);
			prenotationDao.delete(prenotation2);
			s.getTransaction().commit();
			
			s.beginTransaction();
			Prenotation prenotation3 = prenotationDao.findById(id);
			assertEquals(prenotation3, null);
			s.getTransaction().commit();	
		}
		
	}

}
