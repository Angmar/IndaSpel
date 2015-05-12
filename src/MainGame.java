import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends BasicGameState {
	
	Image background;
	Rectangle selectRect;

	boolean test;
	static int minerals;
	
	float cameraX;
	float cameraY;
	
	float mouseX = -1;
	float mouseY = -1;
	
	static ArrayList<Building> resources;
	static ArrayList<Building> buildings;
	static ArrayList<Character> colonists;
	static ArrayList<Character> enemies;
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
		
		resources = new ArrayList<Building>();
		buildings = new ArrayList<Building>();
		colonists = new ArrayList<Character>();
		enemies = new ArrayList<Character>();
		selected = new ArrayList<GameObject>();
		
		buildings.add(new CommandCenter(400,400));
		colonists.add(new Worker(300,200));
		colonists.add(new Worker(300,300));
		colonists.add(new Worker(300,400));
		colonists.add(new Fighter(500,400));
		resources.add(new MineralOre(710,320));
		resources.add(new MineralOre(730,350));
		resources.add(new MineralOre(720,400));
		resources.add(new MineralOre(700,450));
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
			mouseX = input.getMouseX() - cameraX; // Store mouse position when starting rectangle
			mouseY = input.getMouseY() - cameraY;
			selectRect = new Rectangle(mouseX, mouseY, 1, 1);
		} else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (mouseX != -1 && mouseY != -1) // Store rectangle size for drawing
				selectRect.setBounds(Math.min(input.getMouseX() - cameraX, mouseX), Math.min(input.getMouseY() - cameraY, mouseY), Math.abs(input.getMouseX() - cameraX - mouseX), Math.abs(input.getMouseY() - cameraY - mouseY));
		} else if (mouseX != -1 && mouseY != -1 && !input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			for (GameObject gob : selected) {
				gob.deselect();
			}
			selected.clear();
			selectFromList(colonists); // Try to select colonists
			if (selected.isEmpty()) // If that fails (if there aren't any colonists in the select box)
				selectFromList(buildings); // Try to select buildings
			selectRect = null;
			mouseX = -1;
			mouseY = -1;
		}
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			for(GameObject sel : selected){
				if(sel.getClass() != MineralOre.class){
				GameObject target = mouseTarget(buildings, input.getMouseX(), input.getMouseY());
				if(target == null){
					target = mouseTarget(colonists, input.getMouseX(), input.getMouseY());
					if(target == null){
						target = mouseTarget(enemies, input.getMouseX(), input.getMouseY());
					}
				}
				if(target != null){
					sel.setTarget(target);
				}
				else {
					sel.setTarget(new Vector2f(input.getMouseX()-cameraX, input.getMouseY()-cameraY));
				}
				}
			}
		}
		
		updateList(resources, container, delta);
		updateList(buildings, container, delta);
		updateList(colonists, container, delta);
		updateList(enemies, container, delta);
	}
	
	private void selectFromList(ArrayList<? extends GameObject> list) {
		for(GameObject gob : list){
			gob.deselect();
			selected.remove(gob);
			if (selectRect.intersects(gob.getRect())) {
				gob.select();
				selected.add(gob);
			}
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
				

		
		//Translates the coordinates of view, must be first in render
		g.translate(cameraX, cameraY);

		if (selectRect != null)
			g.draw(selectRect);
		//g.drawString("Press SPACE to go to main menu", 400, 200);
		renderList(resources, container, g);
		renderList(buildings, container, g);
		renderList(colonists, container, g);
		renderList(enemies, container, g);

		//Translate back so hud will be rendered on top
		g.translate(-cameraX,-cameraY);
		g.drawString("Minerals: "+minerals, 850, 50);

		if (!(selected.isEmpty())) {
			g.setColor(Color.black);
			g.fill(new Rectangle(container.getWidth()-300, container.getHeight()-100, 100, 300));
			g.setColor(Color.white);
			g.draw(new Rectangle(container.getWidth()-300, container.getHeight()-100, 100, 100));
			g.drawImage(selected.get(0).portrait.getScaledCopy(100, 100), container.getWidth()-300, container.getHeight()-100);
		}

	}
	
	private void updateList(ArrayList<? extends GameObject> gameList, GameContainer container, int delta) throws SlickException{
		if(!gameList.isEmpty()){
			for(Iterator<GameObject> iter = (Iterator<GameObject>) gameList.iterator(); iter.hasNext(); ){
				GameObject gob = (GameObject) iter.next();
				if(gob.isAlive()){
					gob.update(container, delta);
				}
				else{
					iter.remove();
				}
			}
		}
	}
	
	private void renderList(ArrayList<? extends GameObject> characterList, GameContainer container, Graphics g) throws SlickException{
		if(!characterList.isEmpty()){
			for(GameObject gob : characterList){
				gob.render(container, g);
			}
		}
	}
	
	private GameObject mouseTarget(ArrayList<? extends GameObject> list, float mouseX, float mouseY){
		if(!list.isEmpty()){
			for(GameObject gob : list){
				if(isPointingAt(gob, mouseX, mouseY)){
					return gob;
				}
			}
		}
		return null;
	}


	private boolean isPointingAt(GameObject gob, float mouseX, float mouseY){
		if(mouseX-cameraX > gob.getX()-gob.getWidth()/2 && mouseX-cameraX < gob.getX()+gob.getWidth()/2 &&
				mouseY-cameraY > gob.getY()-gob.getHeight()/2 && mouseY-cameraY < gob.getY()+gob.getHeight()/2){
			return true;
		}
		
		return false;
	}
	
	public static CommandCenter nearestCommandCenter(float charX, float charY){
		CommandCenter nearestCC = null;
		
		for(Building comc : buildings){
			if(comc.getClass() == CommandCenter.class){
				if(nearestCC == null){
					nearestCC = (CommandCenter) comc;
				}
				else if(comc.targetDistance(charX, charY) < nearestCC.targetDistance(charX, charY)){
					nearestCC = (CommandCenter) comc;
				}
			}
		}
		return nearestCC;		
	}

	@Override
	public int getID() {
		return 1;
	}

	public static Character nearestColonist(float charX, float charY){
		Character nearestCol = null;	
		for(Character col : colonists){
			if(nearestCol != null){
				if(col.targetDistance(charX, charY) < nearestCol.targetDistance(charX, charY)){
					nearestCol = col;
				}
			}
			else{
				nearestCol = col;
			}
		}
		return nearestCol;
	}

}
