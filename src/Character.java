import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Character extends GameObject {
	
	protected double moveSpeed;
	
	public Character(float x, float y, int width, int height, 
			int maxHealth, int damage, float range, int attackSpeed, String image, double moveSpeed) throws SlickException{
		
		//x, y, width, height, maxHealth, damage, range, attackSpeed, "portrait"
		super(x, y, width, height, maxHealth, damage, range, attackSpeed, image);
		
		this.moveSpeed = moveSpeed;
	}
	
	protected void move(){
		
	}
	
	public void setTarget(Vector2f newMovePoint){
		movePoint = newMovePoint;
		target = null;
	}
	
	protected void setMoveToGameObjectPoint(){
		float distance = targetDistance(target.getX(), target.getY());
		float xDistance = target.getX() - x;
		float yDistance = target.getY() - y;
		
		float moveWidth = width/2 + target.getHeight()/2;
		float moveHeight = height/2 + target.getHeight()/2;
		
		
		if(distance > 0 && (Math.abs(xDistance) > moveWidth || yDistance > moveHeight)){
			float xPoint = target.getX() - ((xDistance/distance) * moveWidth);
			float yPoint = target.getY() - ((yDistance/distance) * moveHeight);
			
			posRect.setLocation(x-width/2, y-height/2);
			setTarget(new Vector2f(xPoint, yPoint));
		}
	}
	
	protected void moveToPoint(int delta){
		
		double distance =  targetDistance(movePoint.getX(), movePoint.getY());
		if(distance > delta*moveSpeed){
			float xDistance = movePoint.getX() - x;
			float yDistance = movePoint.getY() - y;
			
			this.x += delta*moveSpeed*(xDistance/distance);
			this.y += delta*moveSpeed*(yDistance/distance);
			
			portrait.setRotation((float)(90+Math.toDegrees(Math.atan2(yDistance, xDistance))));	
		}
		else{
			x = movePoint.getX();
			y = movePoint.getY();
			movePoint = null;
		}
		posRect.setLocation(x-width/2, y-height/2);
	}
}
