import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import businessLogic.DriverTable;
import domain.Driver;

public class testDriverAdapter {
	public static void main(String[] args) {
		// the BL is local
		boolean isLocal = true;
		BLFacade blFacade;
		try {
			blFacade = BLFacadeFactory.getInstance().getBusinessLogicFactory(isLocal);
			Driver d = blFacade.getDriver("Urtzi");
			DriverTable dt = new DriverTable(d);
			dt.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
