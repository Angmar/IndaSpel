import org.newdawn.slick.SlickException;

public abstract class Building extends GameObject {

	public Building(float x, float y, int width, int height, int maxHealth,
			int damage, float range, int faction, String image)
			throws SlickException {

		// x, y, width, height, maxHealth, damage, range, attackSpeed, faction,
		// "portrait"
		super(x, y, width, height, maxHealth, damage, range, 0, faction, image);
	}

}
