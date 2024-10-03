
import domain.*;
import testOperations.TestDataAccess;
import dataAccess.DataAccess;
import org.junit.Test;

import configuration.UtilDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

public class BookRideWhiteTest {

	static DataAccess sut=new DataAccess();
	static TestDataAccess testDA=new TestDataAccess();
	
	@Test
	public void test1NoUser() {
		String username="Ray";
		String driverName="Test1Driver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			testDA.open();
			Driver driver=testDA.addDriverWithRide(driverName,rideFrom,rideTo, date, 5, 2);
			testDA.close();
			Ride ride=driver.getCreatedRides().get(0);
			
			sut.open();
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}finally {
			testDA.open();
			
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			
			testDA.close();
		}
	}
	
	@Test
	public void test2NoSeats() {
		String username="Traveler";
		String driverName="TestDriver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());
		try {
			testDA.open();
			Driver driver=testDA.addDriverWithRide(driverName,rideFrom,rideTo, date, 5, 2);
			testDA.createTraveler(username, "123", 10);
			testDA.close();
			
			Ride ride=driver.getCreatedRides().get(0);
			
			sut.open();
			int seats=200;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}finally {
			testDA.open();
			
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			testDA.removeTraveler(username);
			
			testDA.close();
		}
	}

	@Test
	public void test3NoRider() {
		String username="Traveler";
		
		try {
			Ride ride=null;
			testDA.open();
			testDA.createTraveler(username, "123", 10);
			testDA.close();
			
			sut.open();
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		} catch(NullPointerException e) {
			fail();
		} catch (Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeTraveler(username);
			testDA.close();
		}
	}
	
	@Test
	public void test4NoMoney() {
		String username="Traveler";
		String driverName="TestDriver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());

		try {
			testDA.open();
			testDA.createTraveler(username, "123", 10);
			Driver driver=testDA.addDriverWithRide(driverName,rideFrom, rideTo, date, 5, 200);
			testDA.close();
			
			Ride ride=driver.getCreatedRides().get(0);
			
			sut.open();
			int seats=4;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}finally {
			testDA.open();
			
			testDA.removeBookings(username);
			testDA.removeTraveler(username);
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			
			testDA.close();
		}
	}
	
	@Test
	public void test5OK() {
		String travelerName="TestTraveler";
		String driverName="TestDriver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());
		
		try {
			testDA.open();
			Driver driver=testDA.addDriverWithRide(driverName, rideFrom, rideTo, date, 5, 2);
			Traveler t=testDA.createTraveler(travelerName, "123", 10);
			testDA.close();
			
			Ride ride=driver.getCreatedRides().get(0);
			
			
			sut.open();
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();
			
			assertTrue(booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			testDA.open();
			
			testDA.removeBookings(travelerName);
			testDA.removeTraveler(travelerName);
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			
			testDA.close();
		}
	}

	@Test
	public void test6UltraDesk() {
		String username="Traveler";
		String driverName="Driver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());
		try {
			sut.open();
			testDA.open();
			
			Driver driver=testDA.addDriverWithRide(driverName,rideFrom, rideTo, date, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);

			Traveler t=testDA.createTraveler(username, "123", 10);
			
			int seats=2;
			double desk=200;
			boolean booked=sut.bookRide(username, ride, seats, desk);

			sut.close();
			assertTrue(!booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally {
			testDA.open();
			
			testDA.removeBookings(username);
			testDA.removeTraveler(username);
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			
			testDA.close();
		}
	}
	/*
	@Test
	public void test7DriverNoTraveler() {
		try {
			sut.open();
			testDA.open();
			
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Murcia", "Barcelona", date2, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);
			
			int seats=2;
			double desk=200;
			boolean booked=sut.bookRide(driver.getUsername(), ride, seats, desk);
			
			testDA.removeBookings(driver.getUsername());
			testDA.removeRide(driver.getUsername(), ride.getFrom(), ride.getTo(), ride.getDate());
			testDA.removeDriver(driver.getUsername());
			
			testDA.close();
			sut.close();
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}finally {
			testDA.close();
			sut.close();
		}
	}*/
}