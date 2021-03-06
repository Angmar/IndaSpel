import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Fighter extends Character {

	public Fighter(float x, float y, int faction) throws SlickException {
		// x, y, width, height, maxHealth, damage, range, attackSpeed, faction, "portrait", moveSpeed
		super(x, y, 60, 75, 200, 40, 150, 500, faction, "fighter.png", 0.35);
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
