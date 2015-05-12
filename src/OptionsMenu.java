import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class OptionsMenu extends BasicGameState {
	
	int selectedOption;
	int menuDepth;
	String[] menuText;

	public OptionsMenu() {
		
	}

	@Override
	public void init(GameContainer contianer, StateBasedGame arg1)
			throws SlickException {
		selectedOption = 1;
		menuDepth = 1;
		
		menuText = new String[]{"Resolution", "Difficulty", "Return"};
	}
	

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(menuDepth == 1){
				switch(selectedOption){
				case 1:
					menuDepth = 2;
					break;
				case 3:
					game.enterState(0);
					break;
				}
			}
		}
		else if(input.isKeyPressed(Input.KEY_UP) && selectedOption > 1){
			selectedOption--;
		}
		else if(input.isKeyPressed(Input.KEY_DOWN) && selectedOption < 3){
			selectedOption++;
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*menuText.length;
		
		for(int i = 0; i < menuText.length; i++){
			g.drawString(menuText[i], xText, yText+(50*i));
		}
		
		g.drawLine(xText-30, yText+(50*(selectedOption-1)), xText-10, yText+(50*(selectedOption-1))+10);
		g.drawLine(xText-30, yText+(50*(selectedOption-1))+20, xText-10, yText+(50*(selectedOption-1))+10);
		
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
