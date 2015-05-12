import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


public abstract class GameObject {
	
	protected float x;
	protected float y;
	protected int height;
	protected int width;
	protected int maxHealth;
	protected int currentHealth;
	protected int damage;
	protected float range;
	protected int attackSpeed;
	protected int attackProgress;
	protected boolean alive;
	protected Image portrait;
	protected Rectangle posRect;
	protected Color attackLaser;
	protected int faction; //0 = Resource, 1 = Friendly, 2 = Enemy
	
	//To be changed
	protected boolean selected;
	protected Vector2f movePoint;
	protected GameObject target;
	
	public GameObject(float x, float y, int width, int height, int maxHealth, 
			int damage, float range, int attackSpeed, int faction, String image) throws SlickException{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.damage = damage;
		this.range = range;
		this.attackSpeed = attackSpeed;
		this.attackProgress = 0;
		this.faction = faction;
		this.attackLaser = Color.red;
		
		this.alive = true;
		
		this.portrait = new Image(image);
		this.portrait = portrait.getScaledCopy(width, height);
		
		this.posRect = new Rectangle(x-width/2, y-height/2, width, height);
	}
	
	public abstract void update(GameContainer container, int delta)
			throws SlickException;
	
	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;
	
	protected void renderPortrait(Graphics g){
		if(selected){
			g.setColor(Color.green);
			g.setLineWidth(2);
			int lineDist = Math.max(height, width);
			
			g.drawLine(x-lineDist/2, y-lineDist/2, x-lineDist/2, y+lineDist/2);
			g.drawLine(x+lineDist/2, y-lineDist/2, x+lineDist/2, y+lineDist/2);
			
			g.drawLine(x+lineDist/2, y-lineDist/2, x+lineDist/3, y-lineDist/2);
			g.drawLine(x+lineDist/2, y+lineDist/2, x+lineDist/3, y+lineDist/2);
			
			g.drawLine(x-lineDist/2, y-lineDist/2, x-lineDist/3, y-lineDist/2);
			g.drawLine(x-lineDist/2, y+lineDist/2, x-lineDist/3, y+lineDist/2);
			g.setColor(Color.red);
			
			g.drawLine(x-lineDist/2, y-lineDist/2-10, (x-lineDist/2+(lineDist*((float)currentHealth/maxHealth))), y-lineDist/2-10);
			
			g.setColor(Color.white);
		}
		if(attackProgress < attackSpeed/2 && attackProgress > 0 && target != null && targetInRange()){
			turnToTarget(target.getX()-x, target.getY()-y);
			
			g.setLineWidth(3);
			g.setColor(attackLaser);
			g.drawLine(x, y, target.getX(), target.getY());
			g.setColor(Color.white);
		}
		
		g.drawImage(portrait, x-width/2, y-height/2);
		
	}
	
	protected void turnToTarget(float xDistance, float yDistance){
		portrait.setRotation((float)(90+Math.toDegrees(Math.atan2(yDistance, xDistance))));	
	}
	
	public abstract void setTarget(Vector2f newMovePoint);
	
	public void setTarget(GameObject newTarget){
		target = newTarget;
		movePoint = null;
	}
	
	public void clearTarget(){
		movePoint = null;
		target = null;
	}
	
	protected boolean targetInRange(){
		if(targetDistance(target.getX(), target.getY()) < range){
			return true;
		}
		return false;
	}
	
	protected float targetDistance(float targetX, float targetY){
		return (float) Math.sqrt(Math.pow(targetX-x, 2) + Math.pow(targetY-y, 2));
	}
	
	protected float targetDistance(){
		return (float) Math.sqrt(Math.pow(target.getX()-x, 2) + Math.pow(target.getY()-y, 2));
	}
	
	protected boolean isSameFaction(int targetFaction){
		return targetFaction==faction;
	}
	
	protected boolean isSameFaction(){
		return faction==target.getFaction();
	}
	
	public GameObject getTarget(){
		return target;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public Rectangle getRect(){
		return posRect;
	}
	
	public void takeDamage(int damage) {
		currentHealth -= damage;
		if(currentHealth <= 0){
			alive = false;
		}
	}
	
	public void select(){ 
		selected = true;
	}
	
	public void deselect(){
		selected = false;
	}
	
	public int getFaction(){
		return faction;
	}
	
}
