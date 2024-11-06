package gui;

import java.util.Locale;

import javax.swing.UIManager;

import configuration.ConfigXML;
import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;

public class ApplicationLauncher {

	public static void main(String[] args) {
		ConfigXML config = ConfigXML.getInstance();

	
		Locale.setDefault(new Locale(config.getLocale()));
		System.out.println("Locale: " + Locale.getDefault());

		try {

			boolean isLocal = config.isBusinessLogicLocal(); 


			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			// Obtener la instancia única de BLFacadeFactory
            BLFacadeFactory factory = BLFacadeFactory.getInstance();

            // Usa la fábrica para obtener la implementación de BLFacade
            BLFacade appFacadeInterface = factory.getBusinessLogicFactory(isLocal);

			// Configurar la lógica de negocio en la GUI
			MainGUI.setBussinessLogic(appFacadeInterface);
			MainGUI mainGUI = new MainGUI();
			mainGUI.setVisible(true);

		} catch (Exception e) {
			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
	}
}