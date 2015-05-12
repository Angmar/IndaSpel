import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Pirate extends Character {
	
	
	public Pirate(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait", moveSpeed
		super(x, y, 55, 65, 170, 30, 130, 600, 2, "pirate.png", 0.4);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(target != null){
			if(targetInRange()){
				attack(delta);
			}
			else{
				if(target.getClass() == Building.class){
					GameObject tempTarg = target;
					target = MainGame.nearestColonist(x, y);
					if(!targetInRange()){
						target = tempTarg;
					}
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);

	}

}
