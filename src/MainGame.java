import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends BasicGameState {
	
	Image testImage;
	boolean test;
	static ArrayList<Character> colonists;
	static ArrayList<Building> buildings;
	static ArrayList<GameObject> selected;

	public MainGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		buildings =  new ArrayList<Building>();
		buildings.add(new CommandCenter(400,400));
		colonists = new ArrayList<Character>();
		colonists.add(new Worker(300,300));
		buildings.add(new MineralOre(400,500));
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			game.enterState(0);
		}

		if(!colonists.isEmpty()){
			for(Character ch : colonists){
				ch.update(container, input, delta);
			}
		}
		

		if(!buildings.isEmpty()){
			for(Building b : buildings){
				b.update(container, input, delta);
			}
		}
		
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Press SPACE to go to main menu", 400, 200);

		if(!colonists.isEmpty()){
			for(Character ch : colonists){
				ch.render(container, g);
			}
		}
		
		if(!buildings.isEmpty()){
			for(Building b : buildings){
				b.render(container, g);
			}
		}

	}

	@Override
	public int getID() {
		return 1;
	}

}
