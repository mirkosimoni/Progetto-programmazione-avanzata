package univpm.advprog.aule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

			aulaDao.create("NomeProva1", 100, 15, true);
			Session s1 = aulaDao.getSession();
			assertFalse(s1.isOpen());
			
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
			
			List<Aula> l1 = aulaDao.findAule(150, -1, null);
			assertEquals(l1.size(), 3);
			
			l1 = aulaDao.findAule(150, -1, true);
			assertEquals(l1.size(), 2);
			
			l1 = aulaDao.findAule(-1, 15, true);
			assertEquals(l1.size(), 3);
			
			l1 = aulaDao.findAule(150, 15, null);
			assertEquals(l1.size(), 2);
			
			l1 = aulaDao.findAule(-1, -1, true);
			assertEquals(l1.size(), 4);
			
			l1 = aulaDao.findAule(150, 10, true);
			assertEquals(l1.size(), 2);
			
			s.getTransaction().commit();
			
			
		}
	}
	
	
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
