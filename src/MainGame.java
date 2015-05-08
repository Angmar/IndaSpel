import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends BasicGameState {
	
	Image background;
	Rectangle selectRect;

	boolean test;
	static int minerals;
	
	float cameraX;
	float cameraY;
	
	float mouseX;
	float mouseY;
	
	
	static ArrayList<GameObject> colonists;
	static ArrayList<GameObject> selected;

	public MainGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		cameraX = 0;
		cameraY = 0;
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
		
		if(input.isKeyDown(Input.KEY_DOWN)){
			cameraY -= delta*0.5;
		}
		if(input.isKeyDown(Input.KEY_UP)){
			cameraY += delta*0.5;
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			cameraX += delta*0.5;
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			cameraX -= delta*0.5;
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			game.enterState(0);
		}
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {	
			mouseX = input.getMouseX(); // Store mouse position when starting rectangle
			mouseY = input.getMouseY();
		} else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (mouseX != -1 && mouseY != -1) // Store rectangle size for drawing
				selectRect = new Rectangle(Math.min(input.getMouseX(), mouseX), Math.min(input.getMouseY(), mouseY), Math.abs(input.getMouseX() - mouseX), Math.abs(input.getMouseY() - mouseY));
		} else if (mouseX != -1 && mouseY != -1 && !input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			// Calculate and store higher and lower bounds of rectangle
			float higherBoundsX = Math.max(input.getMouseX(), mouseX);
			float lowerBoundsX = Math.min(input.getMouseX(), mouseX); 
			float higherBoundsY = Math.max(input.getMouseY(), mouseY);
			float lowerBoundsY = Math.min(input.getMouseY(), mouseY);
			for(GameObject gob : colonists){
				gob.deselect();
				selected.remove(gob);
				if (gob.getX() - lowerBoundsX < higherBoundsX - lowerBoundsX && gob.getY() - lowerBoundsY < higherBoundsY - lowerBoundsY && lowerBoundsX < gob.getX() && lowerBoundsY < gob.getY()) {
					gob.select();
					selected.add(gob);
				}
			}
			selectRect = null;
			mouseX = -1;
			mouseY = -1;
		}
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			for(GameObject sel : selected){
				sel.setTarget(new Vector2f(input.getMouseX()-cameraX, input.getMouseY()-cameraY));
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
		if (selectRect != null)
			g.draw(selectRect);
		//Translates the coordinates of view, must be first in render
		g.translate(cameraX, cameraY);

		//g.drawString("Press SPACE to go to main menu", 400, 200);

		if(!colonists.isEmpty()){
			for(GameObject gob : colonists){
				gob.render(container, g);
			}
		}
	}
	
	private boolean isPointingAt(GameObject gob, float mouseX, float mouseY){
		if(mouseX-cameraX > gob.getX() && mouseX-cameraX < gob.getX()+gob.getWidth() &&
				mouseY-cameraY > gob.getY() && mouseY-cameraY < gob.getY()+gob.getHeight()){
			return true;
		}
		
		return false;
	}

	@Override
	public int getID() {
		return 1;
	}

}
