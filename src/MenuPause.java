import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MenuPause extends BasicGameState {
	
	int selectedOption;
	String[] menuOptions;
	Image selectArrow;

	public MenuPause() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		selectedOption = 0;
		menuOptions = new String[]{"Continue Game", "Save Game", "Return to Main Menu"};
		
		selectArrow = MenuMain.getSelectImage();
	}
	

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			switch(selectedOption){
			case 0:
				input.clearKeyPressedRecord();
				game.enterState(1);
				break;
			case 1:
				try {
					File file = new File("savefile.txt");
		 
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		 
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					
					MainGame.saveGame(bw);
					
					bw.close();
				} catch (IOException e) {
					System.err.println("Could not create savfile.txt");
				}
				break;
			case 2:
				input.clearKeyPressedRecord();
				game.enterState(0);
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
		
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*menuOptions.length;
		
		for(int i = 0; i < menuOptions.length; i++){
			g.drawString(menuOptions[i], xText, yText+(50*i));
		}
		
		g.drawImage(selectArrow, xText-50, yText-10+(50*selectedOption));
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
