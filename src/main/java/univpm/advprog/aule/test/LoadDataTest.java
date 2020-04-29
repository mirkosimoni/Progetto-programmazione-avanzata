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
				Aula aula1 = aulaDao.create("D1", 155, 40, true);
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
				
				Role adminRole = roleDao.create("Admin");
				Role studentRole = roleDao.create("Student");
				Role teacherRole = roleDao.create("Teacher");
				
				Set<Role> setRoleAdmin = new HashSet();
				setRoleAdmin.add(adminRole);
				
				Set<Role> setRoleTeacher = new HashSet();
				setRoleTeacher.add(teacherRole);
				
				Set<Role> setRoleStudent = new HashSet();
				setRoleTeacher.add(studentRole);
				
				User admin = userDao.create("admin", "12345", true, setRoleAdmin);
				Profile profileAdmin = new Profile();
				profileAdmin.setCognome("Rossi"); profileAdmin.setNome("Mario"); profileAdmin.setDataNascita(LocalDate.of(1980, 1, 1));
				profileAdmin = profileDao.create(profileAdmin);
				admin.setProfile(profileAdmin);
				userDao.update(admin);
				
				User teacher1 = userDao.create("teacher1", "12345", true, setRoleTeacher);
				Profile profileTeacher1 = new Profile();
				profileTeacher1.setCognome("Ursino"); profileTeacher1.setNome("Domenico"); profileTeacher1.setDataNascita(LocalDate.of(1980, 1, 1));
				profileTeacher1 = profileDao.create(profileTeacher1);
				teacher1.setProfile(profileTeacher1);
				userDao.update(teacher1);
				
				User teacher2 = userDao.create("teacher2", "12345", true, setRoleTeacher);
				Profile profileTeacher2 = new Profile();
				profileTeacher2.setCognome("Dragoni"); profileTeacher2.setNome("Aldo Franco"); profileTeacher2.setDataNascita(LocalDate.of(1980, 1, 1));
				profileTeacher2 = profileDao.create(profileTeacher2);
				teacher2.setProfile(profileTeacher2);
				userDao.update(teacher2);
				
				User teacher3 = userDao.create("teacher3", "12345", true, setRoleTeacher);
				Profile profileTeacher3 = new Profile();
				profileTeacher3.setCognome("Marinelli"); profileTeacher3.setNome("Fabrizio"); profileTeacher3.setDataNascita(LocalDate.of(1980, 1, 1));
				profileTeacher3 = profileDao.create(profileTeacher3);
				teacher3.setProfile(profileTeacher3);
				userDao.update(teacher3);
				
				User student1 = userDao.create("student1", "12345", true, setRoleStudent);
				Profile profileStudent1 = new Profile();
				profileStudent1.setCognome("Bianchi"); profileStudent1.setNome("Giovanni"); profileStudent1.setDataNascita(LocalDate.of(1990, 1, 1));
				profileStudent1 = profileDao.create(profileStudent1);
				student1.setProfile(profileStudent1);
				userDao.update(student1);
				
				User student2 = userDao.create("student2", "12345", true, setRoleStudent);
				Profile profileStudent2 = new Profile();
				profileStudent2.setCognome("Neri"); profileStudent2.setNome("Enrico"); profileStudent2.setDataNascita(LocalDate.of(1990, 1, 1));
				profileStudent2 = profileDao.create(profileStudent2);
				student2.setProfile(profileStudent2);
				userDao.update(student2);
				
				User student3 = userDao.create("student3", "12345", true, setRoleStudent);
				Profile profileStudent3 = new Profile();
				profileStudent3.setCognome("Verdi"); profileStudent3.setNome("Mauro"); profileStudent3.setDataNascita(LocalDate.of(1990, 1, 1));
				profileStudent3 = profileDao.create(profileStudent3);
				student3.setProfile(profileStudent3);
				userDao.update(student3);
				
				DateTime domani = DateTime.now().plusDays(1);
				int anno = domani.getYear();
				int mese = domani.getMonthOfYear();
				int giorno = domani.getDayOfMonth();
				
				//Creazione prenotazioni
				DateTime oraInizio0 = new DateTime(anno, mese, giorno, 12, 30, 0);
				DateTime oraFine0 = new DateTime(anno, mese, giorno, 15, 30, 0);
				Prenotation p0 = prenotationDao.create(oraInizio0, oraFine0, teacher1, aula10, "Esercitazione PM", "Presentarsi con il computer");
				session.refresh(aula10);
				
				DateTime oraInizio1 = new DateTime(anno, mese, giorno, 12, 30, 0);
				DateTime oraFine1 = new DateTime(anno, mese, giorno, 15, 30, 0);
				Prenotation p1 = prenotationDao.create(oraInizio1, oraFine1, teacher2, aula8, "Esame IA", "Possibilità di consultare la teoria");
				session.refresh(aula8);
				
				DateTime oraInizio2 = new DateTime(anno, mese, giorno, 9, 30, 0);
				DateTime oraFine2 = new DateTime(anno, mese, giorno, 11, 30, 0);
				Prenotation p2 = prenotationDao.create(oraInizio2, oraFine2, admin, aula10, "Aula riservata", "Solo personale");
				session.refresh(aula10);
				
				DateTime oraInizio3 = new DateTime(anno, mese, giorno, 10, 30, 0);
				DateTime oraFine3 = new DateTime(anno, mese, giorno, 13, 30, 0);
				Prenotation p3 = prenotationDao.create(oraInizio3, oraFine3, teacher3, aula3, "Esercitazione RO2", "");
				session.refresh(aula3);
				
				DateTime dopoDomani = domani.plusDays(1);
				anno = dopoDomani.getYear();
				mese = dopoDomani.getMonthOfYear();
				giorno = dopoDomani.getDayOfMonth();
				
				DateTime oraInizio4 = new DateTime(anno, mese, giorno, 13, 30, 0);
				DateTime oraFine4 = new DateTime(anno, mese, giorno, 14, 30, 0);
				Prenotation p4 = prenotationDao.create(oraInizio4, oraFine4, teacher1, aula3, "Ricevimento PM", "Non è necessario appuntamento");
				session.refresh(aula4);
				
				DateTime oraInizio5 = new DateTime(anno, mese, giorno, 10, 30, 0);
				DateTime oraFine5 = new DateTime(anno, mese, giorno, 16, 30, 0);
				Prenotation p5 = prenotationDao.create(oraInizio5, oraFine5, teacher2, aula6, "Esame orale IA", "Solo se [voto scritto] >= 18");
				session.refresh(aula6);
				
				DateTime oraInizio6 = new DateTime(anno, mese, giorno, 10, 30, 0);
				DateTime oraFine6 = new DateTime(anno, mese, giorno, 16, 30, 0);
				Prenotation p6 = prenotationDao.create(oraInizio6, oraFine6, teacher3, aula6, "Verbalizzazione voto RO2", "");
				session.refresh(aula6);
				
				session.getTransaction().commit();
				
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}
		
}
		


