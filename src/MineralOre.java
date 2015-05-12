import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MineralOre extends Building {

	public MineralOre(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait"
		super(x, y, 50, 60, 1000, 10, 60, "mineral.png");
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(target != null && (!target.isAttacking() || target.isAlive())){
			System.out.println("hello");
			target = null;
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);
	}
}
