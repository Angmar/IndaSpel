import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class ConstructionSite extends Building {


	public ConstructionSite(float x, float y)
			throws SlickException {
		super(x, y, 100, 100, 10000, 0, 0, 1, "commandcenter.png");
		currentHealth = 0;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(container, g);
	}

	public void construct(int delta) throws SlickException {
		if (maxHealth > currentHealth)
			currentHealth += delta;
	}
	
}
