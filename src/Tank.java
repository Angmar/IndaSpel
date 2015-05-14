import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Tank extends Character {

	public Tank(float x, float y, int faction) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, attackSpeed, faction, "portrait"
		super(x, y, 80, 95, 400, 70, 140, 900, faction, "tank.png", 0.1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(faction == 1){
			playerControlledAI(delta);
		}
		else{
			enemyAI(delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(container, g);
	}

}
