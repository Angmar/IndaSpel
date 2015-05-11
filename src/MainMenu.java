import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainMenu extends BasicGameState {

	public MainMenu() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			game.enterState(1);
		}
		else if(input.isKeyPressed(Input.KEY_ESCAPE)){
			container.exit();
		}
		else if(input.isKeyPressed(Input.KEY_T)){
			 StartGame.changeFullScreen();
		}

	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Press SPACE to continue", 400, 200);
		g.drawString("Press ESCAPE to quit", 400, 300);
		g.drawString("Press T to toggle fullscreen", 400, 400);
	}

	@Override
	public int getID() {
		return 0;
	}

}
