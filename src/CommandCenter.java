import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class CommandCenter extends Building {

	private int buildProgress;
	private int buildTime;
	
	public CommandCenter(float x, float y) throws SlickException {
		//x, y, width, height, maxHealth, damage, range, "portrait"
		super(x, y, 200, 200, 1000, 10, 60, "commandcenter.png");
		buildProgress = 0;
		buildTime = 5000;
	}
	
	public void spawnWorker(int delta) throws SlickException {
		if (buildProgress >= buildTime) {	
			MainGame.minerals -= 100;
			Worker worker = new Worker(x, y);
			MainGame.colonists.add(worker);	
		} else {
			buildProgress += delta;
		}
	}
	


	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		portrait.rotate((float) (0.005*delta));
		
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);
		
	}

	
}
