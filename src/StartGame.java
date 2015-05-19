import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class StartGame extends StateBasedGame{

	public StartGame(String name){
		super(name);
	}
	
	static AppGameContainer container;
	static int screenWidth;
	static int screenHeight;
	static Image background;
	
	static int difficulty = 2; //1=Easy, 2=Medium, 3=Hard, 4=Developer skills

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
		this.addState(new MenuMain());//State 0
		this.addState(new MainGame());//State 1
		this.addState(new MenuOptions());//State 2
		this.addState(new MenuPause());//State 3
		this.addState(new MenuTutorial());//State 4
		this.addState(new Credits());//State 4
	}
	
	public static void changeFullScreen(int width, int height, boolean full) throws SlickException{
		if(!full){
			container.setDisplayMode(width, height, false);
		}
		else{
			container.setDisplayMode(screenWidth, screenHeight, true);
		}
	}
	
	public static int getDifficulty(){
		return difficulty;
	}
	public static void setDifficulty(int newDifficulty){
		difficulty = newDifficulty;
	}
	public static Image getBackground(){
		return background;
	}
	
	public static TrueTypeFont generateTitleFont(float f, BasicGameState state){
		TrueTypeFont font = null;
		try (InputStream is = state.getClass().getResourceAsStream("/Starcraft.otf")) {
			Font awtfont = Font.createFont(Font.TRUETYPE_FONT, is);
			Font fontBase = awtfont.deriveFont(f);
			//Font fontBase = new Font("Calibri", Font.BOLD, 16);
			font = new TrueTypeFont(fontBase, false);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return font;
	}
}
