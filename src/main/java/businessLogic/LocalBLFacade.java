package businessLogic;

import dataAccess.DataAccess;
import java.util.Date;
import java.util.List;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class LocalBLFacade extends BLFacadeImplementation {
    private final BLFacadeImplementation localFacade;

    public LocalBLFacade() {
    	DataAccess dataAccess = new DataAccess();
        this.localFacade = new BLFacadeImplementation(dataAccess);
    }

    
}
