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
import univpm.advprog.aule.model.entities.Profile;
import univpm.advprog.aule.model.entities.Role;
import univpm.advprog.aule.model.entities.User;
import univpm.advprog.aule.utils.PrenotationsOverlapFinder;

import java.time.LocalDate;
import java.util.Date;
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
		PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			
			System.out.println("Inizio ...........");
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			
			AulaDao aulaDao = ctx.getBean(AulaDao.class);
			PrenotationDao prenotationDao = ctx.getBean(PrenotationDao.class);
			ProfileDao profileDao = ctx.getBean(ProfileDao.class);
			RoleDao roleDao = ctx.getBean(RoleDao.class);
			UserDao userDao = ctx.getBean(UserDao.class);
			
			
			try (Session session = sf.openSession()) {
				
				//set Dao
				//aulaDao.setSession(session);
				//prenotationDao.setSession(session);
				//profileDao.setSession(session);
				//userDao.setSession(session);
				//roleDao.setSession(session);
			
				// phase 1 : add data to database
				session.beginTransaction();
				
				//Creazione Aule
				Aula aula1 = aulaDao.create("Z1", 155, 40, true);
				Aula aula2 = aulaDao.create("D2", 155, 34, false);
				Aula aula3 = aulaDao.create("D3", 155, 70, true);
				Aula aula4 = aulaDao.create("A3", 155, 30, false);
				
				Aula aula5 = aulaDao.create("D1", 160, 30, false);
				Aula aula6 = aulaDao.create("D2", 160, 44, true);
				Aula aula7 = aulaDao.create("D3", 160, 90, true);
				Aula aula8 = aulaDao.create("A3", 160, 20, false);
				
				Aula aula9 = aulaDao.create("D1", 140, 15, false);
				Aula aula10 = aulaDao.create("D2", 140, 70, true);
				Aula aula11 = aulaDao.create("D3", 140, 50, false);
				Aula aula12 = aulaDao.create("A3", 140, 30, true);
				
				Role admin = roleDao.create("Admin");
				Role utente = roleDao.create("Student");
				Set<Role> setRole = new HashSet();
				setRole.add(admin);
				setRole.add(utente);
				
				User user1 = userDao.create("Mirko", "12345", true, setRole);
				User user2 = userDao.create("Daniele", "12345", true, setRole);
				User user3 = userDao.create("Fabio", "12345", true, setRole);
				User user4 = userDao.create("Lorenzo", "12345", true, setRole);
				User user5 = userDao.create("Alberto", "12345", true, setRole);
				
				
				Profile profile1 = new Profile(); 
				profile1.setCognome("Simoni"); profile1.setNome("Mirko"); profile1.setDataNascita(LocalDate.of(1990, 1, 1));
				profile1 = profileDao.create(profile1);
				user1.setProfile(profile1);
				userDao.update(user1);
				
				Profile profile2 = new Profile(); 
				profile2.setCognome("Delli Rocili"); profile2.setNome("Daniele"); profile2.setDataNascita(LocalDate.of(1990, 1, 1));
				profile2 = profileDao.create(profile2);
				user2.setProfile(profile2);
				userDao.update(user2);
				
				Profile profile3 = new Profile(); 
				profile3.setCognome("Morganti"); profile3.setNome("Fabio"); profile3.setDataNascita(LocalDate.of(1990, 1, 1));
				profile3 = profileDao.create(profile3);
				user3.setProfile(profile3);
				userDao.update(user3);
				
				Profile profile4 = new Profile(); 
				profile4.setCognome("Medici"); profile4.setNome("Lorenzo"); profile4.setDataNascita(LocalDate.of(1990, 1, 1));
				profile4 = profileDao.create(profile4);
				user4.setProfile(profile4);
				userDao.update(user4);
				
				Profile profile5 = new Profile(); 
				profile5.setCognome("Pierini"); profile5.setNome("Alberto"); profile5.setDataNascita(LocalDate.of(1990, 1, 1));
				profile5 = profileDao.create(profile5);
				user5.setProfile(profile5);
				userDao.update(user5);

				session.getTransaction().commit();
				
				session.beginTransaction();
				
				//Creazione prenotazioni
				DateTime oraInizio0 = new DateTime(2020, 4, 2, 12, 30, 0);
				DateTime oraFine0 = new DateTime(2020, 4, 2, 15, 30, 0);
				Prenotation p0 = prenotationDao.create(oraInizio0, oraFine0, user2, aula10, "Esame 0", "note");
				session.refresh(aula10);
				
				DateTime oraInizio1 = new DateTime(2020, 5, 2, 12, 30, 0);
				DateTime oraFine1 = new DateTime(2020, 5, 2, 15, 30, 0);
				Prenotation p1 = prenotationDao.create(oraInizio1, oraFine1, user2, aula10, "Esame 1", "note");
				session.refresh(aula10);
				
				DateTime oraInizio2 = new DateTime(2020, 5, 2, 9, 30, 0);
				DateTime oraFine2 = new DateTime(2020, 5, 2, 11, 30, 0);
				Prenotation p2 = prenotationDao.create(oraInizio2, oraFine2, user1, aula10, "Esame 2", "note");
				session.refresh(aula10);
				
				DateTime oraInizio3 = new DateTime(2020, 5, 2, 10, 30, 0);
				DateTime oraFine3 = new DateTime(2020, 5, 2, 13, 30, 0);
				Prenotation p3 = prenotationDao.create(oraInizio3, oraFine3, user3, aula3, "Esame 3", "note");
				session.refresh(aula3);
				
				DateTime oraInizio4 = new DateTime(2020, 5, 2, 13, 30, 0);
				DateTime oraFine4 = new DateTime(2020, 5, 2, 14, 30, 0);
				Prenotation p4 = prenotationDao.create(oraInizio4, oraFine4, user3, aula3, "Esame 4", "note");
				session.refresh(aula4);
				
				DateTime oraInizio5 = new DateTime(2020, 5, 2, 10, 30, 0);
				DateTime oraFine5 = new DateTime(2020, 5, 2, 16, 30, 0);
				Prenotation p5 = prenotationDao.create(oraInizio5, oraFine5, user3, aula6, "Esame 5", "note");
				session.refresh(aula6);
				
				DateTime oraInizio6 = new DateTime(2020, 5, 3, 10, 30, 0);
				DateTime oraFine6 = new DateTime(2020, 5, 3, 16, 30, 0);
				Prenotation p6 = prenotationDao.create(oraInizio6, oraFine6, user3, aula6, "Esame 6", "note");
				session.refresh(aula6);
				
				session.getTransaction().commit();
				
				/*
				// Prova stampa differenza tra oraFine e oraInizio
				
				DateTime fine = prenot.getOraFine();
				DateTime inizio = prenot.getOraInizio();
				Duration durata = new Duration(fine, inizio);
			

				// Ricerca aula
				session.beginTransaction();
				List<Aula> setAule = aulaDao.findAulePosti(20);
				for(int i=0; i< setAule.size(); i++) {
					System.out.println(setAule.get(i).getQuota());
					System.out.println(setAule.get(i).getNome());
				}
				session.getTransaction().commit();
				
				// Update aula
				session.beginTransaction();
				
				aula.setQuota(160);
				aula.setNome("D5");
				aulaDao.update(aula);
				
				session.getTransaction().commit();
				
				//Remove prenotation
				session.beginTransaction();

				// Remove aula (funziona)
				/*
				for(Prenotation p : aula.getPrenotazioni()) {
					prenotationDao.delete(p);
				}
				aula.getPrenotazioni().clear();
				//aulaDao.delete(aula);
				
				session.getTransaction().commit();
				*/
				
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}
		
}
		


