package businessLogic;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import configuration.ConfigXML;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class RemoteBLFacade extends BLFacadeImplementation {
    private final BLFacade remoteFacade;

    public RemoteBLFacade() throws Exception {
        ConfigXML config = ConfigXML.getInstance();
        String serviceName = "http://" + config.getBusinessLogicNode() + ":" + config.getBusinessLogicPort() + "/ws/"
                + config.getBusinessLogicName() + "?wsdl";
        URL url = new URL(serviceName);
        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
        Service service = Service.create(url, qname);
        remoteFacade = service.getPort(BLFacade.class);
    }

}
