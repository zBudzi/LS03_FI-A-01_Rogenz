package view.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.GameController;
import model.Target;

@SuppressWarnings("serial")
public class GameJPanel extends JPanel {

	// Attribute die zum Zeichnen aus dem Controller geholt werden müssen (konkrete
	// Objekte erst in init() erzeugen)
	private GameController gc;
	private MouseClickListener mouseClickListener;
	private List<TargetPainter> rechteckePainter;
	private BufferedImage img, win, loose;
	private JLabel lblTimeleft;

	public GameJPanel(GameController gc) {
		super(); // do the JPanel-stuff
		this.gc = gc;
		this.mouseClickListener = new MouseClickListener(gc);
		this.addMouseListener(this.mouseClickListener);
		this.lblTimeleft = new JLabel(gc.timeleft() + "s");
		this.lblTimeleft.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblTimeleft.setBounds(1000, 10, 200, 40);
		this.lblTimeleft.setFont(new Font("Comic Sans Serif", Font.BOLD, 30));
		init();

	}

	/**
	 * Spielfläche initialisieren
	 */
	public void init() {
		try {
			this.img = ImageIO.read(new File("./pictures/" + this.gc.getCurrLevel().getBackgroundImage()));
			this.win = ImageIO.read(new File("./pictures/YouWin.png"));
			this.loose = ImageIO.read(new File("./pictures/YouLoose.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		LinkedList<TargetPainter> rechteckePainter = new LinkedList<TargetPainter>();
		for (Target t : gc.getTargets()) {
			rechteckePainter.add(new TargetPainter(t));
		}
		this.rechteckePainter = rechteckePainter;
	}

	@Override
	public void paintComponent(Graphics g) {

		// Abfrage Gewinn oder
		if (gc.timeleft() <= 0 || gc.isHasWon()) {

			g.setColor(Color.GRAY);
			if (gc.isHasWon()) {
				g.drawImage(this.win, 300, 120, null);
			} else {
				g.drawImage(this.loose, 300, 120, null);
			}
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			// g.drawString(gc.getShotsFired() + " Schüsse mit " + gc.getAccuracy() + " %
			// Treffergenauigkeit", 300, 850);

		} else {

			// Spielfeldhintergrund zeichnen
			if (img != null) {
				g.drawImage(this.img, 0, 0, null);
			} else {
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}

			// System.out.println(gc.time());
			// Targets zeichnen
			for (TargetPainter rp : this.rechteckePainter) {
				rp.paint(g, gc.time());
			}

			// Sekundenanzeige
			if (gc.timeleft() <= 5)
				g.setColor(Color.RED);
			else
				g.setColor(Color.WHITE);
			g.setFont(new Font("Comic Sans Serif", Font.PLAIN, 30));
			g.drawString(gc.timeleft() + "", 1210, 40);

		}
	}
}
