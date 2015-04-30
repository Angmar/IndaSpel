import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class CommandCenter extends Building {

	public CommandCenter(float x, float y) throws SlickException {
		super(x, y);
		width = 100;
		height = 100;

		createPortrait("commandcenter.png");
	}
	
	public void spawnWorker() throws SlickException {
		Worker worker = new Worker(x, y);
		MainGame.colonists.add(worker);	
	}

	@Override
	public void update(GameContainer container, Input input, int delta)
			throws SlickException {
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			spawnWorker();
		}
		
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawImage(portrait, x, y);
		
	}

	
}
