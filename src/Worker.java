import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Worker extends Character {
	
	public Worker(float x, float y) throws SlickException {
		this.x = x;
		this.y = y;
		width = 60;
		height = 60;
		moveSpeed = 1;
		
		portrait = new Image("worker.jpeg");
		portrait = portrait.getScaledCopy(width, height);
	}

	@Override
	public void update(GameContainer container, Input input, int delta)
			throws SlickException {
		if(selected){
			
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if(selected){
			g.drawString("V", x-10+width/2, y-20);
		}
		g.drawImage(portrait, x, y);

	}

	@Override
	public Order getOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrder(GameObject target) {
		// TODO Auto-generated method stub
		
	}

}
