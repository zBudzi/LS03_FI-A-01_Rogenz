package view;

import controller.AlienDefenceController;
import model.persistence.IPersistance;
import model.persistenceDummy.PersistanceDummy;
import view.menue.MainMenu;

public class StartAlienDefence {

	public static void main(String[] args) {
		
		IPersistance 		   alienDefenceModel      = new PersistanceDummy();//TODO new PersistanceDB();
		AlienDefenceController alienDefenceController = new AlienDefenceController(alienDefenceModel);
		MainMenu mainMenu = new MainMenu(alienDefenceController);
		
		mainMenu.setVisible(true);
	}
}
