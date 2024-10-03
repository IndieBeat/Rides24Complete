
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
		try {
			sut.open();

			String username="Ray";
			
			testDA.open();
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Madrid", "Barcelona", date2, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);
			testDA.close();
			
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test2NoSeats() {
		try {
			sut.open();

			String username="Unax";
			
			testDA.open();
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Madrid", "Barcelona", date2, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);
			testDA.close();
			
			int seats=200;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test3NoRider() {
		try {
			sut.open();

			String username="Unax";
			Ride ride=null;
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			System.out.println(booked);
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test4NoMoney() {
		try {
			sut.open();

			String username="Luken";
			
			testDA.open();
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Madrid", "Barcelona", date2, 5, 200);
			
			Ride ride=driver.getCreatedRides().get(0);
			testDA.close();
			
			int seats=4;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(!booked);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test5OK() {
		try {
			sut.open();

			String username="Unax";
			
			testDA.open();
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Madrid", "Barcelona", date2, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);
			testDA.close();
			
			int seats=2;
			double desk=0;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(booked);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test6UltraDesk() {
		try {
			sut.open();

			String username="Unax";
			
			testDA.open();
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Madrid", "Barcelona", date2, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);
			testDA.close();
			
			int seats=2;
			double desk=200;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(!booked);
		} catch (Exception e) {
			assertTrue(true);
		}finally {
			
		}
	}
	
	@Test
	public void test7DriverNoTraveler() {
		try {
			sut.open();

			String username="Urtzi";
			
			testDA.open();
			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.MAY, 30);
			Date date2 = UtilDate.trim(cal.getTime());
			Driver driver=testDA.addDriverWithRide("Test","Madrid", "Barcelona", date2, 5, 2);
			
			Ride ride=driver.getCreatedRides().get(0);
			testDA.close();
			
			int seats=2;
			double desk=200;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(booked);
		} catch (Exception e) {
			fail();
		}
	}
}
