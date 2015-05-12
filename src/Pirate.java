import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Pirate extends Character {
	
	
	public Pirate(float x, float y, int faction) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, attackSpeed, "portrait", moveSpeed
		super(x, y, 55, 65, 170, 30, 130, 800, faction, "pirate.png", 0.2);
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
