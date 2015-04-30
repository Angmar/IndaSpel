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
	protected boolean alive;
	protected Image portrait;
	protected boolean selected;
	protected Order currentOrder;
	
	public abstract void update(GameContainer container, Input input, int delta)
			throws SlickException;
	
	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;
	
	public abstract Order getOrder();
	
	public abstract void setOrder(GameObject target);
	
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
