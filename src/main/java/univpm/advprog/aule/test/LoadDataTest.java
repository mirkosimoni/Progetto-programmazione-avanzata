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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoadDataTest {
	public static void main(String ...args) {
		System.out.println("Quiiiiiiiii");
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			
			System.out.println("Inizio ...........");
			
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			
			AulaDao aulaDao = ctx.getBean(AulaDao.class);
			//PrenotationDao prenotationDao = ctx.getBean(PrenotationDao.class);
			//ProfileDao profileDao = ctx.getBean(ProfileDao.class);
			//RoleDao roleDao = ctx.getBean(RoleDao.class);
			//UserDao userDao = ctx.getBean(UserDao.class);
			
			
			try (Session session = sf.openSession()) {
				
				
				aulaDao.setSession(session);
				//prenotationDao.setSession(session);
				//profileDao.setSession(session);
				//userDao.setSession(session);
				//roleDao.setSession(session);
			
				// phase 1 : add data to database
				System.out.println("Qui 1");
				session.beginTransaction();
				
				aulaDao.create("D1", 155, 40, true);
				aulaDao.create("D2", 150, 34, false);

				System.out.println("Qui 2");
				/*
				Singer rw = singerDao.create("Roger", "Waters", LocalDate.of(1963, 9, 6));
				Singer mj = singerDao.create("Michael", "Jackson", null);
							
				albumDao.create("Wish you where here", rw);
				
				albumDao.create("Thriller", mj);
				
				assert mj.getAlbums().size() == 0;
				assert rw.getAlbums().size() == 0;
				
				// fai refresh per ricaricare le collezioni di mj ed rw
				session.refresh(mj);
				session.refresh(rw);
				
				assert mj.getAlbums().size() == 1;
				assert rw.getAlbums().size() == 1;
				
				Album tdb = albumDao.create("The division bell", rw);
				tdb.setSinger(rw);
				albumDao.update(tdb);
				
			
				Instrument i1 = instrumentDao.findByName("Stratocaster");
				Instrument i2 = instrumentDao.findByName("Moog");
				Instrument i3 = instrumentDao.findByName("Stradivari");
							
				session.getTransaction().commit();
				
				session.beginTransaction();

				rw.addInstrument(i1);
				rw = singerDao.update(rw);

				assert(rw.getInstruments().contains(i1));
				assert(i1.getSingers().contains(rw));
				
				session.getTransaction().commit();
				
				session.beginTransaction();

				
				rw.addInstrument(i2);
				rw = singerDao.update(rw);
				
				assert rw.getInstruments().contains(i2);
				assert i2.getSingers().contains(rw);
				
				session.getTransaction().commit();

				session.beginTransaction();

				mj.addInstrument(i2);
				mj.addInstrument(i3);
				mj = singerDao.update(mj);
				
				assert mj.getInstruments().contains(i2) == true;
				assert mj.getInstruments().contains(i3) == true;
				assert i2.getSingers().contains(mj);
				assert i3.getSingers().contains(mj);
				
				session.getTransaction().commit();
				
				session.beginTransaction();

				// rimuovi tutte le entita` collegate a quella da eliminare
				mj.getInstruments().clear();
				for (Album a : mj.getAlbums()) {
					albumDao.delete(a);
				}
				mj.getAlbums().clear();
				mj = singerDao.update(mj);

				// elimina l'entita`
				singerDao.delete(mj);

				session.getTransaction().commit();
				
				session.beginTransaction();

				// phase 2 : navigate data in the database
				
				List<Singer> all = singerDao.findAll();
				
				System.out.println("Number of singers: " + all.size());
				for (Singer s : all) {
					System.out.println(" - " + s.getFullName() + " : " + s.getBirthDate());
					
					Set<Album> albums = singerDao.getAlbums(s);
					System.out.println("Number of albums: " + albums.size());
					for (Album a : albums) {
						System.out.println("  - " + a.getTitle());					
					}
				}
				
				List<Instrument> allInstruments = instrumentDao.findAll();
				System.out.println("Number of instruments: " + allInstruments.size());
				for (Instrument i : allInstruments) {
					System.out.println(" - " + i.getFamily() + " : " + i.getName());
					Set<Singer> singers = i.getSingers();
					
					if (singers == null) {
						singers = new HashSet<Singer>();
					}
					
					System.out.println("Number of singers: " + singers.size());
					for (Singer s : singers) {
						System.out.println("  - " + s.getFullName());
					}
				}
				/*
				session.getTransaction().commit();
				
				session.beginTransaction();
				
				Role r1 = roleDao.create("USER");
				Role r2 = roleDao.create("ADMIN");
				
				session.getTransaction().commit();
				
				session.beginTransaction();
				
				User u1 = userDao.create("user1", userDao.encryptPassword("user1"), true);				
				u1.addRole(r1);
				
				User u2 = userDao.create("user2", userDao.encryptPassword("user2"), true);
				u2.addRole(r2);
				
				userDao.update(u1);
				userDao.update(u2);
				session.getTransaction().commit();
				*/
			}

		} catch (Exception e) {
//			logger.error("Eccezione: " + e.getMessage());
			e.printStackTrace(System.err);
		}
//		logger.info("Esco ...");
	}
		
}
		


