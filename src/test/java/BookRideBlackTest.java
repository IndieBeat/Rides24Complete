import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import testOperations.TestDataAccess;

public class BookRideBlackTest {

	static DataAccess sut=new DataAccess();
	static TestDataAccess testDA=new TestDataAccess();
	
	@Test
	public void test1UserNull() {
		String travelerName=null;
		String driverName="TestDriver";
		String rideFrom="Murcia";
		String rideTo="Valencia";

		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());
		
		int seats=2;
		double desk=0;
		
		try {
			testDA.open();
			Driver driver=testDA.addDriverWithRide(driverName, rideFrom, rideTo, date, seats, 2);
			testDA.close();
			
			Ride ride=driver.getCreatedRides().get(0);
			
			sut.open();
			boolean booked=sut.bookRide(travelerName, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}finally {
			testDA.open();
			
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			
			testDA.close();
		}
	}
	
	@Test
	public void test2RideNull() {
		String travelerName="TestTraveler";
		
		int seats=2;
		double desk=0;
		
		try {
			testDA.open();
			testDA.createTraveler(travelerName, "123", 10);
			testDA.close();
			
			Ride ride=null;
			
			sut.open();
			boolean booked=sut.bookRide(travelerName, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}finally {
			testDA.open();

			testDA.removeBookings(travelerName);
			testDA.removeTraveler(travelerName);
			
			testDA.close();
		}
	}
	

	@Test
	public void test3UnkownUser() {
		String travelerName="UnknowUser";
		String driverName="TestDriver";
		String rideFrom="Murcia";
		String rideTo="Barcelona";
		
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.MAY, 30);
		Date date = UtilDate.trim(cal.getTime());
		
		try {
			testDA.open();
			Driver driver=testDA.addDriverWithRide(driverName, rideFrom, rideTo, date, 5, 2);
			testDA.close();
			
			Ride ride=driver.getCreatedRides().get(0);
			
			
			sut.open();
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(travelerName, ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			testDA.open();
			
			testDA.removeRide(driverName, rideFrom, rideTo, date);
			testDA.removeDriver(driverName);
			
			testDA.close();
		}
	}

	@Test
	public void test4SeatsPos() {
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
			int seats=-5;
			double desk=0;
			boolean booked=sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
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
	public void test5MegaDesk() {
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
			double desk=200;
			boolean booked=sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
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
	public void test6MoreSeats() {
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
			int seats=500;
			double desk=0;
			boolean booked=sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
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
	public void test7NoEnoughMoney() {
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
			Traveler t=testDA.createTraveler(travelerName, "123", 1);
			testDA.close();
			
			Ride ride=driver.getCreatedRides().get(0);
			
			
			sut.open();
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(t.getUsername(), ride, seats, desk);
			sut.close();
			
			assertTrue(!booked);
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
	public void test8OK() {
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
}
