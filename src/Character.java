import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Character extends GameObject {
	
	protected double moveSpeed;
	
	public Character(float x, float y, int width, int height, int maxHealth, int damage, 
				float range, int attackSpeed, int faction, String image, double moveSpeed) throws SlickException{
		
		//x, y, width, height, maxHealth, damage, range, attackSpeed, faction, "portrait"
		super(x, y, width, height, maxHealth, damage, range, attackSpeed, faction, image);
		
		this.moveSpeed = moveSpeed;
	}
	
	protected void move(){
		
	}
	
	public void setTarget(Vector2f newMovePoint){
		movePoint = newMovePoint;
		target = null;
	}
	
	protected void setMoveToGameObjectPoint(){
		float distance = getDistance(target.getX(), target.getY());
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
	
	protected void moveToTarget(int delta){
		moveTo(target.getX(), target.getY(), range, delta);
	}
	
	protected void moveToPoint(int delta){
		moveTo(movePoint.getX(), movePoint.getY(), delta*moveSpeed, delta);
	}
	
	private void moveTo(float xPoint, float yPoint, double moveRange, int delta){
		double distance =  getDistance(xPoint, yPoint);
		if(distance > moveRange){
			float xDistance = xPoint - x;
			float yDistance = yPoint - y;
			
			this.x += delta*moveSpeed*(xDistance/distance);
			this.y += delta*moveSpeed*(yDistance/distance);
			
			turnToTarget(xDistance, yDistance);
		}
		else{
			x = movePoint.getX();
			y = movePoint.getY();
			movePoint = null;
		}
		posRect.setLocation(x-width/2, y-height/2);
	}
	
	protected void assaultTarget(int delta){
		
		if(!targetInRange() && target.isAlive()){
			moveToTarget(delta);
		}
		else if(targetInRange() && attackProgress == 0 && target.isAlive()){
			target.takeDamage(damage);
			target.setAttacker(this);
			attackProgress += delta;
		}
		else if(attackProgress > 0 &&  attackProgress < attackSpeed){
			attackProgress += delta;
		}
		else{
			attackProgress = 0;
			if(!target.isAlive()){
				target = null;
			}
		}
	}
	
	protected void playerControlledAI(int delta){
		if(target != null){
			if(target.getFaction() == 0 || target.getFaction() == faction){
				setMoveToGameObjectPoint();
			}
			else if(!isSameFaction()){
				assaultTarget(delta);
			}
		}
		else if(attacker != null){
			target = attacker;
			attacker = null;
		}
		else if(movePoint != null){
			moveToPoint(delta);
		}
		else{
			GameObject possibleTarget = MainGame.nearestEnemy(x, y, faction);
				
			if(possibleTarget != null && targetInSpotRange(possibleTarget)){
				target = possibleTarget;
			}
		}
	}
	
	protected void enemyAI(int delta){
		if(target != null){
			if(!isSameFaction()){
				assaultTarget(delta);
			}
			if(target == null){
				target = MainGame.nearestCommandCenter(x, y);
			}
			else if(Building.class.isAssignableFrom(target.getClass())){
				GameObject possibleTarget = MainGame.nearestEnemy(x, y, faction);
				if(target != null && targetInSpotRange(possibleTarget)){
					target = possibleTarget;
				}
			}
		}
		else if(attacker != null){
			target = attacker;
			attacker = null;
		}
		else{
			target = MainGame.nearestCommandCenter(x, y);
		}
	}
	
	public void push(double cos, double sin, double force, int delta){
		x += force*cos*delta;
		y += force*sin*delta;
		posRect.setLocation(x-width/2, y-height/2);
	}
}
