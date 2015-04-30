import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Character extends GameObject {
	
	protected double moveSpeed;
	
	public Character(){
		
	}
	
	protected void move(){
		
	}
	
	protected void moveTo(int delta){
		
		float xDistance = target.getX() - x;
		float yDistance = target.getY() - y;
		
		float hyp = (float) targetDistance();
		
		this.x += delta*moveSpeed*(xDistance/hyp);
		this.y += delta*moveSpeed*(yDistance/hyp);
	}
}
