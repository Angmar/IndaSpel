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
	Rectangle hudbg;

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

	static final int FIELDSIZE = 10000;
	
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
		colonists.add(new Fighter(500,400, 1));
		
		enemies.add(new Pirate(11000,12000, 2));
		enemies.add(new Pirate(11000,11000, 2));
		enemies.add(new Pirate(11000,10000, 2));
		enemies.add(new Pirate(11000,11500, 2));
		enemies.add(new Pirate(11000,13000, 2));
		
		resources.add(new MineralOre(710,320));
		resources.add(new MineralOre(730,350));
		resources.add(new MineralOre(720,400));
		resources.add(new MineralOre(700,450));
		
		hudbg = new Rectangle(0, container.getHeight()-container.getHeight()/4, container.getWidth(), container.getHeight()/4);
	}
	

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		updateList(resources, container, delta);
		updateList(buildings, container, delta);
		updateList(colonists, container, delta);
		updateList(enemies, container, delta);
		
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
			mouseX = input.getMouseX();
			mouseY = input.getMouseY();
			if (hudbg.contains(mouseX, mouseY)) { //Check if interface was pressed
				if ((new Rectangle(0, container.getHeight()-hudbg.getHeight(), hudbg.getHeight(), hudbg.getHeight())).contains(mouseX, mouseY))
					return;
				if ((!(selected.isEmpty())) && selected.get(0) instanceof Builder) {
					Builder b = (Builder) selected.get(0);
					for (int i=0;i<b.getBuildOptions().size();i++) {
						if ((new Rectangle(container.getWidth()-container.getWidth()/5-(container.getWidth()/5-hudbg.getHeight()), container.getHeight()-hudbg.getHeight()+i*hudbg.getHeight()/3, hudbg.getHeight()*2, hudbg.getHeight()/3)).contains(mouseX,  mouseY)) {
							b.queueSpawn(i);
							mouseX = -1;
							mouseY = -1;
						}
					}
				}
				mouseX = -1;
				mouseY = -1;
				return;
			}
			mouseX -= cameraX; // Store mouse position when starting rectangle
			mouseY -= cameraY;
			selectRect = new Rectangle(mouseX, mouseY, 1, 1);
		} else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (mouseX != -1 && mouseY != -1 && selectRect != null) // Store rectangle size for drawing
				selectRect.setBounds(Math.min(input.getMouseX() - cameraX, mouseX), Math.min(input.getMouseY() - cameraY, mouseY), Math.abs(input.getMouseX() - cameraX - mouseX), Math.abs(input.getMouseY() - cameraY - mouseY));
			else if ((new Rectangle(0, container.getHeight()-hudbg.getHeight(), hudbg.getHeight(), hudbg.getHeight())).contains(input.getMouseX(), input.getMouseY())) {
				cameraX = -(MainGame.FIELDSIZE*input.getMouseX()/hudbg.getHeight())+container.getWidth()/2;
				cameraY = -((MainGame.FIELDSIZE*(input.getMouseY()-container.getHeight()+hudbg.getHeight())/hudbg.getHeight()))+container.getHeight()/2;
				mouseX = -1;
				mouseY = -1;
			}
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
			float mouseX = input.getMouseX();
			float mouseY = input.getMouseY();
			if (hudbg.contains(input.getMouseX(), input.getMouseY())) {
				if (input.getMouseX() < hudbg.getHeight()) {
					mouseX = mapToRealCord(mouseX);
					mouseY = mapToRealCord(mouseY-3*container.getHeight()/4);
				}
				else 
					return;
			} else {
				mouseX -= cameraX;
				mouseY -= cameraY;
			}
			for(GameObject sel : selected){
				
				GameObject target = mouseTarget(enemies, input.getMouseX(), input.getMouseY()); 
				if(target == null){
					target = mouseTarget(buildings, input.getMouseX(), input.getMouseY());
					if(target == null){
						target = mouseTarget(resources, input.getMouseX(), input.getMouseY());
						if(target == null){
							target = mouseTarget(colonists, input.getMouseX(), input.getMouseY());
						}
					}
				}
				if(target != null){
					sel.setTarget(target);
				}
				else{
					sel.setTarget(new Vector2f(mouseX, mouseY));
				}
			}
		}
	}

	private float mapToRealCord(float cord) {
		float r = MainGame.FIELDSIZE*cord/hudbg.getHeight();
		return r;
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
		

		drawHud(container, g);

		renderListOnMap(resources, container, g);
		renderListOnMap(buildings, container, g);
		renderListOnMap(colonists, container, g);
		renderListOnMap(enemies, container, g);
		
	}
	
	private void drawHud(GameContainer container, Graphics g) {
		float hudHeight = hudbg.getHeight();
		float width = hudbg.getWidth()/5;
		g.setColor(Color.black);
		g.fill(hudbg);
		g.setColor(Color.white);
		g.draw(hudbg);
		if (!(selected.isEmpty())) {
			g.draw(new Rectangle(container.getWidth()-width*2, container.getHeight()-hudHeight, hudHeight, hudHeight));
			g.drawImage(selected.get(0).portrait.getScaledCopy((float)hudHeight/selected.get(0).portrait.getHeight()), container.getWidth()-width*2, container.getHeight()-hudHeight);
			if (selected.get(0) instanceof Builder) {
				Builder b = (Builder) selected.get(0);
				ArrayList<String> buildOptions = b.getBuildOptions();
				for (int i=0;i<buildOptions.size();i++) {
					g.draw(new Rectangle(container.getWidth()-width-(width-hudHeight), container.getHeight()-hudHeight+i*hudHeight/3, hudHeight*2, hudHeight/3));
					g.drawString(buildOptions.get(i) + "  cost:" + b.getBuildCosts()[i], container.getWidth()-width, container.getHeight()-hudHeight+hudHeight/8+i*hudHeight/3);
				}
				if (!(b.getBuildQueue().isEmpty())) {
					g.setColor(Color.red);
					g.fill(new Rectangle(hudHeight, container.getHeight()-hudHeight+hudHeight/16, width*b.getProgress()/b.getBuildTime()[b.getBuildQueue().get(0)], 3));
					g.setColor(Color.white);
				}
				for (int i=0;i<b.getBuildQueue().size();i++) {
					g.draw(new Rectangle(hudHeight, container.getHeight()-hudHeight+i*hudHeight/3, width, hudHeight/3));
					g.drawString(b.getBuildOptions().get(b.getBuildQueue().get(i)), hudHeight+hudHeight/8, container.getHeight()-hudHeight+hudHeight/8+i*hudHeight/3);
				}	
			}
		}
		Rectangle map = new Rectangle(0, container.getHeight()-hudHeight, hudHeight, hudHeight);
		Rectangle currentPos = new Rectangle(cameraX*-hudHeight/MainGame.FIELDSIZE, container.getHeight()-hudHeight+cameraY*-hudHeight/MainGame.FIELDSIZE, hudHeight*container.getWidth()/MainGame.FIELDSIZE, hudHeight*container.getHeight()/MainGame.FIELDSIZE);
		g.draw(map);
		g.draw(currentPos);		
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
	
	private void renderListOnMap(ArrayList<? extends GameObject> characterList, GameContainer container, Graphics g) throws SlickException{
		if(!characterList.isEmpty()){
			for(GameObject gob : characterList){
				gob.drawOnMap(container, g, cameraX, cameraY);
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

}
