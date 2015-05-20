import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ConstructionSite extends Building {

	// What building is being constructed
	private Building building;

	// whatBuilding, 0 = CommandCenter, 1 = Factory
	public ConstructionSite(float x, float y, int whatBuilding)
			throws SlickException {
		super(x, y, 100, 100, 10000, 0, 0, 1, "commandcenter.png");
		currentHealth = 0;
		switch (whatBuilding) {
		case 0:
			building = new CommandCenter(x, y);
			break;
		case 1:
			building = new Factory(x, y);
			break;
		default:
			building = new CommandCenter(x, y);
		}
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

	public Building getBuilding() {
		return building;
	}

}
