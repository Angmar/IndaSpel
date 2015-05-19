import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MenuMain extends BasicGameState {

	int selectedOption;
	String[] menuOptions;
	static Image selectArrow;
	static Image background;
	
	TrueTypeFont font;
	TrueTypeFont titleFont;
	
	public MenuMain() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		selectedOption = 0;
		menuOptions = new String[]{"Begin New Game", "Continue Saved Game", "Options", "Basic Text Tutorial", "Credits", "Quit Game"};
		selectArrow = new Image("SelectArrow.png");
		selectArrow = selectArrow.getScaledCopy(40, 40);
		selectArrow.rotate(90);
		
		background = new Image("space2.png");
		
		font = StartGame.generateTitleFont(28, this);
		titleFont = StartGame.generateTitleFont(50, this);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			switch(selectedOption){
			case 0:
				game.init(container);
				input.clearKeyPressedRecord();
				game.enterState(1);
				break;
			case 1:
				input.clearKeyPressedRecord();
				try(Scanner scan = new Scanner(new File("savefile.txt"))){
					MainGame.initSaveFile(scan);
				} catch (IOException e) {
					//Failed to read file, either because it doesn't exist or is corrupted
					e.printStackTrace();
					game.init(container);
				}
				game.enterState(1);
				break;
			case 2:
				input.clearKeyPressedRecord();
				game.enterState(2);
				break;
			case 3:
				input.clearKeyPressedRecord();
				game.enterState(4);
				break;
			case 4:
				input.clearKeyPressedRecord();
				game.enterState(5);
				break;
			default:
				container.exit();
				break;
			}
		}
		else if(input.isKeyPressed(Input.KEY_UP) && selectedOption > 0){
			selectedOption--;
		}
		else if(input.isKeyPressed(Input.KEY_DOWN) && selectedOption < menuOptions.length){
			selectedOption++;
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		
		renderBackground(g);
		
		g.setFont(font);
		
		float xText = container.getWidth()/7*2;
		float yText = container.getHeight()/2-25*menuOptions.length;
		
		for(int i = 0; i < menuOptions.length; i++){
			g.drawString(menuOptions[i], xText, yText+(50*i));
		}
		
		g.drawImage(selectArrow, xText-50, yText+(50*selectedOption));
		
		g.setFont(titleFont);
		g.setColor(Color.red);
		g.drawString("Foreign Frontier", xText, yText-100);
		g.setColor(Color.white);
		
	}
	
	public static void renderBackground(Graphics g){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				g.drawImage(background, background.getWidth()*i, background.getHeight()*j);
			}
		}
	}
	
	public static Image getSelectImage(){
		return selectArrow;
	}

	@Override
	public int getID() {
		return 0;
	}

}
