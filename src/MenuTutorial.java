import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MenuTutorial extends BasicGameState {
	
	ArrayList<Image> tutorialImage; 
	ArrayList<String> tutorialText;
	ArrayList<String> tutorialTitle;
	
	int selectedOption;
	int showTutorial;
	Image selectArrow;
	TrueTypeFont fontTitle;

	public MenuTutorial() {
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		selectedOption = 0;
		showTutorial = 0;
		selectArrow = MenuMain.getSelectImage();
		fontTitle = StartGame.generateTitleFont(20, this);

		tutorialImage = new ArrayList<Image>();
		tutorialText = new ArrayList<String>();
		tutorialTitle = new ArrayList<String>();
		
		tutorialImage.add(new Image("movepoint.png").getScaledCopy(200, 200));
		tutorialTitle.add("Basic Controls");
		tutorialText.add("You select units and buildings by left clicking\n"
						+ "on them. You can also select multiple units by\n"
						+ "a box around them.\n"
						+ "To order your units to interact or go somewhere,\n"
						+ "simply right click at what you want them to\n"
						+ "interract with or where you want them to go to.\n\n"
						+ "You control the camera with the arrow keys. \n"
						+ "But you can also click on the minimap if you want\n"
						+ "to jump to any area on the map directly.");
		
		tutorialImage.add(new Image("commandcenter.png").getScaledCopy(200, 200));
		tutorialTitle.add("Command Center");
		tutorialText.add("The Command Center is you base of operation. \n"
						+ "It's where you produce and Workers that \n"
						+ "gather reosurces and then return them to \n"
						+ "the Command Center to be stored.\n\n"
						+ "To produce more Workers, simply click in the\n"
						+ "button readin 'Workers' at the right-bottom\n"
						+ "whenever you got a Command Center selected.");
		
		tutorialImage.add(new Image("worker.png").getScaledCopy(200, 200));
		tutorialTitle.add("Worker");
		tutorialText.add("Workers are the backbone of your expidition. \n"
						+ "They are the ones that gather resources, \n"
						+ "build new buildings such as new Command Centers \n"
						+ "and Factories. \n\n"
						+ "To have them gather resources, simply right click \n"
						+ "on a resource when you have your Worker selected. \n"
						+ "They will also start building at the spot when you \n"
						+ "order them to build.\n"
						+ "So be careful to have the worker be exactly where \n"
						+ "you want to place the building.");
		
		tutorialImage.add(new Image("mineral.png").getScaledCopy(200, 200));
		tutorialTitle.add("Mineral Ore");
		tutorialText.add("Minerals are the basic resource. Workers mine this\n"
						+ "and bring it back to the nearest Command Center. \n"
						+ "Always make sure to have Workers mine minerals as \n"
						+ "you'll need it for everything you make.");
		
		
		tutorialImage.add(new Image("factory.png").getScaledCopy(200, 200));
		tutorialTitle.add("Factory");
		tutorialText.add("Factories are where you produce your main military \n"
						+ "might to defend your expidition. \n\n"
						+ "You build units at the factory the same way you \n"
						+ "build Workers at a Command Center.");
		
		tutorialImage.add(new Image("fighter.png").getScaledCopy(160, 200));
		tutorialTitle.add("Fighter");
		tutorialText.add("Fighters are you basic grunts. They are fast, but \n"
						+ "not very durable. Perfect for going quickly \n"
					    + "intercepting enemy forces.");
		
		tutorialImage.add(new Image("tank.png").getScaledCopy(168, 200));
		tutorialTitle.add("Tank");
		tutorialText.add("Tanks are slow, but have a a lot of health and thus\n"
					    + "is hard to kill. These units are what you want to \n"
					    + "keep in the front row of your army.");
		
		tutorialImage.add(new Image("sniper.png").getScaledCopy(173, 200));
		tutorialTitle.add("Sniper");
		tutorialText.add("Snipers are the long range glass canons. They can \n"
						+ "target and hit enemies at long range. But their \n"
						+ "slow speed and low health makes them easy targets \n"
						+ "once the enemy is close.");
		
		tutorialImage.add(new Image("pirate.png").getScaledCopy(169, 200));
		tutorialTitle.add("Pirate");
		tutorialText.add("Pirates are the scum of the galaxy. They lack proper\n"
						+ "ship hardware and most often make up for it by sheer\n"
						+ "numbers. They prey on anyone they think hold valuable\n"
						+ "cargo.");
		
		
		//Must be last
		tutorialTitle.add("Return");
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(selectedOption == tutorialTitle.size()-1){
				input.clearKeyPressedRecord();
				game.enterState(0);
			}
			else{
				showTutorial = selectedOption;
			}
		}
		else if(input.isKeyPressed(Input.KEY_UP) && selectedOption > 0){
			selectedOption--;
		}
		else if(input.isKeyPressed(Input.KEY_DOWN) && selectedOption < tutorialTitle.size()-1){
			selectedOption++;
		}
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		float xText = container.getWidth()/4;
		float yText = container.getHeight()/2-25*tutorialTitle.size();
		
		g.drawImage(selectArrow, xText-50, yText-6+(50*selectedOption));
		
		g.drawImage(tutorialImage.get(showTutorial), container.getWidth()/2, container.getHeight()/2-tutorialImage.get(showTutorial).getHeight()-50);
		
		g.drawString(tutorialText.get(showTutorial), container.getWidth()/2, container.getHeight()/2);
		
		g.setFont(fontTitle);
		for(int i = 0; i < tutorialTitle.size(); i++){
			g.drawString(tutorialTitle.get(i), xText, yText+(50*i));
		}
		
	}

	@Override
	public int getID() {
		return 4;
	}

}
