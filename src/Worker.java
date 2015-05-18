import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Worker extends Character implements Builder {
	
	private int minerals;
	private int buildProgress;
	private int[] buildTimes;
	private int[] buildCosts;
	private String[] buildOpts;
	private ArrayList<Integer> buildQueue;
	
	public Worker(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, attackSpeed, "portrait", moveSpeed
		super(x, y, 60, 60, 100, 20, 60, 1000, 1, "worker.png", 0.2);
		
		attackLaser = Color.yellow;
		spotRange = 500;
	
		buildProgress = 0;
		int[] buildTimes = {10000, 10000};
		this.buildTimes = buildTimes;
		int[] buildCosts = {200, 200};
		this.buildCosts = buildCosts;
		String[] buildOpts = {"Command Center", "Factory"};
		this.buildOpts = buildOpts;
		buildQueue = new ArrayList<Integer>();
	}

	@Override
	public void update(GameContainer container, int delta) 
			throws SlickException {
		if(target != null){
			//Check if target is construction site
			if(target.getClass() == ConstructionSite.class) {
				buildProgress = target.getCurrentHealth();
				if (buildProgress >= target.getMaxHealth()) {
					MainGame.build((Building) target, ((ConstructionSite)target).getBuilding());
					buildQueue.clear();
					target = null;
					buildProgress = 0;
				} else if (buildProgress < target.getMaxHealth()) {
					((ConstructionSite) target).construct(delta);
				}

			}
			//Check if target is a mineral
			else if(target.getClass() == MineralOre.class){
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
		if (buildProgress == 0 && !buildQueue.isEmpty() && target == null) {
			ConstructionSite cs = new ConstructionSite(x, y, buildQueue.get(0));
			MainGame.buildings.add(cs);
			target = cs;
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(container, g);
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
			if(target.isAlive()){
				
				if(target.getTarget() == this){
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
					attackProgress += delta;
				}
				else{
					target = MainGame.nearestFreeResource(x, y);
					if(!targetInSpotRange(target)){
						target = null;
					}
					attackProgress = 0;
				}
			}
			else{
				target = MainGame.nearestFreeResource(x, y);
				if(!targetInSpotRange(target)){
					target = null;
				}
				attackProgress = 0;
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
					target = MainGame.nearestFreeResource(x, y);
					if(!targetInSpotRange(target)){
						target = null;
					}
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

	
	@Override
	public ArrayList<String> getBuildOptions() {
		ArrayList<String> list = new ArrayList<String>();
		for (String s : buildOpts) {
			list.add(s);
		}
		return list;
	}

	@Override
	public int getProgress() {
		return buildProgress;
	}

	@Override
	public void queueSpawn(int opt) {
		if (buildQueue.isEmpty() && MainGame.minerals > buildCosts[opt]) {
			buildQueue.add(opt);
			MainGame.minerals -= buildCosts[opt];
			
		}
	}

	@Override
	public ArrayList<Integer> getBuildQueue() {
		return buildQueue;
	}

	@Override
	public int[] getBuildTime() {
		return buildTimes;
	}

	@Override
	public int[] getBuildCosts() {
		return buildCosts;
	}
}
