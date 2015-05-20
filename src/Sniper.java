import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Sniper extends Character {

	public Sniper(float x, float y, int faction) throws SlickException {
		// x, y, width, height, maxHealth, damage, range, attackSpeed, faction, "portrait", moveSpeed
		super(x, y, 65, 75, 120, 100, 2000, 4000, faction, "sniper.png", 0.05);

		spotRange = range;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (faction == 1) {
			playerControlledAI(delta);
		} else {
			enemyAI(delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(container, g);

	}

}
