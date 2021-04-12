package view.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Target;

public class TargetPainter {

	private Target target;
	private BufferedImage image;
	private BufferedImage imageDestroyed;

	public TargetPainter(Target target) {
		this.target = target;
		try {
			this.image = ImageIO.read(new File("./pictures/" + target.getImageAddress()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			this.imageDestroyed = ImageIO.read(new File("./pictures/explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paint(Graphics g, long time) {

		long targetStartTime = target.getTime();
		long targetEndTime = target.getTime() + target.getDuration();

		// Prüft, ob das Target erscheint.
		if ((time < targetStartTime || time > targetEndTime))
			return;

		if (this.target.isHit() && time < targetEndTime) {

			g.drawImage(this.imageDestroyed, this.target.getHitbox().getX(), this.target.getHitbox().getY(),
					this.target.getHitbox().getWidth(), this.target.getHitbox().getHeight(), null);

			return;

		} 

		g.drawImage(this.image, this.target.getHitbox().getX(), this.target.getHitbox().getY(),
				this.target.getHitbox().getWidth(), this.target.getHitbox().getHeight(), null);
	}

}
