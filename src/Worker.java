import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Worker extends Character {
	
	private int minerals;
	
	//Time it takes to mine
	private int miningTime;
	private int miningProgress;
	
	public Worker(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait", moveSpeed
		super(x, y, 60, 60, 100, 20, 60, "worker.png", 0.2);
		
		miningTime = 1000;
		miningProgress = 0;
	}

	@Override
	public void update(GameContainer container, Input input, int delta)
			throws SlickException {
		if(target != null){
			//Check if target is a mineral
			if(target.getClass() == MineralOre.class){
				//If the worker holds no minerals
				if(minerals == 0){
					if(targetInRange()){
						movePoint = null;
						mine(delta);
					}
					else if(movePoint != null){
						moveToPoint(delta);
					}
					else{
						movePoint = new Vector2f(target.getX(), target.getY());
					}
				}
				//Else the target holds minerals and goes to deposit
				else{
					if(movePoint != null){
						moveToPoint(delta);
						if(targetDistance(movePoint.getX(), movePoint.getY()) < 100){
							movePoint = null;
							depositMinerals();
						}
					}
					else{
						CommandCenter comC = MainGame.nearestCommandCenter(x, y);
						if(comC != null){
							movePoint = new Vector2f(comC.getX(), comC.getY());
						}
					}
				}
			}
			else{
				setTarget(new Vector2f(target.getX(), target.getY()));
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
		if(miningProgress != 0){
			miningProgress = 0;
		}
	}
	
	private void mine(int delta) {
		if(miningProgress >= miningTime){
			target.takeDamage(damage);
			minerals += damage;
			miningProgress = 0;
		}
		else{
			miningProgress += delta;
		}
	}
	
	private void depositMinerals(){
		MainGame.minerals += minerals;
		minerals = 0;
	}
	
}
