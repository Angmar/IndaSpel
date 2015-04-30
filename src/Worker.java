import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Worker extends Character {
	
	

	public Worker(float x, float y) throws SlickException {
		super();
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
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			order.setMoveTarget(input.getMouseX(), input.getMouseY());
		}
		
		if(order.isActive()){
			moveTo(order.getMoveToX(),order.getMoveToY(),delta);
		}
		
		
		moveTo(order.getMoveToX(),order.getMoveToY(), delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawImage(portrait, x, y);

	}

}
