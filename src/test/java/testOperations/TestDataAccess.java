package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();

	public TestDataAccess()  {
		System.out.println("TestDataAccess created");
		//open();
	}

	public void open(){
		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");
	}
	
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String name) {
		System.out.println(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, name);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public Driver createDriver(String name, String pass) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
		db.getTransaction().begin();
		try {
		    driver=new Driver(name,pass);
			db.persist(driver);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return driver;
    }
	
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
	public Driver addDriverWithRide(String name, String from, String to,  Date date, int nPlaces, float price) {
		System.out.println(">> TestDataAccess: addDriverWithRide");
		Driver driver=null;
		db.getTransaction().begin();
		try {
			 driver = db.find(Driver.class, name);
			if (driver==null) {
				System.out.println("Entra en null");
				driver=new Driver(name,null);
		    	db.persist(driver);
			}
		    driver.addRide(from, to, date, nPlaces, price);
			db.getTransaction().commit();
			System.out.println("Driver created "+driver);
			
			return driver;
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
    }
	
	
	public boolean existRide(String name, String from, String to, Date date) {
		System.out.println(">> TestDataAccess: existRide");
		Driver d = db.find(Driver.class, name);
		if (d!=null) {
			return d.doesRideExists(from, to, date);
		} else 
		return false;
	}
	
	public Ride removeRide(String name, String from, String to, Date date ) {
		System.out.println(">> TestDataAccess: removeRide");
		Driver d = db.find(Driver.class, name);
		if (d!=null) {
			db.getTransaction().begin();
			Ride r= d.removeRide(from, to, date);
			db.getTransaction().commit();
			System.out.println("created rides" +d.getCreatedRides());
			return r;

		} else 
		return null;

	}

	public Traveler createTraveler(String username,String pass,double money) {
		Traveler traveler = null;
		
		db.getTransaction().begin();
		try {
			traveler = db.find(Traveler.class, username);
			if (traveler==null) {
				System.out.println("Entra en null");
				traveler = new Traveler(username, pass);
				traveler.setIzoztatutakoDirua(0);
				traveler.setMoney(money);
				traveler.setBalorazioa(14);
				traveler.setBalkop(4);
		    	db.persist(traveler);

				db.getTransaction().commit();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return traveler;
	}
	
	public boolean removeTraveler(String username) {
		db.getTransaction().begin();

		try {
			Traveler t = db.find(Traveler.class, username);
			if (t==null) return false;
			
			db.remove(t);
			db.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean removeBookings(String username) {
		Traveler t=db.find(Traveler.class, username);
		List<Booking> list=t.getBookedRides();
		db.getTransaction().begin();
		try {
			for(Booking b:list) {
				db.remove(b);
			}
			db.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public User addUserWithMoney(String username, String password, double money) {
	    System.out.println(">> TestDataAccess: addUserWithMoney");
	    User user = null;
	    db.getTransaction().begin();
	    try {
	        // Verifica si el usuario ya existe en la base de datos
	        user = db.find(User.class, username);
	        if (user == null) {
	            // Si no existe, crea un nuevo usuario
	            user = new User(username, password, "regular");
	            user.setMoney(money); // Establece la cantidad de dinero inicial
	            db.persist(user); // Persiste el usuario en la base de datos
	        } else {
	            // Si el usuario ya existe, simplemente actualiza su saldo
	            user.setMoney(money);
	            db.merge(user); // Actualiza el usuario existente
	        }
	        db.getTransaction().commit();
	        System.out.println("User created/updated: " + user);
	    } catch (Exception e) {
	        e.printStackTrace();
	        db.getTransaction().rollback(); // Revertir si ocurre un error
	    }
	    return user;
	}

	
	public boolean removeUserWithMoney(String username) {
	    System.out.println(">> TestDataAccess: removeUserWithMoney");
	    User user = null;
	    db.getTransaction().begin();
	    try {
	        // Busca el usuario en la base de datos
	        user = db.find(User.class, username);
	        if (user != null) {
	            // Si el usuario existe, elim�nalo
	            db.remove(user);
	            db.getTransaction().commit();
	            System.out.println("User removed: " + user);
	            return true; // El usuario fue eliminado correctamente
	        } else {
	            db.getTransaction().rollback();
	            System.out.println("User not found: " + username);
	            return false; // El usuario no existe
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        db.getTransaction().rollback(); // Revertir si ocurre un error
	        return false;
	    }
	}
}