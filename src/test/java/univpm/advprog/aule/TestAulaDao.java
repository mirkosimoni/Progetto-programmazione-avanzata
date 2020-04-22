package univpm.advprog.aule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
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


public class TestAulaDao {

	@Test
	void testBeginCommitTransaction(){
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			
			Session s = sf.openSession();
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			aulaDao.setSession(s);
			assertEquals(s, aulaDao.getSession());
			s.getTransaction().commit();
			
			assertFalse(s.getTransaction().isActive());
		}
	}
	
	
	//@Test
	void testBeginWithoutSpecifyingSession() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);

			aulaDao.create("NomeProva1", 100, 15, true); //Funziona
			
			//Non funziona
			Session s1 = aulaDao.getSession(); //Problemi in questo metodo, anche se la create funziona
			assertFalse(s1.isOpen());
			
			List<Aula> aule = aulaDao.findAll(); //Funziona
			assertEquals(aule.size(), 1); //Funziona
			
			//aulaDao.create("NomeProva2", 130, 15, true);
			//Session s2 = aulaDao.getSession();
			//assertNotEquals(s1, s2);
			
		}catch (Exception e) {
			fail("Error unexpected: " + e);
		}
	}
	
	@Test
	void createAndUpdateAula() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			Session s = sf.openSession();
			aulaDao.setSession(s);
			
			s.beginTransaction();
			
			Aula aula1 = aulaDao.create("Aula1", 150, 10, true);
			s.getTransaction().commit();
			
			
			s.beginTransaction();
			assertEquals(aulaDao.findAll().size(), 1);
			
			Aula aula2 = new Aula();
			aula2.setId(aula1.getId());
			aula2.setNome("Aula2");
			aula2.setQuota(150);
			aula2.setNumeroPosti(20);
			aula2.setPresentiPrese(false);
			
			assertEquals(aula1.getNome(), "Aula1");
			assertNotEquals(aula1.getNome(), "Aula2");
			
			Aula aula3 = aulaDao.update(aula2);
			s.getTransaction().commit();
			
			
			s.beginTransaction();
			assertNotEquals(aula2, aula3);
			assertEquals(aula3, aula1);
			
			assertNotEquals(aula1.getNome(), "Aula1");
			assertEquals(aula1.getNome(), "Aula2");
			assertEquals(aulaDao.findAll().size(), 1);
			
			s.getTransaction().commit();
		}
		catch (Exception e) {
			fail("Unexpected error: " + e);
		}
	}
	
	
	@Test
	void searchAulaParameters() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			Session s = sf.openSession();
			aulaDao.setSession(s);
			
			s.beginTransaction();
			Aula aula1 = aulaDao.create("Aula1", 150, 10, true);
			Aula aula2 = aulaDao.create("Aula2", 150, 15, true);
			Aula aula3 = aulaDao.create("Aula3", 150, 20, false);
			Aula aula4 = aulaDao.create("Aula4", 155, 20, true);
			Aula aula5 = aulaDao.create("Aula5", 155, 30, true);
			s.getTransaction().commit();
			
			s.beginTransaction();
			
			List<Aula> l1 = aulaDao.findAule(150, null, -1, null);
			assertEquals(l1.size(), 3);
			
			l1 = aulaDao.findAule(150, null, -1, true);
			assertEquals(l1.size(), 2);
			
			l1 = aulaDao.findAule(-1, null, 15, true);
			assertEquals(l1.size(), 3);
			
			l1 = aulaDao.findAule(150, null, 15, null);
			assertEquals(l1.size(), 2);
			
			l1 = aulaDao.findAule(-1, null, -1, true);
			assertEquals(l1.size(), 4);
			
			l1 = aulaDao.findAule(150, null, 10, true);
			assertEquals(l1.size(), 2);
			
			List<String> listaQuote = aulaDao.findByQuota();
			assertEquals(listaQuote.size(), 2);
			
			
			s.getTransaction().commit();
			
			
		}
	}
	
	@Test
	void searchAuleLibere(){
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			PrenotationDao prenotationDao = ctx.getBean("prenotationDao", PrenotationDao.class);
			UserDao userDao = ctx.getBean("userDao", UserDao.class);
			ProfileDao profileDao = ctx.getBean("profileDao", ProfileDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s = sf.openSession();
			aulaDao.setSession(s);
			prenotationDao.setSession(s);
			userDao.setSession(s);
			profileDao.setSession(s);
			roleDao.setSession(s);
			
			//Creazione aule
			s.beginTransaction();
			Aula aula1 = aulaDao.create("Aula1", 150, 10, true);
			Aula aula2 = aulaDao.create("Aula2", 150, 15, true);
			Aula aula3 = aulaDao.create("Aula3", 155, 10, true);
			Aula aula4 = aulaDao.create("Aula4", 155, 15, true);
			s.getTransaction().commit();
			
			//Creazione utente con ruoli
			s.beginTransaction();
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
			userDao.update(user1);
			s.getTransaction().commit();
			
			//Creazione prenotazioni
			s.beginTransaction();
			
			DateTime inizio1 = new DateTime(2020, 7, 1, 10, 0, 0);
			DateTime fine1 = new DateTime(2020, 7, 1, 13, 0, 0);
			prenotationDao.create(inizio1, fine1, user1, aula1, "evento1", "note1");
			
			DateTime inizio2 = new DateTime(2020, 7, 1, 15, 0, 0);
			DateTime fine2 = new DateTime(2020, 7, 1, 17, 0, 0);
			prenotationDao.create(inizio2, fine2, user1, aula1, "evento2", "note2");
			
			prenotationDao.create(inizio2, fine2, user1, aula2, "evento3", "note3");
			s.getTransaction().commit();
			
			//Ricerca aule libere
			s.beginTransaction();
			
			DateTime inizio = new DateTime(2020, 7, 1, 10, 0, 0);
			List<Aula> auleLibere = aulaDao.findAuleLibere(inizio, null, -1, null, -1, null);
			assertEquals(auleLibere.size(), 3);
			
			inizio = new DateTime(2020, 7, 1, 13, 0, 0);
			auleLibere = aulaDao.findAuleLibere(inizio, null, -1, null, -1, null);
			assertEquals(auleLibere.size(), 4);
			
			DateTime fine = new DateTime(2020, 7, 1, 16, 0, 0);
			auleLibere = aulaDao.findAuleLibere(inizio, fine, -1, null, -1, null);
			assertEquals(auleLibere.size(), 2);
			
			inizio = new DateTime(2020, 7, 1, 9, 0, 0);
			fine = new DateTime(2020, 7, 1, 15, 0, 0);
			auleLibere = aulaDao.findAuleLibere(inizio, fine, -1, null, -1, null);
			assertEquals(auleLibere.size(), 3);
			
			auleLibere = aulaDao.findAuleLibere(null, null, -1, null, -1, null);
			assertEquals(auleLibere.size(), 4);
			
			s.getTransaction().commit();
			s.beginTransaction();
			
			DateTime inizio3 = DateTime.now();
			DateTime fine3 = DateTime.now().plusMinutes(60);
			prenotationDao.create(inizio3, fine3, user1, aula1, "evento3", "note3");
			
			s.getTransaction().commit();
			
			s.beginTransaction();
			
			auleLibere = aulaDao.findAuleLibere(null, null, -1, null, -1, null);
			assertEquals(auleLibere.size(), 3);
			
			s.getTransaction().commit();
			
			
		}
	}
	
	@Test
	void deleteAula() {
		
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)){
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			AulaDao aulaDao = ctx.getBean("aulaDao", AulaDao.class);
			Session s = sf.openSession();
			aulaDao.setSession(s);
			
			s.beginTransaction();
			Aula aula1 = aulaDao.create("Aula1", 150, 10, true);
			s.getTransaction().commit();
			
			s.beginTransaction();
			Long id1 = aula1.getId();
			Aula aula2 = aulaDao.findById(id1);
			assertNotEquals(aula2, null);
			aulaDao.delete(aula2);
			s.getTransaction().commit();
			
			s.beginTransaction();
			Aula aula3 = aulaDao.findById(id1);
			assertEquals(aula3, null);
			s.getTransaction().commit();
			
		}
	}
}
