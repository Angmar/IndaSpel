import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Character extends GameObject {
	
	protected double moveSpeed;

	public abstract void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException;
	
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException;
	
	protected void moveTo(float goX, float goY, int delta){
		
		float xDistance = goX - x;
		float yDistance = goY - y;
		double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));
		portrait.setRotation((float)angleToTurn);
		x += moveSpeed*
		
	}
	
}
