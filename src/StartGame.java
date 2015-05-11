import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class StartGame extends StateBasedGame{

	public StartGame(String name){
		super(name);
	}
	
	static AppGameContainer container;
	static int screenWidth;
	static int screenHeight;

	public static void main(String[] args) throws SlickException {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
		
		StartGame game = new StartGame("Foreign Frontier");
		container = new AppGameContainer(game);
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
	
	public static void changeFullScreen() throws SlickException{
		if(container.isFullscreen()){
			container.setDisplayMode(1000, 600, false);
		}
		else{
			container.setDisplayMode(screenWidth, screenHeight, true);
		}
	}

}
