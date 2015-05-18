import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Credits extends BasicGameState {
	
	ArrayList<String> credits;
	float timePassed;

	public Credits() {
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		timePassed = 0;
		
		credits = new ArrayList<String>();
		
		credits.add("Main Director \n\nErik Svensson");
		credits.add("Co-Director \n\nTeodor Karlgren");
		
		credits.add("Game Design \n\nErik Svensson\nTeodor Karlgren");
		credits.add("Programming \n\nErik Svensson\nTeodor Karlgren");
		
		credits.add("Sprite Artist \n\nMillionthVenctor");

	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
				input.clearKeyPressedRecord();
				timePassed = 0;
				game.enterState(0);
		}
		
		timePassed += 0.15*delta;
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*credits.size();
		
		for(int i = 0; i < credits.size(); i++){
			g.drawString(credits.get(i), xText, yText+(170*i)-timePassed/4);
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 5;
	}

}
