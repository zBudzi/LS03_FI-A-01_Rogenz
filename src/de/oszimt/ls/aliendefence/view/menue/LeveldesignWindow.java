package de.oszimt.ls.aliendefence.view.menue;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.oszimt.ls.aliendefence.controller.LevelController;
import de.oszimt.ls.aliendefence.controller.TargetController;
import de.oszimt.ls.aliendefence.model.Level;

@SuppressWarnings("serial")
public class LeveldesignWindow extends JFrame {

	private LevelController lvlControl;
	private JPanel contentPane;
	private LevelChoice cardChooseLevel;
	private LevelEditor cardLevelEditor;

	private CardLayout cards;

	/**
	 * Create the frame.
	 */
	public LeveldesignWindow(LevelController lvlControl, TargetController targetControl) {
		this.lvlControl = lvlControl;

		setTitle("Leveldesigner");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.cards = new CardLayout();
		contentPane.setLayout(cards);

		this.cardChooseLevel = new LevelChoice(lvlControl, this);
		contentPane.add(cardChooseLevel.getPanel(), "levelChooser");

		this.cardLevelEditor = new LevelEditor(this, lvlControl, targetControl, Level.getDefaultLevel());
		contentPane.add(cardLevelEditor, "levelEditor");

		this.showLevelChooser();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * display leveleditor with a new level
	 */
	public void startLevelEditor() {
		this.cardLevelEditor.setLvl(this.lvlControl.createLevel());
		this.cards.show(contentPane, "levelEditor");
	}

	/**
	 * disply leveleditor with a new level
	 * 
	 * @param level_id
	 */
	public void startLevelEditor(int level_id) {
		this.cardLevelEditor.setLvl(this.lvlControl.readLevel(level_id));
		this.cards.show(contentPane, "levelEditor");
	}

	/**
	 * display a jTable with all Levels
	 */
	public void showLevelChooser() {
		this.cards.show(contentPane, "levelChooser");
		this.cardChooseLevel.updateTableData();
	}

}
