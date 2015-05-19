import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Credits extends BasicGameState {
	
	ArrayList<String[]> credits;
	float timePassed;
	TrueTypeFont fontTitle;

	public Credits() {
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		timePassed = 0;
		fontTitle = StartGame.generateTitleFont(20, this);
		credits = new ArrayList<String[]>();
		
		credits.add(new String[]{"Director", "Erik Svensson"});
		credits.add(new String[]{"Co-Director", "Teodor Karlgren"});
		
		credits.add(new String[]{"Game Design", "Erik Svensson\nTeodor Karlgren"});
		credits.add(new String[]{"Programming", "Erik Svensson\nTeodor Karlgren"});
		
		credits.add(new String[]{"Sprite Artist", "MillionthVector"});

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
		
		timePassed += 0.03*delta;
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*credits.size();
		
		for(int i = 0; i < credits.size(); i++){
			g.drawString(credits.get(i)[1], xText, yText+30+(170*i)-timePassed);
		}
		
		g.setFont(fontTitle);
		for(int i = 0; i < credits.size(); i++){
			g.drawString(credits.get(i)[0], xText, yText+(170*i)-timePassed);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 5;
	}

}
