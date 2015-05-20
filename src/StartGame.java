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

public class StartGame extends StateBasedGame {

	public StartGame(String name) {
		super(name);
	}

	static AppGameContainer container;
	static int screenWidth;
	static int screenHeight;

	static int difficulty = 2; // 1=Easy, 2=Medium, 3=Hard, 4=Developer skills

	public static void main(String[] args) throws SlickException {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
		StartGame game = new StartGame("Foreign Frontier");
		container = new AppGameContainer(game);
		container.setTargetFrameRate(60);
		// container.setShowFPS(false);
		container.setDisplayMode(1000, 600, false);
		container.start();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new MenuMain());// State 0
		this.addState(new MainGame());// State 1
		this.addState(new MenuOptions());// State 2
		this.addState(new MenuPause());// State 3
		this.addState(new MenuTutorial());// State 4
		this.addState(new Credits());// State 4
	}

	/**
	 * Used to change the size and thus resolution of the games screen. If it is
	 * to be fullscreen, then width and height are ignored and the screens size
	 * (that was taken in the beginning of static void main) are set as the
	 * width and height, as that is required if fullscreen is to work.
	 * 
	 * @param width
	 *            New width of the game-screen
	 * @param height
	 *            New height of the game-screen
	 * @param full
	 *            If the screen should be fullscreen
	 */
	public static void changeFullScreen(int width, int height, boolean full)
			throws SlickException {
		if (!full) {
			container.setDisplayMode(width, height, false);
		} else {
			container.setDisplayMode(screenWidth, screenHeight, true);
		}
	}

	/**
	 * @return The current difficulty
	 */
	public static int getDifficulty() {
		return difficulty;
	}

	/**
	 * Set the difficulty.
	 * 
	 * @param newDifficulty
	 */
	public static void setDifficulty(int newDifficulty) {
		difficulty = newDifficulty;
	}

	/**
	 * Load the starcaft font and create a new font in a specific size to be
	 * used.
	 * 
	 * @param f
	 *            The size of the font
	 * @param state
	 *            Used to load the resource, for some reason
	 * @return the newly created font.
	 */
	public static TrueTypeFont generateTitleFont(float f, BasicGameState state) {
		TrueTypeFont font = null;
		try (InputStream is = state.getClass().getResourceAsStream(
				"/Starcraft.otf")) {
			Font awtfont = Font.createFont(Font.TRUETYPE_FONT, is);
			Font fontBase = awtfont.deriveFont(f);
			font = new TrueTypeFont(fontBase, false);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return font;
	}
}
