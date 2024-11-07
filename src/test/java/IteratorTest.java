import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import businessLogic.ExtendedIterator;

public class IteratorTest {

	public static void main(String[] args) {
		boolean isLocal = true;
		BLFacade blFacade;
		try {
			blFacade = BLFacadeFactory.getInstance().getBusinessLogicFactory(isLocal);
			ExtendedIterator<String> i = blFacade.getDepartingCitiesIterator();
			String c;
			System.out.println("_____________________");
			System.out.println("FROM LAST TO FIRST");
			i.goLast(); // Go to last element
			while (i.hasPrevious()) {
				c = i.previous();
				System.out.println(c);
			}
			System.out.println();
			System.out.println("_____________________");
			System.out.println("FROM FIRST TO LAST");
			i.goFirst(); // Go to first element
			while (i.hasNext()) {
				c = i.next();
				System.out.println(c);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
