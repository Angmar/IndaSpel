import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class CommandCenter extends Building implements Builder {

	private int buildProgress;
	private int[] buildTimes;
	private int[] buildCosts;
	private String[] buildOpts;
	private ArrayList<Integer> buildQueue;
	
	public CommandCenter(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait"
		super(x, y, 200, 200, 1000, 10, 60, 1, "commandcenter.png");
		buildProgress = 0;
		int[] buildTimes = {5000};
		this.buildTimes = buildTimes;
		int[] buildCosts = {50};
		this.buildCosts = buildCosts;
		String[] buildOpts = {"Worker"};
		this.buildOpts = buildOpts;
		buildQueue = new ArrayList<Integer>();
	}
	
	public void spawn(int delta, int opt) throws SlickException {
		if (buildProgress >= buildTimes[opt]) {
			Character gob;
			switch (opt) {
				case 0:
					gob = new Worker(x, y);
					break;
				case 1:
					gob = new Fighter(x, y, 1);
					break;
				default:
					gob = new Pirate(x, y, 1);
			}
			MainGame.colonists.add(gob);
			buildProgress = 0;
			buildQueue.remove(0);
		} else {
			buildProgress += delta;
		}
	}
	
	public void queueSpawn(int opt) {
		if (MainGame.minerals >= buildCosts[opt] && buildQueue.size() <= 6) {
			buildQueue.add(opt);
		}
	}
	

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		portrait.rotate((float) (0.005*delta));
		if (!(buildQueue.isEmpty())) {
			if (buildProgress == 0) {
				if (MainGame.minerals >= buildCosts[buildQueue.get(0)]) {
					MainGame.minerals -= buildCosts[buildQueue.get(0)];
					spawn(delta, buildQueue.get(0));

				} else {	
					buildQueue.remove(0);
				}
		} else {
			spawn(delta, buildQueue.get(0));
		}
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(container, g);
		
	}

	public ArrayList<String> getBuildOptions() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i=0;i<buildOpts.length;i++)
			list.add(buildOpts[i]);
		return list;
	}

	@Override
	public int getProgress() {
		return buildProgress;
	}
	
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
