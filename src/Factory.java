import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Factory extends Building implements Builder {

	private String[] buildOpts;
	private int buildProgress;
	private ArrayList<Integer> buildQueue;
	private int[] buildTimes;
	private int[] buildCosts;

	public Factory(float x, float y)
			throws SlickException {
		super(x, y, 150, 150, 1000, 10, 60, 1, "factory.png");


		buildProgress = 0;
		buildQueue = new ArrayList<Integer>();
		String[] buildOpts = {"Fighter", "Tank", "Sniper"};
		this.buildOpts = buildOpts;
		int[] buildCosts = {100, 200, 200};
		this.buildCosts = buildCosts;
		int[] buildTimes = {3000, 5000, 5000};
		this.buildTimes = buildTimes;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
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

	public void spawn(int delta, int opt) throws SlickException {
		if (buildProgress >= buildTimes[opt]) {
			Character gob;
			switch (opt) {
				case 0:
					gob = new Fighter(x, y, 1);
					break;
				case 1:
					gob = new Tank(x, y, 1);
					break;
				default:
					gob = new Sniper(x, y, 1);
			}
			MainGame.colonists.add(gob);
			buildProgress = 0;
			buildQueue.remove(0);
		} else {
			buildProgress += delta;
		}
	}
	
	@Override
	public ArrayList<String> getBuildOptions() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i=0;i<buildOpts.length;i++)
			list.add(buildOpts[i]);
		return list;
	}

	@Override
	public int getProgress() {
		// TODO Auto-generated method stub
		return buildProgress;
	}

	@Override
	public void queueSpawn(int opt) {
		buildQueue.add(opt);
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
