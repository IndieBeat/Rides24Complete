
import domain.*;
import dataAccess.DataAccess;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BookRideWhiteTest {

	static DataAccess sut=new DataAccess();
	
	@Test
	public void test1() {
		try {
			sut.open();

			String username="Ray";
			Ride ride=null;
			int seats=5;
			double desk=3.8;
			boolean booked=sut.bookRide(username, ride, seats, desk);
			
			assertTrue(!booked);

			fail();
		} catch (Exception e) {
			fail();
		}

		
	}
}
