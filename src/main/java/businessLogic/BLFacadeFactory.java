package businessLogic;

import dataAccess.DataAccess;

public class BLFacadeFactory {

	// Instancia única de BLFacadeFactory
	private static BLFacadeFactory instance = null;

	private BLFacadeFactory() {
	}

	// Método para obtener la instancia única de BLFacadeFactory
	public static BLFacadeFactory getInstance() {
		if (instance == null) {
			instance = new BLFacadeFactory();
		}
		return instance;
	}

	public BLFacade getBusinessLogicFactory(boolean isLocal) throws Exception {
		if (isLocal) {
			return new LocalBLFacade();
		} else {
			return new RemoteBLFacade();
		}
	}
}
