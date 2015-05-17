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
	protected float spotRange;
	protected int attackSpeed;
	protected int attackProgress;
	protected boolean alive;
	protected Image portrait;
	protected Rectangle posRect;
	protected Color attackLaser;
	protected int faction; //0 = Resource, 1 = Friendly, 2 = Enemy
	
	private Image movePointFlag;
	
	//To be changed
	protected boolean selected;
	protected Vector2f movePoint;
	protected GameObject target;
	protected GameObject attacker;
	
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
		this.spotRange = range*3;
		this.attackSpeed = attackSpeed;
		this.attackProgress = 0;
		this.faction = faction;
		this.attackLaser = Color.red;
		
		if(faction == 1){
			movePointFlag  = new Image("movepoint.png");
			movePointFlag = movePointFlag.getScaledCopy(20, 20);
		}
		
		this.alive = true;
		
		this.portrait = new Image(image);
		this.portrait = portrait.getScaledCopy(width, height);
		
		this.posRect = new Rectangle(x-width/2, y-height/2, width, height);
	}
	
	public abstract void update(GameContainer container, int delta)
			throws SlickException;
	
	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;
	
	protected void renderPortrait(GameContainer c, Graphics g){
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
			//g.setLineWidth(1);
			
			if(selected && hasTarget() && faction==1){
				if(movePoint != null && target == null){
					g.drawImage(movePointFlag, movePoint.getX()-movePointFlag.getWidth()/2, movePoint.getY()-movePointFlag.getHeight()/2);
				}
				else{
					//Find a good way to indicate target
				}
			}
		}
		if(attackProgress < attackSpeed/2 && attackProgress > 0 && target != null && targetInRange()){
			turnToTarget(target.getX()-x, target.getY()-y);
			
			g.setLineWidth(3);
			g.setColor(attackLaser);
			g.drawLine(x, y, target.getX(), target.getY());
			g.setColor(Color.white);
			//g.setLineWidth(1);
		}
		
		g.drawImage(portrait, x-width/2, y-height/2);
	}
	
	private void drawSelectBox(Graphics g, float xBox, float yBox, int lineDist){
		
	}
	
	public void drawOnMap(GameContainer c, Graphics g, float cameraX, float cameraY) {
		if (faction == 1){
			g.setColor(Color.green);
		}
		else if (faction == 2){
			g.setColor(Color.red);
		}
		float mapX = (c.getHeight()/4)*x/MainGame.FIELDSIZE;
		float mapY = (c.getHeight()/4)*y/MainGame.FIELDSIZE+3*c.getHeight()/4;
		
		if(mapX > 0 && mapX < c.getHeight()/4 && mapY > c.getHeight()*3/4 && mapY < c.getHeight()){
			g.fill(new Rectangle(mapX, mapY, 2, 2));
		}
		g.setColor(Color.white);
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
		if(targetDistance(target.getX(), target.getY()) <= range){
			return true;
		}
		return false;
	}
	
	protected boolean targetInSpotRange(GameObject possibleTarget){
		return targetDistance(possibleTarget.getX(), possibleTarget.getY()) <= spotRange;
	}
	
	public float targetDistance(float targetX, float targetY){
		return (float) Math.sqrt(Math.pow(targetX-x, 2) + Math.pow(targetY-y, 2));
	}
	
	public boolean hasTarget(){
		if(target != null || movePoint != null){
			return true;
		}
		return false;
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
	
	public Vector2f getMovePoint(){
		return movePoint;
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
	
	public boolean isAttacking(){
		return attackProgress > 0;
	}
	
	public void setAttacker(GameObject attacker){
		this.attacker = attacker;
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
