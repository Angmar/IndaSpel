import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Character extends GameObject {
	
	protected double moveSpeed;
	protected Order order;
	
	public Character(){
		order = new Order();
	}

	public abstract void update(GameContainer container, Input input, int delta)
			throws SlickException;
	
	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;
	
	protected void moveTo(float goX, float goY, int delta){
		
		float xDistance = goX - x;
		float yDistance = goY - y;
		
		double angle = Math.toDegrees(Math.atan2(yDistance, xDistance));
		portrait.setRotation((float)angle);
		
		this.x += moveSpeed*Math.cos(angle);
		this.y += moveSpeed*Math.sin(angle);
	}
}
