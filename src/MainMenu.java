import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainMenu extends BasicGameState {

	int selectedOption;
	String[] menuText;
	static Image selectArrow;
	TrueTypeFont font;
	
	public MainMenu() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		selectedOption = 0;
		menuText = new String[]{"Begin Game", "Options", "Quit Game"};
		
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
				game.enterState(1);
				break;
			case 1:
				game.enterState(2);
				break;
			case 2:
				container.exit();
				break;
			}
		}
		else if(input.isKeyPressed(Input.KEY_UP) && selectedOption > 0){
			selectedOption--;
		}
		else if(input.isKeyPressed(Input.KEY_DOWN) && selectedOption < 2){
			selectedOption++;
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		//g.setFont(font);
		
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*menuText.length;
		
		for(int i = 0; i < menuText.length; i++){
			g.drawString(menuText[i], xText, yText+(50*i));
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
