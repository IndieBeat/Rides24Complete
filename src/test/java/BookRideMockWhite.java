import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;

public class BookRideMockWhite {

	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	@Mock
	TypedQuery<Double> typedQuery;
	
	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void Test1UserNull() {
		String username="Ray";
		String driverName="Test1Driver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			Driver driver=new Driver(driverName, "123");
			Ride ride=new Ride(rideFrom, rideTo, date, 5, 3, driver);
			
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
			Mockito.when(typedQuery.getSingleResult()).thenReturn(null);
	        
			sut.open();
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally {
			
		}
	}
}
