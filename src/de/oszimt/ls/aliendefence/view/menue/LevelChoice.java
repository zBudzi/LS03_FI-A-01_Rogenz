package de.oszimt.ls.aliendefence.view.menue;

import de.oszimt.ls.aliendefence.controller.AlienDefenceController;
import de.oszimt.ls.aliendefence.controller.GameController;
import de.oszimt.ls.aliendefence.controller.LevelController;
import de.oszimt.ls.aliendefence.model.Level;
import de.oszimt.ls.aliendefence.model.User;
import de.oszimt.ls.aliendefence.view.game.GameGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LevelChoice {
    private JPanel panel;
    private JButton btnNewLevel;
    private JButton btnUpdateLevel;
    private JTable tblLevels;
    private JButton btnDeleteLevel;
    private JButton btnSpielen;

    private final LevelController lvlControl;
    private final LeveldesignWindow leveldesignWindow;
    private DefaultTableModel jTableData;

    /**
     * Create the panel
     * @param controller
     * @param leveldesignWindow
     */
    public LevelChoice(AlienDefenceController controller, LeveldesignWindow leveldesignWindow, User user, String source) {
        this.lvlControl = controller.getLevelController();
        this.leveldesignWindow = leveldesignWindow;

        btnSpielen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnSpielen_Clicked(controller, user);
            }
        });

        btnNewLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewLevel_Clicked();
            }
        });

        btnUpdateLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnUpdateLevel_Clicked();
            }
        });

        btnDeleteLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDeleteLevel_Clicked();
            }
        });

        tblLevels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.updateTableData();

        if(source.equals("Testen")) {
            btnNewLevel.setVisible(false);
            btnUpdateLevel.setVisible(false);
            btnDeleteLevel.setVisible(false);
        } else if(source.equals("Leveleditor")) {
            btnSpielen.setVisible(false);
        }
    }

    private String[][] getLevelsAsTableModel() {
        List<Level> levels = this.lvlControl.readAllLevels();
        String[][] result = new String[levels.size()][];
        int i = 0;
        for (Level l : levels) {
            result[i++] = l.getData();
        }
        return result;
    }

    public void updateTableData() {
        this.jTableData = new DefaultTableModel(this.getLevelsAsTableModel(), Level.getLevelDescriptions());
        this.tblLevels.setModel(jTableData);
    }

    public void btnNewLevel_Clicked() {
        this.leveldesignWindow.startLevelEditor();
    }

    public void btnUpdateLevel_Clicked() {
        int level_id = Integer
                .parseInt((String) this.tblLevels.getModel().getValueAt(this.tblLevels.getSelectedRow(), 0));
        this.leveldesignWindow.startLevelEditor(level_id);
    }

    public void btnDeleteLevel_Clicked() {
        int level_id = Integer
                .parseInt((String) this.tblLevels.getModel().getValueAt(this.tblLevels.getSelectedRow(), 0));
        this.lvlControl.deleteLevel(level_id);
        this.updateTableData();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void btnSpielen_Clicked(AlienDefenceController alienDefenceController, User user) {
        //Level_id des selektierten Elements auslesen
        int level_id = Integer
                .parseInt((String) this.tblLevels.getModel().getValueAt(this.tblLevels.getSelectedRow(), 0));

        //gewähltes Level aus der Persistenz holen
        Level level = alienDefenceController.getLevelController().readLevel(level_id);

        //Gameprozess starten
        Thread t = new Thread("GameThread") {

            @Override
            public void run() {

                // Spielaufruf durchführen
                GameController gameController = alienDefenceController.startGame(level, user);
                new GameGUI(gameController).start();

            }
        };
        //Prozess starten
        t.start();
        //Levelauswahlfenster schließen
        this.leveldesignWindow.dispose();
    }
}

