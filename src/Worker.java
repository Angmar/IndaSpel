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
		this.x = x;
		this.y = y;
		width = 60;
		height = 60;
		moveSpeed = 0.2;
		range = width;
		
		createPortrait("worker.png");
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
						mine();
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
				target = null;
			}
		}
		else if(movePoint != null){
			moveToPoint(delta);
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);
	}
	
	private void mine() {
		target.takeDamage(10);
		minerals += 10;
	}
	
	private void depositMinerals(){
		MainGame.minerals += minerals;
		minerals = 0;
	}
	
}
