import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class StartGame extends StateBasedGame{

	public StartGame(String name){
		super(name);
	}

	public static void main(String[] args) throws SlickException {
		StartGame game = new StartGame("Foreign Frontier");
		AppGameContainer container = new AppGameContainer(game);
		container.setTargetFrameRate(60);
		//container.setShowFPS(false);
		container.setDisplayMode(1000, 600, false);
		container.start();

	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new MainMenu());//State 0
		this.addState(new MainGame());//State 1
		
	}

}
