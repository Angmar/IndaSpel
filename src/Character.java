import org.lwjgl.util.vector.Vector2f;
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
	
	public void setTarget(Vector2f newMovePoint){
		movePoint = newMovePoint;
		target = null;
	}
	
	private float movePointDistance(float xDistance, float yDistance){
		return (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}
	
	protected void moveToPoint(int delta){
		float xDistance = movePoint.getX() - x;
		float yDistance = movePoint.getY() - y;
		
		float hyp =  movePointDistance(xDistance, yDistance);
		
		this.x += delta*moveSpeed*(xDistance/hyp);
		this.y += delta*moveSpeed*(yDistance/hyp);
		
		if(hyp < 2){
			movePoint = null;
		}
	}
}
