import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Character extends GameObject {
	
	protected double moveSpeed;
	
	public Character(float x, float y, int width, int height, 
			int maxHealth, int damage, float range, String image, double moveSpeed) throws SlickException{
		
		//x, y, width, height, maxHealth, damage, range, "portrait"
		super(x, y, width, height, maxHealth, damage, range, image);
		
		this.moveSpeed = moveSpeed;
	}
	
	protected void move(){
		
	}
	
	public void setTarget(Vector2f newMovePoint){
		movePoint = newMovePoint;
		target = null;
	}
	
	protected void setMoveToBuildingPoint(){
		float distance = targetDistance(target.getX(), target.getY());
		float xDistance = target.getX() - x;
		float yDistance = target.getY() - y;
		
		float xPoint = target.getX() - ((xDistance/distance) * (width/2 + target.getHeight()/2));
		float yPoint = target.getY() - ((yDistance/distance) * (height/2 + target.getHeight()/2));
		
		posRect.setLocation(x-width/2, y-height/2);
		setTarget(new Vector2f(xPoint, yPoint));
	}
	
	protected void moveToPoint(int delta){
		float xDistance = movePoint.getX() - x;
		float yDistance = movePoint.getY() - y;
		
		double distance =  targetDistance(movePoint.getX(), movePoint.getY());
		
		this.x += delta*moveSpeed*(xDistance/distance);
		this.y += delta*moveSpeed*(yDistance/distance);
		
		posRect.setLocation(x-width/2, y-height/2);
		portrait.setRotation((float)(90+Math.toDegrees(Math.atan2(yDistance, xDistance))));	
		if(distance < 2){
			movePoint = null;
		}
	}
}
