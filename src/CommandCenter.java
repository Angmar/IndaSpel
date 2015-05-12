import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class CommandCenter extends Building {

	private int buildProgress;
	private int[] buildTimes;
	private int[] buildCosts;
	private String[] buildOpts;
	private ArrayList<Integer> buildQueue;
	
	public CommandCenter(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait"
		super(x, y, 200, 200, 1000, 10, 60, "commandcenter.png");
		buildProgress = 0;
		int[] buildTimes = {5000, 7000, 7000};
		this.buildTimes = buildTimes;
		int[] buildCosts = {50, 100, 100};
		this.buildCosts = buildCosts;
		String[] buildOpts = {"Worker", "Fighter", "Other"};
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
					gob = new Fighter(x, y);
					break;
				default:
					gob = new Pirate(x, y);
			}
			MainGame.minerals -= buildCosts[opt];
			MainGame.colonists.add(gob);
			buildProgress = 0;
			buildQueue.remove(0);
		} else {
			buildProgress += delta;
		}
	}
	
	public void queueSpawn(int opt) {
		if (MainGame.minerals > buildCosts[opt])
			buildQueue.add(opt);
	}
	

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		portrait.rotate((float) (0.005*delta));
		
		if (!(buildQueue.isEmpty()))
				spawn(delta, buildQueue.get(0));

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);
		
	}

	public ArrayList<String> getBuildOptions() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i=0;i<buildOpts.length;i++)
			list.add(buildOpts[i] + "   cost: " + buildCosts[i]);
		return list;
	}
}
