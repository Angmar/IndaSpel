import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Fighter extends Character {
	
	
	public Fighter(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait", moveSpeed
		super(x, y, 60, 70, 200, 40, 150, "fighter.jpg", 0.35);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);

	}

}
