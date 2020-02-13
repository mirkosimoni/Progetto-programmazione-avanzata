package univpm.advprog.aule.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.dao.PrenotationDao;
import univpm.advprog.aule.model.dao.ProfileDao;
import univpm.advprog.aule.model.dao.RoleDao;
import univpm.advprog.aule.model.dao.UserDao;
import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.model.entities.Role;
import univpm.advprog.aule.model.entities.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoadDataTest {
	public static void main(String ...args) {
		System.out.println("Inizio di load data test");
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			
			System.out.println("Inizio ...........");
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			
			AulaDao aulaDao = ctx.getBean(AulaDao.class);
			PrenotationDao prenotationDao = ctx.getBean(PrenotationDao.class);
			ProfileDao profileDao = ctx.getBean(ProfileDao.class);
			RoleDao roleDao = ctx.getBean(RoleDao.class);
			UserDao userDao = ctx.getBean(UserDao.class);
			
			
			try (Session session = sf.openSession()) {
				
				
				aulaDao.setSession(session);
				prenotationDao.setSession(session);
				profileDao.setSession(session);
				userDao.setSession(session);
				roleDao.setSession(session);
			
				// phase 1 : add data to database
				System.out.println("Qui 1");
				session.beginTransaction();
				
				Aula aula = aulaDao.create("D1", 155, 40, true);
				aulaDao.create("D2", 150, 34, false);

				System.out.println("Qui 2");
				
				User user = userDao.create("Mirko", "12345", true);
				
				DateTime oraInizio = new DateTime(2005, 3, 26, 12, 0, 0);
				DateTime oraFine = new DateTime(2005, 3, 26, 15, 0, 0);
				
				prenotationDao.create(oraInizio, oraFine, user, aula, "esame spegni", "esame");
				
				session.getTransaction().commit();
				
				System.out.println("Fine transazione creazione prenotazione");
				
				session.beginTransaction();
				
				Prenotation prenot = prenotationDao.findById(1L);
				
				System.out.println(prenot.getOraFine());
				
				session.getTransaction().commit();
				
				// Prova stampa differenza tra oraFine e oraInizio
				
				DateTime fine = prenot.getOraFine();
				DateTime inizio = prenot.getOraInizio();
				
				Duration durata = new Duration(fine, inizio);
				
				System.out.println(durata.getStandardHours());
				
				
				// Creazione di un ruolo
				session.beginTransaction();
				
				Role admin = roleDao.create("Admin");
				
				Role utente = roleDao.create("User");
				
				Set<Role> setRole = new HashSet();
				
				setRole.add(admin);
				setRole.add(utente);
				
				session.getTransaction().commit();
				
				// Assegnazione ruolo ad un utente
				session.beginTransaction();
				
				user.setRoles(setRole);
				
				session.getTransaction().commit();
				
			
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}
		
}
		


