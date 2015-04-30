import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
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
	static int minerals;
	static ArrayList<GameObject> colonists;
	static ArrayList<GameObject> selected;

	public MainGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		minerals = 0;
		colonists = new ArrayList<GameObject>();
		selected = new ArrayList<GameObject>();
		
		colonists.add(new CommandCenter(400,400));
		colonists.add(new Worker(300,300));
		colonists.add(new MineralOre(700,400));
		
		
	}
	

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			game.enterState(0);
		}
		else if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			for(GameObject gob : colonists){
				if(isPointingAt(gob, input.getMouseX(), input.getMouseY())){
					gob.select();
					selected.add(gob);
				}
				else{
					gob.deselect();
				}
			}
		}
		else if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			for(GameObject sel : selected){
				sel.setTarget(new Vector2f(input.getMouseX(), input.getMouseY()));
			}
		}

		if(!colonists.isEmpty()){
			for(GameObject gob : colonists){
				gob.update(container, input, delta);
			}
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Press SPACE to go to main menu", 400, 200);

		if(!colonists.isEmpty()){
			for(GameObject gob : colonists){
				gob.render(container, g);
			}
		}
	}
	
	private boolean isPointingAt(GameObject gob, float mouseX, float mouseY){
		if(mouseX > gob.getX() && mouseX < gob.getX()+gob.getWidth() &&
				mouseY > gob.getY() && mouseY < gob.getY()+gob.getHeight()){
			return true;
		}
		
		return false;
	}

	@Override
	public int getID() {
		return 1;
	}

}
