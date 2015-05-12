import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Pirate extends Character {
	
	
	public Pirate(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, attackSpeed, "portrait", moveSpeed
		super(x, y, 55, 65, 170, 40, 130, 600, 2, "pirate.png", 0.4);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		attack(delta);
		if(target != null && target.isAlive()){
			if(Building.class.isAssignableFrom(target.getClass())){
				GameObject tempTarg = target;
				target = MainGame.nearestColonist(x, y);
				if(!targetInRange()){
					target = tempTarg;
				}
			}
		}
		else{
			target = MainGame.nearestCommandCenter(x, y);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(container, g);

	}

}
