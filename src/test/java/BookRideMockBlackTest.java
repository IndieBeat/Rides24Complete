import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

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

public class BookRideMockBlackTest {
	static DataAccess sut;

	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	@Mock
	TypedQuery<Traveler> travelerTypedQuery;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);

		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test1UserNull() {
		String travelerName = null;
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Valencia";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		int seats = 2;
		double desk = 0;

		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, seats, desk, driver);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getSingleResult()).thenReturn(null);

			sut.open();
			boolean booked = sut.bookRide(travelerName, ride, seats, desk);
			sut.close();

			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2RideNull() {
		String travelerName = "TestTraveler";

		int seats = 2;
		double desk = 0;

		try {
			Traveler t = new Traveler(travelerName, "123");
			t.setMoney(10);

			List<Traveler> l = new Vector<Traveler>();
			l.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getResultList()).thenReturn(l);

			Ride ride = null;

			sut.open();
			boolean booked = sut.bookRide(travelerName, ride, seats, desk);
			sut.close();

			// Printea excepcion pero no la levanta, si no que devuelve false, luego
			// correcto
			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3UnkownUser() {
		String travelerName = "UnknowUser";
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Barcelona";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		int seats = 2;
		double desk = 0;

		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, 5, 2, driver);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getSingleResult()).thenReturn(null);

			sut.open();
			boolean booked = sut.bookRide(travelerName, ride, seats, desk);
			sut.close();

			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4SeatsPos() {
		String travelerName = "TestTraveler";
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Barcelona";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		int seats = -5;
		double desk = 0;
		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, 5, 2, driver);

			Traveler t = new Traveler(travelerName, "123");
			t.setMoney(10);

			List<Traveler> l = new Vector<Traveler>();
			l.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getResultList()).thenReturn(l);

			sut.open();
			boolean booked = sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();

			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test5MegaDesk() {
		String travelerName = "TestTraveler";
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Barcelona";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, 5, 2, driver);

			Traveler t = new Traveler(travelerName, "123");
			t.setMoney(10);

			List<Traveler> l = new Vector<Traveler>();
			l.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getResultList()).thenReturn(l);

			sut.open();
			int seats = 2;
			double desk = 200;
			boolean booked = sut.bookRide(travelerName, ride, seats, desk);
			sut.close();

			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test6MoreSeats() {
		String travelerName = "TestTraveler";
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Barcelona";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, 5, 2, driver);

			Traveler t = new Traveler(travelerName, "123");
			t.setMoney(10);

			List<Traveler> l = new Vector<Traveler>();
			l.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getResultList()).thenReturn(l);

			sut.open();
			int seats = 500;
			double desk = 0;
			boolean booked = sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();

			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test7NoEnoughMoney() {
		String travelerName = "TestTraveler";
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Barcelona";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, 5, 20, driver);

			Traveler t = new Traveler(travelerName, "123");
			t.setMoney(10);

			List<Traveler> l = new Vector<Traveler>();
			l.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getResultList()).thenReturn(l);

			sut.open();
			int seats = 2;
			double desk = 0;
			boolean booked = sut.bookRide(travelerName, ride, seats, desk);
			sut.close();

			assertFalse(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test8OK() {
		String travelerName = "TestTraveler";
		String driverName = "TestDriver";
		String rideFrom = "Murcia";
		String rideTo = "Barcelona";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			Driver driver = new Driver(driverName, "123");
			Ride ride = new Ride(rideFrom, rideTo, date, 5, 2, driver);

			Traveler t = new Traveler(travelerName, "123");
			t.setMoney(100);
			List<Traveler> l = new Vector<Traveler>();
			l.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(travelerTypedQuery);
			Mockito.when(travelerTypedQuery.getResultList()).thenReturn(l);

			sut.open();
			int seats = 2;
			double desk = 0;
			boolean booked = sut.bookRide(travelerName, ride, seats, desk);
			sut.close();

			assertTrue(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
