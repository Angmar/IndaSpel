import java.awt.Font;

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
	TrueTypeFont font;
	
	public MenuMain() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		selectedOption = 0;
		menuOptions = new String[]{"Begin New Game", "Continue Saved Game", "Options", "Quit Game"};
		
		selectArrow = new Image("SelectArrow.png");
		selectArrow = selectArrow.getScaledCopy(40, 40);
		selectArrow.rotate(90);
		
		
		//Font awtFont = new Font("Papyrus", Font.PLAIN, 16);
		//font = new TrueTypeFont(awtFont, false);
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
				game.enterState(1);
				break;
			case 2:
				input.clearKeyPressedRecord();
				game.enterState(2);
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
		
		//g.setFont(font);
		
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*menuOptions.length;
		
		for(int i = 0; i < menuOptions.length; i++){
			g.drawString(menuOptions[i], xText, yText+(50*i));
		}
		
		g.drawImage(selectArrow, xText-50, yText-10+(50*selectedOption));
	}
	
	public static Image getSelectImage(){
		return selectArrow;
	}

	@Override
	public int getID() {
		return 0;
	}

}
