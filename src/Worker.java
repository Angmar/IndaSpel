import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Worker extends Character {
	
	private int minerals;
	
	public Worker(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, attackSpeed, "portrait", moveSpeed
		super(x, y, 60, 60, 100, 20, 60, 1000, 1, "worker.png", 0.2);
	}

	@Override
	public void update(GameContainer container, int delta) 
			throws SlickException {
		if(target != null){
			//Check if target is a mineral
			if(target.getClass() == MineralOre.class){
				//If the worker holds no minerals
				if(minerals == 0){
					mine(delta);
				}
				//Else the target holds minerals and goes to deposit
				else{
					deposit(delta);
				}
			}
			else{
				setMoveToGameObjectPoint();
				miningInterrupt();
			}
		}
		else if(movePoint != null){
			moveToPoint(delta);
			miningInterrupt();
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);
	}
	
	private void miningInterrupt(){
		if(attackProgress != 0){
			attackProgress = 0;
		}
	}
	
	//Worker goes to mine the target mineral ore
	private void mine(int delta) {
		if(targetInRange()){
			movePoint = null;
			if(target.getTarget() == this && target.isAlive()){
				if(attackProgress >= attackSpeed){
					target.takeDamage(damage);
					target.clearTarget();
					
					minerals += damage;
					attackProgress = 0;
				}
				else{
					attackProgress += delta;
				}
			}
			else if(target.getTarget() == null){
				target.setTarget(this);
			}
		}
		else if(movePoint != null){
			moveToPoint(delta);
		}
		else{
			movePoint = new Vector2f(target.getX(), target.getY());
		}
	}
	
	//Worker goes to deposit minerals
	private void deposit(int delta){
		if(movePoint != null){
			moveToPoint(delta);
			if(movePoint == null){
				movePoint = null;
				MainGame.minerals += minerals;
				minerals = 0;
				if(!target.isAlive()){
					target = null;
				}
			}
		}
		else{
			CommandCenter comC = MainGame.nearestCommandCenter(x, y);
			if(comC != null){
				GameObject minTarg = target;
				target = comC;
				setMoveToGameObjectPoint();
				target = minTarg;
				//movePoint = new Vector2f(comC.getX(), comC.getY());
			}
		}
		
	}
	
}
