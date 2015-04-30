import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	protected boolean alive;
	protected Image portrait;
	
	//To be changed
	protected boolean selected;
	protected String order;
	protected GameObject target;
	
	public abstract void update(GameContainer container, Input input, int delta)
			throws SlickException;
	
	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;
	
	protected void createPortrait(String image) throws SlickException{
		portrait = new Image(image);
		portrait = portrait.getScaledCopy(width, height);
	}
	
	protected boolean inRange(){
		if(targetDistance() < range){
			return true;
		}
		return false;
	}
	
	protected double targetDistance(){
		return Math.sqrt(Math.pow(target.getX()-x, 2) + Math.pow(target.getX()-x, 2));
	}
	
	public void setTarget(GameObject newTarget){
		target = newTarget;
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
	
	public void takeDamage(int damage) {
		currentHealth -= damage;
	}
	
	public void select(){ 
		selected = true;
	}
	
	public void deselect(){
		selected = false;
	}
	
}
